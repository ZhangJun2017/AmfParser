package utils;

import com.weedong.flex.messaging.io.ASObject;

public class Tools {
    public String getNameById(int examId, int classId, ASObject asObject) {
        /*
            examId:考试的[]id
            classId:[seId]
            asObject:传入get好的amf
         */
        java.util.ArrayList rootMap = (java.util.ArrayList) asObject.get("source");
        ASObject examMap = (ASObject) rootMap.get(examId);
        ASObject eachTypeMap = (ASObject) examMap.get("singleExams");
        java.util.ArrayList eachTypeRoot = (java.util.ArrayList) eachTypeMap.get("source");
        int subjectLength = eachTypeRoot.size();
        for (int i = 0; i < subjectLength; i++) {
            ASObject eachType = (ASObject) eachTypeRoot.get(i);
            double d_seId = (double) eachType.get("seId");
            int seId = (new Double(d_seId)).intValue();
            String name = (String) eachType.get("seCourseName");
            if (seId == classId) {
                return name;
            }
        }
        return null;
    }
}
