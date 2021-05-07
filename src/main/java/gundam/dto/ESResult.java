package gundam.dto;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.*;

public class ESResult {
    private int code;
    private String message;
    private List<Map<String,Object>> hits;
    private String total;

    public void setCode(int code) {
        this.code = code;
    }

    public void setHits(List<Map<String, Object>> hits) {
        this.hits = hits;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public int getCode() {
        return code;
    }

    public List<Map<String, Object>> getHits() {
        return hits;
    }

    public String getMessage() {
        return message;
    }

    public String getTotal() {
        return total;
    }

    public static ESResult parse(JSONObject json) {
        ESResult esResult = new ESResult();
        Integer code = Integer.parseInt(json.get("code").toString());
        String message = json.get("message").toString();
        esResult.setCode(code);
        esResult.setMessage(message);

        if (code != 200)
            return esResult;
        List<Map<String,Object>> hits = new ArrayList<Map<String,Object>>();
        esResult.setHits(hits);
        String total=((JSONObject) ((JSONObject) json.get("data")).get("hits")).get("total").toString();
        esResult.setTotal(total);
        JSONArray jsonArray = (JSONArray) ((JSONObject) ((JSONObject) json.get("data")).get("hits")).get("hits");
        Iterator<Object> iter = jsonArray.iterator();
        while (iter.hasNext()) {
            JSONObject jsonObject = (JSONObject) iter.next();
            jsonObject=(JSONObject) jsonObject.get("_source");
            Map<String,Object> hit=new HashMap<String,Object>();
            for(String key:jsonObject.keySet()){
                hit.put(key,jsonObject.get(key));
            }
            hits.add(hit);
        }
        return esResult;
    }
    public static ESResult originalParse(JSONObject json) {
        ESResult esResult = new ESResult();
        if(json.containsKey("error")){
            esResult.setCode(Integer.parseInt(json.get("status").toString()));
            esResult.setMessage(((JSONObject) json.get("error")).get("reason").toString());
            return esResult;
        }
        List<Map<String,Object>> hits = new ArrayList<Map<String,Object>>();
        esResult.setHits(hits);
        String total=((JSONObject) ((JSONObject) json.get("hits")).get("total")).get("value").toString();
        esResult.setTotal(total);
        JSONArray jsonArray = (JSONArray) ((JSONObject) json.get("hits")).get("hits");
        Iterator<Object> iter = jsonArray.iterator();
        while (iter.hasNext()) {
            JSONObject jsonObject = (JSONObject) iter.next();
            jsonObject=(JSONObject) jsonObject.get("_source");
            Map<String,Object> hit=new HashMap<String,Object>();
            for(String key:jsonObject.keySet()){
                hit.put(key,jsonObject.get(key));
            }
            hits.add(hit);
        }
        return esResult;
    }
}
