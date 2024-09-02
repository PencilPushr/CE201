package MVP_src;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * A Class to hold the relevant Student information held within the CSV files
 */
public class Student {

    /**
     * String name of the course a Student is enrolled
     */
    public String course;

    /**
     * Mapping a Module and a Result to the Module.
     */
    private final HashMap<String, Integer> m_ModuleResult = new HashMap<>();

    /**
     * Appends a (Integer) marks to relevant module (String)
     * @param module Name of module
     * @param marks Integer value of mark recieved from module
     */
    public void addModuleMark(String module, int marks){ m_ModuleResult.put(module, marks); }

    /**
     * Accessor to get a module based on input
     * @param module name of module
     */
    public void get(String module){ m_ModuleResult.get(module); }

    /**
     * Returns set of Key-Value pair of Module and result
     * @return Set of map entry
     */
    public Set<Map.Entry<String, Integer>> entrySet(){ return m_ModuleResult.entrySet(); }
    public Set<String> keySet(){ return m_ModuleResult.keySet(); }
    public boolean doesStudentTakeModule(String module) {
        return m_ModuleResult.containsKey(module);
    }
}