package textTranslator;
/**
 * Replaces special characters &, <, >, " with entities.
 * @author Dave Matuszek
 * @version Jun 21, 2007
 */
public class TextToHtmlTranslator implements TranslatorInterface {

    public String translate(String text) {
        text = text.replaceAll("&", "&amp;");
        text = text.replaceAll("<", "&lt;");
        text = text.replaceAll(">", "&gt;");
        text = text.replaceAll("\"", "&quot;");
        return text;
    }

    public String getName() {
        return "&<>\" in text to HTML entities";
    }

    public String getDescription() {
        return "Converts the characters '&', '<', and '>' to the\n" +
               "entities \"&amp;\", \"&lt;\", and \"&gt;\", respectively.";
    }
}