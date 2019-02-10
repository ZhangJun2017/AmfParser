package sn.zhang.amfparser;

import sn.zhang.amfparser.utils.Tools;

import java.io.IOException;

public class ConsoleInterface extends Interface {
    @Override
    public String httpGet(String url) throws IOException {
        return Tools.httpGet(url);
    }

    @Override
    public void addText(String str) {
        System.out.println();
        System.out.print(str);
    }

    @Override
    public void throwException(String str) {
        System.out.println();
        System.err.print(str);
    }

    @Override
    public String getDeviceId() {
        return System.getenv("PROCESSOR_REVISION");
        //return System.getenv("COMPUTERNAME") + "-" + System.getenv("USERNAME");
    }

}
