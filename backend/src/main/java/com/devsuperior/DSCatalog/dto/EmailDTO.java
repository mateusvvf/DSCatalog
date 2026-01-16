package com.devsuperior.DSCatalog.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class EmailDTO {

    @NotBlank(message = "This field cannot be blank.")
    @Email(message = "Please enter a valid e-mail address.")
    private String email;
    
    public EmailDTO() {		
	}

	public EmailDTO(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}
    
}