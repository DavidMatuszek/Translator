package textTranslator;
/**
 * Replaces all UNIX newlines ("\012") with Windows newline sequences ("\015\012").
 * However, care is taken not to modify existing Windows newlines--that is,
 * "\015\012" is not changed to "\015\015\012".
 *
 * @author Dave Matuszek
 * @version April 21, 2009
 */
public class WindowsNewlinesTranslator implements TranslatorInterface {

    public String translate(String text) {
        return text.replaceAll("\015?\012", "\015\012");
    }

    public String getName() {
        return "Use Windows Newlines";
    }

    public String getDescription() {
        return "Replaces all UNIX newlines (\"\\012\") with Windows" +
        		" newline sequences (\"\\015\\012\").";
    }
}
