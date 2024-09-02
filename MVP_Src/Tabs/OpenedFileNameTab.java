package Tabs;

import javax.swing.*;
import java.awt.*;

/**
 * A class to be used to display the file path/name of the opened file by user
 */
public class OpenedFileNameTab extends JPanel {

    private JTextArea m_TextArea;

    public OpenedFileNameTab(){
        setupTextArea();
        setLayout(new GridLayout());
    }

    /**
     * Mutator method to set text area to the file name provided
     * @param name File name
     */
    public void setFileName(String name){
        m_TextArea.setText(name);
    }

    /**
     * Creates the text area to be utilised within the sidebar
     */
    private void setupTextArea(){
        m_TextArea = new JTextArea();
        m_TextArea.setEditable(false);
        m_TextArea.setLineWrap(true);
        m_TextArea.setFont(new Font("Calibri", Font.PLAIN, 16));
        add(new JScrollPane(m_TextArea));
    }
}
