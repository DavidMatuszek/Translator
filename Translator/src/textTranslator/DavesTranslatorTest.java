package textTranslator;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;


public class DavesTranslatorTest {
    TranslatorInterface t;
    String message = "We the People of the United States, in" +
    		" Order to form a more perfect Union, establish" +
    		" Justice, insure domestic Tranquility, provide" +
    		" for the common defence, promote the general" +
    		" Welfare, and secure the Blessings of Liberty" +
    		" to ourselves and our Posterity, do ordain and" +
    		" establish this Constitution for the United" +
    		" States of America.";
    String result;

    @Before
    public void setUp() throws Exception {}

    @Test
    public void testIdentity() {
        t = new IdentityTranslator();
        result = t.translate(message);
        assertEquals(message, result);
    }
    
    @Test
    public void testDetab() {
        String input =
            "\tOne, two,\n\t\tBuckle my shoe,\nThree,\t" +
            " four,\n\t\t  Close the door.";
        String expected =
            "    One, two,\n        Buckle my shoe,\nThree,\t" +
            " four,\n          Close the door.";
        String actual = new DetabTranslator().translate(input);
        actual = chomp(actual);
        assertEquals(expected, actual);
    }
    
    @Test
    public void testEntab() {
        String input =
            "    One, two,\n        Buckle my shoe,\nThree,\t" +
            " four,\n          Close the door.";
        String expected =
            "\tOne, two,\n\t\tBuckle my shoe,\nThree,\t" +
            " four,\n\t\t  Close the door.";
        String actual = new EntabTranslator().translate(input);
        actual = chomp(actual);
        assertEquals(expected, actual);
    }
    
    @Test
    @Ignore("Not part of CIT591-2011 assignment")
    public void testUnixNewlines() {
        String input =
            "Fiddle de diddle\015\012And fol de rol\n" +
            "Not every parrot\015\012Can be a poll.";
        String expected =
            "Fiddle de diddle\012And fol de rol\012" +
            "Not every parrot\012Can be a poll.";
        String actual = new UnixNewlinesTranslator().translate(input);
        actual = chomp(actual);
        assertEquals(expected, actual);
    }
    
    @Test
    @Ignore("Not part of CIT591-2011 assignment")
    public void testWindowsNewlines() {
        String input =
            "Fiddle de diddle\012And fol de rol\n" +
            "Not every parrot\015\012Can be a poll.";
        String expected =
            "Fiddle de diddle\015\012And fol de rol\015\012" +
            "Not every parrot\015\012Can be a poll.";
        String actual = new WindowsNewlinesTranslator().translate(input);
        actual = chomp(actual);
        assertEquals(expected, actual);
    }
    
    @Test
    public void testSingleSpace() {
        String newline = System.getProperty("line.separator");
        String input =
            "one\ntwo\n\nthree\n\n\nfour\n\n\n\nfive\n\n\n\n\nsix\n\n\n\n\n\nend";
        String expected =
            "one\ntwo\nthree\n\nfour\n\nfive\n\n\nsix\n\n\nend";
        expected = expected.replaceAll("\n", newline);
        String actual = new SingleSpaceTranslator().translate(input);
        actual = chomp(actual);
        assertEquals(expected, actual);
    }
    
    @Test
    public void testDoubleSpace() {
        String newline = System.getProperty("line.separator");
        String input =
            "one\ntwo\n\nthree\n\n\nfour\n\n\n\nend";
        String expected =
            "one\n\ntwo\n\n\n\nthree\n\n\n\n\n\nfour\n\n\n\n\n\n\n\nend";
        expected = expected.replaceAll("\n", newline);
        String actual = new DoubleSpaceTranslator().translate(input);
        actual = chomp(actual);
        assertEquals(expected, actual);
    }
    
