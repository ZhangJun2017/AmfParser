package sn.zhang.amfparser;

import com.weedong.flex.client.ClientStatusException;
import com.weedong.flex.client.ServerStatusException;

import java.io.IOException;

public class Interface {
    public Interface() {

    }

    public void addText(String str) {

    }

    public void addTextNNL(String str) {

    }

    public String httpGet(String url) throws IOException {
        return null;
    }

    public Object amfGet(String urls, String command, String... args) throws ClientStatusException, ServerStatusException {
        return null;
    }

    public void throwException(String str) {

    }

    public String getDeviceId() {
        return null;
    }
}
