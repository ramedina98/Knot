/**
 * @author Noel
 * @date  27/07/2024
 */

package semantics;

public class Variable {
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