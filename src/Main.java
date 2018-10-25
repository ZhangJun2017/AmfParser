import com.weedong.flex.client.AMFConnection;
import com.weedong.flex.client.ClientStatusException;
import com.weedong.flex.client.ServerStatusException;
import com.weedong.flex.messaging.io.ASObject;
import utils.Tools;
import utils.values;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        values values = new values();
        Scanner scanner = new Scanner(System.in);
        boolean whileQuery = true;
        if (args.length != 0) {
            whileQuery = false;
            values.studentID = args[0];
        } else {
            System.out.print("Waiting for input... > ");
            values.studentID = scanner.next();
        }
        Tools tools = new Tools();
        System.out.println("AmfParser Running!");
        System.out.println("By ZhangJun");
        System.out.println("Development Version-" + values.version);
        do {
            AMFConnection amfConnection = new AMFConnection();
            try {
                amfConnection.connect(values.url);
                Object result = amfConnection.call(values.command, 19868, values.studentID, "");
                //拿数据
                System.out.println("解析数据：");
                System.out.println("====================");
                System.out.println(result);
                System.out.println("====================");
                ASObject asObject = (ASObject) result;
                java.util.ArrayList rootMap = (java.util.ArrayList) asObject.get("source");
                System.out.println("解析到的数据map为：");
                System.out.println(rootMap);
                System.out.println("====================");
                tools.query(rootMap, asObject);
                if (whileQuery == true) {
                    System.out.println();
                    System.out.print("Waiting for input... > ");
                    values.studentID = scanner.next();
                }
            } catch (ClientStatusException e) {
                e.printStackTrace();
            } catch (ServerStatusException e) {
                e.printStackTrace();
            } finally {
                amfConnection.close();
            }
        } while (whileQuery);
    }
}
