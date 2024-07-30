package knot;

// Este va a hacer el main principal, aqui es donde tendremos que mandar a llamar nuestro formulario para que aparesca en el
// .jar
import gui.Interfaz;
import javax.swing.SwingUtilities;
/**
 *
 * @author ricardomedina
 */
public class Knot {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Interfaz interfaz = new Interfaz();
                interfaz.setVisible(true);
            }
        });
    }
}
