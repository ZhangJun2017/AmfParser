package utils;

import flex.messaging.io.amf.ASObject;

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
