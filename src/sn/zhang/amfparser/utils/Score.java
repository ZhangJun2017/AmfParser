package sn.zhang.amfparser.utils;

import com.weedong.flex.messaging.io.ASObject;

import java.util.HashMap;

public class Score {
    HashMap scoreMap = new HashMap();

    public Score(ASObject scoreList) {
        scoreMap = scoreList;
    }

    public Number getScoreBySeId(int seId) {
        return (Number) scoreMap.get(seId);
    }
}
