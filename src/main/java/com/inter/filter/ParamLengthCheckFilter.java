package com.inter.filter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inter.util.RequestParamUtil;

public class ParamLengthCheckFilter implements Filter {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	public void init(FilterConfig filterConfig) throws ServletException {

	}

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;

		String servletPath = request.getServletPath(); // /consumer/versionCheck/
		String titleHeader = servletPath.substring(1).replace("/", "."); // consumer.versionCheck.

		Properties prop = new Properties();
		InputStream input = null;

		try {
			input = ParamLengthCheckFilter.class.getClassLoader().getResourceAsStream("paramLength.properties");

			prop.load(input);

			Map<String, String[]> paramMap = request.getParameterMap();
			Map<String, String> param = RequestParamUtil.getParamMap(paramMap);

			Enumeration<String> parameterNames = request.getParameterNames();

			while (parameterNames.hasMoreElements()) {
				String paramName = parameterNames.nextElement();

				if ("img".equals(paramName)) {
					continue;
				}

				String paramValue = param.get(paramName);
				
				int maxLength = Integer.parseInt(prop.getProperty(titleHeader + paramName));

				if (paramValue.length() > maxLength) {
					response.getWriter().write("{\"resultCode\":421,\"invalidParameter\":\"" + paramName
							+ "\" ,\"resultMsg\":\"invalid input\"}");

					log.debug("{\"resultCode\":421,\"invalidParameter\":\"" + paramName
							+ "\", \"paramValue\": " + paramValue + ",\"resultMsg\":\"invalid input\"}");
					return;
				}
			}

			chain.doFilter(request, response);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void destroy() {

	}

}
