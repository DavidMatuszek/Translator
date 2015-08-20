package textTranslator;
/**
 * Translates c_style_words to camelCaseWords.
 *
 * @author Dave Matuszek
 * @version June 24, 2009
 */
public class UnderscoresToCamelCaseTranslator implements TranslatorInterface {

    enum State { OUT, CHAR, UNDERSCORE }
    
    public String translate(String text) {
        StringBuilder builder = new StringBuilder(text);
        State state = State.OUT;
        for (int i = 0;; i++) {
            if (i >= builder.length()) break;
            char ch = builder.charAt(i);
            switch (state) {
                case OUT:
                    if (Character.isLowerCase(ch) || Character.isDigit(ch)) {
                        state = State.CHAR;
                    }
                    break;
                case CHAR:
                    if (ch == '_') {
                        state = State.UNDERSCORE;
                    }
                    else if (!Character.isJavaIdentifierPart(ch)) {
                        state = State.OUT;
                    }
                    break;
                case UNDERSCORE:
                    if (Character.isLetter(ch)) {
                        builder.setCharAt(i, Character.toUpperCase(ch));
                        builder.deleteCharAt(i - 1);
                        state = State.CHAR;
                    } else if (Character.isJavaIdentifierPart(ch)) {
                        state = State.CHAR;
                    } else {
                        state = State.OUT;
                    }
                    break;
            }
        }
        return builder.toString();
    }

    public String getName() {
        return "Convert to camelCase";
    }

    public String getDescription() {
        return "Translate c_style_words to camelCaseWords.";
    }
}
