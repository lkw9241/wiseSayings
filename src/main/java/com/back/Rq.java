package com.back;

import java.util.HashMap;
import java.util.Map;

public class Rq {
    private final String actionName; // 명령어 이름
    // 명령어와 파라미터를 저장하는 맵
    private final Map<String, String> paramsMap;

    public Rq(String cmd){
        paramsMap = new HashMap<>();

        String[] cmdBits = cmd.split("\\?",2);
         // cmd.split("\\?",2) : ?를 기준으로 최대 2개로 나누기
        actionName = cmdBits[0]; // 명령어 부분
        String queryString = cmdBits.length > 1 ? cmdBits[1].trim() : ""; // 쿼리 문자열 부분

        String[] queryStringBits= queryString.split("&"); // &를 기준으로 파라미터 분리

        for(String queryParam : queryStringBits){
            String[] queryParamBits = queryParam.split("=",2); // =를 기준으로 최대 2개로 나누기
            String key = queryParamBits[0].trim(); // 파라미터 이름
            String value = queryParamBits.length > 1 ? queryParamBits[1].trim() : ""; // 파라미터 값

            if(key.isEmpty()){
                continue;
            }
            paramsMap.put(key, value); // 파라미터 이름과 값을 맵에 저장
        }
    }
        // this.cmd = cmd.trim();
        public String getActionName() {
            return actionName;
        }


    public String getParam(String paramName, String defaultValue) {
        if(paramsMap.containsKey(paramName)) {
            return paramsMap.get(paramName);
        } else {
            return defaultValue; // 해당 파라미터가 없으면 기본값 반환
        }
    }


}
