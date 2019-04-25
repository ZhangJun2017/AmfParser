package sn.zhang.amfparser;

import com.google.gson.JsonObject;
import sn.zhang.amfparser.utils.ConfigPullingException;
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
        config.put("studentId", "21812140");
        config.setAnalysis(new Analysis());
        System.out.println("=====DEBUG START=====");
        System.out.println("Start pull server config");
        config.pullServerConfig();
        System.out.println("pull finish");
        config.getAnalysis().
                config.getInterface().addText("======DEBUG INFO=====");
        config.getInterface().addText(config.getConfigMap().toString());
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
        /**
         * Hey,I know you are looking at me,I'm here to remind you how to throw out an exception.
         *   First,set "canQuery" to false;
         *   Second,set "errCode" and "errMsg" to details.
         *   Finally,throw new ConfigPullingException(extraMsg);
         */
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
        put("errCode", "400");
        put("errMsg", "UnknownInternalException");

        JsonObject jsonDefault, jsonDevice;
        String jsonDevice_;

        try {
            jsonDefault = Tools.parseJson(mInterface.httpGet(values.version + "/default/config.json"));
            //This is default config,if not 200,server must have some problem.
            //Getting default config

            put("universeMessage", Tools.jsonGet(jsonDefault, "message"));
            put("needPwd", Tools.jsonGet(jsonDefault, "need_pwd"));
            put("bannedAll", Tools.jsonGet(jsonDefault, "banned_all"));
            put("openedAll", Tools.jsonGet(jsonDefault, "opened_all"));
            put("specialBanned", Tools.jsonGet(jsonDefault, "special_banned"));
            put("specialOpened", Tools.jsonGet(jsonDefault, "special_opened"));
            put("bannedAllMsg", Tools.jsonGet(jsonDefault, "bannedAllMsg"));
            put("specialBannedMsg", Tools.jsonGet(jsonDefault, "specialBannedMsg"));
            //Converting json into Config

            if (get("bannedAll").equals(get("openedAll"))) {
                put("errCode", "401");
                put("errMsg", "Server config seems wrong.");
                throw new ConfigPullingException();
                //Server config seems wrong,stop all process and exit.
            }
            //Judging if server config wrong

            if (get("bannedAll").equals("true")) {
                //Can only query a little user
                //If to-query user is not in the (global)whitelist,throw a exception and show "bannedAllMsg"
                String whiteListMsg = mInterface.httpGet(values.version + "/default/special_opened/" + get("studentId"));
                if (!whiteListMsg.equals("ensured")) {
                    //Not in whitelist or get config failed
                    put("canQuery", "false");
                    put("errCode", "204");
                    put("errMsg", get("bannedAllMsg"));
                } else {
                    //OK,next step is each-device config
                    put("canQuery", "true");
                }
            } else {
                //Can NOT query some user
                //Blacklist mode
                String blackListMsg = mInterface.httpGet(values.version + "/default/special_banned/" + get("studentId"));
                if (blackListMsg.equals("ensured")) {
                    //Not in blacklist or get config failed
                    put("canQuery", "false");
                    put("errCode", "203");
                    //TODO:Show a "SpecialBannedMsg"
                    put("errMsg", "Permission Denied");
                } else {
                    //OK,next step is each-device config
                    put("canQuery", "true");
                }
            }
            //OK,global part is finished,now let's deal with each-device part

            /**Each-Device Part**/

            jsonDevice_ = mInterface.httpGet(values.version + "/id/" + mInterface.getDeviceId() + "/config.json");
            if (!Tools.isValidJson(jsonDevice_)) {
                //Json invalid,use global config
                if (get("canQuery").equals("true")) {
                    return this;
                }
                throw new ConfigPullingException();
            }
//=====================================================================================================================
            //Json valid,so this device has its custom config
            jsonDevice = Tools.parseJson(jsonDevice_);
            put("deviceMessage", Tools.jsonGet(jsonDevice, "message"));
            //Overwrite global setting
            put("needPwd", Tools.jsonGet(jsonDevice, "need_pwd"));
            put("bannedAll", Tools.jsonGet(jsonDevice, "banned_all"));
            put("openedAll", Tools.jsonGet(jsonDevice, "opened_all"));
            put("specialBanned", Tools.jsonGet(jsonDevice, "special_banned"));
            put("specialOpened", Tools.jsonGet(jsonDevice, "special_opened"));
            put("bannedAllMsg", Tools.jsonGet(jsonDevice, "bannedAllMsg"));
            put("specialBannedMsg", Tools.jsonGet(jsonDevice, "specialBannedMsg"));
            //Converting json into Config

            if (get("bannedAll").equals(get("openedAll"))) {
                put("errCode", "402");
                put("errMsg", "Each-device config seems wrong.");
                throw new ConfigPullingException();
                //Each-device config seems wrong,but it does not matter,use global config.
            }
            //Judging if server config wrong

            if (get("bannedAll").equals("true")) {
                //Can only query a little user
                //If to-query user is not in the (device)whitelist,throw a exception and show "bannedAllMsg"
                String whiteListMsg = mInterface.httpGet(values.version + "/id/" + mInterface.getDeviceId() + "/special_opened/" + get("studentId"));
                if (!whiteListMsg.equals("ensured")) {
                    //Not in whitelist or get config failed
                    put("canQuery", "false");
                    put("errCode", "202");
                    put("errMsg", get("bannedAllMsg"));
                } else {
                    //Wow,whitelisted user,who it could be?
                    put("canQuery", "true");
                }
            } else {
                //Can NOT query some user
                //Blacklist mode
                String blackListMsg = mInterface.httpGet(values.version + "/id/" + mInterface.getDeviceId() + "/special_banned/" + get("studentId"));
                if (blackListMsg.equals("ensured")) {
                    //Not in blacklist or get config failed
                    put("canQuery", "false");
                    put("errCode", "201");
                    //TODO:Show a "SpecialBannedMsg"
                    put("errMsg", "Permission Denied");
                } else {
                    put("canQuery", "true");
                }
            }

            if (get("canQuery").equals("true")) {
                return this;
            }
            throw new ConfigPullingException();
//======================================================================================================================
        } catch (ConfigPullingException e) {
            if (e.flag == 0) {
                mInterface.throwException(get("errCode") + " - " + get("errMsg"));
                put("canQuery", "false");
                //TODO:Send analysis
            } else if (e.flag == 1) {
                put("errCode", e.errCode);
                put("errMsg", e.errMsg);
                mInterface.throwException(get("errCode") + " - " + get("errMsg"));
                //TODO:Send analysis
            } else {
                //WTF Exception in exception?
                //Then go die
                System.exit(-999);
            }
            return this;
        } catch (IOException e) {
            put("canQuery", "false");
            put("errCode", "101");
            put("errMsg", "Network seems unreachable.");
            mInterface.throwException(get("errCode") + " - " + get("errMsg"));
            //TODO:Send analysis
            return this;
        } finally {
            return this;
        }
    }
    //So it finally finished logical operation.
}
