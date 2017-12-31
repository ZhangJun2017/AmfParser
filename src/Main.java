import com.weedong.flex.client.AMFConnection;
import com.weedong.flex.client.ClientStatusException;
import com.weedong.flex.client.ServerStatusException;
import com.weedong.flex.messaging.io.ASObject;

public class Main {

    public static void main(String[] args) {
        System.out.println("AmfParser Running!");
        System.out.println("By ZhangJun");
        System.out.println("Development Version-0.125.6");
        AMFConnection amfConnection = new AMFConnection();
        try{
            String url="http://211.141.133.22:8081/SchoolCenter/messagebroker/amf";
            amfConnection.connect(url);
            Object result = amfConnection.call("multiExamServiceNew.getAllStudentMultiExam", 19600, "0120151513", "");
            System.out.println(result);
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
            //System.out.println(tree);
            System.out.println("解析到的数据map为：");
            System.out.println(rootMap);
            ASObject examMap = (ASObject) rootMap.get(0);
            //[0]永远是最新一次考试
            System.out.println("解析到的数据exam_map为：");
            System.out.println(examMap);
            Object id = examMap.get("studentId");
            System.out.println("解析到的学号为：");
            System.out.println(id);
        } catch (ClientStatusException e) {
            e.printStackTrace();
        } catch (ServerStatusException e) {
            e.printStackTrace();
        } finally {
            amfConnection.close();
        }
    }

}
