package Tabs;

import GraphDrawer.GraphDrawer;
import MVP_src.AllModules;
import MVP_src.Sorter;
import MVP_src.Student;
import MVP_src.StudentInfo;
import util.Pair;
import util.SortingAlg;

import javax.swing.*;
import java.awt.*;
import java.sql.Array;
import java.util.*;

public class SorterSectionTab extends JPanel{

    private GraphDrawer m_graphDrawer;
    private SortType sortType;
    private SortDirection sortDir;

    /**
     * Graph types
     */
    public enum SortType{
        DEFAULT_UNSORTED,
        NUMERIC_YEAR,
        NUMERIC_ID,
        NUMERIC_MODULE,
        NUMERIC_NUM_OF_STUDENTS,
        ALPHANUMERIC_COURSE,
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
    private int paddingX = 10;

    /**
     * Constructor
     */
    public SorterSectionTab(StudentInfo info, AllModules allModules) {
        //setupApplyButton();
        setupSortDirDropdownMenu();
        setupSortTypeDropdownMenu();
        setLayout(new FlowLayout());
    }

    public void initGraphDrawer(GraphDrawer gd){
        this.m_graphDrawer = gd;
    }

    /*
    private void setupApplyButton(){
        m_ApplySortType = new JButton("Apply Sort");
        add(m_ApplySortType);
        applySortType();
    }
     */

    private void setupSortDirDropdownMenu(){
        String[] sortDirections = {"Unsorted", "Descending order", "Ascending order"};
        m_SortDirMenu = new JComboBox<>(sortDirections);
        add(new JLabel("Sort Direction: "));
        add(m_SortDirMenu);
    }

