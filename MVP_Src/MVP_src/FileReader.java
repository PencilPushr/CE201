package MVP_src;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

class CSVParser{
    public List<String> parseLine(String line) {
        StringBuilder token = new StringBuilder();
        List<String> tokens = new ArrayList<>();
        for (int i = 0; i < line.length(); i++) {
            char currChar = line.charAt(i);
            if (currChar == ',') {
                tokens.add(token.toString().strip());
                token = new StringBuilder();
                continue;
            }
            token.append(currChar);
        }
        tokens.add(token.toString().strip());
        return tokens;
    }
}

//This is currently very break-prone.
// TODO: add data validation
public class FileReader {
    final static private CSVParser m_Parser = new CSVParser();

    public static boolean readStudentData(String fileName, HashMap<Integer, Student> regToStudent){
        Scanner s;
        try{
           s = new Scanner(new java.io.FileReader(fileName));
        }catch (FileNotFoundException e) {
            return false;
        }
        List<String> header = m_Parser.parseLine(s.nextLine());
        while(s.hasNextLine()){
           List<String> tokens = m_Parser.parseLine(s.nextLine());
           Student student = new Student();
           int i = 0;
           regToStudent.put(Integer.valueOf(tokens.get(i++)), student);
           student.course = tokens.get(i++);
           for (; i < header.size(); i++){
              if (!tokens.get(i).equals("")){
                  student.addModuleMark(header.get(i), Integer.parseInt(tokens.get(i)));
              }
           }
        }
        return true;
    }

    public static boolean readModules(String fileName, ArrayList<String> modules){
        Scanner s;
        try{
            s = new Scanner(new java.io.FileReader(fileName));
        }catch (FileNotFoundException e) {
            return false;
        }
        List<String> header = m_Parser.parseLine(s.nextLine());
        for (int i = 2; i < header.size(); i++){
            modules.add(header.get(i));
        }
        return true;
    }

    public static boolean readCourse(String fileName, HashMap<Integer, String> regToCourses){
        Scanner s;
        try{
            s = new Scanner(new java.io.FileReader(fileName));
        }catch (FileNotFoundException e) {
            return false;
        }
        List<String> header = m_Parser.parseLine(s.nextLine());
        while(s.hasNextLine()) {
            List<String> tokens = m_Parser.parseLine(s.nextLine());
            int i = 1;
            String course = tokens.get(i);
            i--;
            regToCourses.put(Integer.valueOf(tokens.get(i)), course);
        }
        return true;
    }
}
