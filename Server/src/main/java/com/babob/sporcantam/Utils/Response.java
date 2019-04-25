package com.babob.sporcantam.Utils;


public class Response {
    private String msg;
    private Boolean success;
    private Object payload;
    public Response(String msg, Boolean success){
        this.msg = msg;
        this.success = success;
    }
    public void setPayload(Object payload){ this.payload = payload;}
    public String getMsg() {
        return msg;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
