package com.wildwolf.myplayer.model.net;

/**
 * Created by ${wild00wolf} on 2016/12/6.
 */
public class GankHttpResponse<T> {

    private boolean error;
    private T results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public T getResults() {
        return results;
    }

    public void setResults(T results) {
        this.results = results;
    }
}