    /**
     * Setups combo box to contain pre-declared sort type string names
     */
    private void setupSortTypeDropdownMenu(){
        String[] sortTypes = { "Sort Year", "Sort ID", "Sort Module", "Sort Course", "Sort Number of Students" };
        m_SortTypeMenu = new JComboBox<>(sortTypes);
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
            case "Sort Course" -> sortType = SortType.ALPHANUMERIC_COURSE;
            case "Sort Number of Students" -> sortType = SortType.NUMERIC_NUM_OF_STUDENTS;
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
            case "Descending order" ->  sortDir = SortDirection.DESCENDING;
            case "Ascending order" ->   sortDir = SortDirection.ASCENDING;
            case "Unsorted" ->          sortDir = SortDirection.ERROR_TYPE;
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

    private void applySortType() {
        /*
        m_graphDrawer.setGraphType(m_VisualizationSettings.getSelectedGraphType());
        var sorted = Sorter.getStudentModulesAndMark(student);
        for (var i : sorted.entrySet()) {
            m_graphDrawer.add(i.getKey(), i.getValue());
        }
        m_graphDrawer.setGraphName("Module marks for student with ID: " + studentID);
        m_graphDrawer.setXAxisName("Module");
        m_graphDrawer.setYAxisName("Marks");
        m_graphDrawer.show();
        */
    }


    //________________________Algorithm section______________________________

    //What would be really smart is to create a static member variable, that resets when we call one of these functions.
    //This would mean we save space on creating a copy of our sortedArray

    /**
     *
     * @param dir
     * @param info
     * @return
     */
    public static ArrayList<Pair<Integer, Student>> sortByID(SortDirection dir, StudentInfo info){
        ArrayList<Pair<Integer, Student>> sortedArray = new ArrayList<>();
        switch(dir){
            case ERROR_TYPE -> JOptionPane.showMessageDialog(null, "ERROR");
            case ASCENDING -> {
                var index = 0;

                var ID = new ArrayList<Integer>();
                for (var i : info.entrySet()) {
                    ID.add(i.getKey());
                }
                ID.sort(Collections.reverseOrder());

                for (var i : info.entrySet()) {
                    var IDindex = ID.get(index);
                    sortedArray.add(new Pair<Integer, Student>(IDindex, info.get(IDindex)));
                    index++;
                }
                return sortedArray;
            }
            case DESCENDING -> {
                var index = 0;

                var ID = new ArrayList<Integer>();
                for (var i : info.entrySet()) {
                    ID.add(i.getKey());
                }
                Collections.sort(ID);

                for (var i : info.entrySet()) {
                    var IDindex = ID.get(index);
                    sortedArray.add(new Pair<Integer, Student>(IDindex, info.get(IDindex)));
                    index++;
                }
                return sortedArray;
            }
        }
        return null;
    }


    /**
     *
     * @param dir
     * @param allModules
     * @return
     */

    public ArrayList<String> sortByModuleName(SortDirection dir,AllModules allModules, StudentInfo info){
        ArrayList<Pair<Integer, Student>> sortedArray = new ArrayList<>();
        switch(dir){
            case ERROR_TYPE -> JOptionPane.showMessageDialog(null, "ERROR");
            case ASCENDING -> {

                //Gather the modules
                var ModuleList = new ArrayList<String>(allModules.size());
                for (int i = 0; i < allModules.size(); i++) {
                    ModuleList.add(allModules.get(i));
                }
                //sort the modules
                ModuleList.sort(Collections.reverseOrder());
                return ModuleList;
            }
            case DESCENDING -> {
                var ModuleList = new ArrayList<String>(allModules.size());
                for (int i = 0; i < allModules.size(); i++) {
                    ModuleList.add(allModules.get(i));
                }
                Collections.sort(ModuleList);
                return ModuleList;
            }
        }
        return null;
    }


    /**
     *
     * @param dir
     * @param allModules
     * @return
     */
    public ArrayList<Pair<Integer, Student>> sortByCourseAndYear(SortDirection dir, StudentInfo info, AllModules allModules){
        ArrayList<Pair<Integer, Student>> sortedArray = new ArrayList<>();
        switch(dir){
            case ERROR_TYPE -> JOptionPane.showMessageDialog(null, "ERROR");
            case ASCENDING -> {

                //all the courses
                var allCoursesList = new ArrayList<String>();
                //iterate through the student to get all the possible courses
                for (var i: info.entrySet()) {
                    allCoursesList.add(i.getValue().course);
                }
                //sort them
                Collections.sort(allCoursesList);
                /*
                SortingAlg.MergeSort<String> merger = new SortingAlg.MergeSort<>();
                merger.mergeSort(allCoursesList, dir);
                */

                for (int i = 0; i < allCoursesList.size(); i++) {
                    for (var j : info.entrySet()) {
                        if (j.getValue().doesStudentTakeModule(allCoursesList.get(i)))
                            sortedArray.add(new Pair<Integer, Student>(j.getKey(), j.getValue()));
                    }
                }
                return sortedArray;
            }
            case DESCENDING -> {
                //all the courses
                var allCoursesList = new ArrayList<String>();
                //iterate through the student to get all the possible courses
                for (var i: info.entrySet()) {
                    allCoursesList.add(i.getValue().course);
                }
                //sort them
                //allCoursesList.sort(Collections.reverseOrder());
                SortingAlg.MergeSort<String> merger = new SortingAlg.MergeSort<>();
                merger.mergeSort(allCoursesList, dir);


                for (int i = 0; i < allCoursesList.size(); i++) {
                    for (var j : info.entrySet()) {
                        if (j.getValue().doesStudentTakeModule(allCoursesList.get(i)))
                            sortedArray.add(new Pair<Integer, Student>(j.getKey(), j.getValue()));
                    }
                }
            }
        }
        return null;
    }

    public ArrayList<Pair<Integer, Student>> sortByNumStudents(SortDirection dir, AllModules allModules, StudentInfo info, Module ModuleSelected){
        ArrayList<Pair<Integer, Student>> sortedArray = new ArrayList<>();
        switch(dir){
            case ERROR_TYPE -> JOptionPane.showMessageDialog(null, "ERROR");
            case ASCENDING -> {

                var numStudentsList = new ArrayList<Student>();
                for (int i = 0; i < allModules.size(); i++) {

                }

            }
            case DESCENDING -> {

            }
        }
        return null;
    }

    /*
     * --------------------------------------------- Drawing Section ---------------------------------------------
     */

    // This code is how we visualise the data
    /*
    m_GraphDrawer.setGraphType(m_VisualizationSettings.getSelectedGraphType());
        var sorted = Sorter.getStudentModulesAndMark(student);
        for (var i : sorted.entrySet()) {
            m_GraphDrawer.add(i.getKey(), i.getValue());
        }
        m_GraphDrawer.setGraphName("Module marks for student with ID: " + studentID);
        m_GraphDrawer.setXAxisName("Module");
        m_GraphDrawer.setYAxisName("Marks");
        m_GraphDrawer.show();

     */


}
