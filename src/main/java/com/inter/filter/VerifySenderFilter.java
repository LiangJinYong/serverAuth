package com.inter.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class VerifySenderFilter implements Filter {

	public void init(FilterConfig filterConfig) throws ServletException {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		
		String sender = req.getHeader("sender");
			
		// when reWrite request, not check sender
		String path = req.getRequestURI();
		if ((path != null && path.startsWith("/appInterfaceIntl/consumer/reWrite")) || (sender != null && ("101".equals(sender) || "201".equals(sender) || "301".equals(sender)))) {
			chain.doFilter(req, resp);
		} else {
			response.getWriter().write("{\"resultCode\":422, \"resultMsg\":\"No authority.\"}");
		}
	}

	public void destroy() {
	}

}
