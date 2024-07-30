/**
 * @author Ricardo Medina
 * @date 28/07/2024
 * @description: The purpose of this parser is to help us verify the syntax of the control structures...
 */

package syntax;

import java.util.List;

public class SyntaxAnalyzerControlStructures {
    // attributes of class Control structures analyzer
    // List of tokens to be analyzed
    private List<Token> tokens;
    // Index of the current token being processed
    private int currentTokenIndex;

    //Constructor...
    public SyntaxAnalyzerControlStructures(List<Token> tokens){
        // Initialize tokens
        this.tokens = tokens;
        // Start at the beginning of the token list
        this.currentTokenIndex = 0;
    }

    //All the require methods...

    // Method to start analyzing tokens...
    public void analyze() throws SyntaxException {
        // Continue analyzing tokens until the end of the list is reached..
        while (currentTokenIndex < tokens.size()){
            parseControlStructure();
        }
    }

    // here we have all the methods to analyze all the control structures that we need...
    private void parseControlStructure() throws SyntaxException {
        Token nextToken = tokens.get(currentTokenIndex);

        switch (nextToken.getType()) {
            case SLIP:
                parseIfStatement();
                break;
            case KNOT:
                parseElseStatement();
                break;
            case SLIPKNOT:
                parseElseIfStatement();
                break;
            case CIRCLE:
                parseWhileLoop();
                break;
            case EVERYTHINGENDS:
                parseForLoop();
                break;
            case SHOW:
                parseShow();
                break;
            default:
                throw new SyntaxException("Unexpected token:" + nextToken);
        }
    }

    // slip = if...
    private void parseIfStatement() throws SyntaxException {
        expect(TokenType.SLIP);
        expect(TokenType.LEFT_PAREN);
        parseCondition();
        expect(TokenType.RIGHT_PAREN);
        expect(TokenType.LEFT_BRACE);
        parseBlock();
        expect(TokenType.RIGHT_BRACE);
    }

    // Knot = else...
    private void parseElseStatement() throws SyntaxException {
        expect(TokenType.KNOT);
        expect(TokenType.LEFT_BRACE);
        parseBlock();
        expect(TokenType.RIGHT_BRACE);
    }

    // SlipKnot = Else if
    private void parseElseIfStatement() throws SyntaxException {
        expect(TokenType.SLIPKNOT);
        expect(TokenType.LEFT_PAREN);
        parseCondition();
        expect(TokenType.RIGHT_PAREN);
        expect(TokenType.LEFT_BRACE);
        parseBlock();
        expect(TokenType.RIGHT_BRACE);
    }

    // circle = while
    private void parseWhileLoop() throws SyntaxException {
        expect(TokenType.CIRCLE);
        expect(TokenType.LEFT_PAREN);
        parseCondition();
        expect(TokenType.RIGHT_PAREN);
        expect(TokenType.LEFT_BRACE);
        parseBlock();
        expect(TokenType.RIGHT_BRACE);
    }

    //EverythingEnds = For...
    private void parseForLoop() throws SyntaxException {
        expect(TokenType.EVERYTHINGENDS);
        expect(TokenType.LEFT_PAREN);
        expect(TokenType.NUMBER);
        parseParameters();
        expect(TokenType.NUMBER);
        parseParameters();
        expect(TokenType.NUMBER);
        parseParameters();
        expect(TokenType.RIGHT_PAREN);
        expect(TokenType.LEFT_BRACE);
        parseBlock();
        expect(TokenType.RIGHT_BRACE);
    }

    // Show = System.out.println();
    private void parseShow() throws SyntaxException {
        expect(TokenType.SHOW);
        expect(TokenType.LEFT_BRACE);
        parseShowAssignment();
        expect(TokenType.RIGHT_BRACE);
        expect(TokenType.DOT);
    }

