package com.sikaseal.bank.kata.domain.exception;

/** Thrown when a deposit on a savings account would exceed its configured deposit ceiling. */
public class DepositCeilingExceededException extends RuntimeException {

  public DepositCeilingExceededException(String message) {
    super(message);
  }
}
