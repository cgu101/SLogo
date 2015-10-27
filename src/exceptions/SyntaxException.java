package exceptions;

public class SyntaxException extends RuntimeException {
	private static final long serialVersionUID = -2334156867450322894L;

	public SyntaxException(String msg)	{
		super(msg);
	}
}
