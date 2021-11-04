package com.example.bamboo.javaBean;

public class BaseResponse<T> {
    //与json返回的一致
    private T results;

    public T getResults() {
        return results;
    }

    public void setResults(T results) {
        this.results = results;
    }

}
