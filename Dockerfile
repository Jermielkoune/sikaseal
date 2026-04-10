# ==============================================================================
# STAGE 1 : Compilation (Builder)
# Objectif : Télécharger les dépendances en cache, injecter le secret et compiler
# ==============================================================================
FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /sikaseal

COPY .mvn/ .mvn/
COPY mvnw pom.xml sonar-project.properties ./

# Copier aussi les pom.xml des modules pour maximiser le cache Maven en multi-modules
COPY domain/pom.xml domain/pom.xml
COPY exposition/pom.xml exposition/pom.xml
COPY infrastructure/pom.xml infrastructure/pom.xml
COPY launcher/pom.xml launcher/pom.xml

# Re-copie du parent POM après les modules (workaround cache/buildkit)
COPY pom.xml ./

RUN chmod +x mvnw

# (Note) En multi-modules, `dependency:go-offline` peut échouer tant que Maven ne peut pas
# résoudre correctement le réacteur. On privilégie donc un build (qui téléchargera les deps)
# après avoir copié les sources.

# Copier le code source des modules (structure réelle du repo)
COPY domain/src/ domain/src/
COPY exposition/src/ exposition/src/
COPY infrastructure/src/ infrastructure/src/
COPY launcher/src/ launcher/src/

# Build du jar exécutable (Spring Boot) du module launcher uniquement
RUN ./mvnw -q -DskipTests -pl launcher -am package

# ==============================================================================
# STAGE 2 : Extraction (Extractor)
# ==============================================================================
FROM eclipse-temurin:21-jre-alpine AS extractor
WORKDIR /sikaseal

# Le jar est produit dans launcher/target (pas target/ à la racine)
COPY --from=builder /sikaseal/launcher/target/*.jar application.jar
RUN java -Djarmode=layertools -jar application.jar extract

# ==============================================================================
# STAGE 3 : Image finale (Runtime)
# ==============================================================================
FROM eclipse-temurin:21-jre-alpine
WORKDIR /sikaseal

RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

COPY --from=extractor /sikaseal/dependencies/ ./
COPY --from=extractor /sikaseal/spring-boot-loader/ ./
COPY --from=extractor /sikaseal/snapshot-dependencies/ ./
COPY --from=extractor /sikaseal/application/ ./

# Ton app écoute sur 8082 (cf. launcher/src/main/resources/application.yml)
EXPOSE 8082

ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]