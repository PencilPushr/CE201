package Tabs;

import MVP_src.*;

import javax.swing.*;
import java.util.Map;

public class StudentTab extends JPanel {


    public StudentTab(JFrame frame, StudentInfo info, TextOutputTab textOutputTab, HomePage homePage){
        setUpDrawDumpButton(frame, info, textOutputTab, homePage);
    }

    /**
     *
     * @param frame
     * @param info
     * @param textOutputTab
     * @param homePage
     */
    private void setUpDrawDumpButton(JFrame frame, StudentInfo info, TextOutputTab textOutputTab, HomePage homePage){
        JButton dumpRawButton = new JButton("Dump Raw Data");
        dumpRawButton.setLocation(
                Math.max(Layout.fileSelectorMinWidthPx, (int)(frame.getWidth() * Layout.fileSelectorWidthPer)),
                100);
        this.add(dumpRawButton);
        dumpRawButton.addActionListener(
                e -> {
                    StringBuilder output = new StringBuilder();
                    if(e.getSource() == dumpRawButton){
                        for(var studentInfo : info.entrySet()){
                            output.append("Student Registration number: "); output.append(studentInfo.getKey());
                            output.append('\n');
                            output.append("Course: "); output.append(studentInfo.getValue().course);
                            output.append('\n');
                            for (var moduleInfo : studentInfo.getValue().entrySet()){
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
}
