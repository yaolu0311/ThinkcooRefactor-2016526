package com.example.administrator.publicmodule.model.rest.deserializers;

import com.example.administrator.publicmodule.entity.BaseStringResponse;
import com.example.administrator.publicmodule.entity.PageBean;
import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by Robert.yao on 2016/6/22.
 */
public class BaseStringResponseDes implements JsonDeserializer<BaseStringResponse> {


    public static  final String TAG = "BaseStringResponseDes";

    @Override
    public BaseStringResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        int status = getStatus(json);
        String msg = getMsg(json);
        String dataString = getData(json);

        PageBean pageBean = getPageBean(json);

        BaseStringResponse baseStringResponse = new BaseStringResponse();
        baseStringResponse.setStatus(status);
        baseStringResponse.setMsg(msg);
        baseStringResponse.setData(dataString);
        baseStringResponse.setPage(pageBean);

        return baseStringResponse;
    }

    private PageBean getPageBean(JsonElement json) {
        PageBean pageBean = new PageBean();
        if (json.getAsJsonObject().has("page")){
            JsonElement jsonElement = json.getAsJsonObject().get("page");
            pageBean.setPageCount(jsonElement.getAsJsonObject().get("pageCount").getAsInt());
            pageBean.setPageNo(jsonElement.getAsJsonObject().get("pageNo").getAsInt());
            pageBean.setPageSize(jsonElement.getAsJsonObject().get("pageSize").getAsInt());
            pageBean.setTotalCount(jsonElement.getAsJsonObject().get("totalCount").getAsInt());
        }
        return pageBean;
    }

    private String getData(JsonElement json) {
        try{
            JsonObject jsonObject = json.getAsJsonObject().getAsJsonObject("data");
            return jsonObject.toString();
        }catch (Exception e){
            ThinkcooLog.e(TAG,e.getMessage(),e);
            return "";
        }
    }

    private String getMsg(JsonElement json) {
        if (json.getAsJsonObject().has("msg")){
            return json.getAsJsonObject().get("msg").getAsString();
        }else {
            return "";
        }
    }

    private int getStatus(JsonElement json) {
        if (json.getAsJsonObject().has("status")){
            return json.getAsJsonObject().get("status").getAsInt();
        }else {
            return -1;
        }
    }
}
