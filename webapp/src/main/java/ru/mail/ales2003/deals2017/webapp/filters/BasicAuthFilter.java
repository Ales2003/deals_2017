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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import ru.mail.ales2003.deals2017.datamodel.Role;
import ru.mail.ales2003.deals2017.services.IUserAuthService;
import ru.mail.ales2003.deals2017.services.impl.UserAuthStorage;
import ru.mail.ales2003.deals2017.webapp.jedis.JedisCache;

public class BasicAuthFilter implements Filter {

	private static final Logger LOGGER = LoggerFactory.getLogger(BasicAuthFilter.class);

	private static final Map<String, Integer> USERS_DB = new HashMap<>();
	static {
		USERS_DB.put("admin", 1);
		USERS_DB.put("NeAdmin", 2);
		System.out.println(USERS_DB.size());
	}

	private IUserAuthService service;

	// private ICharacterTypeService characterTypeService;

	private ApplicationContext appContext;

	private static JedisCache cache = new JedisCache();// = new
														// JedisCache();

	private int cashingTimeInSec = 30;

	@Override
	public void init(FilterConfig config) throws ServletException {

		WebApplicationContext context = WebApplicationContextUtils
				.getRequiredWebApplicationContext(config.getServletContext());

		// characterTypeService = context.getBean(ICharacterTypeService.class);
		service = context.getBean(IUserAuthService.class);

		appContext = context;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws java.io.IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		// if (!isAuthRequired(req)) {
		// chain.doFilter(request, response);
		// }

		// cache = appContext.getBean(JedisCache.class);

		UserAuthStorage userJVMDataStorage = appContext.getBean(UserAuthStorage.class);

		String[] credentials = resolveCredentials(req);

		boolean isCredentialsResolved = credentials != null && credentials.length == 2;

		if (!isCredentialsResolved) {

			// OR: You Came in as unregistered visitor. guest id = -1
			userJVMDataStorage.setId(-1);
			userJVMDataStorage.setRole(Role.GUEST);
			chain.doFilter(request, response);
			return;
			// String errMsg = "Visiting this page requires authorization. But
			// you [username] or [password] is not correct. Please try again.";
			// res.sendError(401, errMsg);
			// return;
		}

		String username = credentials[0];
		String password = credentials[1];

		Integer userIdFromStorage;
		Role userRoleFromStorage;

		LOGGER.info("Length of JedisCache [{}] = [{}].", username, cache.getLength(username));

		// query to cashe
		if (cache.isExistInCache(username)) {
			LOGGER.info("Fortunately, the JedisCache stores the requested data.");
			Integer idFromCashe = cache.getIdFromCache(username);
			LOGGER.info("Getting userId  = [{}] by username [{}] from Jedis Cache.", idFromCashe, username);
			userIdFromStorage = idFromCashe;

			Role roleFromCashe = cache.getRoleFromCache(username);
			LOGGER.info("Getting userRole  = [{}] by username [{}] from Jedis Cache.", roleFromCashe, username);
			userRoleFromStorage = roleFromCashe;

			// query to DB
		} else {
			LOGGER.info("Unfortunately, the JedisCache does not store the requested data.");

			// from local Map<String, Integer> USERS_DB
			// Integer userIdFromDB = USERS_DB.get(username);

			// from real DB table user_auth
			Integer userIdFromDB = service.getByLogin(username).getId();
			LOGGER.info("Getting userId  = [{}] by username [{}] from DataBase.", userIdFromDB, username);
			userIdFromStorage = userIdFromDB;

			Role roleFromDB = service.getByLogin(username).getRole();
			LOGGER.info("Getting userRole  = [{}] by username [{}] from Jedis Cache.", roleFromDB, username);
			userRoleFromStorage = roleFromDB;

			// TODO query to DB instead of MAP
			// TODO get user from DB by username and check password
			// get user by username
			// user.get id

		}
		// if (cashe.isExistInCashe(username)) {
		// Integer idFromCashe = cashe.getIdFromCashe(username);
		// userIdFromStorage = idFromCashe;

		// } else {

		// TODO query to DB instead of MAP

		// TODO get user from DB by username and check password
		// get user by username
		// user.get id
		// user.get password
		// Integer userIdFromDB = USERS_DB.get(username);
		// userIdFromStorage = userIdFromDB;
		// userPasswordFromStorage = "password";
		// }
		// if (validateUserPassword(userIdFromStorage, password)) {
		if (validateUserPassword(username, password)) {

			LOGGER.info("Saving userId  = [{}] to JVM Storage.", userIdFromStorage);
			userJVMDataStorage.setId(userIdFromStorage);
			LOGGER.info("Saving userRole  = [{}] to JVM Storage.", userRoleFromStorage);
			userJVMDataStorage.setRole(userRoleFromStorage);

			LOGGER.info("Cleaning old userData from JedisCache");
			cache.cleanUserData(username, userIdFromStorage, password, userRoleFromStorage);

			LOGGER.info(
					"Chaching to JedisCache: username = [{}], userRole  = [{}], userId  = [{}], password = [{}], the lifetime of cached data = [{}] sec.",
					username, userRoleFromStorage, userIdFromStorage, password, cashingTimeInSec);
			cache.addToCache(username, userRoleFromStorage, userIdFromStorage, password, cashingTimeInSec);

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

	// private boolean validateUserPassword(Integer userId, String password) {
	private boolean validateUserPassword(String username, String password) {

		Integer userIdFromStorage;
		String userPasswordFromStorage;

		// query to cashe
		if (cache.isExistInCache(username)) {
			Integer idFromCashe = cache.getIdFromCache(username);
			userIdFromStorage = idFromCashe;

			if (userIdFromStorage == null) {
				return false;
			}
			// TODO check in cashe

			String passwordFromCashe = cache.getPasswordFromCache(username);
			userPasswordFromStorage = passwordFromCashe;

		} else {
			// TODO get user from DB by username and check password
			// TODO query to DB instead of MAP

			// TODO get user from DB by username and check password
			// get user by username
			// user.get id
			// user.get password
			Integer userIdFromDB = service.getByLogin(username).getId();
			userIdFromStorage = userIdFromDB;
			// userPasswordFromStorage = "password";

			userPasswordFromStorage = service.getByLogin(username).getPassword();
		}

		if (userIdFromStorage == null) {
			return false;
		}

		return userPasswordFromStorage.equals(password);
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