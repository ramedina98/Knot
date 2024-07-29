/**
 *@author Ricardo Medina
@date 09/07/2024
@description: this enum defines the type
 */
package syntax;

public enum TokenType {
    IDENTIFIER, // Represents an identifier token
    COLON,      // Represents a colon token
    TYPE,       // Represents a type token
    EQUALS,     // Represents an equals token
    NUMBER,     // Represents a number token
    DOT,       // Represents a dot token
    //this is to identify the variable's type...
    STRING,  // token type for string literals
    BOOLEAN, // token type for boolean values
    TEXT,     // token type for text (can be same as TYPE)
    LEFT_BRACE,
    RIGHT_BRACE,
    LEFT_PAREN,
    RIGHT_PAREN,
    LESS_THAN,
    GREATER_THAN,
    SEMICOLON,
    PLUS,
    MINUS,
    MULTIPLY,
    DIVIDE,
    /**
     * This is to identify the data structure's type...
    */
    CONTROL_STRUCTURE,
    CASE,
    BREAK,
    OPERATION,
    PARAMETERS,
    CONDITION,
    SLIP, // token type for slip (slip = if)...
    KNOT, // token type for knot (knot = else)..
    SLIPKNOT, // token type for slipknot (slipknot = else if)...
    CIRCLE, // token type for circle (circle = while)...
    EVERYTHINGENDS, // token type for everythingEnds (everythingEnds = for)...
    DUALITY, // token type for duality (duality = switch)...
}