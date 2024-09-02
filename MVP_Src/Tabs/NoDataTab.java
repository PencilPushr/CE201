package Tabs;
import javax.swing.*;
import java.awt.*;

final class NoDataLayout{
    private NoDataLayout(){}
    static final public float fileSelectorWidthPer = 0.25f;//25 percent of window width
    static final public int fileSelectorMinWidthPx = 140;//140 pixels
}

public class NoDataTab extends JPanel {
    private final JFrame m_Frame;

    /**
     * Instantiates member frame to the parsed frame while the landing tab has no data.
     * @param frame Main frame to be parsed
     */
    public NoDataTab(JFrame frame){
        m_Frame = frame;
    }


    public void paintComponent(Graphics gfx){
        super.paintComponent(gfx);
        gfx.setFont(new Font(gfx.getFont().getFontName(), Font.PLAIN, 25));
        gfx.drawString("Upload data to start analyzing",
                Math.max(NoDataLayout.fileSelectorMinWidthPx,
                        (int)(m_Frame.getWidth() * NoDataLayout.fileSelectorWidthPer)),
                (int)(m_Frame.getHeight() * 0.5f));
    }
}
