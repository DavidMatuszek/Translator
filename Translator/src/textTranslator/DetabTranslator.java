package textTranslator;
/**
 * Replaces tabs at the beginning of lines with four spaces.
 *
 * @author Dave Matuszek
 * @version April 21, 2009
 */
public class DetabTranslator implements TranslatorInterface {

    public String translate(String text) {
        String[] lines = text.split("\n");
        StringBuilder result = new StringBuilder();
        for (String line : lines) {
            int i = 0;
            while (i < line.length() && line.charAt(i) == '\t') {
                result.append("    ");
                i++;
            }
            if (i < line.length()) {
                result.append(line.substring(i));
            }
            result.append("\n");
        }
        return result.toString();
    }

    public String getName() {
        return "Detab";
    }

    public String getDescription() {
        return "Replaces tabs at the beginning of lines with four spaces.";
    }
}
