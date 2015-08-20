package textTranslator;
/**
 * Replaces newlines with \02028 characters for pasting into Keynote.
 * @author Dave Matuszek
 * @version Jun 21, 2007
 */
public class TextToKeynote implements TranslatorInterface {
    String cr = "\015";   // carriage return
    String lf = "\012";   // line feed
    String line_separator = "\u2028"; // Unicode line separator

    public String translate(String text) {;
      text = text.replaceAll(cr + lf, line_separator);
      text = text.replaceAll(cr, line_separator);
      text = text.replaceAll(lf, line_separator);
        return text;
    }

    public String getName() {
        return "Text to Keynote";
    }

    public String getDescription() {
        return "Translates lines into a single Keynote bullet point.";
    }
}