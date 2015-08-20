package textTranslator;

import java.util.Stack;

/**
 * Checks ( ), [ ], and { } for balance.
 */
public class NestingChecker implements TranslatorInterface {
    Stack<Character> stack = new Stack<Character>();
    
    private char toChar(int i) {
        if (i < 0) return '-';
        if (i < 10) return (char)('0' + i);
        return (char)('A' + i - 10);
    }

    public String translate(String text) {
        Stack<Character> stack = new Stack<Character>();
        StringBuilder outText = new StringBuilder();
        int parenDepth = 0;
        int bracketDepth = 0;
        int braceDepth = 0;
        boolean parensAreOk = true;
        boolean bracketsAreOk = true;
        boolean bracesAreOk = true;
        StringBuilder line1 = new StringBuilder();
        StringBuilder line2 = new StringBuilder();
        String error = "";
        char expected;
        char okay;

        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            line1.append(ch);
            switch (ch) {
                case '(':
                    line2.append(toChar(parenDepth++));
                    stack.push(')');
                    break;
                case '[':
                    line2.append(toChar(bracketDepth++));
                    stack.push(']');
                    break;
                case '{':
                    line2.append(toChar(braceDepth++));
                    stack.push('}');
                    break;
                case ')':
                    if (!stack.isEmpty() && stack.peek() == ch) {
                        line2.append(toChar(--parenDepth));
                        parensAreOk &= parenDepth >= 0;
                        stack.pop();
                    } else {
                        parensAreOk = false;
                        // to pop or not to pop, that is the question
                        if (stack.isEmpty()) line2.append('E');
                        else line2.append(stack.pop());
                    }
                    break;
                case ']':
                    if (!stack.isEmpty() && stack.peek() == ch) {
                        line2.append(toChar(--bracketDepth));
                        bracketsAreOk &= bracketDepth >= 0;
                        stack.pop();
                    } else {
                        bracketsAreOk = false;
                        // to pop or not to pop, that is the question
                        if (stack.isEmpty()) line2.append('E');
                        else line2.append(stack.pop());
                    }
                    break;
                case '}':
                    if (!stack.isEmpty() && stack.peek() == ch) {
                        line2.append(toChar(--braceDepth));
                        bracesAreOk &= braceDepth >= 0;
                        stack.pop();
                    } else {
                        bracesAreOk = false;
                        // to pop or not to pop, that is the question
                        if (stack.isEmpty()) line2.append('E');
                        else line2.append(stack.pop());
                    }
                    break;
                case '\n':
                    line2.append('\n');
                    outText.append(line1);
                    if (line2.toString().trim().length() > 0) {
                        outText.append(line2);
                    }
                    line1.setLength(0);
                    line2.setLength(0);
                    break;
                default:
                    line2.append(' ');
                    break;
            }
        }
        if (line1.length() > 0) outText.append(line1).append('\n');
        if (line2.length() > 0) outText.append(line2).append('\n');
        
        parensAreOk &= parenDepth == 0;
        if (!parensAreOk) outText.append("*** Mismatched parentheses.\n");
        
        bracketsAreOk &= bracketDepth == 0;
        if (!bracketsAreOk) outText.append("*** Mismatched brackets.\n");
        
        bracesAreOk &= braceDepth == 0;
        if (!bracesAreOk) outText.append("*** Mismatched braces.\n");
        
        if (parensAreOk && bracketsAreOk && bracesAreOk) {
            outText.append("--- No problems detected. ---\n");
        }
        return outText.toString();
    }

    public String getName() {
        return "Nesting Checker";
    }

    public String getDescription() {
        return "Puts matching digits under matching (), [], and {}.";
    }
}
