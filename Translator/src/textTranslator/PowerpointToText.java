package textTranslator;
/**
 * Replaces vertical tab characters from PowerPoint with newlines,
 * curly quotes with straight ones, and removes garbage characters.
 * @author Dave Matuszek
 * @version Jun 21, 2007
 */
public class PowerpointToText implements TranslatorInterface {
    String goodNewline = System.getProperty("line.separator");

    public String translate(String text) {
        text = text.replaceAll("\013", goodNewline); // vertical tab
        text = text.replaceAll("\u201C", "\""); // open curly double quote
        text = text.replaceAll("\u201D", "\""); // close curly double quote
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (ch >= 0x20 && ch <= 0x127) continue; // good character
            if (ch == '\t') continue; // tab
            if (ch == 0x0A) continue; // linefeed
            if (ch == '\r') continue; // carriage return
            // Delete garbage character
            text = text.substring(0, i) + text.substring(i + 1);
            i--;
        }
        return text;
    }

    public String getName() {
        return "PowerPoint to Text";
    }

    public String getDescription() {
        return "Replaces vertical tabs (\\013) with System-defined newlines,\n"
             + "curly quotes with straight ones, and removes garbage characters.";
    }
}