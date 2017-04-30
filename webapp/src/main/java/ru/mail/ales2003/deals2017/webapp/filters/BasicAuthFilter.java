package ru.mail.ales2003.deals2017.webapp.filters;

import java.nio.charset.Charset;
import java.util.Base64;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import ru.mail.ales2003.deals2017.services.ICharacterTypeService;
import ru.mail.ales2003.deals2017.services.impl.UserAuthStorage;

public class BasicAuthFilter implements Filter {

	private static final Map<String, Integer> USERS_DB = new HashMap<>();
	static {
		USERS_DB.put("admin", 1);
		USERS_DB.put("NeAdmin", 2);
		System.out.println(USERS_DB.size());
	}

	private ICharacterTypeService service;

	private ApplicationContext appContext;

	@Override
	public void init(FilterConfig config) throws ServletException {

		WebApplicationContext context = WebApplicationContextUtils
				.getRequiredWebApplicationContext(config.getServletContext());
		service = context.getBean(ICharacterTypeService.class);
		appContext = context;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws java.io.IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		if (!isAuthRequired(req)) {
			chain.doFilter(request, response);
		}

		UserAuthStorage userDataStorage = appContext.getBean(UserAuthStorage.class);

		String[] credentials = resolveCredentials(req);

		boolean isCredentialsResolved = credentials != null && credentials.length == 2;

		if (!isCredentialsResolved) {
			res.sendError(401);
			return;
		}

		String username = credentials[0];
		String password = credentials[1];

		// TODO query to DB instead of MAP
		Integer userId = USERS_DB.get(username);
		if (validateUserPassword(userId, password)) {

			userDataStorage.setId(userId);

			// userDataStorage.getId();

			chain.doFilter(request, response);
		} else {
			res.sendError(401);
		}

	}

	private boolean isAuthRequired(HttpServletRequest req) {
		if (req.getMethod().toUpperCase().equals("GET")) {
			return false;
		}
		// TODO other variants
		return true;
	}

	private boolean validateUserPassword(Integer userId, String password) {

		if (userId == null) {
			return false;
		}

		// TODO get user from DB by username and check password

		return "password".equals(password);
	}

	private String[] resolveCredentials(HttpServletRequest req) {
		try {
			Enumeration<String> headers = req.getHeaders("Authorization");
			String nextElement = headers.nextElement();
			// String base64Credentials =
			// nextElement.substring("Basic".length()).trim();
			// String credentials = new
			// String(Base64.getDecoder().decode(base64Credentials),
			// Charset.forName("UTF-8"));
			String credentials = new String(Base64.getDecoder().decode(nextElement), Charset.forName("UTF-8"));
			return credentials.split(":", 2);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public void destroy() {
	}

}