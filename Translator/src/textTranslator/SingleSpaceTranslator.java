package textTranslator;
/**
 * Replaces consecutive pairs of newlines with a single newline.
 * For example, six consecutive newlines would be changed into
 * three newlines; seven consecutive newlines would be changed
 * into four newlines. All newlines in the output will be correct
 * for the platform on which this program is running.
 *
 * @author Dave Matuszek
 * @version April 21, 2009
 */
public class SingleSpaceTranslator implements TranslatorInterface {

    public String translate(String text) {
        String newline = System.getProperty("line.separator");
        text = text.replaceAll("\n", newline); // XXX Is this necessary?
        return text.replaceAll(newline + newline, newline);
    }

    public String getName() {
        return "Single Space";
    }

    public String getDescription() {
        return "Replaces consecutive pairs of newlines with a single newline.";
    }
}
