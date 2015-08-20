package textTranslator;
/**
 * Does nothing (default).
 *
 * @author Dave Matuszek
 * @version Aug 2, 2007
 */
public class FixIndentationTranslator implements TranslatorInterface {

    public String translate(String text) {
        // Pass the buck to a previously written translator
        TranslatorInterface t = new IndentationFixer();
        return t.translate(text);
    }

    public String getName() {
        return "Fix Indentation (2011)";
    }

    public String getDescription() {
        return "Corrects indentation, according to braces.\n" +
               "Does not do well when quoted strings contain braces.";
    }
}
