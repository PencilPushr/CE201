package Tabs;

import GraphDrawer.GraphDrawer;
import MVP_src.*;
import util.Pair;
import util.Statistics;

import javax.swing.*;
import java.awt.*;
import java.util.*;

import static MVP_src.Sorter.pairOfIDandMarkBasedOnModule;
import static Tabs.SorterSectionTab.sortByID;

final class StudentTabLayout{
    static final public int fileSelectorMinWidthPx = 140;//140 pixels

    public final int tabStartPosX = 10;
    private int m_SoFarPosY;

    public int componentHeight = 25;
    public final int componentWidth = 200;

    public int yOffsetBetweenComponentsPx = 5;

    private boolean firstCalcY = true;

    private final JFrame m_Frame;
    StudentTabLayout(JFrame frame){
        m_Frame = frame;
        m_SoFarPosY = 10;
    }

    public int calculateY()
    {
        if (firstCalcY){
            firstCalcY = false;
            return m_SoFarPosY;
        }
        m_SoFarPosY += yOffsetBetweenComponentsPx + componentHeight;
        return m_SoFarPosY;
    }

    public int getMaxY(){
        return m_SoFarPosY + componentHeight * 5;//leave some space at the bottom
    }
}

public class AnalyzerTab extends JPanel {
    //references to the other tabs
    private VisualizationSettingsTab m_VisualizationSettings;
    private SorterSectionTab m_SorterSectionTab;

    //layout
    private StudentTabLayout m_Layout;

    //references to information used by this and the other tabs.
    private final StudentInfo m_StudentInfo;
    private final GraphDrawer m_GraphDrawer = new GraphDrawer();

    private JComboBox<String> m_ModuleMenu;
    private JTextField m_RegNoTextInputField;
    private JComboBox<String> m_SchemeMenu;
    private JButton m_VisualiseButton;
    private JButton m_StatisticButton;

    public AnalyzerTab(JFrame frame, StudentInfo info,
                      TextOutputTab textOutputTab, HomePage homePage, AllModules allModuleList,
                      VisualizationSettingsTab visualizationSettings, SorterSectionTab sorterSectionTab){
        m_StudentInfo = info;
        m_Layout = new StudentTabLayout(frame);
        m_VisualizationSettings = visualizationSettings;
        m_SorterSectionTab = sorterSectionTab;
        setUpDumpRawButton(frame, textOutputTab, homePage);
        setupDumpModuleDifficultyButton(info, allModuleList, textOutputTab, homePage);
        setupStudentPerformanceButton(info, allModuleList, textOutputTab, homePage);
        setupModuleStatsDumpButtons(info, textOutputTab, homePage);
        setupSchemeMenu();
        setupStudentRegInputField(allModuleList);
        setupStudentModuleMenu();
        setupAllModuleRadioButtons(allModuleList);
        setupVisualiseButton(info, allModuleList);
        m_SorterSectionTab.initGraphDrawer(m_GraphDrawer);
        setPreferredSize(
                new Dimension(
                    Math.max(StudentTabLayout.fileSelectorMinWidthPx, 0),
                    m_Layout.getMaxY()
                )
        );
        setLayout(null);
        enableModuleSelectionIfTextFieldEmpty(allModuleList);
    }

    private void enableModuleSelectionIfTextFieldEmpty(AllModules allModules){
        String regNoStr = m_RegNoTextInputField.getText();
        if (regNoStr.equals("")){
            for (int i = 0; i < allModules.size(); i++)
                m_ModuleMenu.addItem(allModules.get(i));
        }
    }

    private void setUpDumpRawButton(JFrame frame, TextOutputTab textOutputTab, HomePage homePage){
        JButton dumpRawButton = new JButton("Dump raw data");
        dumpRawButton.setBounds(m_Layout.tabStartPosX, m_Layout.calculateY(),
                m_Layout.componentWidth,
                m_Layout.componentHeight);
        add(dumpRawButton);
        dumpRawButton.addActionListener(
                e -> {
                    StringBuilder output = new StringBuilder();
                    if(e.getSource() == dumpRawButton){
                        for(Map.Entry<Integer, Student> studentInfo : m_StudentInfo.entrySet()){
                            output.append("Student registration number: "); output.append(studentInfo.getKey());
                            output.append('\n');
                            output.append("Course: "); output.append(studentInfo.getValue().course);
                            output.append('\n');
                            for (Map.Entry<String, Integer> moduleInfo : studentInfo.getValue().entrySet()){
                                output.append(moduleInfo.getKey()); output.append(" : "); output.append(moduleInfo.getValue());
                                output.append('\n');
                            }
                            output.append('\n');
                        }
                    }
                    textOutputTab.setOutput(output.toString());
                    homePage.setActiveInfoTab(HomePage.TabIndex.textOutput);
                }
        );
        frame.add(this);
    }

