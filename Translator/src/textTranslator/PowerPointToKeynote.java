package textTranslator;

/**
 * Translate PowerPoint slides to Keynote slides.
 * @author David Matuszek
 * @version August, 2015
 */
public class PowerPointToKeynote implements TranslatorInterface {

	@Override
    public String translate(String text) {
        text = text.replaceAll("\013", "\u2028"); // vertical tab to Unicode line separator
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (ch >= 0x20 && ch <= 0x127) continue; // good character
            if (ch == '\t') continue; // tab
            if (ch == 0x0A) continue; // linefeed
            if (ch == '\r') continue; // carriage return
            if (ch > 255) continue;   // Unicode character (hopefully)
            // Delete garbage character
            text = text.substring(0, i) + text.substring(i + 1);
            i--;
        }
        return text;
    }

	@Override
	public String getName() {
		return "PowerPoint to Keynote";
	}

	@Override
	public String getDescription() {
		return "Converts PowerPoint to Keynote; formatting is not preserved.";
	}

}