    /**
     * This method expects the next token in the token list to be of the specified type.
     * If the current token does not match the expected type, a SyntaxException is thrown.
     *
     * @param expectedType The type of the token that is expected.
     * @return The token if it matches the expected type.
     * @throws SyntaxException If the current token does not match the expected type or if the end of input is reached.
     */
    private Token expect(TokenType expectedType) throws SyntaxException {
        // Check if the current token index is out of bounds (i.e., end of the token list).
        if (currentTokenIndex >= tokens.size()) {
            throw new SyntaxException("Unexpected end of input");
        }

        // Retrieve the current token from the token list.
        Token token = tokens.get(currentTokenIndex);

        // Check if the type of the current token matches the expected type.
        if (token.getType() != expectedType) {
            throw new SyntaxException("Expected " + expectedType + " but found " + token.getType());
        }

        // Move to the next token in the list.
        currentTokenIndex++;

        // Return the current token.
        return token;
    }

    /**
     * This method parses a condition from the token list.
     * It expects a condition to be in the format of:
     * <NUMBER or IDENTIFIER> <CONDITION> <NUMBER or IDENTIFIER>
     *
     * @throws SyntaxException If the tokens do not match the expected format for a condition.
     */
    private void parseCondition() throws SyntaxException {
        // Retrieve the current token from the token list.
        Token token = tokens.get(currentTokenIndex);

        // Check if the token is either a NUMBER or IDENTIFIER.
        if (token.getType() == TokenType.NUMBER || token.getType() == TokenType.IDENTIFIER) {
            // Move to the next token in the list.
            currentTokenIndex++;

            // Retrieve the next token.
            Token condition = tokens.get(currentTokenIndex);

             // Check if the next token is a CONDITION.
            if (condition.getType() == TokenType.CONDITION) {
                // Move to the next token in the list.
                currentTokenIndex++;
                // Retrieve the token after the CONDITION.
                Token next = tokens.get(currentTokenIndex);
                 // Check if this token is either a NUMBER or IDENTIFIER.
                if (next.getType() == TokenType.NUMBER || next.getType() == TokenType.IDENTIFIER) {
                     // Move to the next token in the list.
                    currentTokenIndex++;
                } else {
                     // Throw a SyntaxException if the token is not a NUMBER or IDENTIFIER.
                    throw new SyntaxException("Expected NUMBER or IDENTIFIER but found " + next.getType());
                }
            } else {
                // Throw a SyntaxException if the token is not a CONDITION.
                throw new SyntaxException("Expected CONDITION but found " + condition.getType());
            }
        } else {
            // Throw a SyntaxException if the token is not a NUMBER or IDENTIFIER.
            throw new SyntaxException("Expected NUMBER or IDENTIFIER but found " + token.getType());
        }
    }

    /**
     * This method parses parameters from the token list.
     * It expects a parameter to be in the format of PARAMETERS or NUMBER.
     *
     * @throws SyntaxException If the token does not match the expected PARAMETERS or NUMBER format.
     */
    private void parseParameters() throws SyntaxException {
        // Retrieve the current token from the token list.
        Token token = tokens.get(currentTokenIndex);

        // Check if the token is either PARAMETERS or NUMBER.
        if (token.getType() == TokenType.PARAMETERS || token.getType() == TokenType.NUMBER) {
            // Move to the next token in the list.
            currentTokenIndex++;
        } else {
            // Throw a SyntaxException if the token is not PARAMETERS or NUMBER.
            throw new SyntaxException("Expected PARAMETERS but found " + token.getType());
        }
    }

    /**
     * This method parses a block of statements enclosed within braces.
     * It continues parsing statements until it encounters a RIGHT_BRACE token,
     * indicating the end of the block.
     *
     * @throws SyntaxException If an error occurs during the parsing of statements.
     */
    private void parseBlock() throws SyntaxException {
        // Continue parsing tokens while the end of the token list is not reached
        // and the current token is not a RIGHT_BRACE.
        while (currentTokenIndex < tokens.size() && tokens.get(currentTokenIndex).getType() != TokenType.RIGHT_BRACE) {
            // Parse the current statement.
            parseStatement();
        }
    }

