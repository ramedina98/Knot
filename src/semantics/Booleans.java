package semantics;

public class Booleans {
    private boolean value;

    // Constructor
    public void Bool(boolean value) {
        this.value = value;
    }

    // Método para obtener el valor
    public boolean getValue() {
        return value;
    }

    // Método para establecer el valor
    public void setValue(boolean value) {
        this.value = value;
    }
}