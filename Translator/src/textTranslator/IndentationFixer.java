package textTranslator;
/**
 * Corrects bad indentation (according to braces) Aug 2, 2007
 * December 2011 -- modified to maintain indentation of first nonblank line
 *
 * @author Dave Matuszek
 * @version December 13, 2011
 */
public class IndentationFixer implements TranslatorInterface {
    private StringBuffer output;
    int indentAmount = 4;
    String spacesForTab = "    "; // must agree with line above
    int indentThisLine = 0;
    int indentNextLine = 0;

    public String translate(String text) {
        output = new StringBuffer();
        String[] lines = text.split("\n");
        int indent = 0;
        boolean atBeginning = true;
        
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            if (atBeginning) {
                if (line.trim().length() == 0) {
                    output(0, "");
                } else {
                    indent = getIndentation(line);
                    System.out.println("Indenting line " + i + " by " + indent);
                    output(indent, line);
                    int[] deltas = computeDeltas(line);
                    indent += deltas[0]; // not used on first nonblank line, but counted
                    indentNextLine = deltas[1];
                    atBeginning = false;
                }
                continue; // just to save an "else"
            }
            int[] deltas = computeDeltas(line);
            indent += deltas[0];
            output(indent, line);
            indent += deltas[1];
        }
        return chomp(output.toString());
    }
    
    private int getIndexOfFirstNonblankLine(String[] lines) {
        for (int i = 0; i < lines.length; i++) {
            if (lines[i].trim().length() > 0) {
                return i;
            }
        }
        return -1;
    }

    private int getIndentation(String line) {
        line = line.replaceAll("\t", spacesForTab); // hack
        for (int j = 0; j < line.length(); j++) {
            if (line.charAt(j) != ' ') return j;
        }
        throw new RuntimeException("Can't happen!");
    }
    
    private void output(int indent, String text) {
        for (int i = 0; i < indent; i++) {
            output.append(' ');
        }
        output.append(text.trim());
        output.append('\n');
    }
    
    private int[] computeDeltas(String line) {
        int indentThisLine = 0;
        int indentNextLine = 0;
        for (int i = 0; i < line.length(); i++) {
            char ch = line.charAt(i);
            if (ch == '{') {
                indentNextLine += indentAmount;
            } else if (ch == '}') {
                if (indentNextLine > 0) {
                    indentNextLine -= indentAmount;
                } else {
                    indentThisLine -= 4;
                }
            }
        }
        return new int[] {indentThisLine, indentNextLine};
    }
    
    private String chomp(String line) {
        if (line.endsWith("\n")) return line.substring(0, line.length() - 1);
        else return line;
    }

    public String getDescription() {
        return "Corrects indentation, according to braces.\n" +
        	   "Does not do well when quoted strings contain braces.";
    }

    public String getName() {
        return "Fix Indentation (2007)";
    }
}