package com.jenwis.litehttp_gradle.core.http.response.handler;

import com.jenwis.litehttp_gradle.core.http.exception.HttpException;
import com.jenwis.litehttp_gradle.core.http.response.Response;

/**
 * 请求的统一返回方法，拓展至HttpModelHandler
 * 方便统一对处理请求返回的处理，如取消进度条等
 *
 * @author Jenwis
 * 2015-5-8
 */
public abstract class HttpModelHandlerWithCommonResponse<Model> extends HttpModelHandler<Model> {
    /*
    * 在onSuccess、onFailure之前调用，不管请求结果是成功还是失败，均调用此方法
    * */
    protected abstract void onResponse(Response res);

    @Override
    public HttpModelHandler<Model> handleModelData(Model data, Response res) {
        onResponse(res);
        super.handleModelData(data, res);

        return this;
    }
}
