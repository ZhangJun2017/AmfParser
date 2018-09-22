import com.weedong.flex.client.AMFConnection;
import com.weedong.flex.client.ClientStatusException;
import com.weedong.flex.client.ServerStatusException;
import com.weedong.flex.messaging.io.ASObject;
import utils.Tools;
import utils.values;

public class Main {

    public static void main(String[] args) {

        values values = new values();
        Tools tools = new Tools();
        System.out.println("AmfParser Running!");
        System.out.println("By ZhangJun");
        System.out.println("Development Version-" + values.version);
        if (args.length == 0) {
            System.out.println("没有指定学号，使用默认学号。");
        } else {
            values.studentID = args[0];
        }
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
            //从有几个[]的大body里开始解析
            //[MARK:1]
            //System.out.println(tree);

            System.out.println("解析到的数据map为：");
            System.out.println(rootMap);
            tools.query(rootMap, asObject);

        } catch (ClientStatusException e) {
            e.printStackTrace();
        } catch (ServerStatusException e) {
            e.printStackTrace();
        } finally {
            amfConnection.close();
        }
    }

}
/*
class values {
    String version = "0.3.7-school_build";
    String studentID = "0120151513";
    String url = "http://211.141.133.22:8081/SchoolCenter/messagebroker/amf";
    String command = "multiExamServiceNew.getAllStudentMultiExam";
    int examId = 0;
    String studentName = "Null";
    double fullScore = 0;
}
*/