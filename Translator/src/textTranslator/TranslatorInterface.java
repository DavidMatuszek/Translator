package textTranslator;



public interface TranslatorInterface {

    /**
     * Translates an input string to an output string.
     *
     * @param text The string to be translated.
     * @return The translation of the input string.
     */
    String translate(String text);

    /**
     * Returns the name of this translator, to be used in
     *  menu items and the like.
     *
     * @return The name of this translator.
     */
    String getName();

    /**
     * Provides a  short phrase describing the action of
     * this translator.
     *
     * @return A short description of what this translator does.
     */
    String getDescription();
}
