package textTranslator;
/**
 * Provides an outline for classes that translate strings from
 * one form to another.
 * 
 * @author David Matuszek
 * @version Aug 12, 2007
 */
abstract class AbstractTranslator {
    
    /**
     * Performs any initialization needed for this Translator,
     * most likely by bringing up a dialog box in which to
     * choose options.
     */
    void initialize() { }
    
    /**
     * Translates an input string to an output string.
     * 
     * @param text The string to be translated.
     * @return The translation of the input string.
     */
    abstract String translate(String text);
    
    /**
     * Provides a meaningful name for the action of
     * this translator, to be used in menu items and the like.
     * 
     * @return A very short description of what this translator does.
     */
    abstract String getName();
    
    /**
     * Provides a description of the action of this translator.
     * @return A description of what this translator does.
     */
    String getDescription() {
        return "No description provided.";
    }
}
