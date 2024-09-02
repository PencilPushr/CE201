package MVP_src;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;

class GUI{

    /**
     * Creates the main frame for the program to run off.
     * Applies a theme and creates an Object of HomePage to parse studentInfo.
     *
     * @param percent size of the desired frame based on a percentage of the current screen size
     * @throws Exception Custom Exception, unable to set the UI theme.
     */
    public GUI(float percent) {
        JFrame frame = new JFrame();
        Color color = new Color(0, 61, 227);
        UIManager.put("nimbusBase", color);
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                }catch (Exception e){
                    JOptionPane.showMessageDialog(null, "Couldn't set look and feel");
                }
                break;
            }
        }


        initFrame(frame, percent);
        new HomePage(frame, new StudentInfo(), new AllModules());

    }

    /**
     * Sets properties of the main window frame such that:
     * 1. The window takes up "percent" percentage of the monitor size.
     * 2. The window is centered in the monitor.
     * 3. IsetLayout(new FlowLayout());t is non-resizeable.
     *
     * @param frame the JFrame to be parsed
     * @param percent size of the desired frame based on a percentage of the current screen size
     */
    private void initFrame(JFrame frame, float percent){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        percent = Math.max(0.0f, Math.min(percent * 0.01f, 1.0f));
        float windowX = ((1.0f - percent) * (float)screenSize.getWidth()) * 0.5f;
        float windowY = ((1.0f - percent) * (float)screenSize.getHeight()) * 0.5f;
        float windowW = percent * (float)screenSize.getWidth();
        float windowH = percent * (float)screenSize.getHeight();
        frame.setBounds((int)windowX, (int)windowY, (int)windowW, (int)windowH);
        //if we want to resize the frame
        frame.setResizable(false);
        frame.setTitle("Team Project");
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

