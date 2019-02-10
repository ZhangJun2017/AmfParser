package sn.zhang.amfparser;

import java.util.HashMap;

public class Analysis {

    Interface mInterface = null;
    Config config = null;
    HashMap<String, String> analysisMap = new HashMap();
    boolean hasInited = false;

    public Analysis() {
    }

    public Analysis(Config c) {
        init(c);
    }

    public void init(Config c) {
        this.analysisMap.put("studentId", c.get("studentId"));
        this.analysisMap.put("deviceId", c.get("deviceId"));
        hasInited = true;
    }

    public void setInterface(Interface mInterface) {
        this.mInterface = mInterface;
    }

    public String get(String key) {
        return this.analysisMap.get(key);
    }

    public void put(String key, String obj) {
        this.analysisMap.put(key, obj);
    }

    public void push() {

    }
}
