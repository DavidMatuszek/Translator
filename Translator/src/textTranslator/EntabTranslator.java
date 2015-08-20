package textTranslator;
/**
 * Does nothing (default).
 *
 * @author Dave Matuszek
 * @version April 21, 2009
 */
public class EntabTranslator implements TranslatorInterface {

    public String translate(String text) {
        String[] lines = text.split("\n");
        StringBuilder result = new StringBuilder();
        for (String line : lines) {
            int count = 0;
            while (count < line.length() && line.charAt(count) == ' ') {
                count++;
            }
            if (count < 4) {
                result.append(line + "\n");
            }
            else {
                int numberOfTabs = count / 4;
                for (int i = 0; i < numberOfTabs; i++) {
                    result.append('\t');
                }
                int start = 4 * numberOfTabs;
                result.append(line.substring(start) + "\n");
            }
        }
        return result.toString();
    }

    public String getName() {
        return "Entab";
    }

    public String getDescription() {
        return "Replaces each group of four spaces at the\n" +
               " beginning of each line with a tab character.";
    }
}
