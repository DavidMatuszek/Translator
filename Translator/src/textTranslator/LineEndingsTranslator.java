package textTranslator;
/**
 * Does nothing (default).
 *
 * @author Dave Matuszek
 * @version Aug 2, 2007
 */
public class LineEndingsTranslator implements TranslatorInterface {

    public String translate(String text) {
        System.out.println(text.length());
        text = text.replaceAll("\n", "\012");
        System.out.println(text.length());
        return text;
    }

    public String getName() {
        return "Line Endings";
    }

    public String getDescription() {
        return "Replaces \n with \r\n.";
    }
}
