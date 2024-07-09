/**
 * @author Ricardo Medina
 * @date 09/07/2024
 * @description: this will be the code that will check the language syntax
 */
package syntax;
import java.util.List;

public class SyntaxAnalyzer {
    // A list of tokens to be analyzed
    private List<Token> tokens;
    // Index to track the current token being analyzed... 
    private int currentTokenIndex;

    // constructor to initilize the list of tokens and set the current token index to 0
    public SyntaxAnalyzer(List<Token> tokens){
        this.tokens = tokens;
        this.currentTokenIndex = 0;
    }

    //Method to start the syntax analysis
    public void analyze() throws SyntaxException{
        //loop through all tokens and parse each variable declaration
        while (currentTokenIndex < tokens.size()) {
            parseVariableDeclaration(); 
        }
    }

    // method to parse a variable declaration...
    private void parseVariableDeclaration() throws SyntaxException{
        //Expect an identifier token...
        Token identifier = expect(TokenType.IDENTIFIER); 
        expect(TokenType.COLON); // Expect a colon token
        Token type = expect(TokenType.TYPE); // Expect a type token
        expect(TokenType.EQUALS); // Expect an equals token
        Token value = expect(TokenType.NUMBER); // Expect a number token (assuming only numbers for simplicity)
        expect(TokenType.DOT); // Expect a dot token

        // Perform semantic validation on the parsed type and value
        validateType(type, value);
    }

    // method to ensure the next token is of the expected type...
    private Token expect(TokenType expectedType) throws SyntaxException{
        //check if the current token index is beyond the list of tokens...
        if(currentTokenIndex >= tokens.size()){
            throw new SyntaxException("Unexpected end of input"); 
        }
        //get the current token...
        Token token = tokens.get(currentTokenIndex); // get the current token...
        // check if the current token is of the expected type...
        if(token.getType() != expectedType){
            throw new SyntaxException("Expected " + expectedType + " but found " + token.getType());
        }
        currentTokenIndex++; //move to the next token...
        return token;
    }

    //method to validate the type of a token...
    private void validateType(Token type, Token value) throws SyntaxException{
        //TODO: Revisar bien esta parte, deveria de funcionar tambien con texto y con booleanos...
        //check if the type is "Number" and the value is not a number
        if(type.getValue().equals("Number") && !isNumber(value.getValue())){
            throw new SyntaxException("Type mismatch: expected Number, got " + value.getValue());
        }
        // FIXME: Aquí se puede agregar más validaciones...
    }

    //Method to check if a string value is a valid number
    private boolean isNumber(String value){
        try {
            //try to parse the string as an integer...
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            // if parsing fails, return false...
            return false;
        }
    }
}