    /**
     * This method parses an individual statement based on the type of the current token.
     * It determines the type of statement and invokes the appropriate parsing method.
     *
     * @throws SyntaxException If an unexpected token is encountered in the statement.
     */
    private void parseStatement() throws SyntaxException {
        // Get the next token from the list of tokens.
        Token nextToken = tokens.get(currentTokenIndex);

        // Determine the type of the statement based on the token type.
        switch (nextToken.getType()) {
            case IDENTIFIER:
                // If the token is an IDENTIFIER, parse it as an assignment statement.
                parseAssignment();
                break;
            case SLIP:
            case KNOT:
            case SLIPKNOT:
            case CIRCLE:
            case EVERYTHINGENDS:
            case SHOW:
                // If the token is a control structure keyword, parse it as a control structure.
                parseControlStructure();
                break;
            default:
                // If the token is not recognized, throw a syntax exception.
                throw new SyntaxException("Unexpected token in statement: " + nextToken);
        }
    }

    /**
     * This method parses an assignment statement, which includes handling
     * variable assignments and optional operations.
     *
     * @throws SyntaxException If the expected tokens for an assignment are not found.
     */
    private void parseAssignment() throws SyntaxException {
        expect(TokenType.IDENTIFIER); // Expect an identifier for the variable name
        expect(TokenType.EQUALS); // Expect an equals sign '='

        // Expect an identifier, number, string, or boolean (optional)
        if (currentTokenIndex < tokens.size() &&
            (tokens.get(currentTokenIndex).getType() == TokenType.IDENTIFIER ||
            tokens.get(currentTokenIndex).getType() == TokenType.NUMBER ||
            tokens.get(currentTokenIndex).getType() == TokenType.STRING ||
            tokens.get(currentTokenIndex).getType() == TokenType.BOOLEAN)) {
            currentTokenIndex++; // Move to the next token
        } else {
            throw new SyntaxException("Expected IDENTIFIER, NUMBER, STRING, or BOOLEAN but found: " + tokens.get(currentTokenIndex));
        }

        // Optionally handle an operation (plus, minus, multiply, divide)
        if (currentTokenIndex < tokens.size() &&
            (tokens.get(currentTokenIndex).getType() == TokenType.PLUS ||
            tokens.get(currentTokenIndex).getType() == TokenType.MINUS ||
            tokens.get(currentTokenIndex).getType() == TokenType.MULTIPLY ||
            tokens.get(currentTokenIndex).getType() == TokenType.DIVIDE)) {
            currentTokenIndex++; // Move to the next token
        }

        // Optionally handle another value (number, string, or boolean)
        if (currentTokenIndex < tokens.size() &&
            (tokens.get(currentTokenIndex).getType() == TokenType.NUMBER ||
            tokens.get(currentTokenIndex).getType() == TokenType.STRING ||
            tokens.get(currentTokenIndex).getType() == TokenType.BOOLEAN)) {
            currentTokenIndex++; // Move to the next token
        } else {
            throw new SyntaxException("Expected NUMBER, STRING, or BOOLEAN but found: " + tokens.get(currentTokenIndex));
        }

        // Expect a semicolon ';' to end the assignment statement.
        expect(TokenType.SEMICOLON);
    }

    /**
     * This method helps us to define how it should be what Show will contain between the brances...
     * @throws SyntaxException If an unexpected token is encountered in the statement.
     */
    private void parseShowAssignment() throws SyntaxException {
        // expect the first part of the concatenation (string or identifier)...
        expectStringOrIdentifier();

        // Loop to handle multiple concatenation...
        while(currentTokenIndex < tokens.size() && tokens.get(currentTokenIndex).getType() == TokenType.CONCAT){
            currentTokenIndex++; // Skip the &
            expectStringOrIdentifier();
        }
    }

    /**
     * Helper method to expect a string or identifier...
     * @throws SyntaxException if the next token is not a string or identifier...
     */
    private void expectStringOrIdentifier() throws SyntaxException {
        Token token = tokens.get(currentTokenIndex);
        if(token.getType() == TokenType.STRING || token.getType() == TokenType.IDENTIFIER || token.getType() == TokenType.NUMBER){
            currentTokenIndex++; // Move to the next token...
        } else{
            throw new SyntaxException("Expected String, Identifier, or Number but found: " + token.getType());
        }
    }
}