    private void setupVisualiseButton(StudentInfo info, AllModules allModules){
        m_VisualiseButton = new JButton("Visualise");
        add(m_VisualiseButton);
        m_VisualiseButton.setBounds(m_Layout.tabStartPosX, m_Layout.calculateY(),
                m_Layout.componentWidth,
                m_Layout.componentHeight);
        addVisulaizeAction(info, allModules);
    }

    private void setupStudentRegInputField(AllModules allModules){
        int maxRegNoLen = 7;
        m_RegNoTextInputField = new JTextField();
        m_RegNoTextInputField.setColumns(maxRegNoLen);
        m_RegNoTextInputField.setEditable(true);
        m_RegNoTextInputField.setDocument(new TextFieldLimit(maxRegNoLen));
        JLabel label = new JLabel("Student registration number: ");
        add(label);
        add(m_RegNoTextInputField);
        int y = m_Layout.calculateY();
        label.setBounds(m_Layout.tabStartPosX, y,
                m_Layout.componentWidth,
                m_Layout.componentHeight);
        m_RegNoTextInputField.setBounds(170, y,
                m_Layout.componentWidth, m_Layout.componentHeight);
    }

    private void setupAllModuleRadioButtons(AllModules allModulesList){
        JLabel label = new JLabel("Specific module group to analyze: ");
        label.setBounds(m_Layout.tabStartPosX,  m_Layout.calculateY(),
                m_Layout.componentWidth,
                m_Layout.componentHeight);

        add(label);
        int tab = 5;
        int temp = m_Layout.componentHeight;
        m_Layout.componentHeight = 25;
        for (int i = 0; i < allModulesList.size(); i++) {
            JRadioButton b = new JRadioButton(allModulesList.get(i));
            b.setName(allModulesList.get(i));
            b.setBounds(tab + m_Layout.tabStartPosX, m_Layout.calculateY() + m_Layout.componentHeight - 25,
                    m_Layout.componentWidth, m_Layout.componentHeight);
            add(b);
        }
        m_Layout.componentHeight = temp;
    }

    private void setupStudentModuleMenu() {
        m_ModuleMenu = new JComboBox<>();
        JLabel label = new JLabel("Individual module: ");
        add(label);
        add(m_ModuleMenu);

        int y = m_Layout.calculateY();
        label.setBounds(m_Layout.tabStartPosX, y,
                m_Layout.componentWidth,
                m_Layout.componentHeight);
        m_ModuleMenu.setBounds(113, y,
                m_Layout.componentWidth,
                m_Layout.componentHeight);
    }

    private void setupSchemeMenu(){
        m_SchemeMenu = new JComboBox<>(StudentAnalyzeScheme.array());
        JLabel label = new JLabel("Scheme: ");
        add(label);
        add(m_SchemeMenu);

        int y = m_Layout.calculateY();
        label.setBounds(m_Layout.tabStartPosX, y,
                m_Layout.componentWidth,
                m_Layout.componentHeight);
        m_SchemeMenu.setBounds(60, y,
                m_Layout.componentWidth,
                m_Layout.componentHeight);
    }

    private void addVisulaizeAction(StudentInfo info, AllModules allModules){
        m_VisualiseButton.addActionListener(
                e -> {
                    switch (m_VisualizationSettings.getSelectedGraphCategory()){
                        case EACH_STUDENT_MODULE_MARK -> plotStudentModuleMark(info);
                        case ALL_STUDENT_MARKS_FOR_A_MODULE -> plotStudentMarksByModule(info, allModules);
                        case INDIVIDUAL_MODULE_PERFORMANCE -> individualModulePerformance(info);
                        case ALL_MODULE_PERFORMANCE -> allModulePerformance(info, allModules);
                        case GROUP_MODULE_PERFORMANCE -> groupModulePerformance(info);
                    }
                }

        );
    }

