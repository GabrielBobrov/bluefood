package br.com.softblue.bluefood.application.service;

@SuppressWarnings("serial")
public class ApplicattionServiceException extends RuntimeException {

	public ApplicattionServiceException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public ApplicattionServiceException(String message) {
		super(message);
		
	}

	public ApplicattionServiceException(Throwable cause) {
		super(cause);
		
	}

	
	
}
