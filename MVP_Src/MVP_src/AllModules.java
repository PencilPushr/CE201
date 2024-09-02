package MVP_src;

import java.util.ArrayList;

public class AllModules {
    private final ArrayList<String> m_AllModules = new ArrayList<>();
    public void load(String path) { FileReader.readModules(path, m_AllModules); }
    public int size() { return m_AllModules.size(); }
    public String get(int i){ return m_AllModules.get(i); }
}
