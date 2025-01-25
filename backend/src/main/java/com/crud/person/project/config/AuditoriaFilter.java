package com.crud.person.project.config;

import com.crud.person.project.model.Auditoria;
import com.crud.person.project.repository.AuditoriaRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuditoriaFilter implements Filter {

    private final AuditoriaRepository auditoriaRepository;

    public AuditoriaFilter(AuditoriaRepository auditoriaRepository) {
        this.auditoriaRepository = auditoriaRepository;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (servletRequest instanceof HttpServletRequest httpServletRequest && servletResponse instanceof HttpServletResponse httpServletResponse) {
            String metodo = httpServletRequest.getMethod();
            String endpoint = httpServletRequest.getRequestURI();

            filterChain.doFilter(servletRequest, servletResponse);

            int status = httpServletResponse.getStatus();

            Auditoria auditoria = new Auditoria();

            auditoria.setMetodo(metodo);
            auditoria.setEndpoint(endpoint);
            auditoria.setStatus(status);

            auditoriaRepository.save(auditoria);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
