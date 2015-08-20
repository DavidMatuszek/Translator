package textTranslator;
/**
 * Replaces each newline in the input with two newlines.
 * All newlines in the output will be correct
 * for the platform on which this program is running.
 *
 * @author Dave Matuszek
 * @version April 21, 2009
 */
public class DoubleSpaceTranslator implements TranslatorInterface {

    public String translate(String text) {
        String newline = System.getProperty("line.separator");
        text = text.replaceAll("\012\015", "\012"); // This IS necessary
        text = text.replaceAll("\012", newline); // XXX Is this necessary?
        text = text.replaceAll(newline, newline + newline);
        return text;
    }

    public String getName() {
        return "Double Space";
    }

    public String getDescription() {
        return "Replaces each newline in the input with two newlines.";
    }
}
