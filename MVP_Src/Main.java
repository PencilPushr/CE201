//Entry point

import MVP_src.Application;

import javax.swing.*;

class Main {
  /**
   * Main class that runs the application
   * @param args
   */
  public static void main(String[] args) {
    SwingUtilities.invokeLater(Application::new);
  }
}