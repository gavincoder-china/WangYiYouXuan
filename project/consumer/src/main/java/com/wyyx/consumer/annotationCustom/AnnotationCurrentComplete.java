package com.wyyx.consumer.annotationCustom;




import com.wyyx.consumer.model.UserRedisModel;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

//Todo 设置自定义注解
public class AnnotationCurrentComplete implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(UserRedisModel.class)
               && parameter.hasParameterAnnotation(AnnotationCurrentUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter,
                                  ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory webDataBinderFactory) throws Exception {

        UserRedisModel userRedisModel = (UserRedisModel) webRequest.getAttribute("userToken", RequestAttributes.SCOPE_REQUEST);

        if (userRedisModel != null) {

            return userRedisModel;
        }

        return null;

    }
}
