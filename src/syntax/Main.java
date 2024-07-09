// NOTE: este main solo nos servira para hacer pruebas...
package syntax;

import java.io.StringReader;
import java.util.List;

import lexer.Lexer;

public class Main {
    public static void main(String[] args) {
        String code = "NumeroInicial: Number = 100000.";
        Lexer lexer = new Lexer(new StringReader(code));
        List<Token> tokens = lexer.tokenize();

        SyntaxAnalyzer syntaxAnalyzer = new SyntaxAnalyzer(tokens);
        try {
            syntaxAnalyzer.analyze();
            System.out.println("Syntax is correct.");
        } catch (SyntaxException e) {
            System.err.println("Syntax error: " + e.getMessage());
        }
    }
}
