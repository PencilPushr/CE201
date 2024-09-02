package GraphDrawer;

import util.Pair;
import java.awt.*;

public class BarGraphDrawer extends Graph {
    public BarGraphDrawer(String graphTypeName, String graphName, String xAxisName, String yAxisName, int scale){
        super(graphTypeName, graphName, xAxisName, yAxisName, scale);
    }

    @Override
    void drawGraph(Graphics2D gfx2d){
        int loops = 10;
        if (m_Slide == (int)(m_ValuesToPlot.size() / 10.0f))
            loops = m_ValuesToPlot.size() % 10;

        for (int i = 0; i < loops; i++){
            Pair<String, Integer> value = m_ValuesToPlot.get(i + (m_Slide * 10));
            gfx2d.setColor(getPlotColor(i));
            int heightbarpx = getScaledValue(value.second());
            gfx2d.fillRect(120 + i * 50, m_WindowHeight - 150, 15,  -heightbarpx);
        }
        gfx2d.setColor(Color.black);
    }
}
