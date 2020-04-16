package com.zhengwei.clound.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import javax.servlet.http.HttpServletRequest;

/**
 * TODO
 *
 * @author zevin aibaokeji
 * @version 1.0
 * 2020/4/1113:39
 **/
public class MethodFilter extends ZuulFilter {

    public MethodFilter(){
        super();
    }

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        String method = request.getMethod();
        System.out.println("请求方法：" + method);
        if (method == null) {
            //告诉 Zuul 不需要将当前请求转发到后端的服务了。通过 setResponseBody 返回数据给客户端
            context.setSendZuulResponse(false);
            context.setResponseBody("错误请求方法");
            return null;
        }

        /*忽略白名单的uri访问，直接通过 eg: /czh/zebra/data/callback*/
        String reqURI = request.getRequestURI();
        System.out.println("请求路径：" + reqURI);
        if (reqURI.endsWith("bucunzai")){
            //告诉 Zuul 不需要将当前请求转发到后端的服务了。通过 setResponseBody 返回数据给客户端
            context.setSendZuulResponse(false);
            context.setResponseBody("错误请求路径");
            return null;
        }
        return null;
    }
}
