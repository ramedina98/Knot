package interfaces;

import java.util.regex.Pattern;

public interface PatternConstants {
    Pattern VAR_PATTERN = Pattern.compile("([a-zA-Z_][a-zA-Z0-9_]*)\\s*:\\s*(Number|Text|Bool)\\s*=\\s*(.*)\\s*");
    Pattern IF_PATTERN = Pattern.compile("Slip\\s*\\((.*)\\)\\s*\\{(.*)\\}");
    Pattern ELSE_IF_PATTERN = Pattern.compile("SlipKnot\\s*\\((.*)\\)\\s*\\{(.*)\\}");
    Pattern ELSE_PATTERN = Pattern.compile("Knot\\s*\\{(.*)\\}");
    Pattern WHILE_PATTERN = Pattern.compile("Circle\\s*\\((.*)\\)\\s*\\{(.*)\\}");
    Pattern FOR_PATTERN = Pattern.compile("EverythingEnds\\s*\\((\\d+),\\s*(\\d+),\\s*(\\d+),?\\)\\s*\\{(.*)\\}");
    Pattern SHOW_PATTERN = Pattern.compile("Show\\s*\\{(.*)\\}");
}
