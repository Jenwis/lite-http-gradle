package com.jenwis.litehttp_gradle.core.http.listener;

import com.jenwis.litehttp_gradle.core.http.request.Request;

/**
 * @author MaTianyu
 * @date 2014-11-06
 */
public interface HttpReadingListener {
    public void onReading(Request req, long total, int len);
}
