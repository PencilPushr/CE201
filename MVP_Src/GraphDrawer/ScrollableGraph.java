package GraphDrawer;

import util.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;

abstract class ScrollableGraph extends JPanel{
    protected int m_WindowWidth;
    protected int m_WindowHeight;
    protected int m_NoYUnits = 10;

    Rectangle m_ScrollRect = new Rectangle();

    protected int m_paddingYTop = 50;
    protected int m_PaddingX = 60;
    protected int m_PaddingYBottom = 200;
    protected int m_NumberFontSize = 15;

    protected int m_XUnitPadding = 50;
    protected int m_Scale = 10;

    protected String m_GraphName = "";
    protected String m_XAxisName = "";
    protected String m_YAxisName = "";
    protected String m_GraphTypeName = "";

    private final JFrame m_Frame = new JFrame();
    final protected ArrayList<Pair<String, Integer>> m_ValuesToPlot = new ArrayList<>();

    protected final int m_HalfMarkerSize = 5;
    protected final int m_AxisNameSize = 20;

    ScrollableGraph(String graphTypeName){
        initWindow(70, 70);
        initScrollBar();
        ScrollMover scrollMover = new ScrollMover();
        addMouseListener(scrollMover);
        addMouseMotionListener(scrollMover);
        addMouseWheelListener(e -> {
            float scrollSens = 10.0f;
            m_ScrollRect.x -= e.getWheelRotation() * scrollSens;
            m_ScrollRect.x = Math.max(m_PaddingX, Math.min(m_WindowWidth - m_PaddingX - m_ScrollRect.width, m_ScrollRect.x));
            repaint();
        });
        m_GraphTypeName = graphTypeName;
    }

    class ScrollMover extends MouseAdapter {
        private int lastMousePos = 0;
        @Override
        public void mousePressed (MouseEvent e){
            lastMousePos = e.getX();
        }

        @Override
        public void mouseDragged (MouseEvent e){
            m_ScrollRect.x += e.getX() - lastMousePos;
            m_ScrollRect.x = Math.max(m_PaddingX, Math.min(m_WindowWidth - m_PaddingX - m_ScrollRect.width, m_ScrollRect.x));
            lastMousePos = e.getX();
            repaint();
        }
    }

    void initScrollBar(){
        m_ScrollRect.x = m_PaddingX;
        int spaceForNames = 100;
        m_ScrollRect.y = m_WindowHeight - m_PaddingYBottom + spaceForNames;
        m_ScrollRect.width = m_WindowWidth - (m_PaddingX * 2);
        m_ScrollRect.height = 10;
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
        m_Frame.setVisible(true);
        m_Frame.add(this);
    }

    public static void drawRotate(Graphics2D g2d, double x, double y, int angle, String text) {
        g2d.translate((float)x,(float)y);
        g2d.rotate(Math.toRadians(angle));
        g2d.drawString(text,0,0);
        g2d.rotate(-Math.toRadians(angle));
        g2d.translate(-(float)x,-(float)y);
    }

    void drawAxisNamesAndTitle(Graphics2D gfx2d){
        Font defaultFont = gfx2d.getFont();
        Font font = new Font("serif", Font.PLAIN, m_AxisNameSize);
        gfx2d.setFont(font);
        int textWidthPx = gfx2d.getFontMetrics().stringWidth(m_GraphName);
        int textX = (int)((m_WindowWidth * 0.5f) - (textWidthPx * 0.5f));
        gfx2d.drawString(m_GraphName, textX, 20);
        drawRotate(gfx2d, m_AxisNameSize, (int)(m_WindowHeight * 0.5f), 270, m_YAxisName);
        gfx2d.drawString(m_XAxisName, textX, m_WindowHeight - 100);
        gfx2d.setFont(defaultFont);
    }

    void drawXAxis(Graphics2D gfx2d){
        int y = m_WindowHeight - m_PaddingYBottom - m_NumberFontSize;
        Font defaultFont = gfx2d.getFont();
        Font font = new Font("serif", Font.PLAIN, m_NumberFontSize);
        gfx2d.setFont(font);
        int padSpace = 6;
        int i;
        for (i = 0; i < m_ValuesToPlot.size(); i++){
            int x = (i * m_XUnitPadding) - scrollOffset() + m_PaddingX;
            gfx2d.drawLine(x, y - m_HalfMarkerSize, x, y + m_HalfMarkerSize);
            drawRotate(gfx2d, x + 10, y + m_NumberFontSize + 70, -60, m_ValuesToPlot.get(i).first());
            gfx2d.drawString(String.valueOf(m_ValuesToPlot.get(i).second()), x, y + m_NumberFontSize + padSpace);
        }
        int x = ((i - 1) * m_XUnitPadding) - scrollOffset() + m_PaddingX;
        gfx2d.drawLine(m_PaddingX, y, Math.max(x, m_WindowWidth - m_PaddingX), y);
        gfx2d.setFont(defaultFont);
    }

