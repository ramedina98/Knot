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

            SyntaxAnalyzer syntaxAnalyzer = new SyntaxAnalyzer(tokens);
            try {
                syntaxAnalyzer.analyze();
                System.out.println("Syntax is correct.");

                // Simular la reasignación de valor para la variable "uno"
                if (code.startsWith("uno")) {
                    // Crear una nueva entrada de tokens para reasignación
                    String reassignCode = "uno : Number = 2 .";
                    System.out.println("Reassigning value: " + reassignCode);

                    // Tokenizar y analizar la reasignación
                    Lexer reassignLexer = new Lexer(new StringReader(reassignCode));
                    List<Token> reassignTokens = reassignLexer.tokenize();
                    SyntaxAnalyzer reassignAnalyzer = new SyntaxAnalyzer(reassignTokens);

                    reassignAnalyzer.analyze();  // Ejecutar el análisis de reasignación
                    System.out.println("Value reassigned successfully.");
                    syntaxAnalyzer.printVariableTable();
                }

            } catch (SyntaxException e) {
                System.err.println("Syntax error: " + e.getMessage());
            }
            System.out.println();
        }
    }
}
