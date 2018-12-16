package sn.zhang.amfparser;

import java.util.HashMap;

public class Config {
    Interface mInterface;
    Analysis analysis;
    HashMap configMap = new HashMap();

    public Config() {

    }

    public Config(Interface i) {
        this.mInterface = i;
    }

    public void setAnalysis(Analysis analysis) {
        this.analysis = analysis;
    }

    public void setInterface(Interface i) {
        this.mInterface = i;
    }

    public Interface getInterface() {
        return mInterface;
    }

    public Analysis getAnalysis() {
        return analysis;
    }

    public HashMap getConfigMap() {
        return configMap;
    }

    public String get(String key) {
        return this.configMap.get(key).toString();
    }

    public void put(String key, String obj) {
        this.configMap.put(key, obj);
    }

}
