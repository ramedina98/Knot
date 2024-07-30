/**
 * @author Ricardo Medina
 * @date 09/07/2024
 * @description: this will be the code that will check the language syntax
 * This is to analize the syntax of the variables...
 */
package syntax;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class SyntaxAnalyzerVariables {
    // List of tokens to be analyzed
    private List<Token> tokens;
    // Index of the current token being processed
    private int currentTokenIndex;
    // a map to store variable names and their types..
    private Map<String, Token> variableTable;

    // Constructor to initialize with a list of tokens
    public SyntaxAnalyzerVariables(List<Token> tokens) {
        // Initialize tokens
        this.tokens = tokens;
        // Start at the beginning of the token list
        this.currentTokenIndex = 0;
        this.variableTable = new HashMap<>();
    }

    // Method to start analyzing tokens
    public void analyze() throws SyntaxException {
        // Continue analyzing tokens until the end of the list is reached
        while (currentTokenIndex < tokens.size()) {
            parseStatement();  // Parse and validate variable declarations
        }
    }

    // method to parse statements in the syntax analyzer...
    private void parseStatement() throws SyntaxException {
        Token nextToken = tokens.get(currentTokenIndex); // Get the next token to analyze

        // Check if the next token is an identifier
        if (nextToken.getType() == TokenType.IDENTIFIER) {
            String identifier = nextToken.getValue(); // Get the value of the identifier

            // Check if the identifier already exists in the variables map
            if (variableTable.containsKey(identifier)) {
                // Update the value of the existing variable
                expect(TokenType.IDENTIFIER); // Consume the identifier token
                expect(TokenType.EQUALS);    // Expect equals sign for assignment
                Token newValue = expectValue(); // Get the new value token
                variableTable.put(identifier, newValue); // Update variable in the map
            } else {
                // Parse a new variable declaration
                parseVariableDeclaration();
            }
        } else {
            // Throw an exception if the expected token is not an identifier
            throw new SyntaxException("Unexpected token: " + nextToken);
        }
    }

    // Method to parse a variable declaration
    private void parseVariableDeclaration() throws SyntaxException {
        // Expect an identifier token
        Token identifier = expect(TokenType.IDENTIFIER);
        // Expect a colon token
        expect(TokenType.COLON);
        // Expect a type token
        Token type = expect(TokenType.TYPE);
        // Expect an equals token
        expect(TokenType.EQUALS);

        Token value;
        // Depending on the type, expect a specific token type for the value
        if (type.getValue().equals("Number")) {
            // Expect a number token
            value = expect(TokenType.NUMBER);
        } else if (type.getValue().equals("Text")) {
            // Expect a string token
            value = expect(TokenType.STRING);
        } else if (type.getValue().equals("Bool")) {
            // Expect a boolean token
            value = expect(TokenType.BOOLEAN);
        } else {
            // Error for unexpected type
            throw new SyntaxException("Unexpected type: " + type.getValue());
        }

        // store the variable in the variable table...
        variableTable.put(identifier.getValue(), value);

        // Perform semantic validation based on the type and value
        validateType(type, value);
    }

    // Method to expect a token of a specific type
    private Token expect(TokenType expectedType) throws SyntaxException {
        // Check if there are tokens left to process
        if (currentTokenIndex >= tokens.size()) {
            // Error if no more tokens
            throw new SyntaxException("Unexpected end of input");
        }

        // Get the current token
        Token token = tokens.get(currentTokenIndex);

        // Check if the current token matches the expected type
        if (token.getType() != expectedType) {
            // Error for type mismatch
            throw new SyntaxException("Expected " + expectedType + " but found " + token.getType());
        }
        // Move to the next token
        currentTokenIndex++;

        // Return the expected token
        return token;
    }

    // method to retrieves the current token and advence to the nect one.
    private Token expectValue() throws SyntaxException {
        // Check if there are tokens remaining to process
        if (currentTokenIndex >= tokens.size()) {
            throw new SyntaxException("Unexpected end of input");
        }

        // Retrieve the current token and move to the next one
        return tokens.get(currentTokenIndex++);
    }

    // Method to validate if the value matches the expected type
    private void validateType(Token type, Token value) throws SyntaxException {
        switch (type.getValue()) {
            case "Number":
                // Check if the value is a valid number
                if (!isNumber(value.getValue())) {
                    throw new SyntaxException("Type mismatch: expected Number, got " + value.getValue());
                }
                break;
            case "Text":
                // Check if the value is a non-empty string
                if (!isString(value.getValue())) {
                    throw new SyntaxException("Type mismatch: expected Text, got " + value.getValue());
                }
                break;
            case "Bool":
                // Check if the value is a boolean (True or False)
                if (!isBoolean(value.getValue())) {
                    throw new SyntaxException("Type mismatch: expected Bool, got " + value.getValue());
                }
                break;
            default:
                // Error for unknown type
                throw new SyntaxException("Unknown type: " + type.getValue());
        }
    }

    // Method to check if a string represents a valid number
    private boolean isNumber(String value) {
        try {
            // Try parsing the value as an integer
            Integer.parseInt(value);
            // Return true if successful
            return true;
        } catch (NumberFormatException e) {
            // Return false if parsing fails
            return false;
        }
    }

    // Method to check if a string is non-empty (basic string validation)
    private boolean isString(String value) {
        // Return true if non-null and non-empty
        return value != null && !value.isEmpty();
    }

    // Method to check if a string represents a boolean (True or False)
    private boolean isBoolean(String value) {
        // Return true if matches "True" or "False"
        return value.equals("True") || value.equals("False");
    }

}
