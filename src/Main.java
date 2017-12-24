import flex.messaging.io.amf.client.AMFConnection;
import flex.messaging.io.amf.client.exceptions.ClientStatusException;
import flex.messaging.io.amf.client.exceptions.ServerStatusException;

public class Main {

    public static void main(String[] args) {
        System.out.println("AmfParser Running!");
        System.out.println("By ZhangJun");
        System.out.println("Development Version-0.125.2");
        AMFConnection amfConnection = new AMFConnection();
        try{
            String url="http://211.141.133.22:8081/SchoolCenter/messagebroker/amf";
            amfConnection.connect(url);
            Object result = amfConnection.call("multiExamServiceNew.getAllStudentMultiExam",19600,"0120151513","HDC/gq15+GtcBjjeW6hTYw==");

            System.out.println(result.toString());
        } catch (ClientStatusException e) {
            e.printStackTrace();
        } catch (ServerStatusException e) {
            e.printStackTrace();
        } finally {
            amfConnection.close();
        }
    }
}
