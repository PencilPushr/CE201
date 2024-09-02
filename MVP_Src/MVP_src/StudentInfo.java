package MVP_src;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *  A wrapper Class to wrap m_RegNoToStudent.
 *  The application passes it to the GUI therefore requiring protection.
 */
public class StudentInfo {

    /**
     * Hashmap of ID number to Student.
     */
    final private HashMap<Integer, Student> m_RegNoToStudent = new HashMap<>();

    /**
     * Checks if the Hashmap is empty. Returns a boolean indicative if it is empty (true) or vice versa (false)
     * @return boolean
     */
    public boolean isEmpty(){ return m_RegNoToStudent.isEmpty(); }

    /**
     * Accessor method for Key in Hashmap returning the registration number
     * @param regNo Integer ID of Student Obj
     * @return Object of Student
     */
    public Student get(Integer regNo){ return m_RegNoToStudent.get(regNo); }

    /**
     * Returns a set of key value pair(s) of ID and Student
     * @return Set
     */
    public Set<Map.Entry<Integer, Student>> entrySet(){ return m_RegNoToStudent.entrySet(); }

    //public Pair getregNoToStudent_PAIR(Integer TEMP_THIS_NEEDS_TO_BE_CHANGED) { return m_RegNoToStudent.get(TEMP_THIS_NEEDS_TO_BE_CHANGED);}
    //public Integer moduleMark

    /**
     * Reads a files contents into the Student Hashmap
     * @param path location/name of file
     */
    public void load(String path){ FileReader.readStudentData(path, m_RegNoToStudent); }
}
