/**
 * @author Ricardo Medina
 * @date 09/07/2024
 * @description: For the parse to work, we need a token class and a token Type enumerator...
 */
package syntax;

public class Token {
    //Field to store the type of the token...
    private TokenType type;
    // field to store the value of the token...
    private String value;

    // Constructor to initialize the token with a type and value...
    public Token(TokenType type, String value){
        this.type = type;
        this.value = value;
    }

    //getter method to retrieve the type of the token...
    public TokenType getType(){
        return type;
    }

    //getter method to retrieve the value of the token...
    public String getValue(){
        return value;
    }
}