    @Test
    public void testWrapTextWhenNotIndented() {
        WrapTextTranslator t = new WrapTextTranslator();
        String input = "Wraps lines so that no line is longer than 72 characters. Long lines should" +
        		" be broken at a space character, with the remainder put on a new line with" +
        		" the same indentation as the original. Very long lines will result in" +
        		" several shorter lines. If a line contains more than 72 consecutive" +
        		" non-space characters (as often happens with a URL), the long \"word\" is" +
        		" not broken, but is put on a line by itself. This method does not join" +
        		" lines, it only wraps long lines.";
        String expected = "Wraps lines so that no line is longer than 72 characters. Long lines\n" + 
        		"should be broken at a space character, with the remainder put on a new\n" + 
        		"line with the same indentation as the original. Very long lines will\n" + 
        		"result in several shorter lines. If a line contains more than 72\n" + 
        		"consecutive non-space characters (as often happens with a URL), the long\n" + 
        		"\"word\" is not broken, but is put on a line by itself. This method does\n" + 
        		"not join lines, it only wraps long lines.";
        expected = normalizeNewlines(expected);
        String actual = normalizeNewlines(t.translate(input).trim());
//        dump("expected", expected);
//        dump("actual  ", actual);
        assertEquals(expected, actual);
    }
    
    @Test
    public void testWrapTextWhenIndented() {
        WrapTextTranslator t = new WrapTextTranslator();
        String input = "     Wraps lines so that no line is longer than 72 characters. Long lines should" +
                       " be broken at a space character, with the remainder put on a new line with" +
                       " the same indentation as the original. Very long lines will result in" +
                       " several shorter lines.\n   If a line contains more than 72 consecutive" +
                       " non-space characters (as often happens with a URL), the long \"word\" is" +
                       " not broken, but is put on a line by itself. This method does not join" +
                       " lines, it only wraps long lines.";
        String expected = "     Wraps lines so that no line is longer than 72 characters. Long\n" + 
        		  "     lines should be broken at a space character, with the remainder put\n" + 
        		  "     on a new line with the same indentation as the original. Very long\n" + 
        		  "     lines will result in several shorter lines.\n" + 
        		  "   If a line contains more than 72 consecutive non-space characters (as\n" + 
        		  "   often happens with a URL), the long \"word\" is not broken, but is put\n" + 
        		  "   on a line by itself. This method does not join lines, it only wraps\n" + 
        		  "   long lines.";
        expected = normalizeNewlines(expected);
        String actual = chomp(chomp(normalizeNewlines(t.translate(input))));
//        dump("expected", expected);
//        dump("actual  ", actual);
        assertEquals(expected, actual);
    }

    
    @Test
    public void testFlowText() {
        FlowTextTranslator t = new FlowTextTranslator();
        
        String input = "Flows text (the way web pages do).\nLines longer" +
                       " than 72 characters should be wrapped (as in the Wrap" +
                       " Text translation), and short lines should be joined" +
                       " with the following line, if the following line is" +
                       " nonempty.\n\nSeparate paragraphs, separated by a blank" +
                       " line, should be kept separate.\n(Note: A \"blank line\"" +
                       " is any line that is visibly blank; it may contain spaces" +
                       " or tabs, which should be removed.) ";
        String expected = "Flows text (the way web pages do). Lines longer than 72 characters\n" + 
                          "should be wrapped (as in the Wrap Text translation), and short lines\n" + 
                          "should be joined with the following line, if the following line is\n" + 
                          "nonempty.\n" + 
                          "\n" + 
                          "Separate paragraphs, separated by a blank line, should be kept separate.\n" + 
                          "(Note: A \"blank line\" is any line that is visibly blank; it may contain\n" + 
                          "spaces or tabs, which should be removed.)";
        expected = normalizeNewlines(expected);
        String actual = normalizeNewlines(t.translate(input).trim());
        assertEquals(expected, actual);
        
        input = "abc def 123456789x123456789x123456789x123456789x123456789x123456789x123456789x12 xyz";
        expected = "abc def\n123456789x123456789x123456789x123456789x123456789x123456789x123456789x12\nxyz";
        expected = normalizeNewlines(expected);
        actual = normalizeNewlines(t.translate(input).trim());
        assertEquals(expected, actual);
    }

