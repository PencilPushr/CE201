package GraphDrawer;

import util.Formatter;
import util.Gaussian;
import util.Pair;
import util.Statistics;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Arrays;

public class DeviationGraphDrawer extends JPanel {
    private String m_GraphName;
    private String m_XAxisName;
    private String m_YAxisName;
    private String m_GraphTypeName;

    private int m_Scale;

    private int m_TopXAxisLine;

    final private JFrame m_Frame = new JFrame();
    final private ArrayList<Integer> m_ValuesToPlot = new ArrayList<>();

    private int m_WindowWidth;
    private int m_WindowHeight;

    private double m_mean;
    private double m_variance;
    private double m_sigma;

    public DeviationGraphDrawer(String graphTypeName, String graphName, String xAxisName, String yAxisName, int scale) {
        this.m_GraphTypeName = graphTypeName;
        this.m_GraphName = graphName;
        this.m_XAxisName = xAxisName;
        this.m_YAxisName = yAxisName;
        this.m_Scale = scale;
        setLayout(null);
        initWindow(40, 40);
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

    public void add(int value){
        m_ValuesToPlot.add(value);
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
        int yaxisTextX = (int)((m_WindowHeight * 0.5f));
        gfx2d.drawString(m_YAxisName, -yaxisTextX, 20);

        gfx2d.setTransform(defaultAt);

        int xaxisWidthPx = gfx2d.getFontMetrics().stringWidth(m_XAxisName);
        int xAxisTextX = (int)((m_WindowWidth * 0.5f) - (xaxisWidthPx * 0.5f));
        gfx2d.drawString(m_XAxisName, xAxisTextX, m_WindowHeight - 50);
    }

    void drawAxis(Graphics2D gfx2d){
        //x axis
        /*
        for (int i = 0; i < 11; i++){
            int x = (int)(i * (m_WindowWidth - 150) / 11.0f) + 100;
            gfx2d.drawLine(x, m_WindowHeight - 145, x, m_WindowHeight - 155);
            gfx2d.drawString(String.valueOf(i * 10), x, m_WindowHeight - 115);
        }
         */
        for (int i = -4; i < 5; i++){
            int x = (int)(((i+4) * 1.02) * (m_WindowWidth - 150) / 9.0f) + 100;
            gfx2d.drawLine(x, m_WindowHeight - 145, x, m_WindowHeight - 155);
            if (i != 0)
                gfx2d.drawString((i + "σ"), x, m_WindowHeight - 115);
            else gfx2d.drawString("0", x, m_WindowHeight - 115);
        }
        int yAxisPx = m_WindowHeight - 150;
        int yNow = yAxisPx;
        //generate line for y axis to build the indicator lines
        gfx2d.drawLine(100, yNow, 100, m_TopXAxisLine);
        //y axis
        double tempYAxisNumber = 0.0d;
        for (int i = 0; i < 5; i++){
            if (i == 4){
                m_TopXAxisLine = yNow;
            }
            gfx2d.drawLine(95, yNow, 105, yNow);
            gfx2d.drawString(Formatter.clampTrailingDecimals(tempYAxisNumber, 1), 55, yNow);
            //var pdf = Statistic.pdf(i, m_mean, m_sigma)
            //gfx2d.drawString(Formatter.clampTrailingDecimals(pdf, 4), 55, yNow);
            //gfx2d.drawString(Formatter.clampTrailingDecimals( (m_mean/10.0f) * i, 2), 55, yNow);
            tempYAxisNumber = tempYAxisNumber + 0.1d;
            yNow -= (int)(yAxisPx / (5.0f));
        }
    }

    public void drawGaussian(Graphics g, double[] marks, int offsetx, int offsety, int width, int height){
        Gaussian gaussian = new Gaussian(Statistics.mean(marks), Statistics.standardDeviation(marks));
        m_mean = Statistics.mean(marks);
        m_variance = Statistics.variance(marks);
        m_sigma = Statistics.standardDeviation(marks);
        int actualGraphWidth = 4000;
        int actualBottom = (int)(gaussian.getY(actualGraphWidth) * height) + offsety + height;
        for (double xval = 0; xval < actualGraphWidth; xval++) {
            double x = xval - (actualGraphWidth / 2.0);
            int y = (int)(gaussian.getY(x) * height) - offsety;
            g.setColor(new Color(150, (int)((255.0 / actualGraphWidth) * xval),100));
            int actualx = (int)(xval * (width / (float)actualGraphWidth)) + offsetx;
            g.drawLine(actualx, -y + height, actualx, actualBottom);
        }
        g.setColor(Color.black);
    }

    private void drawMarksGaussian(Graphics gfx){
        double[] marks = m_ValuesToPlot.stream()
                .mapToDouble(Integer::doubleValue)
                .toArray();

        int ydiff = m_WindowHeight - 150 - m_TopXAxisLine;

        drawGaussian(gfx, marks, 100, m_TopXAxisLine,
                (int)(10 * (m_WindowWidth - 150) / 11.0f), ydiff);
    }

    @Override
    protected void paintComponent(Graphics gfx){
        Graphics2D gfx2d = (Graphics2D)gfx;
        gfx2d.setColor(new Color(255, 255, 255));
        gfx2d.fillRect(0, 0, m_WindowWidth, m_WindowHeight);
        gfx2d.setColor(new Color(10, 10, 10));
        drawTitleAndAxisNames(gfx2d);
        drawMarksGaussian(gfx2d);
        drawAxis(gfx2d);
        repaint();
    }

    public void setGraphName(String name){
        m_GraphName = name;
        m_Frame.setTitle(m_GraphTypeName + " - " + m_GraphName);
    }

    public void setXAxisName(String name){
        //m_XAxisName = name;
        //we don't care about this anymore, as we aren't setting the names for all the graphs and instead making them
        //independent of one another
        m_XAxisName = "Standard Deviations from the Mean";
    }

    public void setYAxisName(String name){
        //m_YAxisName = name;
        m_YAxisName = "Probability Density";
    }

    public void setScale(int scale){
        m_Scale = scale;
    }

    public void show(){
        m_Frame.setVisible(true);
    }
}

