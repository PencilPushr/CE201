package GraphDrawer;

import util.Pair;

import java.awt.*;

public class LineGraphDrawer extends ScrollableGraph {
    public LineGraphDrawer(String graphTypeName){
        super(graphTypeName);
    }

    @Override
    public void drawGraph(Graphics2D gfx2d){
        for (int i = 0; i < m_ValuesToPlot.size() - 1; i++){
            gfx2d.setColor(getPlotColor(i));
            Pair<String, Integer> value = m_ValuesToPlot.get(i);
            int heightpx = getScaledYValue(value.second());
            gfx2d.fillOval((i * m_XUnitPadding) + m_PaddingX - scrollOffset(),  heightpx, 5,  5);
            Pair<String, Integer> value2 = m_ValuesToPlot.get(i + 1);
            int height2px = getScaledYValue(value2.second());
            gfx2d.drawLine((i * m_XUnitPadding) + m_PaddingX - scrollOffset() + 2,  heightpx + 2,
                    ((i + 1) * m_XUnitPadding) + m_PaddingX - scrollOffset() + 2,  height2px + 2);
            gfx2d.fillOval(((i + 1) * m_XUnitPadding) + m_PaddingX - scrollOffset(),  height2px, 5,  5);
        }

        gfx2d.setColor(Color.black);
    }
}
