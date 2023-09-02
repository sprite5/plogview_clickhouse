package vip.zhaoxing.plogview.base;


import lombok.Data;

@Data
public class Response<T>{
    private int code;
    private String msg;
    private T data;

    public static Response<Void> defaultSuccessResponse(){
        var response = new Response<Void>();
        response.setCode(200);
        return response;
    }

    public static <T> Response<T> successResponse(T data){
        var response = new Response<T>();
        response.setCode(200);
        response.setData(data);
        return response;
    }

    public static <T> Response<T> errorResponse(int code,String msg){
        var response = new Response<T>();
        response.setCode(code);
        response.setMsg(msg);
        return response;
    }

    public static  <T> Response<T> errorByCodeEnum(int code, String msg){
        var response = new Response<T>();
        response.setCode(code);
        response.setMsg(msg);
        return response;
    }
}
