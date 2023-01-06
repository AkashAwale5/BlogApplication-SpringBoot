package com.yash.security;

import java.io.IOException;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

@Component
public class JwtAuthenticationFiltter extends OncePerRequestFilter
{
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;

	
	

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// 1.get token
		
		String requestToken = request.getHeader("Authorisation");
		System.out.println(requestToken);
		
		String username= null;
		String token=null;
		
		if(request != null && requestToken.startsWith("Bearer"))
		{
			 token = requestToken.substring(7);
			 try
			 {
				 username = this.jwtTokenHelper.getUsernameFromtoken(token);
			 }
			catch (IllegalArgumentException e) 
			 {
				System.out.println("unable to get jwt token");
			 }
			 catch(ExpiredJwtException e)
			 {
				 System.out.println("Token Expired");
			 }
			 catch(MalformedJwtException e)
			 {
				 System.out.println("malformed jwt Exception");
			 }
			
		}
		else
		{
			System.out.println("not starts with bearer");
		}
		//validate
		if(username != null && SecurityContextHolder.getContext().getAuthentication() == null)
		{
			UserDetails userdetails = this.userDetailsService.loadUserByUsername(username);
			if(this.jwtTokenHelper.validateToken(token, userdetails))
			{
				
			}
			else
			{
				System.out.println("invalid jwt token");
			}
			
		}
		
	}
	
}
