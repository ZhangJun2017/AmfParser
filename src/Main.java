import com.weedong.flex.client.AMFConnection;
import com.weedong.flex.client.ClientStatusException;
import com.weedong.flex.client.ServerStatusException;
import com.weedong.flex.messaging.io.ASObject;
import sn.zhang.amfparser.ConsoleInterface;
import sn.zhang.amfparser.Interface;
import sn.zhang.amfparser.utils.Tools;
import sn.zhang.amfparser.utils.values;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Interface mInterface = new ConsoleInterface();
        String url = "0.6.0/default/config.json1";
        String result = "";
        try {
            result = mInterface.httpGet(url);
            System.out.println(Tools.parseJson(result));
        } catch (Exception e) {
            mInterface.throwException(e.toString());
        }/**
         if (result.get("status").equals("ok")) {
         if (result.get("code").equals("200")) {
         //do something
         //mInterface.addText(result.get("result").toString());
         } else {
         //get failed!
         //mInterface.addText(result.get("code").toString());
         }
         } else {
         mInterface.throwException(result.get("result").toString());
         }**/
        /**==========================================DEBUG OUTPUT==========================================**/
        System.out.println("Requested URL:" + url);
        //System.out.println("Result Code:" + result.get("code"));
        //System.out.println("Result:" + result.get("result"));
        //System.out.println("getDeviceId() -> " + mInterface.getDeviceId());
        System.exit(0);
        /**==========================================DEBUG ENABLED==========================================**/
        values values = new values();
        Tools tools = new Tools();
        Scanner scanner = new Scanner(System.in);
        System.out.println("AmfParser Running!");
        System.out.println("By ZhangJun");
        System.out.println("Development Version-" + values.version);
        boolean whileQuery = true;
        if (args.length != 0) {
            whileQuery = false;
            values.studentID = args[0];
        } else {
            System.out.print("Waiting for input... > ");
            values.studentID = scanner.next();
            System.out.println();
        }
        while (true) {
            AMFConnection amfConnection = new AMFConnection();
            try {
                amfConnection.connect(values.url);
                Object result1 = amfConnection.call(values.command, 19868, values.studentID, "Why not check my token???");
                //拿数据
                /*System.out.println("解析数据：");
                System.out.println("====================");
                System.out.println(result);
                System.out.println("====================");*/
                ASObject asObject = (ASObject) result1;
                java.util.ArrayList rootMap = (java.util.ArrayList) asObject.get("source");
                /*System.out.println("解析到的数据map为：");
                System.out.println(rootMap);
                System.out.println("====================");*/
                tools.query(rootMap, asObject, values);
                if (whileQuery == true) {
                    System.out.println();
                    System.out.print("Waiting for input... > ");
                    values.studentID = scanner.next();
                    System.out.println();
                } else {
                    System.exit(0);
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
}
