package semantics;

public class Numbers {
    private int intValue;
    private float floatValue;
    private double doubleValue;
    private String type; // Para indicar el tipo de dato almacenado

    // Constructor para int
    public void Number(int value) {
        this.intValue = value;
        this.type = "int";
    }

    // Constructor para float
    public void Number(float value) {
        this.floatValue = value;
        this.type = "float";
    }

    // Constructor para double
    public void Number(double value) {
        this.doubleValue = value;
        this.type = "double";
    }

    // Métodos para obtener los valores
    public int getIntValue() {
        return intValue;
    }

    public float getFloatValue() {
        return floatValue;
    }

    public double getDoubleValue() {
        return doubleValue;
    }

    // Método para obtener el tipo de dato
    public String getType() {
        return type;
    }
}
