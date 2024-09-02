package GraphDrawer;

import Tabs.VisualizationSettingsTab;

public class GraphDrawer {
    private VisualizationSettingsTab.GraphType m_GraphType; //graph currently being drawn
    private Graph m_Graph;
    private ScrollableGraph m_ScrollableGraph;
    private DeviationGraphDrawer m_DeviationGraph;
    private HistogramDrawer m_HistogramGraph;

    public void setGraphType(VisualizationSettingsTab.GraphType graphType){
        m_GraphType = graphType;
        switch (graphType){
            case BAR_GRAPH -> m_Graph = new BarGraphDrawer("Bar Graph",
                    "", "", "", 5);
            case LINE_GRAPH -> m_ScrollableGraph = new LineGraphDrawer("Line Graph");
            case HISTOGRAM -> m_HistogramGraph = new HistogramDrawer("Histogram",
                    "", "", "", 5);
            case SCATTER_PLOT -> m_ScrollableGraph = new ScatterPlotDrawer("Scatter Plot");
            case DEVIATION_GRAPH -> m_DeviationGraph = new DeviationGraphDrawer("Deviation Graph",
                    "", "", "", 5);

        }
    }

    public void setGraphName(String name){
        if (m_GraphType == VisualizationSettingsTab.GraphType.DEVIATION_GRAPH) {
            m_DeviationGraph.setGraphName(name);
            return;
        }
        if (m_GraphType == VisualizationSettingsTab.GraphType.HISTOGRAM) {
            m_HistogramGraph.setGraphName(name);
            return;
        }
        if (m_GraphType == VisualizationSettingsTab.GraphType.SCATTER_PLOT ||
                m_GraphType == VisualizationSettingsTab.GraphType.LINE_GRAPH){
            m_ScrollableGraph.setGraphName(name);
            return;
        }
        m_Graph.setGraphName(name);
    }

    public void setXAxisName(String name){
        if (m_GraphType == VisualizationSettingsTab.GraphType.DEVIATION_GRAPH) {
            m_DeviationGraph.setXAxisName(name);
            return;
        }
        if (m_GraphType == VisualizationSettingsTab.GraphType.HISTOGRAM) {
            m_HistogramGraph.setXAxisName(name);
            return;
        }
        if (m_GraphType == VisualizationSettingsTab.GraphType.SCATTER_PLOT ||
                m_GraphType == VisualizationSettingsTab.GraphType.LINE_GRAPH){
            m_ScrollableGraph.setXAxisName(name);
            return;
        }
        m_Graph.setXAxisName(name);
    }

    public void setYAxisName(String name){
        if (m_GraphType == VisualizationSettingsTab.GraphType.DEVIATION_GRAPH) {
            m_DeviationGraph.setYAxisName(name);
            return;
        }
        if (m_GraphType == VisualizationSettingsTab.GraphType.HISTOGRAM) {
            m_HistogramGraph.setYAxisName(name);
            return;
        }
        if (m_GraphType == VisualizationSettingsTab.GraphType.SCATTER_PLOT ||
                m_GraphType == VisualizationSettingsTab.GraphType.LINE_GRAPH){
            m_ScrollableGraph.setYAxisName(name);
            return;
        }
        m_Graph.setYAxisName(name);
    }

    public void setScale(int scale){
        if (m_GraphType == VisualizationSettingsTab.GraphType.DEVIATION_GRAPH) {
            m_DeviationGraph.setScale(scale);
            return;
        }
        if (m_GraphType == VisualizationSettingsTab.GraphType.HISTOGRAM) {
            m_HistogramGraph.setScale(scale);
            return;
        }
        if (m_GraphType == VisualizationSettingsTab.GraphType.SCATTER_PLOT ||
                m_GraphType == VisualizationSettingsTab.GraphType.LINE_GRAPH){
            m_ScrollableGraph.setScale(scale);
            return;
        }
        m_Graph.setScale(scale);
    }

    public void add(String name, int value){
        if (m_GraphType == VisualizationSettingsTab.GraphType.DEVIATION_GRAPH) {
            m_DeviationGraph.add(value);
            return;
        }
        if (m_GraphType == VisualizationSettingsTab.GraphType.HISTOGRAM) {
            m_HistogramGraph.add(name, value);
            return;
        }
        if (m_GraphType == VisualizationSettingsTab.GraphType.SCATTER_PLOT ||
                m_GraphType == VisualizationSettingsTab.GraphType.LINE_GRAPH){
            m_ScrollableGraph.add(name, value);
            return;
        }
        m_Graph.add(name, value);
    }

    public VisualizationSettingsTab.GraphType getGraphType(){
        return m_GraphType;
    }

    public void show(){
        if (m_GraphType == VisualizationSettingsTab.GraphType.DEVIATION_GRAPH) {
            m_DeviationGraph.show();
            return;
        }
        if (m_GraphType == VisualizationSettingsTab.GraphType.HISTOGRAM) {
            m_HistogramGraph.show();
            return;
        }
        if (m_GraphType == VisualizationSettingsTab.GraphType.SCATTER_PLOT ||
                m_GraphType == VisualizationSettingsTab.GraphType.LINE_GRAPH){
            m_ScrollableGraph.show();
            return;
        }
        m_Graph.show();
    }

}
