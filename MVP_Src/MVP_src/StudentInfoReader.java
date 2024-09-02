package MVP_src;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

class CSVParser{

    /**
     *
     * @param line
     * @return
     */
    public List<String> parseLine(String line){
        String token = new String();
        List<String> tokens = new ArrayList<>();
        for (int i = 0; i < line.length(); i++){
            char currChar = line.charAt(i);
            if (currChar == ',') {
                tokens.add(token.strip());
                token = "";
                continue;
            }
            token += currChar;
            if (i == line.length() - 1){
                tokens.add(token);
            }
        }
        return tokens;
    }
}

//This is currently very break-prone.
// TODO: add data validation

public class StudentInfoReader {

    /**
     *
     * @param fileName
     * @param regToStudent
     * @return
     */
    public boolean readFile(String fileName, HashMap<Integer, Student> regToStudent){
        Scanner s;
        try{
           s = new Scanner(new FileReader(fileName));
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
           int read = i;
           for (; i < header.size() - read; i++){
              if (!tokens.get(i).equals("")){
                 student.addModuleMark(header.get(i), Integer.parseInt(tokens.get(i)));
              }
           }
        }
        return true;
    }

    final private CSVParser m_Parser = new CSVParser();
}
