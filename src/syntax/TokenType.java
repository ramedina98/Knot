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
    DOT,         // Represents a dot token
    STRING,  // token type for string literals
    BOOLEAN, // token type for boolean values
    TEXT     // token type for text (can be same as TYPE)
}