package sn.zhang.amfparser.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.weedong.flex.client.AMFConnection;
import com.weedong.flex.client.ClientStatusException;
import com.weedong.flex.client.ServerStatusException;
import com.weedong.flex.messaging.io.ASObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class Tools {
    public static String httpGet(String urls) throws IOException {
        String url = values.configServerPrefix + urls;
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        //TRY
        Response response = okHttpClient.newCall(request).execute();
        if (response.code() != 200) {
            return String.valueOf(response.code());
        } else {
            return response.body().string();
        }
    }

    public static Object amfGet(String urls, String command, String... args) throws ClientStatusException, ServerStatusException {
        AMFConnection amfConnection = new AMFConnection();
        amfConnection.connect(urls);
        return amfConnection.call(command, args);
    }

    public static JsonObject parseJson(String json) throws ConfigPullingException {
        JsonElement jsonElement;
        try {
            jsonElement = new JsonParser().parse(json);
        } catch (JsonParseException e) {
            throw new ConfigPullingException("403", "Json Invalid");
        }

        if (jsonElement.isJsonObject()) {
            return jsonElement.getAsJsonObject();
        }

        throw new ConfigPullingException("403", "Json Invalid");
    }

    public static boolean isValidJson(String json) {
        JsonElement jsonElement;

        try {
            jsonElement = new JsonParser().parse(json);
        } catch (JsonParseException e) {
            return false;
        }

        if (jsonElement.isJsonObject()) {
            return true;
        }

        return false;
    }

    public static String jsonGet(JsonObject json, String name) throws ConfigPullingException {
        JsonElement jsonElement = json.get(name);
        if (jsonElement != null) {
            return jsonElement.getAsString();
        }
        throw new ConfigPullingException("404", "Incorrect form in json.");
    }

    public static String getNameById(int examId, int classId, ASObject asObject) {
        /**
         *  examId:考试的[]id
         *  classId:[seId]
         *  asObject:传入get好的amf
         */
        java.util.ArrayList rootMap = (java.util.ArrayList) asObject.get("source");
        ASObject examMap = (ASObject) rootMap.get(examId);
        ASObject eachTypeMap = (ASObject) examMap.get("singleExams");
        java.util.ArrayList eachTypeRoot = (java.util.ArrayList) eachTypeMap.get("source");
        int subjectLength = eachTypeRoot.size();
        for (int i = 0; i < subjectLength; i++) {
            ASObject eachType = (ASObject) eachTypeRoot.get(i);
            double d_seId = (double) eachType.get("seId");
            int seId = (new Double(d_seId)).intValue();
            String name = (String) eachType.get("seCourseName");
            if (seId == classId) {
                return name;
            }
        }
        return null;
    }

    public static String fixNumber(String num) {
        if (num.indexOf(".") > 0) {
            num = num.replaceAll("0+?$", "");//去掉后面无用的零
            num = num.replaceAll("[.]$", "");//如小数点后面全是零则去掉小数点
        }
        return num;
    }

    public static String fixNumber(Object num) {
        String tmp = num.toString();
        if (tmp.indexOf(".") > 0) {
            tmp = tmp.replaceAll("0+?$", "");//去掉后面无用的零
            tmp = tmp.replaceAll("[.]$", "");//如小数点后面全是零则去掉小数点
        }
        return tmp;
    }

    public void query(java.util.ArrayList rootMap, ASObject asObject, values values) {
        java.util.ArrayList eachTypeRoot;
        for (int i = rootMap.size() - 1; i > -1; i--) {
            ASObject examMap = (ASObject) rootMap.get(i);
            ASObject multiExamMap = (ASObject) examMap.get("multiExam");
            ASObject meStudentScore = (ASObject) examMap.get("meStudentScore");
            ASObject scoreMap = (ASObject) examMap.get("seStudentScoreList");
            eachTypeRoot = (java.util.ArrayList) scoreMap.get("source");
            int subjectLength = eachTypeRoot.size();
            System.out.println(multiExamMap.get("meName"));
            System.out.println("共" + subjectLength + "科");
            System.out.println("====================");
            for (int o = 0; o < subjectLength; o++) {
                Tools tools = new Tools();
                ASObject eachType = (ASObject) eachTypeRoot.get(o);
                double d_classId = (double) eachType.get("seId");
                int classId = (new Double(d_classId)).intValue();
                double classScore = Double.valueOf(tools.fixNumber(eachType.get("essScore")));
                /**
                 *double转int：
                 *double d_name = (double) asObject.get("sth.");
                 *  int name = (new Double(d_name)).intValue();
                 */
                values.studentName = (String) eachType.get("studentName");
                String className = tools.getNameById(i, classId, asObject);
                System.out.println("id:" + tools.fixNumber(eachType.get("seId")) + ",name:" + className + ",score:" + tools.fixNumber(eachType.get("essScore")) + ",班排:" + tools.fixNumber(eachType.get("essClassOrder")) + ",年排:" + tools.fixNumber(eachType.get("essGradeOrder")));
                values.fullScore = classScore + values.fullScore;
                switch (className) {
                    case "语文":
                        values.zhuKe = values.zhuKe + classScore;
                        break;
                    case "数学":
                        values.zhuKe = values.zhuKe + classScore;
                        break;
                    case "英语":
                        values.zhuKe = values.zhuKe + classScore;
                        break;
                    case "物理":
                        values.liZhong = values.liZhong + classScore;
                        break;
                    case "化学":
                        values.liZhong = values.liZhong + classScore;
                        break;
                    case "生物":
                        values.liZhong = values.liZhong + classScore;
                        break;
                    case "政治":
                        values.wenZhong = values.wenZhong + classScore;
                        break;
                    case "历史":
                        values.wenZhong = values.wenZhong + classScore;
                        break;
                    case "地理":
                        values.wenZhong = values.wenZhong + classScore;
                        break;
                    default:
                        break;
                }
                //Judge which score to sum(文综，理综，主科)
            }
            Tools tools = new Tools();
            System.out.println("班排：" + tools.fixNumber(meStudentScore.get("messClassOrder")));
            System.out.println("年排：" + tools.fixNumber(meStudentScore.get("messGradeOrder")));
            System.out.println("主科：" + tools.fixNumber(values.zhuKe));
            System.out.println("文综：" + tools.fixNumber(values.wenZhong));
            System.out.println("理综：" + tools.fixNumber(values.liZhong));
            System.out.println("总分：" + tools.fixNumber(values.fullScore));
            System.out.println("===========================================");
            values.fullScore = 0;
            values.zhuKe = 0;
            values.wenZhong = 0;
            values.liZhong = 0;
            //Remove One-Use Vars
        }
        System.out.println("====== " + values.studentName + " (" + values.studentID + ")");
    }
}
