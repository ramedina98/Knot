/**
 * @author Noel
 * @date  27/07/2024
 */

package semantics;


import java.util.regex.*;
import java.util.*;

public class semantic {
    private Map<String, Variable> variables;

    public semantic() {
        this.variables = new HashMap<>();
    }

    public void parseText(String texto) {
        String[] lineas = texto.split("\\n");

        //patrones de todo
        Pattern varPattern = Pattern.compile("(Text|Number|Bool)\\s+([A-Za-z0-9]+)\\s*(=\\s*(.*))?;");
        Pattern ifPattern = Pattern.compile("Slip\\s+'([A-Za-z0-9]+\\s*(==|≠|>|<|≥|≤)\\s*[A-Za-z0-9]+)'\\s*\\((.*)\\)");
        Pattern elseIfPattern = Pattern.compile("SlipKnot\\s+'([A-Za-z0-9]+\\s*(==|≠|>|<|≥|≤)\\s*[A-Za-z0-9]+)'\\s*\\((.*)\\)");
        Pattern elsePattern = Pattern.compile("Knot\\s*\\((.*)\\)");
        Pattern whilePattern = Pattern.compile("Circle\\s+'([A-Za-z0-9]+\\s*(==|≠|>|<|≥|≤)\\s*[A-Za-z0-9]+)'\\s*\\((.*)\\)");
        Pattern forPattern = Pattern.compile("EverythingEnds\\s+'(\\d+),\\s*(\\d+),\\s*(\\d+)'\\s*\\((.*)\\)");
        Pattern switchPattern = Pattern.compile("Duality\\s+'([A-Za-z0-9]+)'\\s*\\((.*)\\)");
        Pattern defaultPattern = Pattern.compile("Default\\s*\\((.*)\\)");
        Pattern mathPattern = Pattern.compile("Math\\{(.*)\\}");

        //matchers de todo
        for (String linea : lineas) {
            Matcher varMatcher = varPattern.matcher(linea.trim());
            Matcher ifMatcher = ifPattern.matcher(linea.trim());
            Matcher elseIfMatcher = elseIfPattern.matcher(linea.trim());
            Matcher elseMatcher = elsePattern.matcher(linea.trim());
            Matcher whileMatcher = whilePattern.matcher(linea.trim());
            Matcher forMatcher = forPattern.matcher(linea.trim());
            Matcher switchMatcher = switchPattern.matcher(linea.trim());
            Matcher defaultMatcher = defaultPattern.matcher(linea.trim());
            Matcher mathMatcher = mathPattern.matcher(linea.trim());

            //revision de variables
            if (varMatcher.matches()) {
                String tipo = varMatcher.group(1);
                String nombre = varMatcher.group(2);
                String valor = varMatcher.group(4) != null ? varMatcher.group(4) : "0";

                if (tipo.equals("Number")) {
                    variables.put(nombre, new Variable(tipo, nombre, Double.parseDouble(valor)));
                } else {
                    variables.put(nombre, new Variable(tipo, nombre, valor));
                }
                System.out.println("Variable Aceptada: " + nombre + (valor.isEmpty() ? "" : " = " + valor));
                    //matcher de if(slip, knot, slipknot)
            } else if (ifMatcher.matches()) {
                String condition = ifMatcher.group(1);
                String operation = ifMatcher.group(2);
                if (evaluateCondition(condition)) {
                    executeOperation(operation);
                }
            } else if (elseIfMatcher.matches()) {
                String condition = elseIfMatcher.group(1);
                String operation = elseIfMatcher.group(2);
                if (evaluateCondition(condition)) {
                    executeOperation(operation);
                }
            } else if (elseMatcher.matches()) {
                String operation = elseMatcher.group(1);
                executeOperation(operation);

                //matcher del while(Circle) 
            } else if (whileMatcher.matches()) {
                String condition = whileMatcher.group(1);
                String operation = whileMatcher.group(2);
                while (evaluateCondition(condition)) {
                    executeOperation(operation);
                }

                //matcher del for (everythingfor)
            } else if (forMatcher.matches()) {
                int start = Integer.parseInt(forMatcher.group(1));
                int end = Integer.parseInt(forMatcher.group(2));
                int increment = Integer.parseInt(forMatcher.group(3));
                String operation = forMatcher.group(4);
                for (int i = start; i <= end; i += increment) {
                    variables.put("TheEnd", new Variable("Number", "TheEnd", (double) i));
                    executeOperation(operation);
                }

                //matcher del switch case
            } else if (switchMatcher.matches()) {
                String variableName = switchMatcher.group(1);
                String[] cases = switchMatcher.group(2).split(";");
                for (String caseStmt : cases) {
                    if (caseStmt.trim().startsWith("Cut")) {
                        break;
                    }
                    Matcher caseMatcher = Pattern.compile("act\\s*\\*(.*)\\*\\s*;\\s*zona\\s*de\\s*operación\\s*(.*)\\)").matcher(caseStmt.trim());
                    if (caseMatcher.matches()) {
                        String condition = caseMatcher.group(1);
                        String operation = caseMatcher.group(2);
                        if (evaluateCondition(condition, variableName)) {
                            executeOperation(operation);
                            break;
                        }
                    }
                }

                //matcher del default
            } else if (defaultMatcher.matches()) {
                String operation = defaultMatcher.group(1);
                executeOperation(operation);
            } else if (mathMatcher.matches()) {
                String equation = mathMatcher.group(1);
                double result = solveEquation(equation);
                System.out.println("Resultado de Math: " + result);
            } else {
                System.out.println("Línea no reconocida: " + linea.trim());
            }
        }
    }

