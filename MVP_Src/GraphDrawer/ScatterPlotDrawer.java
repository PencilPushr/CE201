package GraphDrawer;

import util.Pair;
import java.awt.*;

public class ScatterPlotDrawer extends ScrollableGraph {
    public ScatterPlotDrawer(String graphTypeName){
        super(graphTypeName);
    }

    private double m_Intercept;
    private double m_Slope;
    private final Point m_LineStart = new Point();
    private final Point m_LineEnd = new Point();

    void regression(int[] x, int[] y){
        if (x.length != y.length) {
            throw new IllegalArgumentException("array lengths are not equal");
        }
        int n = x.length;

        // first pass
        double sumx = 0.0, sumy = 0.0, sumx2 = 0.0;
        for (int i = 0; i < n; i++) {
            sumx  += x[i];
            sumx2 += x[i]*x[i];
            sumy  += y[i];
        }
        double xbar = sumx / n;
        double ybar = sumy / n;

        // second pass: compute summary statistics
        double xxbar = 0.0, yybar = 0.0, xybar = 0.0;
        for (int i = 0; i < n; i++) {
            xxbar += (x[i] - xbar) * (x[i] - xbar);
            yybar += (y[i] - ybar) * (y[i] - ybar);
            xybar += (x[i] - xbar) * (y[i] - ybar);
        }
        m_Slope  = xybar / xxbar;
        m_Intercept = ybar - m_Slope * xbar;
    }

    void CalculateStartEndPoints(int[] x, int[] y){
        for (int i = 0; i < m_ValuesToPlot.size(); i++){
            Pair<String, Integer> value = m_ValuesToPlot.get(i);
            int heightpx = getScaledYValue(value.second());
            x[i] = (i * m_XUnitPadding) + m_PaddingX - scrollOffset();
            y[i] = heightpx;
        }
        regression(x, y);
        m_LineStart.x = x[0];
        m_LineStart.y = (int)(m_Slope * m_LineStart.x + m_Intercept);
        m_LineEnd.x = x[x.length - 1];
        m_LineEnd.y = (int)(m_Slope *  m_LineEnd.x + m_Intercept);
    }

    @Override
    public void drawGraph(Graphics2D gfx2d){
        int []xs = new int[m_ValuesToPlot.size()];
        int []ys = new int[m_ValuesToPlot.size()];
        for (int i = 0; i < m_ValuesToPlot.size(); i++){
            Pair<String, Integer> value = m_ValuesToPlot.get(i);
            int heightpx = getScaledYValue(value.second());
            xs[i] = (i * m_XUnitPadding) + m_PaddingX - scrollOffset();
            ys[i] = heightpx;
            gfx2d.fillOval((i * m_XUnitPadding) + m_PaddingX - scrollOffset(),  heightpx, 10,  10);
        }
        if (xs.length > 0) {
            CalculateStartEndPoints(xs, ys);
            gfx2d.drawLine(m_LineStart.x, m_LineStart.y, m_LineEnd.x, m_LineEnd.y);
        }
    }
}
