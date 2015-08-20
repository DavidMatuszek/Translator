package textTranslator;
/**
 * Replaces newlines with vertical tabs for pasting into PowerPoint.
 * @author Dave Matuszek
 * @version Jun 21, 2007
 */
public class TextToPowerpoint implements TranslatorInterface {
    String cr = "\015"; // carriage return
    String lf = "\012"; // line feed
    String vt = "\013"; // vertical tab

    public String translate(String text) {;
      text = text.replaceAll(cr + lf, vt);
      text = text.replaceAll(cr, vt);
      text = text.replaceAll(lf, vt);
        return text;
    }

    public String getName() {
        return "Text to PowerPoint";
    }

    public String getDescription() {
        return "Translates newlines of all types to vertical tabs.";
    }
}