    //evaluacion de condicionales (+-*/),(==,!=,<,>)
    private boolean evaluateCondition(String condition) {
        // Evaluar las condiciones booleanas y de comparación
        String[] parts = condition.split("\\s*(==|!=|>|<|>=|<=)\\s*");
        if (parts.length != 2) return false;

        String left = parts[0];
        String right = parts[1];
        String operator = condition.replaceAll("[A-Za-z0-9\\s]", "");

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

    private boolean evaluateCondition(String condition, String variableName) {
        // Evaluar la condición de un switch
        double value = getValue(variableName);
        double caseValue = getValue(condition);
        return value == caseValue;
    }

    private double getValue(String operand) {
        if (variables.containsKey(operand)) {
            Variable var = variables.get(operand);
            return var.getTipo().equals("Number") ? (double) var.getValor() : 0.0;
        }
        try {
            return Double.parseDouble(operand);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    //evaluacion de operaciones
    private void executeOperation(String operation) {
        // Ejecutar operaciones aritméticas simples
        String[] parts = operation.split("\\s*(\\+|\\-|#|\\/)\\s*");
        if (parts.length != 2) return;

        String left = parts[0];
        String right = parts[1];
        String operator = operation.replaceAll("[A-Za-z0-9\\s]", "");

        double leftValue = getValue(left);
        double rightValue = getValue(right);
        double result = 0.0;

        switch (operator) {
            case "+": result = leftValue + rightValue; break;
            case "-": result = leftValue - rightValue; break;
            case "#": result = leftValue * rightValue; break;
            case "/": if (rightValue != 0) result = leftValue / rightValue; break;
        }

        variables.put(left, new Variable("Number", left, result));
        System.out.println("Resultado de operación: " + left + " = " + result);
    }

    private double solveEquation(String equation) {
        // Resolver ecuaciones de primer y segundo grado
        equation = equation.replace("#", "*");
        String[] parts = equation.split("=");
        if (parts.length != 2) return 0.0;

        String left = parts[0].trim();
        String right = parts[1].trim();
        double rightValue = getValue(right);

        // NOTE: Aun no tiendo que hace esto...
        System.out.println(rightValue);

        // Asumimos que la ecuación está en la forma ax + b = 0 o ax^2 + bx + c = 0
        // Resolveremos solo ecuaciones de primer grado por simplicidad
        if (left.contains("x")) {
            String[] leftParts = left.split("\\+");
            double a = getValue(leftParts[0].trim());
            double b = getValue(leftParts[1].trim());
            return -b / a;
        }
        return 0.0;
    }

    public List<Variable> getVariables() {
        return new ArrayList<>(variables.values());
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduce el texto a analizar (termina con una línea vacía):");

        StringBuilder texto = new StringBuilder();
        String linea;
        while (!(linea = scanner.nextLine()).isEmpty()) {
            texto.append(linea).append("\n");
        }

        semantic parser = new semantic();
        parser.parseText(texto.toString());

        System.out.println("Variables aceptadas:");
        for (Variable var : parser.getVariables()) {
            System.out.println("Tipo: " + var.getTipo() + ", Nombre: " + var.getNombre() + ", Valor: " + var.getValor());
        }

        scanner.close();
    }
}

class Variable {
    private String tipo;
    private String nombre;
    private Object valor;

    public Variable(String tipo, String nombre, Object valor) {
        this.tipo = tipo;
        this.nombre = nombre;
        this.valor = valor;
    }

    public String getTipo() {
        return tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public Object getValor() {
        return valor;
    }
}
