/**
 * 
 */
package textTranslator;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author David Matuszek
 */
public class UnderscoresToCamelCaseTranslatorTest {
    TranslatorInterface t;
    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        t = new UnderscoresToCamelCaseTranslator();
    }

    /**
     * Test method for {@link textTranslator.UnderscoresToCamelCaseTranslator#translate(java.lang.String)}.
     */
    @Test
    public void testTranslate() {
        assertEquals("helloWorld", t.translate("hello_world"));
        assertEquals("_a__b_", t.translate("_a__b_"));
        assertEquals("[_file1, _file2]", t.translate("[_file1, _file2]"));
        assertEquals("searchStringEntryField = Entry(searchFrame)",
                     t.translate("search_string_entry_field = Entry(search_frame)"));
        assertEquals("def __init__(self, master=None)", t.
                     translate("def __init__(self, master=None)"));
        assertEquals("TEXT_WIDTH", t.translate("TEXT_WIDTH"));
    }
}
