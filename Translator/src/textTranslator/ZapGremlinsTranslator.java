package textTranslator;

/*
 * This is file ZapGremlinsTranslator.java in project Translator, created on Oct 16, 2008
 */

public class ZapGremlinsTranslator implements TranslatorInterface {

    public String getDescription() {
        return "Deletes non-ASCII characters";
    }

    public String translate(String text) {
        String result = "";
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (ch >= ' ' && ch <= '~') {
                result += ch;
            }
            else if (ch == '\n' || ch == '\r' || ch == '\t') {
                result += ch;
            }
        }
        return result;
    }

    public String getName() {
        return "Zap Gremlins";
    }

}
