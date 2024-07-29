/**
 * @author Riaro Abraham
 * @date 09/07/2024
 * @description: The Lexer class is responsible for lexical analysis, converting a sequence of characters from a
 * source code input into a list of tokens. Tokens are the smallest units of meaning (such as keywords, operators,
 * identifiers, and literals) that the syntax analyzer can process.
 */
package lexer;

// imports...
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import syntax.Token;
import syntax.TokenType;

public class Lexer {
    // StringReader to read input string
    private StringReader reader;
    // Current character being processed
    private int currentChar;

    // Constructor to initialize StringReader and start reading the first character
    public Lexer(StringReader reader) {
        this.reader = reader;
        this.currentChar = readChar();  // Initialize currentChar with the first character
    }

    // Method to read the next character from the input StringReader
    private int readChar() {
        try {
            return reader.read();   // Read and return the next character's ASCII value
        } catch (IOException e) {
            return -1; // End of stream, return -1 when no more characters to read
        }
    }

    // Method to tokenize the input and return a list of tokens
    public List<Token> tokenize() {
        // List to store tokens
        List<Token> tokens = new ArrayList<>();

        // Continue until end of input stream is reached
        while (currentChar != -1) {
            // Check if current character is a letter
            if (Character.isLetter(currentChar)) {
                // Read identifier, type, or boolean token
                tokens.add(readIdentifierOrTypeOrBoolean());
            } else if (currentChar == ':') {// Check if current character is a colon
                // Colon token
                tokens.add(new Token(TokenType.COLON, ":"));
                // Read next character
                currentChar = readChar();
            } else if (currentChar == '=') {// Check if current character is an equals sign
                tokens.add(readEqualsOrCondition());
            } else if (Character.isDigit(currentChar)) { // Check if current character is a digit
                // Read number token
                tokens.add(readNumber());
            } else if (currentChar == '.') {// Check if current character is a dot
                // Dot token
                tokens.add(new Token(TokenType.DOT, "."));
                // Read next character
                currentChar = readChar();
            } else if (currentChar == '*') {
                // Read string literal token
                tokens.add(readStringLiteral());
            }
            /**
             * This are require to tokenizing control structures
             */
            else if(currentChar == '{'){
                tokens.add(new Token(TokenType.LEFT_BRACE, "{"));
                currentChar = readChar();
            } else if(currentChar == '}'){
                tokens.add(new Token(TokenType.RIGHT_BRACE, "}"));
                currentChar = readChar();
            } else if(currentChar == '('){
                tokens.add(new Token(TokenType.LEFT_PAREN, "("));
                currentChar = readChar();
            } else if(currentChar == ')'){
                tokens.add(new Token(TokenType.RIGHT_PAREN, ")"));
                currentChar = readChar();
            } else if(currentChar == '<'){
                tokens.add(readLessOrCondition());
            } else if(currentChar == '>'){
                tokens.add(readGreaterOrCondition());
            } else if(currentChar == ';'){
                tokens.add(new Token(TokenType.SEMICOLON, ";"));
                currentChar = readChar();
            }else if(currentChar == '+'){
                tokens.add(readPlusOrIncrement());
            } else if(currentChar == '-'){
                tokens.add(readMinusOrCondition());
            } else if(currentChar == '#'){
                tokens.add(new Token(TokenType.MULTIPLY, "#"));
                currentChar = readChar();
            } else if(currentChar == '/'){
                tokens.add(new Token(TokenType.DIVIDE, "/"));
                currentChar = readChar();
            } else {
                // Skip whitespace and other characters not explicitly handled
                currentChar = readChar();  // Read next character
            }
        }
        return tokens;  // Return the list of tokens
    }

