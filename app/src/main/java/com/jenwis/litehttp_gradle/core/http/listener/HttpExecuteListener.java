package com.jenwis.litehttp_gradle.core.http.listener;

import com.jenwis.litehttp_gradle.core.http.exception.HttpException;
import com.jenwis.litehttp_gradle.core.http.request.Request;
import com.jenwis.litehttp_gradle.core.http.response.Response;

/**
 * @author MaTianyu
 * @date 2014-11-06
 */
public interface HttpExecuteListener {
    public void onStart(Request req) throws HttpException;

    public void onEnd(Response res);

    public void onRetry(Request req, int max, int now);

    public void onRedirect(Request req);
}
