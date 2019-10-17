package com.wyyx.consumer.annotationCustom;


import com.alibaba.fastjson.JSONObject;
import com.wyyx.consumer.contants.ReturnResultContants;
import com.wyyx.consumer.vo.UserVo;
import com.wyyx.consumer.result.ReturnResultUtils;
import com.wyyx.consumer.util.RedisUtil;
import com.wyyx.provider.contants.CommonContants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.Method;


public class AnnotationLoginReqComplete implements HandlerInterceptor {

    @Autowired
    private RedisUtil redisUtils;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();


        // 判断方法是否添加了这个注解
        AnnotationLoginRequired methodAnnotation = method.getAnnotation(AnnotationLoginRequired.class);


        if (methodAnnotation != null) {


            // 从 http 请求头中取出 token
            String userToken = request.getHeader("userToken");
            // 从 http 请求头中取出 token
            String wxToken = request.getHeader("wxToken");


            if (!StringUtils.isEmpty(userToken) || !StringUtils.isEmpty(wxToken)) {

                String token = !StringUtils.isEmpty(userToken) ? userToken : wxToken;

                String jsonStr = (String) redisUtils.get(CommonContants.LOGIN_NAME_SPACE + token);

                if (!StringUtils.isEmpty(jsonStr)) {

                    //Todo 设置自定义注解

                    UserVo userVo = JSONObject.parseObject(jsonStr, UserVo.class);

                    request.setAttribute("userToken", userVo);

                    return true;
                }

            }


//           throw  new RuntimeException("请先登录");

            response.setCharacterEncoding("UTF-8");
            PrintWriter pw = response.getWriter();

            pw.write(JSONObject.toJSONString(
                    ReturnResultUtils.returnFail(
                            ReturnResultContants.CODE_INTERCPTOR_LOGIN_ERROR,
                            ReturnResultContants.MSG_INTERCPTOR_LOGIN_ERROR)));

            pw.flush();
            pw.close();
            return false;
        }

        return true;

    }


    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
