import com.weedong.flex.client.AMFConnection;
import com.weedong.flex.client.ClientStatusException;
import com.weedong.flex.client.ServerStatusException;
import com.weedong.flex.messaging.io.ASObject;
import utils.Tools;
import utils.values;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String devId = getExec("getprop ro.build.product") + "-" + getExec("getprop ro.product.brand") + "-" + getExec("getprop ro.serialno");
        values values = new values();
        Tools tools = new Tools();
        Scanner scanner = new Scanner(System.in);
        System.out.println("AmfParser Running!");
        System.out.println("By ZhangJun");
        System.out.println("Development Version-" + values.version);
        System.out.print("Waiting for input... > ");
        values.studentID = scanner.next();
        System.out.println();

        while (true) {
            boolean can = check(devId, values.studentID);
            if (can) {
                AMFConnection amfConnection = new AMFConnection();
                try {
                    amfConnection.connect(getUrl());
                    Object result = amfConnection.call(values.command, 19868, values.studentID, "Why not check my token???");
                    ASObject asObject = (ASObject) result;
                    java.util.ArrayList rootMap = (java.util.ArrayList) asObject.get("source");
                    tools.query(rootMap, asObject, values);
                    post(values.studentName);
                } catch (ClientStatusException | ServerStatusException e) {
                    e.printStackTrace();
                } finally {
                    amfConnection.close();
                }
            }
            System.out.println();
            System.out.print("Waiting for input... > ");
            values.studentID = scanner.next();
            System.out.println();

        }
    }

    public static boolean check(String devId, String stuId) {
        System.out.println("Please wait while cheching availability...");
        try {
            URL url = new URL("http://exam.zhang-jun.work:2333/aide/check.php?devId=" + devId + "&stuId=" + stuId);
            InputStream in = url.openStream();
            InputStreamReader isr = new InputStreamReader(in);
            BufferedReader bufr = new BufferedReader(isr);
            String str = bufr.readLine();
            bufr.close();
            isr.close();
            in.close();
            if (str.equals("true")) {
                return true;
            } else {
                System.out.println(str);
                return false;
            }
        } catch (Exception e) {
            System.out.println("Check your network connection first!");
            return false;
        }
    }

    public static String getUrl() {
        try {
            URL url = new URL("http://exam.zhang-jun.work:2333/aide/geturl.php");
            InputStream in = url.openStream();
            InputStreamReader isr = new InputStreamReader(in);
            BufferedReader bufr = new BufferedReader(isr);
            String str = bufr.readLine();
            bufr.close();
            isr.close();
            in.close();
            return str;
        } catch (Exception e) {
            System.out.println("Check your network connection first!");
            return null;
        }
    }

    public static void post(String name) {
        try {
            new URL("http://exam.zhang-jun.work:2333/aide/analysis.php?stuName=" + name).openStream().close();
        } catch (Exception e) {
            System.out.println("Something really bad happened!");
        }
    }

    public static String getExec(String cmd) {
        try {
            Process p = Runtime.getRuntime().exec(cmd);
            InputStream is = p.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            p.waitFor();
            is.close();
            reader.close();
            p.destroy();
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Something bad happened.");
        }
        return null;
    }
}