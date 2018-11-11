package utils;

import com.weedong.flex.messaging.io.ASObject;
import utils.values;

public class Tools {
    utils.values values = new values();

    public String getNameById(int examId, int classId, ASObject asObject) {
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

    public String fixNumber(String num) {
        if (num.indexOf(".") > 0) {
            num = num.replaceAll("0+?$", "");//去掉后面无用的零
            num = num.replaceAll("[.]$", "");//如小数点后面全是零则去掉小数点
        }
        return num;
    }

    public String fixNumber(Object num) {
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
                /**
                 *double转int：
                 *double d_name = (double) asObject.get("sth.");
                 *  int name = (new Double(d_name)).intValue();
                 */
                values.studentName = (String) eachType.get("studentName");
                String className = tools.getNameById(i, classId, asObject);
                System.out.println("id:" + tools.fixNumber(eachType.get("seId")) + ",name:" + className + ",score:" + tools.fixNumber(eachType.get("essScore")) + ",班排:" + tools.fixNumber(eachType.get("essClassOrder")) + ",年排:" + tools.fixNumber(eachType.get("essGradeOrder")));
                values.fullScore = Double.valueOf(tools.fixNumber(eachType.get("essScore"))) + values.fullScore;
            }
            Tools tools = new Tools();
            System.out.println("班排：" + tools.fixNumber(meStudentScore.get("messClassOrder")));
            System.out.println("年排：" + tools.fixNumber(meStudentScore.get("messGradeOrder")));
            System.out.println("总分：" + tools.fixNumber(values.fullScore));
            System.out.println("===========================================");
            values.fullScore = 0;
        }
        System.out.println("====== " + values.studentName + " (" + values.studentID + ")");
    }
}
