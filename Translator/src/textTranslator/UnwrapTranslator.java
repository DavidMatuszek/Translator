package textTranslator;
/**
 * Replaces each linefeed not followed by whitespace with
 * a space; leaves other linefeeds and characters unchanged.
 * <p>
 * The reason this checks for whitespace after a linefeed,
 * rather than just another linefeed, is that lines which are
 * indented are often formatted in some way, for example, as
 * a list.
 * 
 * @author Dave Matuszek
 * @version Dec 11, 2008
 */
public class UnwrapTranslator implements TranslatorInterface {

    /* (non-Javadoc)
     * @see TranslatorInterface#getDescription()
     */
    public String getDescription() {
        // TODO Auto-generated method stub
        return "Replaces each linefeed not followed by whitespace with\n" +
        	   "a space; compresses multiple linefeeds into just one.\n" +
        	   "Designed to adapt Project Gutenberg files to Kindle.";
    }

    /* (non-Javadoc)
     * @see TranslatorInterface#getName()
     */
    public String getName() {
        return "Unwrap Lines";
    }

    /* (non-Javadoc)
     * @see TranslatorInterface#translate(java.lang.String)
     */
    public String translate(String text) {
        if (text.length() == 0) {
            return "";
        }
        final char lf = (char) 10;
        
        // Copy first character
        char previousChar = text.charAt(0);
        StringBuilder result = new StringBuilder("" + previousChar);
        
        // Handle middle characters
        for (int i = 1; i < text.length() - 1; i++) {
            char thisChar = text.charAt(i);
            if (thisChar == lf && previousChar == lf) {
                continue;  // discard all linefeed characters after first
            }
            if (thisChar == lf && previousChar != lf) {
                char nextChar = text.charAt(i + 1);
                if (!Character.isWhitespace(nextChar)) {
                    thisChar = ' ';
                }
            }
            result.append(thisChar);
            previousChar = thisChar;
        }
        
        // Copy last character
        result.append(text.charAt(text.length() - 1));
        
        return result.toString();
    }
}
