package semantics;

public class Strings {
    private String stringValue;
    private char charValue;
    private boolean isString; // Para indicar si es un string o un char

    // Constructor para String
    public void Text(String value) {
        this.stringValue = value;
        this.isString = true;
    }

    // Constructor para char
    public void Text(char value) {
        this.charValue = value;
        this.isString = false;
    }

    // Método para obtener el valor como String
    public String getStringValue() {
        return stringValue;
    }

    // Método para obtener el valor como char
    public char getCharValue() {
        return charValue;
    }

    // Método para verificar si es un string
    public boolean isString() {
        return isString;
    }
}
