package br.net.serviceapp.mylibrary.api.entity;

public class Response {

    public String status =  "error";
    public String message = "";
    public String type = null;
    public Object object = null;

    public Response(boolean success, String message, Object object){
        if(success) this.status = "success";
        this.message = message;
        if(object != null) this.type = object.getClass().getSimpleName();
        this.object = object;
    }

    public Response(boolean success){
        if(success) this.status = "success";
    }

    public Response(boolean success, String message){
        if(success) this.status = "success";
        this.message = message;
    }    

    public Response(Object object){
        if(object != null) {
            this.status = "success";
            this.type = object.getClass().getSimpleName();
            this.object = object;
        }
    }

}
