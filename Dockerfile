# ==============================================================================
# STAGE 1 : Compilation (Builder)
# Objectif : Télécharger les dépendances en cache, injecter le secret et compiler
# ==============================================================================
FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /sikaseal

COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline

COPY src/ src/
# -- Version AVEC secret --
# RUN --mount=type=secret,id=TOKEN,env=TOKEN \
#     ./mvnw clean package -DskipTests

# -- Version SANS secret --
RUN ./mvnw clean package -DskipTests

# ==============================================================================
# STAGE 2 : Extraction (Extractor)
# Objectif : Découper le Fat JAR Spring Boot généré à l'étape 1 en layers optimisés
# ==============================================================================
FROM eclipse-temurin:21-jre-alpine AS extractor
WORKDIR /sikaseal

COPY --from=builder /sikaseal/target/*.jar application.jar
RUN java -Djarmode=layertools -jar application.jar extract

# ==============================================================================
# STAGE 3 : Image finale (Runtime)
# Objectif : Créer l'image de production sécurisée, minimale et découpée en couches
# ==============================================================================
FROM eclipse-temurin:21-jre-alpine
WORKDIR /sikaseal

RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

COPY --from=extractor /sikaseal/dependencies/ ./
COPY --from=extractor /sikaseal/spring-boot-loader/ ./
COPY --from=extractor /sikaseal/snapshot-dependencies/ ./
COPY --from=extractor /sikaseal/application/ ./

EXPOSE 8080

ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]