    private void plotStudentModuleMark(StudentInfo info) {
        if (m_RegNoTextInputField.getText().equals("")){
            JOptionPane.showMessageDialog(null,
                    "Currently selected graph category is \"Every module for a given student\". " +
                    "This category requires that student registration number is provided. " +
                            "You can enter that in " +
                    "\"student registration number\" text field");
            return;
        }

        int studentID = 0;
        try{
            studentID = Integer.parseInt(m_RegNoTextInputField.getText());
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(null,
                    "Currently selected graph category is \"Every module for a given student\". " +
                    "This category requires that a valid student registration number is provided. " +
                            "Student registration " +
                            "number must be a whole number");
            return;
        }
        var student = info.get(studentID);

        m_GraphDrawer.setGraphType(m_VisualizationSettings.getSelectedGraphType());
        var sorted = Sorter.getStudentModulesAndMark(student);
        for (var i : sorted.entrySet()) {
            m_GraphDrawer.add(i.getKey(), i.getValue());
        }
        m_GraphDrawer.setGraphName("Module marks for student with ID: " + studentID);
        m_GraphDrawer.setXAxisName("Module");
        m_GraphDrawer.setYAxisName("Marks");
        m_GraphDrawer.show();
    }

    private void plotStudentMarksByModule(StudentInfo info, AllModules allModules){
        m_GraphDrawer.setGraphType(m_VisualizationSettings.getSelectedGraphType());
        String module = (String)m_ModuleMenu.getSelectedItem();
        var sorted = Sorter.sortStudentByModules(module,
                allModules, info);
        var temp = sortByID(SorterSectionTab.SortDirection.ASCENDING, info);
        if (sorted == null){
            JOptionPane.showMessageDialog(null,
                    "Currently selected graph category is " +
                            "\"All students' marks for a given individual module\". " +
                    "This category requires that a valid module is selected. " +
                    "You can select a module using \"Individual module\" dropdown menu");
            return;
        }
        var stupid = pairOfIDandMarkBasedOnModule(module, allModules, temp);

        for (var i : sorted.entrySet()) {
            m_GraphDrawer.add(Integer.toString(i.getKey()), i.getValue());
        }
/*

        assert stupid != null;
        for (var i : stupid) {
            m_GraphDrawer.add(Integer.toString(i.first()), i.second());
        }

         */
        m_GraphDrawer.setGraphName("Student Marks for Module: " + module);
        m_GraphDrawer.setXAxisName("Student ID");
        m_GraphDrawer.setYAxisName("Marks");
        m_GraphDrawer.show();
    }

    private void setupModuleStatsDumpButtons(StudentInfo info, TextOutputTab textOutputTab, HomePage homePage){
        m_StatisticButton = new JButton("Dump Individual Module Stats");
        add(m_StatisticButton);
        m_StatisticButton.setBounds(m_Layout.tabStartPosX, m_Layout.calculateY(),
                m_Layout.componentWidth,
                m_Layout.componentHeight);

        m_StatisticButton.addActionListener(
                e -> {
                    String output = "";
                    if(e.getSource() == m_StatisticButton) {
                        String module = (String)m_ModuleMenu.getSelectedItem();
                        ArrayList<Integer> marks = Sorter.getSortedStudentMarks(info, module);
                        double[] arr = marks.stream().mapToDouble(d -> d).toArray();

                        output = "Module: " + module + "\nMean: " + Statistics.mean(arr) + "\nVariance: " +
                                Statistics.variance(arr) + "\nSTD deviation: " + Statistics.standardDeviation(arr);
                    }

                    textOutputTab.setOutput(output);
                    homePage.setActiveInfoTab(HomePage.TabIndex.textOutput);
                });
    }

    private void allModulePerformance(StudentInfo info, AllModules allModules) {
        //     < modules, marks >
        HashMap<String, Integer> AvgModuleMapped2Marks = new HashMap<>();

        for (int i = 0; i < allModules.size(); i++) {
            //get current module we are on during the loop
            var current_module = allModules.get(i);
            //get the current avg module mark
            var avgMarks4Module = Sorter.getSortedStudentMarks(info, current_module);
            //calculate the mean average
            if (avgMarks4Module.size() != 0) {
                var avgmark = avgMarks4Module.stream().mapToInt(a -> a).sum() / avgMarks4Module.size();

                //map the current module and the average student performance
                AvgModuleMapped2Marks.put(current_module, avgmark);
            }
        }



        m_GraphDrawer.setGraphType(m_VisualizationSettings.getSelectedGraphType());
        for (var i : AvgModuleMapped2Marks.entrySet()){
            m_GraphDrawer.add(i.getKey(), i.getValue());
        }
        m_GraphDrawer.setGraphName("Average Student performance for all Modules");
        m_GraphDrawer.setXAxisName("Marks");
        m_GraphDrawer.show();
    }

