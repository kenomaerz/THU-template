/**
 * 
 */
package error;

/**
 * This exception is thrown if a model class has an illegal state.
 * This needs to be handled, as it could potentially corrupt data on the server.
 * 
 * @author Keno März
 */
public class IllegalModelStateException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public IllegalModelStateException(String reason) {
		super(reason);
	}

}
