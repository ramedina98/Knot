# Knot Compiler

## Table of Contents
- [Introduction](#Introduction)
- [Purpose](#Purpose)
- [Variables](#Variables)
- [Data-Types](#data-typesData)
- [Control Structures](#control-structures)
- [Arithmetic Operations](#arithmetic-operations)
- [Console.log()](#consolelog)
- [Examples](#examples)
- [Syntax Analysis Description](#syntax-analysis-description)
- [Path to classes](#path-to-classes)
- [Detector Class](#detector-class)
- [Token Class](#token-class)
- [TokenType Enum](#tokentype-enum)
- [Semantic analysis](#semantic-analysis)
- [Semantics Class](#semantics-class)
- [Unit Test](#unit-test)
- [Integration Test](#integration-test)
- [System Test](#system-test)
- [Technologies used](#technologies-used)
- [IDE](#ide)
- [Development Team](#development-team)
- [Contact](#contact)

## Introduction
Knot is inspired by PSeint and takes its name from the nu metal band `Slipknot`. This project seeks to develop a pseudo-language with a simple and easy to use structure, but also powerful and typed. Knot's syntax is designed to be intuitive and comfortable for programmers, and its development is done in Java.

## Purpose
The purpose of this project, at present, is to present a viable product for further development in the future. For this reason, at this stage, the Knot pseudo-language only has three types of variables and four types of control structures. In the future, we plan to continue its development and create a more robust pseudo-language.

## Variables
In variable declarations, Knot is inspired by the way variables are declared in TypeScript. The format in Knot is as follows: **name** **:** **Type** **=** **value**.
### Data Types
The data types supported in Knot are:
- **Text:** This type includes String and Char.
    - Declaration: **variableName** : **Text** = `*String or char*`
- **Number:** This type includes int, float, and double.
    - Declaration: **variableName** : **Number** = `3`
- **Bool:** Abbreviation for boolean.
    - Declaration: **variableName** : **Bool** = `True`

## Control Structures
For any language or pseudo-language, having control structures is essential. Therefore, Knot includes the 4 basic ones: **if**, **else**, **while**, and **for**, as well as **switch**. The unique aspect is that their names are inspired by words or song titles related to the nu metal band Slipknot. The new names for the control structures are as follows:

- **If** = Slip
    - **Example:** Slip(comparison){operation}
- **Else** = Knot
    - **Example:** Knot(comparison){operation}
- **Else If** = SlipKnot
    - **Example:** SlipKnot(comparison){operation}
- **While** = Circle
    - **Example:** Circle(comparison){operation}
- **For** = EverythingEnds
    - **Example:** EverythingEnds(0, 10, 1){operation}
- **Switch** = Duality
    - **Example:** Duality(variable){act *condition*; operation zone Cut}

## Arithmetic Operations
The arithmetic operations currently supported by Knot are as follows:
- **Addition and Subtraction:**
    - **Examples:** a = b + c, a = b - c
- **Multiplication and Division:**
    - **Examples:** a = a#b, a = b / c
- **First and Second Degree Equations:**
    - **Example:** a = Math{*2 * x + 3 = 0*}
- **Iterators:**
    - **Examples:** a = i++, a += i / i++

## Console.log()
Its Equivalent -> Show{}
- **Text Declaration:** `Show{*Hello world*}`
- **Text and Numbers Declaration:** `Show{*Hello everyone, I have* & 28}`
- **Variable Declaration:** `Show{a}`

## Examples
- one : Number = 1456
- n : Text = `*hi*`
- variable : Text = `*chart*`
- variable : Bool = `True`
- b : Number = `0`
- a : Text = *todo va bien*
- EverythingEnds (0, 5, 2){b = b + 1}
- Slip(0==0){uno = uno + 1}

## Syntax Analysis Description

In the syntax analysis process of this code, a structured approach is implemented to verify the validity of each line of code, identifying whether it is a variable or a control structure.

The analysis begins with iterating over each line of code stored in the `lines` variable. For each line, an instance of the `Detector` class is created, which determines the line type using the `typeOfline()` method. This method defines whether the line represents a variable or a control structure.

If `typeOfline()` indicates that the line is a variable, the following procedure is followed:

- A message is printed indicating that the line is a variable.
- A `Lexer` is used to tokenize the line of code, converting it into a list of tokens representing the lexical elements of the code.
- The `SyntaxAnalyzerVariables` class is instantiated with the list of tokens.
- The `analyze()` method of `SyntaxAnalyzerVariables` is called to validate the syntax of the line. If the analysis is successful, it is confirmed with a message; otherwise, a syntax error message is captured and displayed.

If the line is not a variable and is identified as a control structure, a similar procedure is followed:

- A message is printed indicating that the line is a control structure.
- The line of code is tokenized using the `Lexer`.
- The `SyntaxAnalyzerControlStructures` class is instantiated with the list of tokens.
- The `analyze()` method of `SyntaxAnalyzerControlStructures` is called to check the syntax. If the analysis is correct, it is confirmed with a message; if there is an error, the corresponding message is shown.

This approach ensures that all lines of code are correctly evaluated, whether they represent variables or control structures, providing accurate feedback on their syntactic validity.

### Path to classes

Path to the classes mentioned in this section:

- **Lexer.java:** /src/lexer
- **SyntaxAnalyzerVariables.java:** /src/syntax
- **SyntaxAnalyzerControlStructures.java:** /src/syntax
- **Token.java:** /src/syntax
- **TokenType.java:** /src/syntax

The following code is part of the `process` function of the compiler's user interface.

```Java
for (String code : lines) {
    // initialize the detector class and pass its as a parameter the line in which we are going to...
    Detector detect = new Detector(code);

    // check if it is a variable, true means that the line is a variable...
    if(detect.typeOfline()){
        System.out.println("Variable\n");
        appendMenssages("Testing code: " + code);
        Lexer lexer = new Lexer(new StringReader(code));
        List<Token> tokens = lexer.tokenize();

        SyntaxAnalyzerVariables syntaxAnalyzer = new SyntaxAnalyzerVariables(tokens);
        try {
            syntaxAnalyzer.analyze();
            appendMenssages("Syntax is correct.");

        } catch (SyntaxException e) {
            appendMenssages("Syntax error: " + e.getMessage());
        }
    } else{ // if it is not a variable, is a control structure en we get into this...
        System.out.println("Control Structure\n");
        appendMenssages("Testing code: " + code);
        Lexer lexer = new Lexer(new StringReader(code));
        List<Token> tokens = lexer.tokenize();

        SyntaxAnalyzerControlStructures syntaxAnalyzer = new SyntaxAnalyzerControlStructures(tokens);
        try {
            syntaxAnalyzer.analyze();
            appendMenssages("Syntax is correct.");
        } catch (SyntaxException e) {
            appendMenssages("Syntax error: " + e.getMessage());
        }
    }
}
```

### Detector Class

The Detector class is designed to analyze and determine the type of a line of code, specifically to check whether the line represents a variable declaration or not.

Upon initialization, the class takes a line of code as input and stores it in the line attribute. The primary function of the Detector class is to assess whether this line matches a predefined pattern that indicates a variable declaration.

The typeOfline method performs this analysis by using a regular expression pattern defined in the PatternConstants interface. It first trims any leading or trailing whitespace from the line of code and then applies the pattern to this trimmed line.

The method uses a Matcher object to check if the line conforms to the variable pattern. If the line matches the pattern, indicating that it is a variable declaration, the method returns true. If the line does not match the pattern, it returns false, meaning the line does not represent a variable.

In summary, the Detector class acts as a tool to identify whether a given line of code corresponds to a variable declaration based on predefined patterns.

```Java
package detector;

import java.util.regex.Matcher;
import interfaces.PatternConstants;

public class Detector implements PatternConstants{
    private String line;

    public Detector(String str){
        this.line = str;
    }

    public boolean typeOfline(){
        String linea = this.line;
        // Matchers para cada linea..
        Matcher varMatcher = VAR_PATTERN.matcher(linea.trim());

        // if the type of the line is a variable, return true...
        if(varMatcher.matches()){
            return true;
        }
        // else: return false, is not a variable...
        return false;
    }
}
```

### Token Class

The `Token` class plays a crucial role in the parsing process of the compiler. It is designed to represent individual tokens that are generated during the lexical analysis phase. Each token is characterized by two main attributes: its type and its value. The `Token` class includes a field to store the type of the token, which is defined by the `TokenType` enum, and another field to store the token's string value.

The class has a constructor that initializes a token with its type and value. There are getter methods to retrieve both the type and value of the token. Additionally, the `toString()` method provides a string representation of the token, including its type and value, which is useful for debugging and logging purposes.

```Java
package syntax;

public class Token {
    //Field to store the type of the token...
    private TokenType type;
    // field to store the value of the token...
    private String value;

    // Constructor to initialize the token with a type and value...
    public Token(TokenType type, String value){
        this.type = type;
        this.value = value;
    }

    //getter method to retrieve the type of the token...
    public TokenType getType(){
        return type;
    }

    //getter method to retrieve the value of the token...
    public String getValue(){
        return value;
    }

    @Override
    public String toString(){
        return "Token{" +
                "type=" + type +
                ", value='" + value + '\'' +
                '}';
    }
}
```

### TokenType Enum

The `TokenType` enum defines a set of constants representing the different types of tokens recognized by the lexer. This enumeration provides a comprehensive list of token types, each corresponding to a specific syntactical element in the language.

The `TokenType` enum includes various constants such as `IDENTIFIER`, `COLON`, `TYPE`, `EQUALS`, and `NUMBER`, which represent different categories of tokens like identifiers, colons, types, equality signs, and numbers, respectively. It also includes constants for special symbols like `DOT`, `CONCAT`, and operators like `PLUS`, `MINUS`, `MULTIPLY`, and `DIVIDE`.

Furthermore, the enum defines tokens for control structures and language constructs, including `SLIP` (for `if` statements), `KNOT` (for `else`), `SLIPKNOT` (for `else if`), `CIRCLE` (for `while` loops), `EVERYTHINGENDS` (for `for` loops), and `SHOW` (for output statements). This enumeration is essential for the lexer to correctly classify and handle the various elements of the language's syntax.

```Java
package syntax;

public enum TokenType {
    IDENTIFIER, // Represents an identifier token
    COLON,      // Represents a colon token
    TYPE,       // Represents a type token
    EQUALS,     // Represents an equals token
    NUMBER,     // Represents a number token
    DOT,       // Represents a dot token
    CONCAT, // this is for the symbol that will hepl us to concatenate...
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
    SHOW, // token type for show (show = systme print out)
}
```
## Semantic analysis
The semantic analysis process begins with constructing a comprehensive text representation of the code from an array of strings. This is achieved using a StringBuilder to concatenate non-empty lines, ensuring that each line is properly formatted with a newline character.

Once the complete text is assembled, it is passed to an instance of the Semantic class for analysis. The parseText method of the Semantic class is responsible for parsing the text and extracting meaningful information about the code. This includes identifying variables and their associated properties.

After the parsing is complete, the process continues by retrieving the list of variables obtained from the analysis. Each variable's type, name, and value are then displayed in the console output. This step involves setting the text of a txtConsola component to show detailed information about each variable, including its type, name, and current value.

This approach ensures that the semantic aspects of the code, such as variable declarations and their attributes, are thoroughly examined and presented clearly for further inspection or debugging.

The following code is part of the `process` function of the compiler's user interface.

```Java
StringBuilder texto = new StringBuilder();

// Iterar sobre el array de cadenas y construir el texto completo
for (String linea : lines) {
    if (!linea.trim().isEmpty()) {
        texto.append(linea).append("\n");
    }
}

Semantic parser = new Semantic();
parser.parseText(texto.toString());

for (Variable var : parser.getVariables()) {
    txtConsola.setText("Tipo: " + var.getTipo() + ", Nombre: " + var.getNombre() + ", Valor: " + var.getValor());
}
```

### Semantics Class

```Java
package semantics;

import java.util.regex.*;

import interfaces.PatternConstants;
import utils.Variable;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class Semantic implements PatternConstants {
    private Map<String, Variable> variables;
    private StringBuilder output;

    public Semantic() {
        this.variables = new HashMap<>();
        this.output = new StringBuilder();
    }

    // Method to obtain the accumulated messages...
    public String getOutput() {
        return output.toString();
    }

    // Method to add message...
    private void appendOutput(String message) {
        output.append(message).append("\n");
    }

    public void parseText(String texto) {
        String[] lineas = texto.split("\\n");

        // Matchers para cada línea de código
        for (String linea : lineas) {
            Matcher varMatcher = VAR_PATTERN.matcher(linea.trim());
            Matcher ifMatcher = IF_PATTERN.matcher(linea.trim());
            Matcher elseIfMatcher = ELSE_IF_PATTERN.matcher(linea.trim());
            Matcher elseMatcher = ELSE_PATTERN.matcher(linea.trim());
            Matcher whileMatcher = WHILE_PATTERN.matcher(linea.trim());
            Matcher forMatcher = FOR_PATTERN.matcher(linea.trim());
            Matcher showMatcher = SHOW_PATTERN.matcher(linea.trim());

            // Declaración de variables
            if (varMatcher.matches()) {
                String nombre = varMatcher.group(1).trim();
                String tipo = varMatcher.group(2).trim();
                String valor = varMatcher.group(3).trim();

                // here the variable is storage...
                if (tipo.equals("Number")) {
                    try {
                        variables.put(nombre, new Variable(tipo, nombre, Double.parseDouble(valor)));
                        System.out.println("Variable Aceptada: " + nombre + " = " + valor);
                    } catch (NumberFormatException e) {
                        System.out.println("Error al convertir el valor de la variable: " + valor);
                    }
                } else if (tipo.equals("Text")) {
                    variables.put(nombre, new Variable(tipo, nombre, valor));
                    System.out.println("Variable Aceptada: " + nombre + " = " + valor);
                } else if (tipo.equals("Bool")) {
                    variables.put(nombre, new Variable(tipo, nombre, Boolean.parseBoolean(valor)));
                    System.out.println("Variable Aceptada: " + nombre + " = " + valor);
                }

            // Estructuras de control
            } else if (ifMatcher.matches()) {
                String condition = ifMatcher.group(1).trim();
                String operation = ifMatcher.group(2).trim();
                if (evaluateCondition(condition)) {
                    executeOperation(operation);
                }
            } else if (elseIfMatcher.matches()) {
                String condition = elseIfMatcher.group(1).trim();
                String operation = elseIfMatcher.group(2).trim();
                if (evaluateCondition(condition)) {
                    executeOperation(operation);
                }
            } else if (elseMatcher.matches()) {
                String operation = elseMatcher.group(1).trim();
                executeOperation(operation);

            } else if (whileMatcher.matches()) {
                String condition = whileMatcher.group(1).trim();
                String operation = whileMatcher.group(2).trim();
                while (evaluateCondition(condition)) {
                    executeOperation(operation);
                }

            } else if (forMatcher.matches()) {
                int start = Integer.parseInt(forMatcher.group(1).trim());
                int end = Integer.parseInt(forMatcher.group(2).trim());
                int increment = Integer.parseInt(forMatcher.group(3).trim());
                String operation = forMatcher.group(4).trim();
                for (int i = start; i <= end; i += increment) {
                    variables.put("TheEnd", new Variable("Number", "TheEnd", (double) i));
                    executeOperation(operation);
                }

            } else if (showMatcher.matches()) {
                String content = showMatcher.group(1).trim();
                executeShow(content);

            } else {
                appendOutput("Línea no reconocida: " + linea.trim());
                System.out.println("Línea no reconocida: " + linea.trim());
            }
        }
    }

    private double getValue(String operand) {
        if (variables.containsKey(operand)) {
            Variable var = variables.get(operand);
            return (double) var.getValor();
        }
        try {
            return Double.parseDouble(operand);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    private boolean evaluateCondition(String condition) {
        // Evaluar las condiciones booleanas y de comparación
        String[] parts = condition.split("\\s*(==|!=|>|<|>=|<=)\\s*");
        if (parts.length != 2) return false;

        String left = parts[0].trim();
        String right = parts[1].trim();
        String operator = condition.replaceAll("[A-Za-z0-9\\s]", "").trim();

        double leftValue = getValue(left);
        double rightValue = getValue(right);

        switch (operator) {
            case "==": return leftValue == rightValue;
            case "!=": return leftValue != rightValue;
            case ">": return leftValue > rightValue;
            case "<": return leftValue < rightValue;
            case ">=": return leftValue >= rightValue;
            case "<=": return leftValue <= rightValue;
            default: return false;
        }
    }

    /**
     * @author Ricardo Medina
     * @date: 30/07/2024
     * @description: This method helps us to extract the require items from the operation String...
     */
    private ArrayList<String> itemsSeparator(String operation) {
        ArrayList<String> array = new ArrayList<>();

        // Dividir la operación en variable y el resto de la operación
        String[] varAndRest = operation.split("=");
        if (varAndRest.length != 2) {
            return array;  // Devolver un array vacío si no hay un signo igual
        }

        String variable = varAndRest[0].trim();
        array.add("variable: " + variable);
        array.add("signo igual: =");

        // Dividir el resto de la operación en operandos y operador
        String rest = varAndRest[1].trim();
        String[] parts = rest.split("\\s*(\\+|\\-|#|\\/)\\s*");
        if (parts.length != 2) {
            return array;
        }

        String leftOperand = parts[0].trim();
        String rightOperand = parts[1].trim();
        String operator = rest.replaceAll("[^\\+\\-#/]", "").trim();

        array.add("operando izquierda: " + leftOperand);
        array.add("operador: " + operator);
        array.add("operando derecha: " + rightOperand);

        return array;
    }

    //
    public void executeOperation(String operation) {
        // Dividir la operación usando una expresión regular para identificar el operador
        ArrayList<String> separateItems = itemsSeparator(operation);

        String variable = separateItems.get(0).split(":")[1].trim();
        String leftOperand = separateItems.get(2).split(":")[1].trim();
        String operator = separateItems.get(3).split(":")[1].trim();
        String rightOperand = separateItems.get(4).split(":")[1].trim();

        if (!variables.containsKey(variable)) {
            appendOutput("La variable " + variable + " aun no existe.");
            System.out.println("La variable " + variable + " aun no existe.");
        }

        double leftValue = getValue(leftOperand);
        double rightValue = getValue(rightOperand);
        double result = 0.0;

        // Validate if operands are not numbers and are not existing variables
        if (leftValue == 0.0 && !leftOperand.equals("0") && !variables.containsKey(leftOperand)) {
            appendOutput("Error: El operando izquierdo " + leftOperand + " no es un número ni una variable existente.");
            System.out.println("Error: El operando izquierdo " + leftOperand + " no es un número ni una variable existente.");
            return;
        }
        if (rightValue == 0.0 && !rightOperand.equals("0") && !variables.containsKey(rightOperand)) {
            appendOutput("Error: El operando derecho " + rightOperand + " no es un número ni una variable existente.");
            System.out.println("Error: El operando derecho " + rightOperand + " no es un número ni una variable existente.");
            return;
        }

        switch (operator) {
            case "+": result = leftValue + rightValue; break;
            case "-": result = leftValue - rightValue; break;
            case "#": result = leftValue * rightValue; break;
            case "/": if (rightValue != 0) result = leftValue / rightValue; break;
            default:
                appendOutput("Operador no reconocido: " + operator);
                System.out.println("Operador no reconocido: " + operator);
                return;
        }

        variables.put(variable, new Variable("Number", variable, result));
        appendOutput("Resultado de operación: " + variable + " = " + result);
        System.out.println("Resultado de operación: " + variable + " = " + result);
    }

    public void executeShow(String content) {
        // Ejecutar operación Show
        String[] parts = content.split("&");
        StringBuilder output1 = new StringBuilder();

        for (String part : parts) {
            part = part.trim();
            if (part.startsWith("*") && part.endsWith("*")) {
                output.append(part.substring(1, part.length() - 1));
            } else if (variables.containsKey(part)) {
                output.append(variables.get(part).getValor());
            } else {
                output.append(part);
            }
        }

        appendOutput(output1.toString());
        System.out.println(output1.toString());
    }

    public List<Variable> getVariables() {
        return new ArrayList<>(variables.values());
    }
}
```

## Unit Test

## Integration Test

## System Test

## Technologies used
- Java <img src="https://brandslogos.com/wp-content/uploads/images/large/java-logo-1.png" width="50">
- Ant <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/2/2f/Apache-Ant-logo.svg/2560px-Apache-Ant-logo.svg.png" width="50">
- Swing <img src="https://www.qfs.de/fileadmin/Webdata/logos-icons/java-swing-schriftzug.png" width="50">

## IDE
- Visual Studio Code <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/9/9a/Visual_Studio_Code_1.35_icon.svg/512px-Visual_Studio_Code_1.35_icon.svg.png?20210804221519" width="30">
- NeatBeans Apache <img src="https://download.logo.wine/logo/NetBeans/NetBeans-Logo.wine.png" width="50">

## Development Team

- Ricardo Medina: [Linkedin](https://www.linkedin.com/in/ricardomedinamartin/) [GitHub](https://github.com/ramedina98)

- Noel Alejandro: [Github](https://github.com/Dr-Bugieman)

## Contact
Email: <a href="mailto:rmedinamartindelcampo@gmail.com?subject=Consulta&body=Hola, quisiera hacer una consulta.">rmedinamartindelcampo@gmail.com</a>
