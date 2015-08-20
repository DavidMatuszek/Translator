package textTranslator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PigLatinTranslator implements TranslatorInterface {
    Pattern wordPlusStuff = Pattern.compile("([a-zA-Z]+)([^a-zA-Z]*)");
    Pattern consonantsPlusRest = Pattern.compile("([^aeiouAEIOU]+)([a-zA-Z]*)");

    /**
     * Pig Latin.
     * @param text
     * @return
     * @see textTranslator.TranslatorInterface#translate(java.lang.String)
     */
    public String translate(String text) {
        Matcher m = wordPlusStuff.matcher(text);
        String translatedText = "";

        while (m.find()) {
            translatedText += translateWord(m.group(1)) + m.group(2);
        }
        return translatedText;
    }
    
    private String translateWord(String word) {
        Matcher m = consonantsPlusRest.matcher(word);
        if (m.matches()) {
            return m.group(2) + m.group(1) + "ay";
        }
        else return word + "hay";
    }

    public String getName() {
        return "English to Pig Latin";
    }

    public String getDescription() {
        return "Translates English to Pig Latin.";
    }
}
