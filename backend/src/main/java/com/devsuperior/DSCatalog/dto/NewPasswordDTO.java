package com.devsuperior.DSCatalog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class NewPasswordDTO {

	@NotBlank(message = "This field cannot be blank.")
	private String token;
	
	@NotBlank(message = "This field cannot be blank.")
	@Size(min = 8, message = "Password must have at least 8 characters.")
	private String password;
	
	public NewPasswordDTO() {
		
	}

	public NewPasswordDTO(String token, String password) {
		this.token = token;
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
