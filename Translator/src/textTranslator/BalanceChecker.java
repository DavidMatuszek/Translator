package textTranslator;

import java.util.Stack;

/**
 * Checks ( ), [ ], and { } for balance.
 */
public class BalanceChecker implements TranslatorInterface {
    Stack<Character> stack = new Stack<Character>();
    StringBuilder outText = new StringBuilder();

    private char find_closer(char opener) {
        return ")]}".charAt("([{".indexOf(opener));
    }

    private void checkAndExtend(char ch) {
        //char should_match = opens.charAt(closes.indexOf(ch));
        if (stack.isEmpty()) {
            outText.append("\n*** Found '" + ch +
                    "' but there is nothing to close. ***\n");
            return;
        }
        char desired_close_symbol = find_closer(stack.pop());
        if (ch == desired_close_symbol) {
            return; // balanced, all is well
        }
        outText.append("\n*** Found '" + ch + "' but expected '" +
                desired_close_symbol + "'. ***\n");
    }

    public String translate(String text) {
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            outText.append(ch);
            switch (ch) {
                case '[': case '(': case '{':
                    stack.push(ch);
                    break;
                case ']': case ')': case '}':
                    checkAndExtend(ch);
                    break;
            }
        }
        return outText.toString();
    }

    public String getName() {
        return "Balance Checker";
    }

    public String getDescription() {
        return "Checks ( ), [ ], and { } for balance.";
    }
}
