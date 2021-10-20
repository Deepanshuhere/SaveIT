package com.saveit.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import com.saveit.constants.AppConstants;

@Configuration
public class CustomLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler
{
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException 
	{
		super.onAuthenticationSuccess(request, response, authentication);
	}

	@Override
	protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException 
	{
		String targetUrl = determineTargetUrl(authentication);
				
		if(response.isCommitted()) 
			return;
		
		RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
		redirectStrategy.sendRedirect(request, response, targetUrl);
		
	}

	private String determineTargetUrl(Authentication authentication)
	{
		String url = AppConstants.TARGET_URL;

		// Fetch the roles from Authentication object
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		List<String> roles = new ArrayList<String>();
		for (GrantedAuthority a : authorities) 
			roles.add(a.getAuthority());
		
		// check user role and decide the redirect URL
		if (roles.contains(AppConstants.ROLE_ADMIN)) 
			url = AppConstants.ADMIN_INDEX_PATH;

		else if (roles.contains(AppConstants.ROLE_USER)) 
			url = AppConstants.USER_INDEX_PATH;

		return url;
	}
	
}