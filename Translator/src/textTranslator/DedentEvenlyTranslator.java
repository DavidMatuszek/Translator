/**
 * 
 */
package textTranslator;

/**
 * @author David Matuszek
 */
public class DedentEvenlyTranslator implements TranslatorInterface {

    /**
     * 
     * @param text
     * @return
     * @see textTranslator.TranslatorInterface#translate(java.lang.String)
     */
    public String translate(String text) {
        String detabbedText = text.replace("\t", "    ");
        String[] lines = detabbedText.split("\n");
        int dedentAmount = Integer.MAX_VALUE;
        for (String line : lines) {
            // find minimum number of leading spaces
            for (int position = 0; position < line.length(); position ++) {
                if (line.charAt(position) != ' ') {
                    if (position < dedentAmount) dedentAmount = position;
                    break;
                }
            }
        }
        if (dedentAmount == 0) return text;
        // Remove those spaces
        for (int i = 0; i < lines.length; i++) {
            if (lines[i].length() < dedentAmount) continue; // blank lines
            lines[i] = lines[i].substring(dedentAmount, lines[i].length());            
        }
        // Put it all back together
        String result = "";
        for (String line : lines) result += line + "\n";
        return result;
    }

    /**
     * 
     * @return
     * @see textTranslator.TranslatorInterface#getName()
     */
    public String getName() {
        return "Remove unnecessary indentation";
    }


    public String getDescription() {
        return "Removes the same amount of leading whitespace from every line.\n" +
               "Tabs count as four characters.";
    }

}
