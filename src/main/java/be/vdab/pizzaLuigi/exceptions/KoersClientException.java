package be.vdab.pizzaLuigi.exceptions;

public class KoersClientException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public KoersClientException(String message) {
		super(message);
	}
}
