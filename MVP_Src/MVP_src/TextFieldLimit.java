package MVP_src;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * @see "http://www.java2s.com/Code/Java/Swing-JFC/LimitJTextFieldinputtoamaximumlength.htm"
 *  For more info.
 */
public class TextFieldLimit extends PlainDocument {
    private int limit;
    public TextFieldLimit(int limit) {
        super();
        this.limit = limit;
    }

    public TextFieldLimit(int limit, boolean upper) {
        super();
        this.limit = limit;
    }

    public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
        if (str == null)
            return;

        if ((getLength() + str.length()) <= limit) {
            super.insertString(offset, str, attr);
        }
    }
}