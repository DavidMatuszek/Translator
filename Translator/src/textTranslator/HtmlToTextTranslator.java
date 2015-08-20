package textTranslator;

/**
 * Turns HTML into plain text. Written to de-HTML-ify Odersky's
 * book, Programming in Scala.
 * 
 * @author David Matuszek
 * @version November 19, 2011
 */
public class HtmlToTextTranslator implements TranslatorInterface {

    public String translate(String text) {
        String[] lines = text.split("\n");
        StringBuilder b = new StringBuilder();
//        Pattern p1 = Pattern.compile(".*<title>([^/])*/>).*");
//        Matcher titleMatcher = p1.matcher("");
        
        int i = 0;
        // Delete everything in the head
        for (i = 0; i < lines.length; i++) {
            if (lines[i].contains("<body>")) break;
        }
        for (i += 1; i < lines.length; i++) {
            if (lines[i].trim().length() == 0) {
                // If a line was empty originally, keep it
                b.append("\n");
            } else {
                lines[i] = lines[i].replaceAll("</?(\\w+[^>]*>)", ""); // delete tags
                lines[i] = lines[i].replaceAll("<!--[^>]*>", ""); // delete comments
                lines[i] = lines[i].replaceAll("&amp;", "&");
                lines[i] = lines[i].replaceAll("&lt;", "<");
                lines[i] = lines[i].replaceAll("&le;", "<=");
                lines[i] = lines[i].replaceAll("&gt;", ">");
                lines[i] = lines[i].replaceAll("&ge;", ">=");
                lines[i] = lines[i].replaceAll("&quot;", "\"");
                lines[i] = lines[i].replaceAll("&apos;", "'");
                lines[i] = lines[i].replaceAll("&larr;", "<-");
                lines[i] = lines[i].replaceAll("&rarr;", "->");
                lines[i] = lines[i].replaceAll("&rArr;", "=>");
                lines[i] = lines[i].replaceAll("&nbsp;", " ");
                lines[i] = lines[i].replaceAll("&mdash;", "--");
                lines[i] = lines[i].replaceAll("&hellip;", "...");
                lines[i] = lines[i].replaceAll("&pi;", "pi");
                lines[i] = lines[i].replaceAll("&copy;", "(c)");
                if (lines[i].trim().length() != 0) {
                    // If the line contained only tags, don't keep it.
                    b.append(lines[i] + "\n");
                }
            }
        }
        return b.toString();
    }

    public String getName() {
        return "HTML to plain text";
    }

    public String getDescription() {
        return "Produces plain text from HTML; only the most common\n" +
               "entities are translated (there are a lot of them!).";
    }

}
