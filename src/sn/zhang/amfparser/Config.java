package sn.zhang.amfparser;

import com.google.gson.JsonObject;
import sn.zhang.amfparser.utils.InternalException;
import sn.zhang.amfparser.utils.PermissionException;
import sn.zhang.amfparser.utils.Tools;
import sn.zhang.amfparser.utils.values;

import java.io.IOException;
import java.util.HashMap;

public class Config {
    Interface mInterface = null;
    Analysis analysis = null;
    HashMap<String, String> configMap = new HashMap();

    public static void main(String[] args) {
        Config config = new Config();
        config.setInterface(new ConsoleInterface());
        //config.setAnalysis(new Analysis());
        config.pullServerConfig();
    }

    public Config() {

    }

    public Config setAnalysis(Analysis analysis) {
        this.analysis = analysis;
        return this;
    }

    public Config setInterface(Interface i) {
        this.mInterface = i;
        return this;
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
        return this.configMap.get(key);
    }

    public void put(String key, String obj) {
        this.configMap.put(key, obj);
    }

    public Config pullServerConfig() {
        //Global vars init
        put("universeMessage", "");
        put("deviceMessage", "");
        put("needPwd", "true");
        put("bannedAll", "false");
        put("openedAll", "true");
        put("specialBanned", "false");
        put("specialOpened", "false");
        put("bannedAllMsg", "");
        put("specialBannedMsg", "");
        put("canQuery", "false");
        put("reason", "Unauthorized access!");

        JsonObject jsonDefault, jsonDevice;
        String jsonDevice_;

        try {
            jsonDefault = Tools.parseJson(mInterface.httpGet("http://zhang-jun.work/exam/" + values.version + "/default/config.json"));
            //This is default config,if not 200,server must have some problem.
            //Getting default config

            put("universeMessage", jsonDefault.get("message").getAsString());
            put("needPwd", jsonDefault.get("need_pwd").getAsString());
            put("bannedAll", jsonDefault.get("banned_all").getAsString());
            put("openedAll", jsonDefault.get("opened_all").getAsString());
            put("specialBanned", jsonDefault.get("special_banned").getAsString());
            put("specialOpened", jsonDefault.get("special_opened").getAsString());
            put("bannedAllMsg", jsonDefault.get("bannedAllMsg").getAsString());
            put("specialBannedMsg", jsonDefault.get("specialBannedMsg").getAsString());
            //Converting json into Config

            if (get("bannedAll").equals("openedAll")) {
                throw new InternalException("401");
            }
            //Judging if server config wrong

            if (get("bannedAll").equals("true")) {
                //Can only query a little user
                //If to-query user is not in the (global)whitelist,throw a exception and show "bannedAllMsg"
                String whiteListMsg = "404";
                whiteListMsg = Tools.httpGet("http://zhang-jun.work/exam/" + values.version + "/default/special_opened/" + get("studentId"));
                if (!whiteListMsg.equals("ensured")) {
                    //Not in whitelist or get config failed(404,403,503)
                    mInterface.throwException(get("bannedAllMsg"));
                    throw new PermissionException("204");
                } else {
                    //OK,next step is each-device config
                    put("canQuery", "true");
                }
            } else {
                //Can NOT query some user
                //Blacklist mode
                //TODO:Show a "SpecialBannedMsg"
                String blackListMsg = "404";
                blackListMsg = Tools.httpGet("http://zhang-jun.work/exam/" + values.version + "/default/special_banned/" + get("studentId"));
                if (blackListMsg.equals("ensured")) {
                    //Not in blacklist or get config failed(404,403,503)
                    throw new PermissionException("203");
                } else {
                    //OK,next step is each-device config
                    put("canQuery", "true");
                }
            }
            //OK,global part is finished,now let's deal with each-device part

            /**Each-Device Part**/

            jsonDevice_ = mInterface.httpGet("http://zhang-jun.work/exam/" + values.version + "/id/" + mInterface.getDeviceId() + "/config.json");
            if (!Tools.isValidJson(jsonDevice_)) {
                //Json invalid,use global config
                return this;
            }
//=====================================================================================================================
            //Json valid,so this device has its custom config
            jsonDevice = Tools.parseJson(jsonDevice_);
            put("deviceMessage", jsonDevice.get("message").getAsString());
            //Overwrite global setting
            put("needPwd", jsonDefault.get("need_pwd").getAsString());
            put("bannedAll", jsonDefault.get("banned_all").getAsString());
            put("openedAll", jsonDefault.get("opened_all").getAsString());
            put("specialBanned", jsonDefault.get("special_banned").getAsString());
            put("specialOpened", jsonDefault.get("special_opened").getAsString());
            put("bannedAllMsg", jsonDefault.get("bannedAllMsg").getAsString());
            put("specialBannedMsg", jsonDefault.get("specialBannedMsg").getAsString());
            //Converting json into Config

            if (get("bannedAll").equals("openedAll")) {
                throw new InternalException("401");
            }
            //Judging if server config wrong

            if (get("bannedAll").equals("true")) {
                //Can only query a little user
                //If to-query user is not in the (global)whitelist,throw a exception and show "bannedAllMsg"
                String whiteListMsg = "404";
                whiteListMsg = Tools.httpGet("http://zhang-jun.work/exam/" + values.version + "/id/" + mInterface.getDeviceId() + "/special_opened/" + get("studentId"));
                if (!whiteListMsg.equals("ensured")) {
                    //Not in whitelist or get config failed(404,403,503)
                    mInterface.throwException(get("bannedAllMsg"));
                    throw new PermissionException("202");
                } else {
                    //OK,next step is each-device config
                    put("canQuery", "true");
                }
            } else {
                //Can NOT query some user
                //Blacklist mode
                //TODO:Show a "SpecialBannedMsg"
                String blackListMsg = "404";
                blackListMsg = Tools.httpGet("http://zhang-jun.work/exam/" + values.version + "/id/" + mInterface.getDeviceId() + "/special_banned/" + get("studentId"));
                if (blackListMsg.equals("ensured")) {
                    //Not in blacklist or get config failed
                    throw new PermissionException("201");
                } else {
                    //OK,next step is each-device config
                    put("canQuery", "true");
                }
            }
//======================================================================================================================
        } catch (InternalException | PermissionException e) {
            mInterface.throwException(e.toString());
            analysis.put("status", "Failed");
            analysis.put("errcode", e.getMessage());
            put("reason", e.getMessage());
            return this;
        } catch (IOException e) {
            mInterface.throwException(e.toString());
            analysis.put("status", "Failed");
            analysis.put("errcode", "101");
            put("reason", "101");
            return this;
        } finally {
            return this;
        }
    }

}
