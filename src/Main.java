import com.weedong.flex.client.AMFConnection;
import com.weedong.flex.client.ClientStatusException;
import com.weedong.flex.client.ServerStatusException;
import com.weedong.flex.messaging.io.ASObject;
import sn.zhang.amfparser.utils.ExcelUtils;
import sn.zhang.amfparser.utils.Tools;
import sn.zhang.amfparser.utils.values;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        values values = new values();
        Tools tools = new Tools();
        Scanner scanner = new Scanner(System.in);
        System.out.println("This is a xls-exporter version of Enumer.");
        System.out.println("This is a NUKE-LEVEL version of Enumer!");
        System.out.println("Id Enumer Running!");
        System.out.println("By ZhangJun");
        System.out.println("Kernel Version:" + values.kernelVersion);
        System.out.println("Shell Version:" + values.shellVersion);
        System.out.println();
        //String prefix = "218", subfix = "1", total, class_id;
        //System.out.print("Enter Class:");
        //class_id = scanner.nextLine();
        //prefix = "218" + class_id + "1";
        //ArrayList al = new ArrayList(60);
        //al.addAll(tools.createListByClass(prefix));
        //System.out.println();
        //for (int i = 1; i < al.size() + 1; i++) {
        AMFConnection amfConnection = new AMFConnection();
        try {
            amfConnection.connect(values.url);
            Object result = amfConnection.call(values.command, 3997, 19868);
            ASObject asObject = (ASObject) result;
            java.util.ArrayList rootMap = (java.util.ArrayList) asObject.get("source");
            for (int i = 0; i < rootMap.size(); i++) {
                ASObject temp = (ASObject) rootMap.get(i);
                String tmp[] = {temp.get("studentId").toString(), temp.get("studentName").toString()};
                values.sheet.add(tmp);
                System.out.println(i + "/" + rootMap.size());
            }
            //values.studentID = al.get(i).toString();
            //if (rootMap.size() == 0) {
            //    break;
            //}
            //tools.query(rootMap, asObject, values);
            //String tmp[] = {values.studentName, values.studentID};
            //values.sheet.add(tmp);
        } catch (ClientStatusException e) {
            e.printStackTrace();
        } catch (ServerStatusException e) {
            e.printStackTrace();
        } finally {
            amfConnection.close();
        }
        //}

        String export = "学号#id,姓名#name";
        String[] excelHeader = export.split(",");
        try {
            System.out.println("Excel Working...");
            ExcelUtils.export(excelHeader, values.sheet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

