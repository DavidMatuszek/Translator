
package textTranslator;
/**
 * Replaces "smart quotes" with ASCII equivalents.
 *
 * @author Dave Matuszek
 * @version Jun 9, 2013
 */
public class UnSmartQuoteTranslator implements TranslatorInterface {

    public String translate(String text) {
        StringBuffer buffer = new StringBuffer(text.length());
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (isAscii(ch)) {
                buffer.append(ch);
            } else {
                System.out.println(text.substring(i - 20, i) + "  [" + ((int)ch) + "]  " +
                                   text.substring(i + 1, i + 21));
                if (ch == 160) buffer.append(' ');
                if (ch == 8216) buffer.append('\'');
                if (ch == 8217) buffer.append('\'');
                if (ch == 8220) buffer.append('"');
                if (ch == 8221) buffer.append('"');
                if (ch == 8230) buffer.append("...");
            }
            
        }
        return buffer.toString();
    }

    /**
     * Tests if the parameter is a legal (non-control) ASCII character.
     */
    private boolean isAscii(char ch) {
        if (ch >= ' ' && ch <= '~') return true;
        return (ch == '\t' || ch == '\n' || ch == '\r');
    }

    public String getName() {
        return "UnSmartQuote";
    }

    public String getDescription() {
        return "Replaces \"smart quotes\" with ASCII equivalents.";
    }
}
