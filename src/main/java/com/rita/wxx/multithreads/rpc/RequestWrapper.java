package com.rita.wxx.multithreads.rpc;

/**
 * @author lhbac
 * @create 2023/7/11 23:17
 */
public class RequestWrapper {

    public RequestWrapper(String id, MyRpcRequest request) {
        this.id = id;
        this.request = request;
    }

    public RequestWrapper() { }

    private String id;

    public MyRpcRequest getRequest() {
        return request;
    }

    public void setRequest(MyRpcRequest request) {
        this.request = request;
    }

    private MyRpcRequest request;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