    @Test
    @Ignore("Not part of CIT591-2011 assignment")
    public void testZapGremlins() {
        ZapGremlinsTranslator t = new ZapGremlinsTranslator();
        String input = "abc\f \000xyz \007\007abc\b\nxyz";
        String expected = "abc xyz abc\nxyz";
        assertEquals(expected, t.translate(input));
        
        
    }
    
    @Test
    public void testFixIndentationBasic() {
        FixIndentationTranslator t = new FixIndentationTranslator();
        String input = "  This is a\n" + 
                "  very basic {\n" + 
                "  test of\n" + 
                "  } the\n" + 
                "  indentation {\n" + 
                "        fixer {\n" + 
                "   translator\n" + 
                "      }\n" + 
                "              xxx\n" + 
                "    }\n" + 
                "         end";
        String expected = "  This is a\n" + 
                "  very basic {\n" + 
                "      test of\n" + 
                "  } the\n" + 
                "  indentation {\n" + 
                "      fixer {\n" + 
                "          translator\n" + 
                "      }\n" + 
                "      xxx\n" + 
                "  }\n" + 
                "  end";
        expected = normalizeNewlines(expected);
        String actual = normalizeNewlines(t.translate(input));
        assertEquals(expected, actual);
    }
    
    @Test
    public void testFixIndentationWithCancellation() {
        FixIndentationTranslator t = new FixIndentationTranslator();
        String input =  "First line\n" +
        		"This is a { test }\n" + 
                "    that {matched braces in the middle} do not\n" + 
                "    affect the {\n" + 
                "  indentation\n" + 
                "                     } level";
        String expected = "First line\n" +
        		"This is a { test }\n" + 
        		"that {matched braces in the middle} do not\n" + 
        		"affect the {\n" + 
        		"    indentation\n" + 
        		"} level";
        expected = normalizeNewlines(expected);
        String actual = normalizeNewlines(t.translate(input));
        assertEquals(expected, actual);
    }
    
    @Test
    public void testFixIndentationWithNegativeIndents() {
        FixIndentationTranslator t = new FixIndentationTranslator();
        String input =  "  What goes down\r\n" + 
        		"        must }\n" + 
        		"come }\n" + 
        		"  up again {\n" + 
        		"    and out {\n" + 
        		"to its starting point";
        String expected = "  What goes down\n" + 
        		"must }\n" + 
        		"come }\n" + 
        		"up again {\n" + 
        		"and out {\n" + 
        		"  to its starting point";
        expected = normalizeNewlines(expected);
        String actual = normalizeNewlines(t.translate(input));
        assertEquals(expected, actual);
    }

    /**
     * Replaces all '\015\012' or '\015' newlines with '\012' newlines.
     * 
     * @param text The text to be transformed.
     * @return The resultant text.
     */
    String normalizeNewlines(String text) {
        text = text.replaceAll("\015\012", "\012");
        text = text.replaceAll("\015", "\012");
        return text;
    }
    
    /**
     * If the given string ends with a newline character, remove it,
     * otherwise return the string unchanged.
     * 
     * @param line The string to be chomped.
     * @return The chomped string.
     */
    private String chomp(String line) {
        if (line.endsWith("\n")) {
            line = line.substring(0, line.length() - 1);
        }
        return line;
    }
    
    /**
     * Prints the given label, followed by the given text, where each
     * nonprinting character in the text (except space) is printed as
     * its digital ASCII code, enclosed in brackets.
     * 
     * @param label A label for the output.
     * @param text The text to be printed, with visible whitespace.
     */
    private void dump(String label, String text) {
        System.out.print(label + ":  ");
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            System.out.print(ch < 32 ? "[" + (ch + 0) + "]" : ch);
        }
        System.out.println();
    }

}
