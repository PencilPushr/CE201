package Tabs;

import javax.swing.*;
import java.awt.*;

public class TextOutputTab extends JPanel {

    private JTextArea m_TextArea;

    /**
     *
     */
    public TextOutputTab(){
        setLayout(new GridLayout());
        setupTextArea();
    }

    /**
     *
     * @param name
     */
    public void setOutput(String name){
        m_TextArea.setText(name);
    }

    /**
     *
     */
    private void setupTextArea(){
        m_TextArea = new JTextArea();
        m_TextArea.setEditable(false);
        m_TextArea.setFont(new Font("Calibri", Font.PLAIN, 16));
        add(new JScrollPane(m_TextArea));
    }
}