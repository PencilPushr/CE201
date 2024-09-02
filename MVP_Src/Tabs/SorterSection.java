package Tabs;

import MVP_src.AllModules;
import MVP_src.Student;
import MVP_src.StudentInfo;

import javax.swing.*;
import java.awt.*;

public class SorterSection extends JPanel{
    /**
     * Graph types
     */
    public enum SortType{
        DEFAULT_UNSORTED,
        NUMERIC_YEAR,
        NUMERIC_ID,
        NUMERIC_MODULE,
        //add more types
        //...
    }

    /**
     * Sort direction
     * Default is descending
     */
    public enum SortDirection{
        DESCENDING,
        ASCENDING,
        ERROR_TYPE //default
    }

    //private vars for dropdown menus and the button
    private JComboBox<String> m_SortTypeMenu;
    private JComboBox<String> m_SortDirMenu;
    private JButton m_ApplySortType;
    private final Rectangle m_rec;
    private int paddingX = 10;

    /**
     * Constructor
     */
    public SorterSection(Rectangle rectangle, StudentInfo info, AllModules allModules) {
        this.m_rec = rectangle;
        setupApplyButton();
        setupSortDirDropdownMenu();
        setupSortTypeDropdownMenu();
    }

    private void setupApplyButton(){
        m_ApplySortType = new JButton("Apply Sort");
        m_ApplySortType.setBounds(m_rec);
        add(m_ApplySortType);
        //applySortType();
    }

    private void setupSortDirDropdownMenu(){
        String[] sortDirections = {"Descending order", "Ascending order"};
        m_SortDirMenu = new JComboBox<>(sortDirections);
        m_rec.x += m_rec.width + paddingX;
        m_SortDirMenu.setBounds(m_rec);
        add(new JLabel("Sort Direction: "));
        add(m_SortDirMenu);
    }

    /**
     * Setups combo box to contain pre-declared sort type string names
     */
    private void setupSortTypeDropdownMenu(){
        String[] sortTypes = { "Sort Year", "Sort ID", "Sort Module" };
        m_SortTypeMenu = new JComboBox<>(sortTypes);
        m_rec.x += m_rec.width + paddingX;
        m_SortDirMenu.setBounds(m_rec);
        add(new JLabel("Sort type: "));
        add(m_SortTypeMenu);
    }

    /**
     * Accessor method for the combo box to get the selected item from the combo box and return selected graph type
     * @return SortType returns selected sort type from the combo box
     */
    public SortType getAnalyzerSelectedSortType(){
        //Java built-in function to get the selected item
        String selectedItem = (String) m_SortTypeMenu.getSelectedItem();
        assert selectedItem != null;
        //set up our returning value
        SortType sortType = SortType.DEFAULT_UNSORTED;
        switch (selectedItem) {
            case "Sort Year" -> sortType = SortType.NUMERIC_YEAR;
            case "Sort ID" -> sortType = SortType.NUMERIC_ID;
            case "Sort Module" -> sortType = SortType.NUMERIC_MODULE;
        }
        return sortType;
    }

    /**
     *
     * @return
     */
    public SortDirection getAnalyzerSelectedSortDir(){
        //Java built-in function to get the selected item
        String selectedItem = (String) m_SortDirMenu.getSelectedItem();
        assert selectedItem != null;
        //set up our returning value
        SortDirection sortDir = SortDirection.ERROR_TYPE;
        switch (selectedItem) {
            case "Descending order" -> sortDir = SortDirection.DESCENDING;
            case "Ascending order" -> sortDir = SortDirection.ASCENDING;
        }
        return sortDir;
    }

    /*
    private void applySortType() {
        getAnalyzerSelectedSortType();
        getAnalyzerSelectedSortDir();
        m_ApplySortType.addActionListener(
                e -> {
                    switch (getAnalyzerSelectedSortDir()){

                    }
                }
        );
    }
    */

    //________________________Algorithm section______________________________



}