    /**
     * This method takes in a "StudentInfo" object as a parameter.
     * It calculates the average performance of the students who took each selected module and maps the module to its average performance using a HashMap.
     * It then displays a graph of the average performance of the selected modules
     * @param info StudentInfo object maps ID to Student
     */
    private void groupModulePerformance(StudentInfo info) {
        HashMap<String, Integer> AvgModuleMapped2Marks = new HashMap<>();
        boolean anySelected = false;
        for (var component : getComponents()) {
            if (component instanceof JRadioButton) {
                if (((JRadioButton) component).isSelected()) {
                    anySelected = true;
                    String current_module = ((JRadioButton) component).getText();
                    var avgMarks4Module = Sorter.getSortedStudentMarks(info, current_module);
                    //calculate the mean average
                    if (avgMarks4Module.size() != 0) {
                        var avgmark = avgMarks4Module.stream().mapToInt(a -> a).sum() / avgMarks4Module.size();

                        //map the current module and the average student performance
                        AvgModuleMapped2Marks.put(current_module, avgmark);
                    }
                }
            }
        }

        if (!anySelected) {
            JOptionPane.showMessageDialog(null, "Currently selected graph category is " +
                    " \"Group module performance for all students\"" +
                    " At least one of the modules must be selected from" +
                    " the group of modules for this category.");
            return;
        }
        if (AvgModuleMapped2Marks.isEmpty()){
            JOptionPane.showMessageDialog(null, "None of the students do any of the modules selected");
            return;
        }



        m_GraphDrawer.setGraphType(m_VisualizationSettings.getSelectedGraphType());
        for (var i : AvgModuleMapped2Marks.entrySet()){
            m_GraphDrawer.add(i.getKey(), i.getValue());
        }
        m_GraphDrawer.setGraphName("Average Student performance for selected modules");
        m_GraphDrawer.setXAxisName("Marks");
        m_GraphDrawer.show();
    }

    private void individualModulePerformance(StudentInfo info) {
        String module = (String)m_ModuleMenu.getSelectedItem();
        HashMap<Integer, Integer> idToMarks = Sorter.getStudentID2Marks(info, module);
        if (idToMarks.isEmpty()){
            JOptionPane.showMessageDialog(null, "No student does this module");
            return;
        }
        m_GraphDrawer.setGraphType(m_VisualizationSettings.getSelectedGraphType());
        for (var i : idToMarks.entrySet()){
            m_GraphDrawer.add(String.valueOf(i.getKey()), i.getValue());
        }
        m_GraphDrawer.setGraphName("Student performance for Module: " + module);
        m_GraphDrawer.setYAxisName("Marks");
        m_GraphDrawer.setXAxisName("Students");
        m_GraphDrawer.show();
    }

    /*
     * ------------------------------------------ Report Module Difficulty ----------------------------------
     */

    private void setupDumpModuleDifficultyButton(StudentInfo info, AllModules allModules, TextOutputTab textOutputTab, HomePage homePage){
        var ModuleDiffButton = new JButton("Dump Module Difficulty");
        add(ModuleDiffButton);
        ModuleDiffButton.setBounds(m_Layout.tabStartPosX, m_Layout.calculateY(),
                m_Layout.componentWidth,
                m_Layout.componentHeight);
        ModuleDiffButton.addActionListener(
                e -> {
                    var moduleDifficultyList = getModuleDifficultyList(info, allModules);
                    StringBuilder output = new StringBuilder();
                    if(e.getSource() == ModuleDiffButton) {
                        for (Pair<String, String> stringStringPair : moduleDifficultyList) {
                            output.append("Module: ").append(stringStringPair.first())
                                    .append(" Difficulty: ").append(stringStringPair.second())
                                    .append('\n');
                        }
                    }
                    textOutputTab.setOutput(output.toString());
                    homePage.setActiveInfoTab(HomePage.TabIndex.textOutput);
                }
        );

    }

