package textTranslator;
import java.util.ArrayList;


/**
 * Wraps long lines to 72 characters.
 *
 * @author Dave Matuszek
 * @version Oct 23, 2008
 */
public class TextWrapTranslator implements TranslatorInterface {
    final int LIMIT = 72;

    public String translate(String text) {
        String[] inputLines = text.split("\n");
        ArrayList<String> outputLines = new ArrayList<String>();

        for (int i = 0; i < inputLines.length; i++) {
            String line = inputLines[i];
            String prefix = getLeadingWhitespace(line);

            // Ensure indent isn't more than half of desired line length
            int limit = Math.max(LIMIT / 2, LIMIT - prefix.length());

            while (line.length() > limit) {
                int cutPoint = findWhereToBreakLine(line, limit);
                if (cutPoint < 0) {
                    outputLines.add(line); // No prefix on long unbreakable line
                    break;
                }
                outputLines.add(prefix + line.substring(0, cutPoint));
                if (cutPoint + 1 < line.length()) {
                    line = line.substring(cutPoint + 1);
                }
            }
            if (line.length() <= limit) line = prefix + line;
            outputLines.add(line);
        }
        String output = "";
        for (String line : outputLines) {
            output += line + '\n';
        }
        return output;
    }

    private int findWhereToBreakLine(String line, int limit) {
        // Look for the last space before the limit
        int index = line.lastIndexOf(' ', limit);
        if (index >= 0) return index;
        // Failing that, look for the first space
        index = line.indexOf(' ');
        if (index >= 0) return index;
        // If no spaces, return -1
        return -1;
    }

    private String getLeadingWhitespace(String line) {
	String prefix = "";
	for (int j = 0; j < line.length(); j++) {
	    char ch = line.charAt(j);
	    if (!Character.isWhitespace(ch)) break;
	    prefix += ch;
	}
	return prefix;
    }

    public String getName() {
        return "Wrap Lines";
    }

    public String getDescription() {
        return "Wraps long lines to not more than " + LIMIT + " characters.";
    }
}