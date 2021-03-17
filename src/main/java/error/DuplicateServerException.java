/**
 * 
 */
package error;

/**
 * This exception is thrown if multiple instances of the server object are constructed.
 * This would lead to multiple unnecessary connections. Reuse the same instance for all calls!
 * 
 * @author Keno März
 */
public class DuplicateServerException extends RuntimeException {
	
	public DuplicateServerException(String reason) {
		super(reason);
	}

}
