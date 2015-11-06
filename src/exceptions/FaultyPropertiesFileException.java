// This entire file is part of my masterpiece.
// Connor Usry
package exceptions;

public class FaultyPropertiesFileException extends RuntimeException{
	private static final long serialVersionUID = -276253772487510701L;

	public FaultyPropertiesFileException(String msg) {
		super(msg);
	}

}
