package textTranslator;
/**
 * Replace all newline sequences (Cr, or CrLf) with UNIX newlines (Lf).
 *
 * @author Dave Matuszek
 * @version April 21, 2009
 */
public class UnixNewlinesTranslator implements TranslatorInterface {

    public String translate(String text) {
        text = text.replaceAll("\015\012", "\012");
        return text.replaceAll("\015", "\012");
    }

    public String getName() {
        return "Use Unix Newlines";
    }

    public String getDescription() {
        return "Replace all newline sequences (Cr == \"\\015\" or CrLf == \"\\015\\012\")\n" +
        		"with UNIX newlines (Lf == \"\\012\" == \"\\n\").";
    }
}
