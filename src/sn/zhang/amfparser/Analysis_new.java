package sn.zhang.amfparser;

public class Analysis_new {
    Config config = null;

    public Analysis_new(Config c) {
        config = c;
    }

    public int sendAnalysis() {
        if (config == null) {
            //Invalid "Config"
            return -1;
        }
        //TODO:Send Analysis
        //TODO:Read Config and send analysis by "canQuery" and "errCode".
        return 0;

    }
}
