package me.mocha.planther.common.resolver;

import me.mocha.planther.common.annotations.CurrentUser;
import me.mocha.planther.common.model.entity.User;
import me.mocha.planther.common.model.repository.UserRepository;
import me.mocha.planther.common.security.user.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    private final UserRepository userRepository;

    @Autowired
    public UserArgumentResolver(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(CurrentUser.class) != null &&
                parameter.getParameterType().equals(User.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findById(principal.getUsername()).orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 사용자입니다."));

    }
}
