package knot;

// Este va a hacer el main principal, aqui es donde tendremos que mandar a llamar nuestro formulario para que aparesca en el
// .jar
import javax.swing.JFrame;
import javax.swing.JLabel;
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
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Mi Aplicación");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.add(new JLabel("Hola, Mundo!"));
        frame.setVisible(true);
    }

}
