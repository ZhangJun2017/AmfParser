import com.weedong.flex.client.AMFConnection;
import com.weedong.flex.client.ClientStatusException;
import com.weedong.flex.client.ServerStatusException;
import com.weedong.flex.messaging.io.ASObject;

public class Main {

    public static void main(String[] args) {
        System.out.println("AmfParser Running!");
        System.out.println("By ZhangJun");
        System.out.println("Development Version-0.125.4");
        AMFConnection amfConnection = new AMFConnection();
        try{
            String url="http://211.141.133.22:8081/SchoolCenter/messagebroker/amf";
            amfConnection.connect(url);
            Object object = amfConnection.call("multiExamServiceNew.getAllStudentMultiExam", 19600, "0120151513", "");
            System.out.println(object);
            //ArrayCollection arrayCollection = (ArrayCollection)object;
            //System.out.println(arrayCollection);
            ASObject asObject = (ASObject) object;
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
            System.out.println(asObject);
            java.util.ArrayList tree = (java.util.ArrayList) asObject.get("source");

            //System.out.println(tree);

            System.out.println("解析到的数据tree为：");
            System.out.println(tree);
            ASObject tree1 = (ASObject) tree.get(0);

            System.out.println("解析到的数据tree1为：");
            System.out.println(tree1);
            Object id = tree1.get("studentId");
            System.out.println("解析到的学号为：");
            System.out.println(id);
            if ("" == "[]") {
                System.out.println("数据错误");
            } else {
                //System.out.println(result.equals(result));
                System.out.println("");
                System.out.println("解析到的数据tree为：");
                System.out.println(tree);
                System.out.println("解析到的数据tree1为：");
                System.out.println(tree1.toString());
                System.out.println("解析到的学校为：");
                System.out.println(id);
            }
        } catch (ClientStatusException e) {
            e.printStackTrace();
        } catch (ServerStatusException e) {
            e.printStackTrace();
        } finally {
            amfConnection.close();
        }
    }

}
