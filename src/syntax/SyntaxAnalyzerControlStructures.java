/**
 * @author Ricardo Medina
 * @date 28/07/2024
 * @description: The purpose of this parser is to help us verify the syntax of the control structures...
 */

package syntax;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SyntaxAnalyzerControlStructures {
    // attributes of class Control structures analyzer
    // List of tokens to be analyzed
    private List<Token> tokens;
    // Index of the current token being processed
    private int currentTokenIndex;
    // a map to store variable names and their types..
    private Map<String, Token> variableTable;

    //Constructor...
    public SyntaxAnalyzerControlStructures(List<Token> tokens){
        // Initialize tokens
        this.tokens = tokens;
        // Start at the beginning of the token list
        this.currentTokenIndex = 0;
        this.variableTable = new HashMap<>();
    }

    //All the require methods...

    // Method to start analyzing tokens...
    public void analyze() throws SyntaxException {
        // Continue analyzing tokens until the end of the list is reached..
        while (currentTokenIndex < tokens.size()){
            //
            parseControlStructure();
        }
    }

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
            case DUALITY:
                parseSwitchStatement();
                break;
            default:
                throw new SyntaxException("Unexpected token:" + nextToken);
        }
    }

    //
    private void parseIfStatement() throws SyntaxException {
        expect(TokenType.SLIP);
        expect(TokenType.LEFT_PAREN);
        expect(TokenType.RIGHT_PAREN);
        expect(TokenType.LEFT_BRACE);
        parseBlock();
        expect(TokenType.RIGHT_BRACE);
    }

    private void parseElseStatement() throws SyntaxException {
        expect(TokenType.KNOT);
        expect(TokenType.LEFT_BRACE);
        parseBlock();
        expect(TokenType.RIGHT_BRACE);
    }

    private void parseElseIfStatement() throws SyntaxException {
        expect(TokenType.SLIPKNOT);
        expect(TokenType.LEFT_PAREN);
        Token condition = expectCondition();
        expect(TokenType.RIGHT_PAREN);
        expect(TokenType.LEFT_BRACE);
        parseBlock();
        expect(TokenType.RIGHT_BRACE);
    }

    private void parseWhileLoop() throws SyntaxException {
        expect(TokenType.CIRCLE);
        expect(TokenType.LEFT_PAREN);
        Token condition = expectCondition();
        expect(TokenType.RIGHT_PAREN);
        expect(TokenType.LEFT_BRACE);
        parseBlock();
        expect(TokenType.RIGHT_BRACE);
    }

    private void parseForLoop() throws SyntaxException {
        expect(TokenType.EVERYTHINGENDS);
        expect(TokenType.LEFT_PAREN);
        Token parameters = expect(TokenType.PARAMETERS);
        expect(TokenType.RIGHT_PAREN);
        expect(TokenType.LEFT_BRACE);
        parseBlock();
        expect(TokenType.RIGHT_BRACE);
    }

    private void parseSwitchStatement() throws SyntaxException {
        expect(TokenType.DUALITY);
        expect(TokenType.LEFT_PAREN);
        Token variable = expect(TokenType.IDENTIFIER);
        expect(TokenType.RIGHT_PAREN);
        expect(TokenType.LEFT_BRACE);
        while (currentTokenIndex < tokens.size() && tokens.get(currentTokenIndex).getType() != TokenType.LEFT_BRACE) {
            expect(TokenType.COLON);
            Token condition = expectCondition();
            expect(TokenType.RIGHT_BRACE);
            parseBlock();
        }
        expect(TokenType.LEFT_BRACE);
    }

    private Token expect(TokenType expectedType) throws SyntaxException {
        if (currentTokenIndex >= tokens.size()) {
            throw new SyntaxException("Unexpected end of input");
        }

        Token token = tokens.get(currentTokenIndex);
        if (token.getType() != expectedType) {
            throw new SyntaxException("Expected " + expectedType + " but found " + token.getType());
        }
        currentTokenIndex++;
        return token;
    }

    private Token expectCondition() throws SyntaxException {
        return expect(TokenType.CONDITION);
    }

    private Token expectOperation() throws SyntaxException {
        return expect(TokenType.OPERATION);
    }

    private void validateCondition(Token condition) throws SyntaxException {
        String value = condition.getValue();
        if (!(value.equals("==") || value.equals("!=") || value.equals(">") || value.equals("<") || value.equals(">=") || value.equals("<=") || isBoolean(value))) {
            throw new SyntaxException("Invalid condition: " + value);
        }
    }

    private void validateOperation(Token operation) throws SyntaxException {
        OperationValidator.validate(operation, tokens, currentTokenIndex);
    }

    private void validateParameters(Token parameters) throws SyntaxException {
        String[] params = parameters.getValue().split(",");
        if (params.length != 3) {
            throw new SyntaxException("Invalid parameters for EverythingEnds: " + parameters.getValue());
        }
        try {
            Integer.parseInt(params[0].trim());
            Integer.parseInt(params[1].trim());
            Integer.parseInt(params[2].trim());
        } catch (NumberFormatException e) {
            throw new SyntaxException("Invalid parameter values for EverythingEnds: " + parameters.getValue());
        }
    }

    private boolean isBoolean(String value) {
        return value.equals("True") || value.equals("False");
    }

    private void parseBlock() throws SyntaxException {
        while (currentTokenIndex < tokens.size() && tokens.get(currentTokenIndex).getType() != TokenType.LEFT_BRACE) {
            parseControlStructure();
        }
    }

}
