package exceptions;

public class FrontendException extends RuntimeException {
	private static final long serialVersionUID = -1994194538675153788L;

	public FrontendException(String message)
	{
		super(message);
	}
}

