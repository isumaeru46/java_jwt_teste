package com.example.demo;

public class AccountCredentials {
	
	private String loginOuEmail;
	private String senha;
	
	public String getUsername() {
		return loginOuEmail;
	}
	
	public void setUsername(String username) {
		this.loginOuEmail = username;
	}
	
	public String getPassword() {
		return senha;
	}
	
	public void setPassword(String password) {
		this.senha = password;
	}

}
