package textTranslator;
/**
 * Flows text (the way web pages do). Lines longer than 72 characters
 * are wrapped (as in the Wrap Text translation), and short lines 
 * are joined with the following line, if the following line is nonempty.
 * Separate paragraphs, separated by a blank line, are kept separate.
 * (Note: A "blank line" is any line that is visibly blank; it may contain
 * spaces or tabs, which should be removed.) 
 *
 * @author Dave Matuszek
 * @version Aug 2, 2007
 */
public class FlowTextTranslator implements TranslatorInterface {
    private final int LIMIT = 72;

    /**
     * Flows text (the way web pages do). Lines longer than 72 characters are
     * wrapped, and short lines are joined with the following line, if the
     * following line is not empty. Separate paragraphs, separated by a blank
     * line, are kept separate. (Note: A "blank line" is any line that is
     * visibly blank; it may contain spaces or tabs, which should be removed.)
     * 
     * @param text The text to be flowed.
     * @return The flowed text.
     * @see textTranslator.TranslatorInterface#translate(java.lang.String)
     */
    public String translate(String text) {
        StringBuilder builder = new StringBuilder();

        text = normalizeNewlines(text);

        String[] paragraphs = text.split("\n\n");
        for (String paragraph : paragraphs) {
            builder.append(flowParagraph(paragraph));
            builder.append("\n");
        }
        return builder.toString();
    }

    /**
     * Replaces all newlines of whatever type with '\012' newlines, and
     * ensures that all "blank" lines contain no whitespace characters.
     * 
     * @param text The text to be transformed.
     * @return The resultant text.
     */
    String normalizeNewlines(String text) {
        text = text.replaceAll("\015\012", "\012");
        text = text.replaceAll("\012\\w+\012", "\012\012");
        return text;
    }

    String flowParagraph(String paragraph) {
        paragraph = paragraph.replaceAll("\012", " ");

        if (paragraph.length() <= LIMIT) {
            return paragraph + "\n";
        }

        String word;
        String output = "";
        int charCount = 0;
        while ((word = getWord(paragraph)) != null) {
            if (charCount + word.length() <= LIMIT) {
                // If word fits on line, add it
                output += word;
                charCount += word.length();
            }
            else if (charCount == 0) {
                // Very long words go on a line by themselves
                output += word.trim();
            }
            else {
                // Word starts a new line
                output += "\n" + word.trim();
                charCount = word.trim().length();
            }
            // Remove word from paragraph
            paragraph = paragraph.substring(word.length());
        }
        return output + "\n";
    }

    /**
     * Get the next "word" (sequence of non-whitespace characters) from
     * the input paragraph, along with any preceding whitespace.
     * 
     * @param paragraph The text from which to get a "word".
     * @return Zero or more whitespace characters, followed by one or
     *         more non-whitespace characters.
     */
    private String getWord(String paragraph) {
        int i;
        // Step over all whitespace
        for (i = 0; i < paragraph.length(); i++) {
            if (!Character.isWhitespace(paragraph.charAt(i))) break;
        }
        if (i == paragraph.length()) {
            // Whitespace is all there was
            return null;
        }
        // Step over all non-whitespace
        for (; i < paragraph.length(); i++) {
            if (Character.isWhitespace(paragraph.charAt(i))) break;
        }
        return paragraph.substring(0, i);
    }

    public String getName() {
        return "FlowText";
    }

    public String getDescription() {
        return "Flows and wraps text, similar to the way web pages do.";
    }
}