    // Method to read an identifier, type, or boolean token
    private Token readIdentifierOrTypeOrBoolean() {
        // StringBuilder to accumulate characters
        StringBuilder sb = new StringBuilder();

        // While character is letter or digit
        while (Character.isLetterOrDigit(currentChar)) {
            // Append current character to StringBuilder
            sb.append((char) currentChar);
            // Read next character
            currentChar = readChar();
        }
        // Convert StringBuilder to String
        String value = sb.toString();

        // Check if the value is a predefined type
        if (value.equals("Number") || value.equals("Text") || value.equals("Bool")) {
            return new Token(TokenType.TYPE, value);  // Return type token
        }

        // Check if the value is a boolean literal
        if (value.equals("True") || value.equals("False")) {
            // If the value matches either "True" or "False", it is a boolean literal.
            // Create a new Token object with the type TokenType.BOOLEAN.
            // The token's value will be the string value ("True" or "False").
            return new Token(TokenType.BOOLEAN, value);
            // Return the newly created Token to the caller.
        }

        switch(value){
            case "Slip":
                return new Token(TokenType.SLIP, value);
            case "Knot":
                return new Token(TokenType.KNOT, value);
            case "SlipKnot":
                return new Token(TokenType.SLIPKNOT, value);
            case "Circle":
                return new Token(TokenType.CIRCLE, value);
            case "EverythingEnds":
                return new Token(TokenType.EVERYTHINGENDS, value);
            case "Duality":
                return new Token(TokenType.DUALITY, value);
            default:
                return new Token(TokenType.IDENTIFIER, value);
        }
    }

    // This method helps us to indetify if the token type is a condition or an equals...
    private Token readEqualsOrCondition(){
        currentChar = readChar();
        if(currentChar == '='){
            currentChar = readChar();
            return new Token(TokenType.CONDITION, "==");
        } else{
            return new Token(TokenType.EQUALS, "=");
        }
    }

    // this method helps us to identify if the token is a less or a condition...
    private Token readLessOrCondition(){
        currentChar = readChar();
        if(currentChar == '='){
            currentChar = readChar();
            return new Token(TokenType.CONDITION, "<=");
        } else{
            return new Token(TokenType.LESS_THAN, "<");
        }
    }

    // this method helps us to identify if the token is a greater than or a condition...
    private Token readGreaterOrCondition(){
        currentChar = readChar();
        if(currentChar == '='){
            currentChar = readChar();
            return new Token(TokenType.CONDITION, ">=");
        } else{
            return new Token(TokenType.GREATER_THAN, ">");
        }
    }

    // this method helps us to identify if the token is a plus or a increment...
    private Token readPlusOrIncrement(){
        currentChar = readChar();
        if(currentChar == '+'){
            currentChar = readChar();
            return new Token(TokenType.OPERATION, "++");
        } else if(currentChar == '='){
            currentChar = readChar();
            return new Token(TokenType.OPERATION, "+=");
        } else{
            return new Token(TokenType.PLUS, "+");
        }
    }

    // this method helps us to minus or condition...
    private Token readMinusOrCondition(){
        currentChar = readChar();
        if(currentChar == '='){
            currentChar = readChar();
            return new Token(TokenType.CONDITION, "-=");
        } else{
            return new Token(TokenType.MINUS, "-");
        }
    }

    // Method to read a number token
    private Token readNumber() {
        // StringBuilder to accumulate digits
        StringBuilder sb = new StringBuilder();

        // While character is a digit
        while (Character.isDigit(currentChar)) {
            // Append current character to StringBuilder
            sb.append((char) currentChar);
            // Read next character
            currentChar = readChar();
        }

        // Return number token
        return new Token(TokenType.NUMBER, sb.toString());
    }

    // Method to read a string literal token enclosed in quotes
    private Token readStringLiteral() {
        // Store the opening quote character
        char startType = (char) currentChar;
        // StringBuilder to accumulate characters
        StringBuilder sb = new StringBuilder();
        // Read next character, skip the opening quote
        currentChar = readChar();

        // While not closing quote or end of input
        while (currentChar != startType && currentChar != -1) {
            // Append current character to StringBuilder
            sb.append((char) currentChar);
            // Read next character
            currentChar = readChar();
        }
        // Read next character, skip the closing quote
        currentChar = readChar();

        // Return string literal token
        return new Token(TokenType.STRING, sb.toString());
    }
}