package com.intiFormation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.intiFormation.jwtConfig.AuthentificationRequest;
import com.intiFormation.jwtConfig.AuthentificationResponse;
import com.intiFormation.jwtConfig.jwtUtil;
import com.intiFormation.service.CustemUserDetailService;

@RestController
@CrossOrigin(origins="http://localhost:4200")
public class AuthentificationController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private CustemUserDetailService custemUserDetailsService;
	
	@Autowired
	private jwtUtil jwtokenUtil;
	
	
	@PostMapping(value="/auth" )
	public ResponseEntity<?> authenticate(@RequestBody AuthentificationRequest authentificationRequest) throws Exception
	{
		System.out.println("test auth");
		try {
			System.out.println("dans try");
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authentificationRequest.getUsername(), authentificationRequest.getPassword()));
			System.out.println("test try");
		} catch (Exception e) {
			System.out.println("test catch");
			e.printStackTrace();
			// TODO: handle exception
			//throw new Exception("incorrect username ou password",e);
		}
		
		final UserDetails userdetails=custemUserDetailsService.loadUserByUsername(authentificationRequest.getUsername());
		final String jwt=jwtokenUtil.generateToken(userdetails);
		
		
		return new ResponseEntity(new AuthentificationResponse(jwt), HttpStatus.OK);
	}
	
	
}
