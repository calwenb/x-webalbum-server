package com.wen.xwebalbum.interceptor;

import com.wen.xwebalbum.utils.LoggerUtil;
import com.wen.xwebalbum.utils.NullUtil;
import com.wen.xwebalbum.utils.ResponseUtil;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ValueConstants;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class NormInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
        // 如果不是映射到方法直接通过`
        if (!(object instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();

        //拦截未填写必备参数的请求
        Parameter[] params = method.getParameters();
        for (Parameter param : params) {
            RequestParam annotation = param.getAnnotation(RequestParam.class);
            if (annotation == null || !annotation.required() || !annotation.defaultValue().equals(ValueConstants.DEFAULT_NONE)) {
                continue;
            }
            String value = annotation.value();
            String parameter = request.getParameter(value);
            if (NullUtil.hasNull(parameter)) {
                if (param.getType().equals(MultipartFile.class)) {
                    continue;
                }
                response.getWriter().println(ResponseUtil.badRequest("400"));
                LoggerUtil.error("400错误: null" + "query:" + value + " 属性：" + param, NormInterceptor.class);
                return false;
            }
        }
        return true;
    }

}
