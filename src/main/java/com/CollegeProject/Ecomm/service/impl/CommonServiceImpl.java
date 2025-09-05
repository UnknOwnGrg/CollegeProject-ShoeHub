package com.CollegeProject.Ecomm.service.impl;

import com.CollegeProject.Ecomm.service.CommonService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class CommonServiceImpl implements CommonService {

    //Concept to remove the session on new refresh
    @Override
    public void removeSessionMessage() {
        HttpServletRequest request = ((ServletRequestAttributes)(RequestContextHolder.getRequestAttributes()))
                .getRequest();
        HttpSession session = request.getSession();
        session.removeAttribute("succMsg");
        session.removeAttribute("errorMsg");
    }
}
