package textTranslator;
import java.util.ArrayList;


/**
 * Wraps long lines to 72 characters.
 * This is just a renaming of TextWrapTranslator, because the name
 * WrapTextTranslator was used in an assignment (CIT 590, Spring 2009).
 * The code, however, is quite different.
 *
 * @author Dave Matuszek
 * @version April 21, 2009
 */
public class WrapTextTranslator implements TranslatorInterface {
    final int LIMIT = 72;


            /**
             * Wraps lines so that no line is longer than 72 characters. Long lines are
             * broken at a space character, with the remainder put on a new line with
             * the same indentation as the original. Very long lines will result in
             * several shorter lines. If a line contains more than 72 consecutive
             * non-space characters (as often happens with a URL), the long "word" is
             * not  broken, but is put on a line by itself. This method does not join
             * lines, it only wraps long lines.
             * 
             * @param text The text to be wrapped.
             * @return The wrapped text.
             * @see textTranslator.TranslatorInterface#translate(java.lang.String)
             */
            public String translate(String text) {
                StringBuilder builder = new StringBuilder();
            
                text = normalizeNewlines(text);
            
                String[] paragraphs = text.split("\n\n");
                for (String paragraph : paragraphs) {
            
                    String[] lines = paragraph.split("\n");
                    for (String line : lines) {
                        builder.append(wrapLine(line));
                        //            builder.append("\n");
                    }
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

    String wrapLine(String line) {

        if (line.length() <= LIMIT) {
            return line + "\n";
        }

        String word;
        String output = "";
        int charCount = 0;
        String indent = getIndentation(line);
        
        while ((word = getWord(line)) != null) {
            if (charCount + word.length() <= LIMIT) {
                // If word fits on line, add it
                output += word;
                charCount += word.length();
            }
            else if (charCount == 0) {
                // Very long words go on a line by themselves
                output += word;
            }
            else {
                // Word starts a new line
                output += "\n" + indent + word.trim();
                charCount = (indent + word.trim()).length();
            }
            // Remove word from line
            line = line.substring(word.length());
        }
        return output + "\n";
    }
    
    private String getIndentation(String line) {
        String indent = "";
        for (int i = 0; i < line.length(); i++) {
            if (Character.isWhitespace(line.charAt(i))) {
                indent += line.charAt(i);
            }
            else break;
        }
        return indent;
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
        return "Wrap Lines";
    }

    public String getDescription() {
        return "Wraps long lines to not more than " + LIMIT + " characters.";
    }
}