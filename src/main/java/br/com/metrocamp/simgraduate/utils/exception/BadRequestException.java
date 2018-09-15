package br.com.metrocamp.simgraduate.utils.exception;

public class BadRequestException extends RestException{

  public BadRequestException() {
    this("Request invalida!");
  }

  public BadRequestException(String message) {
    super(message);
  }
}
