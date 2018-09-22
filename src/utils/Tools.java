package utils;

import com.weedong.flex.messaging.io.ASObject;

public class Tools {
    values values = new values();

    public String getNameById(int examId, int classId, ASObject asObject) {
        /*
            examId:考试的[]id
            classId:[seId]
            asObject:传入get好的amf
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

    public void query(java.util.ArrayList rootMap, ASObject asObject) {
        for (int i = 0; i < rootMap.size(); i++) {
            //System.out.println("解析到的数据exam_map为：");
            //System.out.println(examMap);
            ASObject examMap = (ASObject) rootMap.get(i);
            Object id = examMap.get("studentId");
            ASObject multiExamMap = (ASObject) examMap.get("multiExam");
            //[MARK:3]
            //System.out.println("解析到的multiExam为：");
            //System.out.println(multiExamMap);
            ASObject meStudentScore = (ASObject) examMap.get("meStudentScore");
            //System.out.println("解析到的meStudentScore为：");
            //System.out.println(meStudentScore);
            //System.out.println("解析到的学号为：");
            //System.out.println(id);
            ASObject scoreMap = (ASObject) examMap.get("seStudentScoreList");
            //[MARK:4]
            //System.out.println("解析到的各科数据为：");
            //System.out.println(scoreMap);
            java.util.ArrayList eachTypeRoot = (java.util.ArrayList) scoreMap.get("source");
            //root类map一定要用array list解析
            //[MARK:5]

            // System.out.println("解析到的各科root数据为：");
            //System.out.println(eachTypeRoot);
            int subjectLength = eachTypeRoot.size();
            //java.util.ArrayList的size()相当于数组的length参数
            System.out.println("共" + subjectLength + "科");

            for (int o = 0; o < subjectLength; o++) {
                //System.out.println(eachTypeRoot.get(i));
                /*HashMap hashMap = new HashMap();
                hashMap.put(i,eachTypeRoot.get(i));
                System.out.println(hashMap);*/
                Tools tools = new Tools();
                ASObject eachType = (ASObject) eachTypeRoot.get(i);
                double d_classId = (double) eachType.get("seId");
                int classId = (new Double(d_classId)).intValue();
                /*
                double转int：
                    double d_name = (double) asObject.get("sth.");
                    int name = (new Double(d_name)).intValue();
                 */
                values.studentName = (String) eachType.get("studentName");
                String className = tools.getNameById(values.examId, classId, asObject);
                System.out.println("id:" + tools.fixNumber(eachType.get("seId")) + ",name:" + className + ",score:" + tools.fixNumber(eachType.get("essScore")) + ",班排," + tools.fixNumber(eachType.get("essClassOrder")) + ",年排," + tools.fixNumber(eachType.get("essGradeOrder")));
                values.fullScore = Double.valueOf(tools.fixNumber(eachType.get("essScore"))) + values.fullScore;
            }
            System.out.println("解析到的姓名为：" + values.studentName);

            //[MARK:6][DELETED]
            //[MARK:7][DELETED]
            //普通的值用object，print时直接print这个object就行了，文件夹要用asobject，还要用(asobject)强制转换。
            Tools tools = new Tools();
            System.out.println("考试情况：");
            System.out.println(multiExamMap.get("meName"));
            System.out.println("班排：" + tools.fixNumber(meStudentScore.get("messClassOrder")));
            System.out.println("年排：" + tools.fixNumber(meStudentScore.get("messGradeOrder")));
            System.out.println("总分：" + tools.fixNumber(values.fullScore));

        }
    }
}
