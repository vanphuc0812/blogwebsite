package com.example.blogwebsite.security.authorization;

import com.example.blogwebsite.role.model.Operation;
import com.example.blogwebsite.role.repository.OperationRepository;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Aspect
@Component
@Slf4j
public class AuthorizationAspect {
    private final OperationRepository operationRepository;

    public AuthorizationAspect(OperationRepository operationRepository) {
        this.operationRepository = operationRepository;
    }
    // define point cut

    @Before("@annotation(PlogOperation)")
    public void authorizeOperation(PlogOperation PlogOperation) {
        log.info("Pointcut has been activated, operation = " + PlogOperation.name());
        if (PlogOperation.name().equals("publicOperation")) return;
        // get current user
        String username = getCurrentUser();
        // check permission
        if (!isPermitted(username, PlogOperation.name())) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "User is not permitted to use this operation. Please contact administrators for permissions");
        }
    }

    private boolean isPermitted(String username, String operationName) {
        if ("root_admin".equals(username)) return true;
        List<Operation> permittedOperations
                = operationRepository.findAllByNameAndUsername(operationName, username);

        return !permittedOperations.isEmpty();
    }

    private String getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null)
            return null;

        if (auth.getPrincipal() instanceof String principal)
            return principal;

        UserDetails currentUser = (UserDetails) auth.getPrincipal();
        return currentUser.getUsername();
    }
}
