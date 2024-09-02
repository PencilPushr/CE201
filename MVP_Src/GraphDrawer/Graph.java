package GraphDrawer;

import util.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public abstract class Graph extends JPanel {
    protected String m_GraphName;
    protected String m_XAxisName;
    protected String m_YAxisName;
    protected String m_GraphTypeName;

    protected int m_Scale;

    protected JButton m_Next;
    protected JButton m_Prev;

    protected int m_TopXAxisLine;

    protected int m_Slide = 0;

    final protected JFrame m_Frame = new JFrame();
    final protected ArrayList<Pair<String, Integer>> m_ValuesToPlot = new ArrayList<>();

    protected int m_WindowWidth;
    protected int m_WindowHeight;

    public Graph(String graphTypeName, String graphName, String xAxisName, String yAxisName, int scale) {
        this.m_GraphTypeName = graphTypeName;
        this.m_GraphName = graphName;
        this.m_XAxisName = xAxisName;
        this.m_YAxisName = yAxisName;
        this.m_Scale = scale;
        setLayout(null);
        //initWindow(35, 80);
        initWindow(35, 80);
        setupNextButton();
        setupPreviousButton();
    }

    void initWindow(float percentx, float precenty){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        percentx = Math.max(0.0f, Math.min(percentx * 0.01f, 1.0f));
        precenty = Math.max(0.0f, Math.min(precenty * 0.01f, 1.0f));
        float windowX = ((1.0f - percentx) * (float)screenSize.getWidth()) * 0.5f;
        float windowY = ((1.0f - precenty) * (float)screenSize.getHeight()) * 0.5f;
        m_WindowWidth = (int)(percentx * (float)screenSize.getWidth());
        m_WindowHeight = (int)(precenty * (float)screenSize.getHeight());
        m_Frame.setBounds((int)windowX, (int)windowY, m_WindowWidth, m_WindowHeight);
        m_Frame.setResizable(false);
        m_Frame.setTitle(m_GraphTypeName + " - " + m_GraphName);
        m_Frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        m_Frame.setVisible(false);
        m_Frame.add(this);
    }

    public void add(String name, int value){
        m_ValuesToPlot.add(new Pair<>(name, value));
        int adjust = 0;
        if ((int)(m_ValuesToPlot.size() % 10.0f) == 0)
            adjust = 1;
        m_Next.setVisible((int)(m_ValuesToPlot.size() / 10.0f) - m_Slide - adjust != 0);
    }

    int getScaledValue(int value){
        int scaleHeight = m_Scale * 20;
        float scalar = value / (float)scaleHeight;
        int diff = m_WindowHeight - 150 - m_TopXAxisLine;
        return (int)(scalar * diff);
    }

    void drawTitleAndAxisNames(Graphics2D gfx2d){
        gfx2d.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        int textWidthPx = gfx2d.getFontMetrics().stringWidth(m_GraphName);
        int titleTextX = (int)((m_WindowWidth * 0.5f) - (textWidthPx * 0.5f));
        gfx2d.drawString(m_GraphName, titleTextX, 20);

        AffineTransform defaultAt = gfx2d.getTransform();
        AffineTransform at = new AffineTransform();
        at.rotate(-Math.PI / 2);
        gfx2d.setTransform(at);

        gfx2d.setFont(new Font("TimesRoman", Font.PLAIN, 15));
        int yaxisHeightPx = gfx2d.getFontMetrics().stringWidth(m_YAxisName);
        int yaxisTextX = (int)((m_WindowHeight * 0.5f) - (yaxisHeightPx * 0.5f));
        gfx2d.drawString(m_YAxisName, -yaxisTextX, 20);

        gfx2d.setTransform(defaultAt);

        int xaxisWidthPx = gfx2d.getFontMetrics().stringWidth(m_XAxisName);
        int xAxisTextX = (int)((m_WindowWidth * 0.5f) - (xaxisWidthPx * 0.5f));
        gfx2d.drawString(m_XAxisName, xAxisTextX, m_WindowHeight - 50);
    }

    public static void drawRotate(Graphics2D g2d, double x, double y, int angle, String text) {
        g2d.translate((float)x,(float)y);
        g2d.rotate(Math.toRadians(angle));
        g2d.drawString(text,0,0);
        g2d.rotate(-Math.toRadians(angle));
        g2d.translate(-(float)x,-(float)y);
    }

    void drawPlotLabels(Graphics2D gfx2d){
        int loops = 10;
        if (m_Slide == (int)(m_ValuesToPlot.size() / 10.0f)) {
            loops = m_ValuesToPlot.size() % 10;
            if (loops == 0)
                loops = m_ValuesToPlot.size() % 10 - 1;
        }
        for (int i = 0; i < loops; i++){
            Pair<String, Integer> value = m_ValuesToPlot.get(i + (m_Slide * 10));
            gfx2d.setColor(getPlotColor(i));
            gfx2d.setFont(new Font("TimesRoman", Font.PLAIN, 15));
            gfx2d.drawString(Integer.toString(value.second()), 120 + i * 50, m_WindowHeight - 130);
            gfx2d.setFont(new Font("TimesRoman", Font.PLAIN, 13));
            drawRotate(gfx2d, 120 + i * 50, m_WindowHeight - 65, 310, value.first());
        }
        gfx2d.setColor(Color.black);
    }

    abstract void drawGraph(Graphics2D gfx2d);

    void drawAxis(Graphics2D gfx2d){
        gfx2d.drawLine(100, 50, 100, m_WindowHeight - 150);
        gfx2d.drawLine(100, m_WindowHeight - 150, m_WindowWidth - 50, m_WindowHeight - 150);
        int yAxisPx = m_WindowHeight - 150;
        int yNow = yAxisPx;
        int quarterTextHeight = (int)(gfx2d.getFontMetrics().getHeight() * 0.15f);
        for (int i = 0; i < 21; i++){
            if (i == 20){
                m_TopXAxisLine = yNow;
            }
            gfx2d.drawString(Integer.toString(i * m_Scale), 35, yNow + quarterTextHeight);
            yNow -= (int)(yAxisPx / 23.0f);
        }
    }

    @Override
    protected void paintComponent(Graphics gfx){
        Graphics2D gfx2d = (Graphics2D)gfx;
        gfx2d.setColor(new Color(255, 255, 255));
        gfx2d.fillRect(0, 0, m_WindowWidth, m_WindowHeight);
        gfx2d.setColor(new Color(10, 10, 10));
        drawTitleAndAxisNames(gfx2d);
        drawAxis(gfx2d);
        drawPlotLabels(gfx2d);
        drawGraph(gfx2d);
        repaint();
    }

    void setupNextButton(){
        m_Next = new JButton("Next");

        m_Next.setBounds(m_WindowWidth - 90, m_WindowHeight - 68, 75, 30);
        m_Next.addActionListener(e-> {
            if (e.getSource() == m_Next){
                if (m_Slide <= (int)(m_ValuesToPlot.size() / 10.0f) - 1){
                    m_Prev.setVisible(true);
                    m_Slide++;
                }
                int adjust = 0;
                if ((int)(m_ValuesToPlot.size() % 10.0f) == 0)
                    adjust = 1;
                m_Next.setVisible((int)(m_ValuesToPlot.size() / 10.0f) - m_Slide - adjust != 0);
            }
        });
        add(m_Next);
    }

    void setupPreviousButton(){
        m_Prev = new JButton("Prev");
        m_Prev.setBounds(-1, m_WindowHeight - 68, 80, 30);
        m_Prev.setVisible(m_Slide > 0);
        m_Prev.addActionListener(e-> {
            if (e.getSource() == m_Prev){
                if (m_Slide > 0) {
                    m_Slide--;
                }
                m_Prev.setVisible(m_Slide != 0);
                m_Next.setVisible(true);
            }
        });
        add(m_Prev);
    }

    Color getPlotColor(int i) {
        return new Color(100,
                (i + (m_Slide * 10)) * (180 / m_ValuesToPlot.size()),
                150);
    }

    public void setGraphName(String name){
        m_GraphName = name;
        m_Frame.setTitle(m_GraphTypeName + " - " + m_GraphName);
    }

    public void setXAxisName(String name){
        m_XAxisName = name;
    }

    public void setYAxisName(String name){
        m_YAxisName = name;
    }

    public void setScale(int scale){
        m_Scale = scale;
    }

    public void show(){
        m_Frame.setVisible(true);
    }
}
