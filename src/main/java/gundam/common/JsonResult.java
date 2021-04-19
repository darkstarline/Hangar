package gundam.common;

import com.alibaba.fastjson.JSONObject;
import gundam.utils.JaxbUtils;

public class JsonResult extends JSONObject {
    private static final long serialVersionUID = 155493030636726896L;

    public static final String SUCCESS = "success";
    public static final String MESSAGE = "message";
    public static final String CODE = "code";

    public JsonResult(){
        this.put(SUCCESS, true);
    }

    public void setSuccess(boolean success) {
        this.put(SUCCESS, success);
    }

    public void setMessage(String message, Object... args) {
        this.put(MESSAGE, JaxbUtils.getErrorDesc(message, args));
    }

    public boolean isSuccess(){
        return this.getBooleanValue(SUCCESS);
    }

    public String getMessage(){
        return this.getString(MESSAGE);
    }

    public String getSuccess(){
        return this.getString(SUCCESS);
    }

    public void setCode(String message) {
        this.put(CODE, JaxbUtils.getErrorDesc(message));
    }
}
