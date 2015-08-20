package textTranslator;
import java.util.Formatter;

/**
 * Displays hex values of the input string.
 * @author Dave Matuszek
 * @version Jun 21, 2007
 */
public class ShowHex implements TranslatorInterface {
    String goodNewline = System.getProperty("line.separator");
    static String[] codes = new String[] {
        "Nul", "Soh", "Stx", "Etx", "Eot", "Enq", "Ack", "Bel",
        " Bs", "Tab", " Lf", " Vt", " Ff", " Cr", " So", " Si",
        "Dle", "Dc1", "Dc2", "Dc3", "Dc4", "Nak", "Syn", "Etb",
        "Can", " Em", "Sub", "Esc", " Fs", " Gs", " Rs", " Us"
    };

    public String translate(String text) {
        String letters = "";
        StringBuilder builder = new StringBuilder();
        Formatter numbers = new Formatter(builder);
        String result = "";
        String ch;
        for (int i = 0; i < text.length(); i++) {
            char character = text.charAt(i);
            if (character < ' ') {
                ch = codes[character];
            }
            else if (character == 127) {
                ch = "Del";
            }
            else if (character > 255) {
                ch=" ??";
            }
            else {
                ch = "  " + character;
            }
            letters += ch;
            if (character <= 255) {
                numbers.format(" %02x", text.charAt(i) + 0);
            }
            else {
                numbers.format(" %04x", text.charAt(i) + 0);
            }
            if (i % 10 == 9) {
                result += letters + "    " +
                builder.toString().toUpperCase() + "\n";
                letters = "";
                builder.setLength(0);
            }
            else if (i == text.length() - 1) {
                int j = i;
                result += letters;
                while (j % 10 != 9) {
                    j++;
                    result += "   ";
                }
                result += "    " +
                builder.toString().toUpperCase() + "\n";
                letters = "";
                builder.setLength(0);
            }
        }
        return result;
    }

    public String getName() {
        return "Text to Hex";
    }

    public String getDescription() {
        return "Displays input text, making control characters visible,\n" +
               "alongside a hexadecimal representation of the same text.";
    }
}