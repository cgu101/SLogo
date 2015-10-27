/**
 * 
 */
package exceptions;

/**
 * @author loganrooper
 *
 */
public class WontImplementException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public WontImplementException(){
    	super("We chose not to implement this.");
    }
    public WontImplementException(String claass){
    	super("We chose not to implment: " + claass);
    }
}