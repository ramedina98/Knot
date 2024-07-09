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
    // read for input string...
    private StringReader reader;
    // current character being read...
    private int currentChar;

    // constructor to initialize the lexer with a StringReader...
    public Lexer(StringReader reader) {
        this.reader = reader;
        // read the first character...
        this.currentChar = readChar();
    }

    // method to read the next character from the reader...
    private int readChar() {
        try {
            return reader.read(); // read the next character
        } catch (IOException e) {
            return -1; // End of stream
        }
    }

    // method to tokenize the input string...
    public List<Token> tokenize() {
        // list to hold tokens...
        List<Token> tokens = new ArrayList<>();

        // continue until end of input...
        while (currentChar != -1) {
            // Check if current character is a letter
            if (Character.isLetter(currentChar)) {
                // Read an identifier or type token
                tokens.add(readIdentifierOrType());
            } else if (currentChar == ':') {// Check if current character is a colon
                // Add a colon token
                tokens.add(new Token(TokenType.COLON, ":"));
                // Read the next character
                currentChar = readChar();
            } else if (currentChar == '=') { // Check if current character is an equals sign
                // Add an equals token
                tokens.add(new Token(TokenType.EQUALS, "="));
                // Read the next character
                currentChar = readChar();
            } else if (Character.isDigit(currentChar)) {// Check if current character is a digit
                // Read a number token
                tokens.add(readNumber());
            } else if (currentChar == '.') { // Check if current character is a dot
                // Add a dot token
                tokens.add(new Token(TokenType.DOT, "."));
                // Read the next character
                currentChar = readChar();
            } else {// Skip whitespace and any other unrecognized characters
                // read the next character...
                currentChar = readChar();
            }
        }

        // return the list of tokens...
        return tokens;
    }

    // Method to read an identifier or type token
    private Token readIdentifierOrType() {
        // StringBuilder to build the token value
        StringBuilder sb = new StringBuilder();

        // Continue while the character is a letter or digit
        while (Character.isLetterOrDigit(currentChar)) {
            // Append the character to the StringBuilder
            sb.append((char) currentChar);
            // Read the next character
            currentChar = readChar();
        }

        // Convert the StringBuilder to a string
        String value = sb.toString();

        // Check if the value is a predefined type
        if (value.equals("Number") || value.equals("String") || value.equals("Boolean")) {
            return new Token(TokenType.TYPE, value);// Return a type token
        }

        // Return an identifier token
        return new Token(TokenType.IDENTIFIER, value);
    }

    // Method to read a number token
    private Token readNumber() {
        // StringBuilder to build the token value
        StringBuilder sb = new StringBuilder();

        // Continue while the character is a digit
        while (Character.isDigit(currentChar)) {
            // Append the character to the StringBuilder
            sb.append((char) currentChar);
            // Read the next character
            currentChar = readChar();
        }

        // Return a number token
        return new Token(TokenType.NUMBER, sb.toString());
    }
}
