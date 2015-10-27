package exceptions;

public class ExecuteException extends RuntimeException {
	private static final long serialVersionUID = 8646186578446032981L;

	public ExecuteException(String message)
	{
		super(message);
	}
}
