// NOTE: este main solo nos servira para hacer pruebas...
package syntax;

import java.io.StringReader;
import java.util.List;

import lexer.Lexer;

/**
 * Seguir trabajando en:
 * 1. Qué se pueda reasignar el valor a una variable...
 * 2. Decalaración sin inicialización...
 */
public class Main {
    public static void main(String[] args) {
        String[] codes = {
            "uno : Number = 14567 .",         // Caso 1: Declaración de variable Number
            "variable : Text = *hola* .",    // Caso 2: Declaración de variable Text con string literal
            "variable : Text = *chart*.",    // Caso 3: Declaración de variable Text con chart (debe dar error)
            "variable : Bool = True ."       // Caso 4: Declaración de variable Bool
        };

        for (String code : codes) {
            System.out.println("Testing code: " + code);
            Lexer lexer = new Lexer(new StringReader(code));
            List<Token> tokens = lexer.tokenize();

            SyntaxAnalyzerVariables syntaxAnalyzer = new SyntaxAnalyzerVariables(tokens);
            try {
                syntaxAnalyzer.analyze();
                System.out.println("Syntax is correct.");

            } catch (SyntaxException e) {
                System.err.println("Syntax error: " + e.getMessage());
            }
            System.out.println();
        }

        String[] codes1 = {
            "Slip (0 >= 1) { a = b + 1;}",
            "Knot { a = b - 1; }",
            "SlipKnot (0 >= 1) { a= b+1;}",
            "Circle (a <= 10) { a = a + 1;}",
            "EverythingEnds (0, 10, 1,) { sum = sum + 10;}",
        };

        for (String code : codes1) {
            System.out.println("Testing code: " + code);
            Lexer lexer = new Lexer(new StringReader(code));
            List<Token> tokens = lexer.tokenize();

            SyntaxAnalyzerControlStructures syntaxAnalyzer = new SyntaxAnalyzerControlStructures(tokens);
            try {
                syntaxAnalyzer.analyze();
                System.out.println("Syntax is correct.");
            } catch (SyntaxException e) {
                System.err.println("Syntax error: " + e.getMessage());
            }
            System.out.println();
        }
    }
}
