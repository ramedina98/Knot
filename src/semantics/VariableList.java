package semantics;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VariableList {
    // Expresiones regulares para la validación
    private static final String TEXT_DECLARATION_REGEX = "^[a-zA-Z_][a-zA-Z0-9_]*\\s*:\\s*Text;$";
    private static final String TEXT_INITIALIZATION_REGEX = "^[a-zA-Z_][a-zA-Z0-9_]*\\s*:\\s*Text\\s*=\\s*(\".*\"|'.');$";
    private static final String NUMBER_DECLARATION_REGEX = "^[a-zA-Z_][a-zA-Z0-9_]*\\s*:\\s*Number;$";
    private static final String NUMBER_INITIALIZATION_REGEX = "^[a-zA-Z_][a-zA-Z0-9_]*\\s*:\\s*Number\\s*=\\s*\\d+(\\.\\d+)?;$";
    private static final String BOOL_DECLARATION_REGEX = "^[a-zA-Z_][a-zA-Z0-9_]*\\s*:\\s*Bool;$";
    private static final String BOOL_INITIALIZATION_REGEX = "^[a-zA-Z_][a-zA-Z0-9_]*\\s*:\\s*Bool\\s*=\\s*(true|false);$";

    // Métodos para validar cada tipo de declaración e inicialización
    public static boolean validateTextDeclaration(String input) {
        return input.matches(TEXT_DECLARATION_REGEX);
    }

    public static boolean validateTextInitialization(String input) {
        return input.matches(TEXT_INITIALIZATION_REGEX);
    }

    public static boolean validateNumberDeclaration(String input) {
        return input.matches(NUMBER_DECLARATION_REGEX);
    }

    public static boolean validateNumberInitialization(String input) {
        return input.matches(NUMBER_INITIALIZATION_REGEX);
    }

    public static boolean validateBoolDeclaration(String input) {
        return input.matches(BOOL_DECLARATION_REGEX);
    }

    public static boolean validateBoolInitialization(String input) {
        return input.matches(BOOL_INITIALIZATION_REGEX);
    }

    public static void main(String[] args) {
        // Ejemplos de uso
        String[] testCases = {
            "variable : Text;",
            "variable : Text = \"string\";",
            "variable : Text = 'c';",
            "variable : Number;",
            "variable : Number = 3;",
            "variable : Number = 3.14;",
            "variable : Bool;",
            "variable : Bool = true;",
            "variable : Bool = false;"
        };

        for (String testCase : testCases) {
            if (validateTextDeclaration(testCase)) {
                System.out.println(testCase + " is a valid Text declaration.");
            } else if (validateTextInitialization(testCase)) {
                System.out.println(testCase + " is a valid Text initialization.");
            } else if (validateNumberDeclaration(testCase)) {
                System.out.println(testCase + " is a valid Number declaration.");
            } else if (validateNumberInitialization(testCase)) {
                System.out.println(testCase + " is a valid Number initialization.");
            } else if (validateBoolDeclaration(testCase)) {
                System.out.println(testCase + " is a valid Bool declaration.");
            } else if (validateBoolInitialization(testCase)) {
                System.out.println(testCase + " is a valid Bool initialization.");
            } else {
                System.out.println(testCase + " is invalid.");
            }
        }
    }
}

