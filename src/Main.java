import com.weedong.flex.client.AMFConnection;
import com.weedong.flex.client.ClientStatusException;
import com.weedong.flex.client.ServerStatusException;
import com.weedong.flex.messaging.io.ASObject;
import utils.Tools;

public class Main {

    public static void main(String[] args) {
        values values = new values();
        System.out.println("AmfParser Running!");
        System.out.println("By ZhangJun");
        System.out.println("Development Version-" + values.version);
        AMFConnection amfConnection = new AMFConnection();
        try {
            amfConnection.connect(values.url);
            Object result = amfConnection.call(values.command, 19600, values.studentID, "");
            //拿数据
            System.out.println("解析数据：");
            System.out.println("====================");
            System.out.println(result);
            System.out.println("====================");
            //ArrayCollection arrayCollection = (ArrayCollection)object;
            //System.out.println(arrayCollection);
            ASObject asObject = (ASObject) result;
            /*System.out.println(asObject);
            System.out.println("=====================================");
            ArrayList array = (ArrayList)object;
            System.out.println(array);
            System.out.println(array.get(0));*/
            //System.out.println(asObject.toString());
            //ASObject amfResult = (ASObject) result;
            //Object[] array = null;
            //arrayCollection.toArray(array);
            //System.out.println(arrayCollection.get(arrayCollection.size()-1));
            //String resultString = asObject.toString();
            /*HashMap<Integer,String> test = new HashMap<Integer, String>();
            test.put(1,"tes1");
            test.put(2,"test2");
            test.put(3,"t3");
            System.out.println(test);
            System.out.println(test.get(2));*/
            //System.out.println(asObject);
            java.util.ArrayList rootMap = (java.util.ArrayList) asObject.get("source");
            //从有3个[]的body里开始解析
            //[MARK:1]
            //System.out.println(tree);
            System.out.println("解析到的数据map为：");
            System.out.println(rootMap);
            ASObject examMap = (ASObject) rootMap.get(values.examId);
            //[MARK:2]
            //[0]永远是最新一次考试
            System.out.println("解析到的数据exam_map为：");
            System.out.println(examMap);
            Object id = examMap.get("studentId");
            //[MARK:3]
            System.out.println("解析到的学号为：");
            System.out.println(id);
            ASObject scoreMap = (ASObject) examMap.get("seStudentScoreList");
            //[MARK:4]
            System.out.println("解析到的各科数据为：");
            System.out.println(scoreMap);
            java.util.ArrayList eachTypeRoot = (java.util.ArrayList) scoreMap.get("source");
            //root类map一定要用array list解析
            //[MARK:5]

            System.out.println("解析到的各科root数据为：");
            System.out.println(eachTypeRoot);
            int subjectLength = eachTypeRoot.size();
            //java.util.ArrayList的size()相当于数组的length参数
            System.out.println("共" + subjectLength + "科");

            for (int i = 0; i < subjectLength; i++) {
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
                System.out.println("id:" + eachType.get("seId") + ",name:" + className + ",score:" + eachType.get("essScore"));
            }
            System.out.println("解析到的姓名为：" + values.studentName);
            //[MARK:6][DELETED]
            //[MARK:7][DELETED]
            //普通的值用object，print时直接print这个object就行了，文件夹要用asobject，还要用(asobject)强制转换。

        } catch (ClientStatusException e) {
            e.printStackTrace();
        } catch (ServerStatusException e) {
            e.printStackTrace();
        } finally {
            amfConnection.close();
        }
    }

}

class values {
    String version = "0.3.3";
    String studentID = "0120151513";
    String url = "http://211.141.133.22:8081/SchoolCenter/messagebroker/amf";
    String command = "multiExamServiceNew.getAllStudentMultiExam";
    int examId = 0;
    String studentName = "Null1";
}
