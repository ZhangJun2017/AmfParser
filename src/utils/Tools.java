package utils;


import flex.messaging.io.ArrayCollection;
import flex.messaging.io.amf.ASObject;

import java.util.HashMap;

public class Tools {
    utils.values values = new values();
    HashMap examNameMap = new HashMap();

    public String getNameById(int examId, int classId, ArrayCollection asObject) {
        @Deprecated
        // Do not use anymore
                /**
                 *  examId:考试的[]id
                 *  classId:[seId]
                 *  asObject:传入get好的amf
                 */
                ASObject examMap = (ASObject) asObject.get(examId);
        ArrayCollection eachTypeRoot = (ArrayCollection) examMap.get("singleExams");

        int subjectLength = eachTypeRoot.size();
        for (int i = 0; i < subjectLength; i++) {
            ASObject eachType = (ASObject) eachTypeRoot.get(i);
            int seId = Integer.valueOf(eachType.get("seId").toString());

            String name = (String) eachType.get("seCourseName");
            if (seId == classId) {
                return name;
            }
        }
        return null;
    }

    public String getNameById(String id) {
        return examNameMap.get(id).toString();
    }

    public void createNameMap(ArrayCollection asObject, int examId) {
        ASObject examMap = (ASObject) asObject.get(examId);
        ArrayCollection eachTypeRoot = (ArrayCollection) examMap.get("singleExams");

        int subjectLength = eachTypeRoot.size();
        for (int i = 0; i < subjectLength; i++) {
            ASObject eachType = (ASObject) eachTypeRoot.get(i);
            examNameMap.put(eachType.get("seId").toString(), eachType.get("seCourseName").toString());
        }
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

    public void query(ArrayCollection rootMap, values values) {

        for (int i = rootMap.size() - 1; i > -1; i--) {
            // i means exam num
            ASObject examMap = (ASObject) rootMap.get(i);
            createNameMap(rootMap, i);
            ASObject multiExamMap = (ASObject) examMap.get("multiExam");
            ASObject meStudentScore = (ASObject) examMap.get("meStudentScore");
            ArrayCollection eachTypeRoot = (ArrayCollection) examMap.get("seStudentScoreList");
            int subjectLength = eachTypeRoot.size();
            System.out.println(multiExamMap.get("meName"));
            System.out.println("共" + subjectLength + "科");
            System.out.println("====================");
            for (int o = 0; o < subjectLength; o++) {
                Tools tools = new Tools();
                ASObject eachType = (ASObject) eachTypeRoot.get(o);
                String classId = eachType.get("seId").toString();
                double classScore = Double.valueOf(fixNumber(eachType.get("essScore")));

                /**
                 * !!! Don't Use Anymore !!!
                 *
                 * double转int：
                 *  double d_name = (double) asObject.get("sth.");
                 *  int name = (new Double(d_name)).intValue();
                 */

                values.studentName = eachType.get("studentName").toString();
                String className = getNameById(classId);//tools.getNameById(i, classId, rootMap);
                System.out.println("id:" + fixNumber(classId) + ",name:" + className + ",score:" + fixNumber(eachType.get("essScore")) + ",班排:" + fixNumber(eachType.get("essClassOrder")) + ",年排:" + fixNumber(eachType.get("essGradeOrder")));
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
