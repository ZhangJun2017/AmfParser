package sn.zhang.amfparser.utils;

public class ConfigPullingException extends Exception {
    public String errCode, errMsg;
    public int flag = 0;

    /**
     * Flag 0 means exception happens in the process of pulling server config;
     * *Flag 1 is on the contrary of Flag 0.
     **/

    public ConfigPullingException() {
    }

    public ConfigPullingException(String extra) {
        super(extra);
        flag = 0;
    }

    public ConfigPullingException(String errCode, String errMsg) {
        super(errCode);
        flag = 1;
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

}
