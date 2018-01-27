package utils;

import com.weedong.flex.messaging.io.ASObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Tools {
    HashMap examMap = new HashMap();
    HashMap<Integer, ArrayList> hashmap = new HashMap<Integer, ArrayList>();

    public Tools(ASObject asObject) {
        ASObject eachTypeMap = (ASObject) asObject.get("singleExams");
        java.util.ArrayList eachTypeRoot = (java.util.ArrayList) eachTypeMap.get("source");
        int subjectLength = eachTypeRoot.size();
        for (int i = 0; i < subjectLength; i++) {
            ASObject eachType = (ASObject) eachTypeRoot.get(i);
            double d_seId = (double) eachType.get("seId");
            int seId = (new Double(d_seId)).intValue();
            String name = (String) eachType.get("seCourseName");
            ArrayList array = new ArrayList();
            array.add(seId, name);
            examMap.put(i, array);
        }
    }

    /**
     * @param classId [seId]
     */
    public String getNameById(int classId) {
        return (String) examMap.get(classId);
    }

    /**
     * @param name 科目名
     */
    public int getIdByName(String name) {
        for (int i = 0; i < examMap.size(); i++) {
            if (name == examMap.get(i)) {
                return i;
            }
        }
        return 10;
    }

    public int SeId2Id(Number seId) {
        for (int i = 0; i < examMap.size(); i++) {

        }
        return 0;
    }
}
