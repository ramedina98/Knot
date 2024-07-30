
/**
 * @author Ricardo Medina
 * @date 28/07/2024
 * @description:
 */
package syntax;

import java.util.List;

public class OperationValidator {

    // method to validate arithmetica operations
    public static void validate(Token operation, List<Token> tokens, int currentTokenIndex) throws SyntaxException {
        String value = operation.getValue();

        // validate simple arithmetic operations (addition, subtraction, multiplication, division)
        if(value.contains("+") || value.contains("-") || value.contains("#") || value.contains("/")){
            validateSimpleArithmetic(operation, tokens, currentTokenIndex);
        } else if(value.startsWith("Math{}") && value.endsWith("}")){
            // validate the math{} function
            validateMathOperation(operation);
        } else if(value.contains("++") || value.contains("+=")){
            // validate iterators...
            validateIterators(operation);
        } else{ // if the operation is not valid, throw an exception...
            throw new SyntaxException("Invalid operation: " + value);
        }
    }

    //Method to validate simple arithmetic operations...
    private static void validateSimpleArithmetic(Token operation, List<Token> tokens, int currentTokenIndex) throws SyntaxException {
        // implement logic to validate simple arithmetic operations...
        // for example, check if the operands are valid (number, variables, etc.)..
        String[] parts = operation.getValue().split("[+\\-#/]");
        for(String part : parts){
            if(!isValidOperand(part.trim())){
                throw new SyntaxException("Invalid operand in operation: " + operation.getValue());
            }
        }
    }

    // method to validate the math{} funciton...
    private static void validateMathOperation(Token operation) throws SyntaxException {
        // implement logic to validate the math{} function...
        // for example, check if the equantion inside the brances is valid...
        String equantion = operation.getValue().substring(5, operation.getValue().length() - 1);
        // validate the equantion (this may require more complex logic depending on tje structure of the equantion)...
        System.out.println(equantion);
    }

    // method to validate iterators...
    private static void validateIterators(Token operation) throws SyntaxException {
        // implement logic to validate iterators..
        // for example , check if the increments are valid...
        String[] parts = operation.getValue().split("\\++|\\+=");
        for(String part : parts){
            if(!isValidOperand(part.trim())){
                throw new SyntaxException("Invalid operand in iterator operation: " + operation.getValue());
            }
        }
    }

    //helper method to validate if an operand is valid (can be a variable or a number)...
    private static boolean isValidOperand(String operand){
        // implement logic to check if an operand is valid...
        // for example, check if it is a number or an existing variable...
        return operand.matches("\\d+") || operand.matches("[a-zA-Z_][a-zA-Z0-9_]*");
    }
}
