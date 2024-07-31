/**
 * @author Ricardo Medina
 * This class helps us to detect what type of line it is, contro structure or variable...
 */
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