    private ArrayList<Pair<String, String>> getModuleDifficultyList(StudentInfo info, AllModules allModules){
        //          <modules, difficulty>
        ArrayList<Pair<String, String>> out = new ArrayList<>();

        // if 1/3 has 1st -> easy
        // if 1/5 has 1st -> moderate
        // if 1/10 has 1st -> hard

        //for all the modules
        for (int i = 0; i < allModules.size(); i++) {
            //get current module we are on during the loop
            var current_module = allModules.get(i);
            //get the marks for the module we are in
            var studentMarks = Sorter.sortStudentByModules(current_module, allModules, info);
            //calculate ratio of 1sts in a module
            assert studentMarks != null;
            if (studentMarks.size() != 0) {

                //count how many students have a first, this will be our numerator
                int numStudentsHave1st = 0;
                for (var j : studentMarks.entrySet()) {
                    if (j.getValue() > 69){
                        numStudentsHave1st++;
                    }
                }
                //divide the numberof1stStudents by the total number of students that take the module, this is our denominator
                var ratio = ((double) numStudentsHave1st) / studentMarks.size();

                //0.19796975338297 is the mean
                if (ratio >= (float) (0.19796975338297 + 0.11)){
                    out.add(new Pair<String,String>(current_module, "Easy Module"));
                } else if (ratio >= (float)(0.19796975338297)) {
                    out.add(new Pair<String,String>(current_module, "Moderate Module"));
                } else {
                    out.add(new Pair<String,String>(current_module, "Hard Module"));
                }

            }
        }
        //return the modules and their associated ranking
        return out;
    }

    /*
     * ------------------------------------------ Report Student Performance ----------------------------------
     */

    private void setupStudentPerformanceButton(StudentInfo info, AllModules allModules, TextOutputTab textOutputTab, HomePage homePage){
        var StudentPerformanceButton = new JButton("Dump Student Performance");
        add(StudentPerformanceButton);
        StudentPerformanceButton.setBounds(m_Layout.tabStartPosX, m_Layout.calculateY(),
                m_Layout.componentWidth,
                m_Layout.componentHeight);
        StudentPerformanceButton.addActionListener(
                e -> {
                    var studentPerformanceList = getStudentsAndPeformance(info, allModules);
                    StringBuilder output = new StringBuilder();
                    if(e.getSource() == StudentPerformanceButton) {
                        for (Pair<Integer, String> stringStringPair : studentPerformanceList) {
                            output.append("Reg Number: ").append(stringStringPair.first())
                                    .append(" Performance: ").append(stringStringPair.second())
                                    .append('\n');
                        }
                    }
                    textOutputTab.setOutput(output.toString());
                    homePage.setActiveInfoTab(HomePage.TabIndex.textOutput);
                }
        );

    }

    private ArrayList<Pair<Integer, String>> getStudentsAndPeformance(StudentInfo info, AllModules allModules){
        //          <ID, difficulty>
        ArrayList<Pair<Integer, String>> out = new ArrayList<>();
        //              <ID   , MARK>
        ArrayList<Pair<Integer, Float>> avgMarks = new ArrayList<>();

        //for all the students
        for (var i : info.entrySet()){
            //get the current student
            var curr_student = i.getValue();
            assert curr_student != null;

            int mark = 0;
            //for all the modules the student takes
            for (var j : curr_student.entrySet()){
                mark += j.getValue();
            }
            //get the marks, divided by the number of modules the student takes.
            var avg4currStudent = ((float)mark) / curr_student.entrySet().size();
            avgMarks.add(new Pair<Integer, Float>(i.getKey(), avg4currStudent));
        }

        avgMarks.sort(Comparator.comparing(Pair::second));
        for (int i = 0; i < avgMarks.size(); i++) {
            if (i < (1/3.0f) * avgMarks.size()){
                out.add(new Pair<Integer, String>(avgMarks.get(i).first(), "Poor Performer"));
            }else if (i < (1 - (1/3.0f)) * avgMarks.size()){
                out.add(new Pair<Integer, String>(avgMarks.get(i).first(), "Average Performer"));
            } else {
                out.add(new Pair<Integer, String>(avgMarks.get(i).first(), "Top Performer"));
            }
        }
        return out;
    }


    //_____________________________________________________Combo-boxes at bottom___________________________



}
