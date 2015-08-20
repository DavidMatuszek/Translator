# Translator
Various transformations on text files

This program is designed to perform various translations on text, such as plain text to
PowerPoint, or wrapping lines, or checking parentheses. It is modular, so that a Java
programmer can easily add transformations.

To add a translator:
- Duplicate and rename **IdentityTranslator.java**.
- Edit the three included functions. The most important, **translate**, takes a String and returns the transformed String.
- Add a call to **addTranslateItem** in the **Translate.java** class.

Translation is performed when the Translate button is clicked, or when a new translation is chosen from the menu.

It would be nice if the program were to put the translated string into the copy/paste buffer, but this would require the user to reduce security levels.
