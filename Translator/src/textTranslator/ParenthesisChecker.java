package textTranslator;

import java.util.Stack;

/**
 * @author David Matuszek
 */
public class ParenthesisChecker implements TranslatorInterface {

    public String translate(String text) {
        StringBuilder newText = new StringBuilder(1000);
        Stack<Character> stack = new Stack<Character>();
        for (int i = 0; i < text.length(); i++) {
            
            // copy the character
            char ch = text.charAt(i);
            newText.append(ch);
            
            // if newline, note the fact and go on to next character
            boolean beginningOfLine = ch == '\n';
            if (beginningOfLine) continue;
            
            // handle opener at beginning of line
            if (beginningOfLine && in(ch, "({[")) {
                if (!stack.isEmpty()) reportError(ch, stack, newText);
                stack.clear();
                stack.push(ch);
                continue;
            }
            
            // handle grouping symbols elsewhere
            if (in(ch, "([{")) {
                stack.push(ch);
            } else if (in(ch, ")]}")) {
                if (stack.isEmpty()) {
                    reportError(ch, stack, newText);
                } else if (ch == ')' && stack.peek() == '(') {
                    stack.pop();
                } else if (ch == ']' && stack.peek() == '[') {
                    stack.pop();
                } else if (ch == '}' && stack.peek() == '{') {
                    stack.pop();
                } else {
                    reportError(ch, stack, newText);
                }
            }
        }
        if (!stack.isEmpty()) {
            reportError("End of file", stack, newText);
        }
        return newText.toString();
    }

    /**
     * @param string
     * @param stack
     * @param newText
     */
    private void reportError(String string, Stack<Character> stack,
                             StringBuilder newText) {
        newText.append("\n\n*** Encountered '" + string + "' while stack ");
        if (stack.isEmpty()) {
            newText.append("is empty. ***\n");
        } else {
            newText.append("top element is '" + stack.peek() + "'. ***\n");
        }
    }

    /**
     * @param ch
     * @param stack
     * @param newText
     */
    private void reportError(char ch, Stack<Character> stack,
                             StringBuilder newText) {
        reportError(ch + "", stack, newText);
    }

    /**
     * Checks if the character is in the string.
     */
    private boolean in(char ch, String string) {
        return string.indexOf(ch) >= 0;
    }

    public String getName() {
        return "Parenthesis Checker";
    }

    public String getDescription() {
        return "Matches parentheses, brackets, and braces.\n" +
               "Restarts counting when an opener occurs in column 1.";
    }
}