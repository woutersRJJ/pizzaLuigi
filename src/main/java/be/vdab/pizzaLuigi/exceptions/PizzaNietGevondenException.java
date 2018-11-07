package be.vdab.pizzaLuigi.exceptions;

public class PizzaNietGevondenException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public PizzaNietGevondenException(String message) {
		super(message);
	}
}
