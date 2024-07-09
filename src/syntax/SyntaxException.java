/**
 * @author Ricardo Medina
 * @date 09/07/2024
 * @description: we define the syntax Exception exception to handles syntax errores...
 */
package syntax;

public class SyntaxException extends Exception{
    public SyntaxException(String message){
        super(message);
    }
}
