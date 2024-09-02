package GraphDrawer;

import util.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.OptionalDouble;

public class HistogramDrawer extends JPanel {
    private String m_GraphName;
    private String m_XAxisName;
    private String m_YAxisName;
    private String m_GraphTypeName;

    private int m_Scale;

    private JButton m_Next;
    private JButton m_Prev;

    private int m_TopXAxisLine;

    private int m_Slide = 0;

    final private JFrame m_Frame = new JFrame();
    final private ArrayList<Pair<String, Integer>> m_ValuesToPlot = new ArrayList<>();

    private int m_WindowWidth;
    private int m_WindowHeight;

    static class degreeClassificationIndex{
       static public int first;
       static public int upperSecond;
       static public int lowerSecond;
       static public int third;
       static public int fail;

       //set everything to 0
       static void reset(){
           first = 0;
           upperSecond = 0;
           lowerSecond = 0;
           third = 0;
           fail = 0;
       }
    }

    public HistogramDrawer(String graphTypeName, String graphName, String xAxisName, String yAxisName, int scale) {
        this.m_GraphTypeName = graphTypeName;
        this.m_GraphName = graphName;
        this.m_XAxisName = xAxisName;
        this.m_YAxisName = yAxisName;
        this.m_Scale = scale;
        setLayout(null);
        initWindow(50, 70);
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

    void drawPlotLabels(Graphics2D gfx2d){
        for (int i = 0; i < 11; i++){
            gfx2d.setFont(new Font("TimesRoman", Font.PLAIN, 13));
            int x = i * (int)((m_WindowWidth - 150) / 11.0f) + 100;
            gfx2d.drawLine(x, m_WindowHeight - 145, x, m_WindowHeight - 155);
            gfx2d.drawString(String.valueOf(i * 10), x, m_WindowHeight - 130);
        }
        gfx2d.setColor(Color.black);
    }

    void calculateDegreeClassificationIndex(){
        for (var i : m_ValuesToPlot){
            int mark = i.second();
            if (mark < 40)
                degreeClassificationIndex.fail++;
            if (mark >= 40 && mark < 50)
                degreeClassificationIndex.third++;
            if (mark >= 50 && mark < 60)
                degreeClassificationIndex.lowerSecond++;
            if (mark >= 60 && mark < 70)
                degreeClassificationIndex.upperSecond++;
            if (mark >= 70 && mark <= 100)
                degreeClassificationIndex.first++;
        }
    }

    double[] genFrequencyDensity(){
        double[] freqDensity = new double[5];
        freqDensity[0] = degreeClassificationIndex.fail / 40.0;
        freqDensity[1] = degreeClassificationIndex.third / 10.0;
        freqDensity[2] = degreeClassificationIndex.lowerSecond / 10.0;
        freqDensity[3] = degreeClassificationIndex.upperSecond / 10.0;
        freqDensity[4] = degreeClassificationIndex.first / 30.0;
        return freqDensity;
    }

    Double largestFreqDensity(double[] freqDensity){
        OptionalDouble d = Arrays.stream(freqDensity).max();
        if (d.isPresent())
            return d.getAsDouble();
        return null;
    }

    String formatDecimalAndToString(double value){
        DecimalFormat df = new DecimalFormat("#.###");
        df.setRoundingMode(RoundingMode.HALF_UP);
        String formattedValue = df.format(value);
        double roundedValue = Double.parseDouble(formattedValue);
        return String.valueOf(roundedValue);
    }

    void drawAxis(Graphics2D gfx2d){
        gfx2d.drawLine(100, 50, 100, m_WindowHeight - 150);
        gfx2d.drawLine(100, m_WindowHeight - 150, m_WindowWidth - 50, m_WindowHeight - 150);
        int yAxisPx = m_WindowHeight - 150;
        int yNow = yAxisPx;
        int nLoops = 20;
        calculateDegreeClassificationIndex();
        double[] fq = genFrequencyDensity();
        double scale = largestFreqDensity(fq) / nLoops;
        int quarterTextHeight = (int)(gfx2d.getFontMetrics().getHeight() * 0.15f);
        for (int i = 0; i <= 20; i++){
            if (i == 20){
                m_TopXAxisLine = yNow;
            }
            gfx2d.drawString(formatDecimalAndToString(i * scale), 35, yNow + quarterTextHeight);
            gfx2d.drawLine(95, yNow, 105, yNow);
            yNow -= (int)(yAxisPx / 23.0f);
        }
    }

    void drawBars(Graphics2D gfx2d){
        double[] fq = genFrequencyDensity();
        int lastx = 10 * (int)((m_WindowWidth - 150) / 11.0f);
        int[] boxes = {
                (int)(lastx * 0.4f),
                (int)(lastx * 0.1f),
                (int)(lastx * 0.1f),
                (int)(lastx * 0.1f),
                (int)(lastx * 0.3f)
        };

        int yunit = (int)((m_WindowHeight - 150 - m_TopXAxisLine) / largestFreqDensity(fq));
        int offsetx = 100;
        for (int i = 0; i < 5; i++){
            gfx2d.setColor(new Color((40 / 5) * i,
                    (30 / 5) * i,
                    10));
            gfx2d.fillRect(offsetx,  m_WindowHeight - 150, boxes[i], -(int)(yunit * fq[i]));
            gfx2d.drawString(formatDecimalAndToString(fq[i]), offsetx + 10, m_WindowHeight - 90);
            offsetx += boxes[i];
        }
        gfx2d.setColor(Color.BLACK);
        degreeClassificationIndex.reset();
    }

    @Override
    protected void paintComponent(Graphics gfx){
        Graphics2D gfx2d = (Graphics2D)gfx;
        gfx2d.setColor(new Color(255, 255, 255));
        gfx2d.fillRect(0, 0, m_WindowWidth, m_WindowHeight);
        gfx2d.setColor(new Color(10, 10, 10));
        drawTitleAndAxisNames(gfx2d);
        drawAxis(gfx2d);
        drawBars(gfx2d);
        drawPlotLabels(gfx2d);
        repaint();
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
        //m_XAxisName = name;
        m_XAxisName = "Marks (With FQD for each class)";
    }

    public void setYAxisName(String name){
        m_YAxisName = name;
        m_YAxisName = "Frequency Density";
    }

    public void setScale(int scale){
        m_Scale = scale;
    }

    public void show(){
        m_Frame.setVisible(true);
    }
}
