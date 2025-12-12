package com.devsuperior.DSCatalog.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.DSCatalog.dto.RoleDTO;
import com.devsuperior.DSCatalog.dto.UserDTO;
import com.devsuperior.DSCatalog.dto.UserInsertDTO;
import com.devsuperior.DSCatalog.dto.UserUpdateDTO;
import com.devsuperior.DSCatalog.entities.Role;
import com.devsuperior.DSCatalog.entities.User;
import com.devsuperior.DSCatalog.projections.UserDetailsProjection;
import com.devsuperior.DSCatalog.repositories.RoleRepository;
import com.devsuperior.DSCatalog.repositories.UserRepository;
import com.devsuperior.DSCatalog.services.exceptions.DatabaseException;
import com.devsuperior.DSCatalog.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Transactional(readOnly = true)
	public Page<UserDTO> findAll(Pageable pageable) {
		Page<User> users = repository.findAll(pageable);
		return users.map(obj -> new UserDTO(obj));
	}
	
	@Transactional(readOnly = true)
	public UserDTO findById(Long id) {
		Optional<User> result = repository.findById(id);
		User user = result.orElseThrow(() -> new ResourceNotFoundException("Resource not found"));
		return new UserDTO(user);
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) {
		List<UserDetailsProjection> result = repository.searchUserAndRolesByEmail(username);
		
		if(result.size() == 0) {
			throw new UsernameNotFoundException("User not found");
		}
		
		User user = new User();
		user.setEmail(username);
		user.setPassword(result.get(0).getPassword());
		
		for(UserDetailsProjection projection : result) {
			user.addRole(new Role(projection.getRoleId(), projection.getAuthority()));
		}
		
		return user;
	}
	
	protected User authenticated() {
		
		try {
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			Jwt jwtPrincipal = (Jwt) authentication.getPrincipal();
			String username = jwtPrincipal.getClaim("username");
			
			User user = repository.findByEmail(username);
			return user;
			
		}
		catch (Exception e) {
			throw new UsernameNotFoundException("E-mail not found");
		}
		
	}
	
	@Transactional(readOnly = true)
	public UserDTO getMe() {
		User user = authenticated();
		return new UserDTO(user);
	}
	
	@Transactional
	public UserDTO insert(UserInsertDTO dto) {
			User entity = new User();
			copyDtoToEntity(dto, entity);
			entity.setPassword(passwordEncoder.encode(dto.getPassword()));
			entity = repository.save(entity);
			return new UserDTO(entity);
	}
	
	@Transactional
	public UserDTO update(Long id, UserUpdateDTO dto) {
		try {
			User entity = repository.getReferenceById(id);
			copyDtoToEntity(dto, entity);
			entity = repository.save(entity);
			return new UserDTO(entity);
		}
		catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("ID not found: " + id);
		}
	}
	
	@Transactional
	public void delete(Long id) {
		if (!repository.existsById(id)) {
			throw new ResourceNotFoundException("ID not found: " + id);
		}
		try {
			repository.deleteById(id);
			repository.flush();
		}
		catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Referential integrity violation");
		}
	}
	
	
	private void copyDtoToEntity(UserDTO dto, User entity) {
		entity.setFirstName(dto.getFirstName());
		entity.setLastName(dto.getLastName());
		entity.setEmail(dto.getEmail());
		
		entity.getRoles().clear();
		for (RoleDTO roleDto : dto.getRoles()) {
			Role role = roleRepository.getReferenceById(roleDto.getId());
			entity.getRoles().add(role);
		} 
	}
	
}
