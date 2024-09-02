package Tabs;

import javax.swing.*;
import java.awt.*;

/**
 * Class to visualise the statistics and information.
 */
public class VisualizationSettingsTab extends JPanel {
    public VisualizationSettingsTab(){
        setupGraphTypeDropdownMenu();
        setupGraphCategoryDropdownMenu();
        setLayout(new FlowLayout());
    }

    /**
     * Graph types
     */
    public enum GraphType{
        HISTOGRAM,
        BAR_GRAPH,
        SCATTER_PLOT,
        LINE_GRAPH,
        DEVIATION_GRAPH,
        ERROR_TYPE
    }

    public enum GraphCategory{
        EACH_STUDENT_MODULE_MARK,
        ALL_STUDENT_MARKS_FOR_A_MODULE,
        INDIVIDUAL_MODULE_PERFORMANCE,
        GROUP_MODULE_PERFORMANCE,
        ALL_MODULE_PERFORMANCE,
        ERROR_TYPE
    }

    private JComboBox<String> m_GraphTypeMenu;
    private JComboBox<String> m_GraphCategoryMenu;

    /**
     * Accessor method for the combo box to get the selected item from the combo box and return selected graph type
     * @return GraphType returns selected graph type from the combo box
     */
    public GraphType getSelectedGraphType(){
        String selectedItem = (String) m_GraphTypeMenu.getSelectedItem();
        assert selectedItem != null;
        GraphType graphType = GraphType.ERROR_TYPE;
        switch (selectedItem) {
            case "Histogram" -> graphType = GraphType.HISTOGRAM;
            case "Bar Graph" -> graphType = GraphType.BAR_GRAPH;
            case "Scatter Plot" -> graphType = GraphType.SCATTER_PLOT;
            case "Line Graph" -> graphType = GraphType.LINE_GRAPH;
            case "Deviation Graph" -> graphType = GraphType.DEVIATION_GRAPH;
        }
        assert graphType != GraphType.ERROR_TYPE;//should never happen
        return graphType;
    }

    public GraphCategory getSelectedGraphCategory(){
        m_GraphCategoryMenu.setPreferredSize(new Dimension(200, 30));
        String selectedItem = (String) m_GraphCategoryMenu.getSelectedItem();
        assert selectedItem != null;
        GraphCategory graphCat = GraphCategory.ERROR_TYPE;
        switch (selectedItem) {
            case "Every module for a given student" -> graphCat = GraphCategory.EACH_STUDENT_MODULE_MARK;
            case "All students' marks for a given individual module" -> graphCat = GraphCategory.ALL_STUDENT_MARKS_FOR_A_MODULE;
            case "Individual module performance for all students" -> graphCat = GraphCategory.INDIVIDUAL_MODULE_PERFORMANCE;
            case "Group module performance for all students" -> graphCat = GraphCategory.GROUP_MODULE_PERFORMANCE;
            case "All module performance for all students" -> graphCat = GraphCategory.ALL_MODULE_PERFORMANCE;
        }
        assert graphCat != GraphCategory.ERROR_TYPE;//should never happen
        return graphCat;
    }

    /**
     * Setups combo box to contain pre-declared graph type string names
     */
    private void setupGraphTypeDropdownMenu(){
        String[] graphTypes = { "Histogram", "Bar Graph", "Scatter Plot", "Line Graph", "Deviation Graph" };
        m_GraphTypeMenu = new JComboBox<>(graphTypes);
        add(new JLabel("Graph type: "));
        add(m_GraphTypeMenu);
    }

    private void setupGraphCategoryDropdownMenu(){
        String[] graphCat = { "Every module for a given student",
                "All students' marks for a given individual module",
                "Individual module performance for all students",
                "Group module performance for all students",
                "All module performance for all students"};
        m_GraphCategoryMenu = new JComboBox<>(graphCat);
        add(new JLabel("Graph Category: "));
        add(m_GraphCategoryMenu);
    }
}