    void drawYAxis(Graphics2D gfx2d){
        gfx2d.drawLine(m_PaddingX, m_paddingYTop, m_PaddingX, m_WindowHeight - m_PaddingYBottom - m_NumberFontSize);
        for (int i = 0; i < m_NoYUnits; i++){
            int x = m_PaddingX;
            int y = (int)(getGraphHeight() / (float)m_NoYUnits) * i + m_paddingYTop;
            gfx2d.drawLine(x - m_HalfMarkerSize, y, x + m_HalfMarkerSize, y);
            gfx2d.drawString(String.valueOf((m_NoYUnits - i) * m_Scale), x - m_HalfMarkerSize - 20 , y);
        }
    }

    void drawScroll(Graphics2D gfx2d){
        gfx2d.setColor(new Color(154, 151, 151));
        gfx2d.fillRect(m_ScrollRect.x, m_ScrollRect.y + m_HalfMarkerSize + m_NumberFontSize,
                m_ScrollRect.width, m_ScrollRect.height);
    }

    public void add(String name, int value){
        m_ValuesToPlot.add(new Pair<>(name, value));
        float screenPlotSpace = m_WindowWidth - (m_PaddingX * 2.0f);
        float totalPlotSpace = m_ValuesToPlot.size() * m_XUnitPadding;
        float barMinSizeRatio = 0.03f;
        float unitPlotSpace =  Math.min(1, Math.max(barMinSizeRatio, screenPlotSpace / totalPlotSpace));
        m_ScrollRect.width = (int)(unitPlotSpace * m_WindowWidth);
    }

    Color getPlotColor(int i) {
        int screenGraphSpace = m_WindowWidth - (m_PaddingX * 2);
        int scrollWidth = screenGraphSpace - m_ScrollRect.width;
        float scrollRel = (m_ScrollRect.x - m_PaddingX) / (float)scrollWidth;
        return new Color(100,
                (int)(scrollRel * (180.0f / m_ValuesToPlot.size())),
                150);
    }

    protected int scrollOffset(){
        int screenGraphSpace = m_WindowWidth - (m_PaddingX * 2);
        int scrollWidth = screenGraphSpace - m_ScrollRect.width;
        float scrollRel = (m_ScrollRect.x - m_PaddingX) / (float)scrollWidth;
        float totalGraphSpace = m_ValuesToPlot.size() * m_XUnitPadding;
        return (int)((totalGraphSpace - screenGraphSpace) * scrollRel);
    }

    protected int getScaledYValue(int value){
        return (int)((1 - (value / 100.0f)) * getGraphHeight()) + m_paddingYTop;
    }

    public abstract void drawGraph(Graphics2D gfx2d);

    @Override
    public void paintComponent(Graphics gfx){
        super.paintComponent(gfx);
        Graphics2D gfx2d = (Graphics2D)gfx;
        gfx2d.setColor(new Color(255, 255, 255));
        gfx2d.fillRect(0, 0, m_WindowWidth, m_WindowHeight);
        gfx2d.setColor(new Color(10, 10, 10));
        drawXAxis(gfx2d);
        drawScroll(gfx2d);
        drawGraph(gfx2d);
        gfx2d.setColor(Color.WHITE);
        gfx2d.fillRect(0, 0, m_PaddingX, m_WindowHeight);
        gfx2d.fillRect(m_WindowWidth - m_PaddingX, 0, m_PaddingX, m_WindowHeight);
        gfx2d.setColor(Color.BLACK);
        drawYAxis(gfx2d);
        drawAxisNamesAndTitle(gfx2d);
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

    protected int getGraphX(){
        return m_PaddingX;
    }

    protected int getGraphY(){
        return  m_paddingYTop;
    }

    protected int getGraphWidth(){
        return m_WindowWidth - (m_PaddingX * 2);
    }

    protected int getGraphHeight(){
        return m_WindowHeight - m_PaddingYBottom - m_NumberFontSize - m_paddingYTop;
    }
}
