package utils;

import java.util.HashMap;

public class Subject {
    HashMap<Integer, String> subjects = new HashMap<Integer, String>();

    public Subject() {
        subjects.put(0, "语文");
        subjects.put(1, "数学");
        subjects.put(2, "英语");
        subjects.put(3, "政治");
        subjects.put(4, "历史");
        subjects.put(5, "地理");
        subjects.put(6, "生物");
        subjects.put(7, "物理");
        subjects.put(8, "化学");
        subjects.put(9, "体育");
        subjects.put(10, "Wrong!");
    }

    public HashMap getSubjects() {
        return subjects;
    }

    /**
     * @param name 科目名
     */
    public int getIdByName(String name) {
        for (int i = 0; i < 10; i++) {
            if (name.equals(subjects.get(i))) {
                return i;
            } else {
                System.out.println(subjects.get(i) + " is not " + name);
            }
        }
        return 10;
    }

    /**
     * @param id 需获取的ID
     */
    public String getNameById(int id) {
        return subjects.get(id).toString();
    }


}
