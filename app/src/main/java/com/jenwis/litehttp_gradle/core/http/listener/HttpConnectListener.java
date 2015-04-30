package com.jenwis.litehttp_gradle.core.http.listener;

import com.jenwis.litehttp_gradle.core.http.request.Request;

/**
 * @author MaTianyu
 * @date 2014-11-06
 */
public interface HttpConnectListener {
    public void onPreConnect(Request req);
    public void onAfterConnect(Request req);
}
