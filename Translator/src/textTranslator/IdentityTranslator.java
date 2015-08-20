package textTranslator;
/**
 * Does nothing (default).
 *
 * @author Dave Matuszek
 * @version Aug 2, 2007
 */
public class IdentityTranslator implements TranslatorInterface {

    public String translate(String text) {
        return text;
    }

    public String getName() {
        return "Identity";
    }

    public String getDescription() {
        return "Does nothing.";
    }
}
