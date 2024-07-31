/**
 * @author Noel
 * @date 27/07/2024
 */

package semantics;

import java.util.regex.*;
import utils.Variable;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class Semantic {
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

        // Patrones para variables y estructuras de control
        Pattern varPattern = Pattern.compile("([a-zA-Z_][a-zA-Z0-9_]*)\\s*:\\s*(Number|Text|Bool)\\s*=\\s*(.*)\\s*");
        Pattern ifPattern = Pattern.compile("Slip\\s*\\((.*)\\)\\s*\\{(.*)\\}");
        Pattern elseIfPattern = Pattern.compile("SlipKnot\\s*\\((.*)\\)\\s*\\{(.*)\\}");
        Pattern elsePattern = Pattern.compile("Knot\\s*\\{(.*)\\}");
        Pattern whilePattern = Pattern.compile("Circle\\s*\\((.*)\\)\\s*\\{(.*)\\}");
        Pattern forPattern = Pattern.compile("EverythingEnds\\s*\\((\\d+),\\s*(\\d+),\\s*(\\d+),?\\)\\s*\\{(.*)\\}");
        Pattern showPattern = Pattern.compile("Show\\s*\\{(.*)\\}");

        // Matchers para cada línea de código
        for (String linea : lineas) {
            Matcher varMatcher = varPattern.matcher(linea.trim());
            Matcher ifMatcher = ifPattern.matcher(linea.trim());
            Matcher elseIfMatcher = elseIfPattern.matcher(linea.trim());
            Matcher elseMatcher = elsePattern.matcher(linea.trim());
            Matcher whileMatcher = whilePattern.matcher(linea.trim());
            Matcher forMatcher = forPattern.matcher(linea.trim());
            Matcher showMatcher = showPattern.matcher(linea.trim());

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
                appendOutput(linea.trim());
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

    /*private void showVariables() {
        System.out.println("Variables existentes:");
        for (Map.Entry<String, Variable> entry : variables.entrySet()) {
            Variable var = entry.getValue();
            System.out.println("Tipo: " + var.getTipo() + ", Nombre: " + var.getNombre() + ", Valor: " + var.getValor());
        }
    }*/

    public List<Variable> getVariables() {
        return new ArrayList<>(variables.values());
    }
}