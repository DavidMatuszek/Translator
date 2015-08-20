package textTranslator;
import java.awt.Frame;
import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.io.StringReader;

/**
 * Tells what the StreamTokenizer does.
 * 
 * File TokenizerTranslator.java
 * Created on Jan 11, 2007
 */
public class TokenizerTranslator implements TranslatorInterface {

    public String translate(String text) {
        Reader reader = new StringReader(text);
        StreamTokenizer tokenizer = new StreamTokenizer(reader);
        int currentToken = StreamTokenizer.TT_EOF;
        StringBuilder builder = new StringBuilder();

        tokenizer.quoteChar('<');
        do {
            try {
                currentToken = tokenizer.nextToken();
                builder.append("[" + currentToken + "] ");
                switch (currentToken) {
                    case StreamTokenizer.TT_NUMBER:
                        builder.append("double: ");
                        break;
                    case StreamTokenizer.TT_WORD:
                        builder.append("word:    ");
                        break;
                    case StreamTokenizer.TT_EOL:
                        builder.append("EOL ");
                        break;
                    case StreamTokenizer.TT_EOF:
                        builder.append("EOF ");
                        break;
                    default:
                        builder.append("char:    ");
                        break;
                }
                builder.append("     nval=" + tokenizer.nval);
                builder.append("     sval=" + tokenizer.sval);
                if (currentToken > 0) builder.append("     " + (char)currentToken);
                builder.append("\n");
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        } while(currentToken != StreamTokenizer.TT_EOF);
        return builder.toString();
    }

    public String getName() {
        return "Text to StreamTokenizer tokens";
    }

    public String getDescription() {
        return "Shows how the input text will be parsed into\n" +
               "tokens by the StreamTokenizer class.";
    }
}
