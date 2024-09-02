package MVP_src;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
*    Creates a button. Text for the button is passed in through constructor.
*    Clicking the button will open a file selector.
*    setAction function can be used to set an action. Action describes what
*    should happen when a file is selected by the user.
*    Action is an interface with "perform" as a method that is required to be
*    provided to setAction. This "perform" method gets path as an argument. This
*    path is the path of the file that is selected by the user.
*/

public class FileSelector implements ActionListener {

    /**
     * Main interface derive any file actions
     */
    public interface Action{
        void Perform(String path);
    }

    /**
     * utility Variables
     */
    final private JButton m_Button;
    final private JFileChooser m_FileChooser;
    private boolean m_GetSuccess = false;
    private Action m_Action = null;

    /**
     * Constructor creating a button with a passed name and a member variable File chooser
     * @param name
     */
    FileSelector(String name){
        m_Button = new JButton(name);
        m_FileChooser = new JFileChooser();
        m_Button.addActionListener(this);
    }

    //File extensions to allow. For example, setExtensionFilter("txt", "csv") would
    //only allow text and csv files to be selected.

    /**
     * Takes in a String that defines what type of file will be inputted.
     * Such as: csv, plaintext ...etc.
     *
     * @param ext the String to be inputted
     */
    public void setExtensionFilter(String... ext){
        FileNameExtensionFilter filter = new FileNameExtensionFilter("", ext);
        m_FileChooser.setFileFilter(filter);
    }

    /**
     * Set what should happen when the user has selected a file.
     * @param action
     */
    public void setAction(Action action){ m_Action = action; }

    /**
     * Set the position and size of the button.
     * The variables X and Y define the coordinate bounds
     * W and H define size of the button.
     * @param x X coordinate
     * @param y Y coordinate
     * @param w Width
     * @param h Height
     */
    public void setBounds(int x, int y, int w, int h){ m_Button.setBounds(x, y, w, h); }

    /**
     * Method that identifies which JFrame should the button be displayed on.
     * @param frame
     */
    public void attachTo(JFrame frame){
        frame.add(m_Button);
    }

    /**
     * member variable to declare if the file was opened successfully.
     * @return boolean
     */
    public boolean successful(){ return m_GetSuccess; }

    /**
     * Refreshes or draws the current button
     */
    public void repaint(){ m_Button.repaint(); }

    /**
     * Mutator method to set the text of the button member
     * @param text Button text to be displayed
     */
    public void setButtonText(String text){
        m_Button.setText(text);
    }

    /**
     * Method that checks if there is a button press event and creates a popup to select a file.
     * @param e Event on an action performed by a relevant ActionListener
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == m_Button){
            if (m_FileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
                String path = m_FileChooser.getSelectedFile().getAbsolutePath();
                if(!new File(path).isFile()){
                    JOptionPane.showMessageDialog(null, "Invalid File");
                    return;
                }
                m_GetSuccess = true;
                m_Action.Perform(path);
                return;
            }
            m_GetSuccess = false;
        }
    }
}
