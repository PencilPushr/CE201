package MVP_src;

import util.Pair;

import java.util.*;

import static util.Verifier.ModuleNameExists;

public class Sorter {

    public enum DumpBy {
        MODULE,
        STUDENT,
    }

    /**
     * Given an inputted student, the function gets the students modules and their associated mark,
     * from the member hashmap using .entrySet()
     * @param student
     * @return
     */
    static public HashMap<String, Integer> getStudentModulesAndMark(Student student)
    {
        HashMap<String, Integer> map = new HashMap<>();
        for (Map.Entry<String, Integer> pair : student.entrySet()){
            map.put(pair.getKey(), pair.getValue());
        }
        return map;
    }

    /**
     * Requires allModules (a list of all the modules in the csv loaded into its respective class) and studentInfo (Hashmap of ID to a student).
     * Given a name of a module, it collects all the students that take the module and their given mark.
     * @param Module
     * @param allModules
     * @return HashMap that maps a Student ID to a Mark
     */
    public static HashMap<Integer, Integer> sortStudentByModules(String Module, AllModules allModules, StudentInfo studentInfo) {
        //check if it is a real module
        if (ModuleNameExists(Module, allModules)) {
            //create a hashmap to return all the student ID's to Modules Mark
            var students_in_module = new HashMap<Integer, Integer>();
            //get all the students that do this module
            for (Map.Entry<Integer, Student> si : studentInfo.entrySet()) {
                if (si.getValue().doesStudentTakeModule(Module)) {
                    for (Map.Entry<String, Integer> moduleMark : si.getValue().entrySet())
                    {
                        students_in_module.put(si.getKey(), moduleMark.getValue());
                    }
                }
            }
            return students_in_module;
        }
        return null;
    }

    public static ArrayList<Pair<Integer, Integer>> pairOfIDandMarkBasedOnModule(String Module, AllModules allModules, ArrayList<Pair<Integer, Student>> Students) {
        //check if it is a real module
        if (ModuleNameExists(Module, allModules)) {
            //create a hashmap to return all the student ID's to Modules Mark
            var students_in_module = new ArrayList<Pair<Integer, Integer>>();
            //get all the students that do this module
            for (var si : Students) { //var si is a Pair<Int, Stu>
                if (si.second().doesStudentTakeModule(Module)) {
                    for (var moduleMark : si.second().entrySet())
                    {
                        students_in_module.add(new Pair<Integer, Integer>(si.first(), moduleMark.getValue()));
                    }
                }
            }
            return students_in_module;
        }
        return null;
    }

    public static ArrayList<Integer> getSortedStudentMarks(StudentInfo info, String module){
        ArrayList<Integer> avgMarks = new  ArrayList<>();
        for (var studentIdToStudent : info.entrySet()) {
            var sorted = getStudentModulesAndMark(studentIdToStudent.getValue());
            for (var moduleMark : sorted.entrySet()){
                if (moduleMark.getKey().equals(module)) {
                    avgMarks.add(moduleMark.getValue());
                    break;
                }
            }
        }
        Collections.sort(avgMarks);
        return avgMarks;
    }

    public static HashMap<Integer, Integer> getStudentID2Marks(StudentInfo info, String module){
        HashMap<Integer, Integer> avgMarks = new  HashMap<>();
        for (var studentIdToStudent : info.entrySet()) {
            var sorted = getStudentModulesAndMark(studentIdToStudent.getValue());
            for (var moduleMark : sorted.entrySet()){
                if (moduleMark.getKey().equals(module)) {
                    avgMarks.put(studentIdToStudent.getKey(), moduleMark.getValue());
                    break;
                }
            }
        }
        return avgMarks;
    }




    //public HashMap
    //...

    //add functions for sortability ((((((((((((((This had been moved to SorterSection)))))))))))))))
    /*
    public HashMap<> sort_SMaM_Alphabetically_Desc(){    }
    public HashMap<> sort_SMaM_Numerically_Desc(){    }
    */




}