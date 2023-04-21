package com.example.topkart;

import com.example.topkart.config.AppConstants;
import com.example.topkart.model.Role;
import com.example.topkart.repository.RoleRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
public class TopkartApiApplication implements CommandLineRunner {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepo roleRepo;

	@Bean
	ModelMapper modelMapper()
	{
		return new ModelMapper();
	}


	public static void main(String[] args) {
		SpringApplication.run(TopkartApiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		try {

			Role role=new Role();
			role.setId(AppConstants.ADMIN_USER);
			role.setName("ROLE_ADMIN");

			Role role1=new Role();
			role1.setId(AppConstants.NORMAL_USER);
			role1.setName("ROLE_NORMAL");

			List<Role> roles = List.of(role,role1);

			List<Role> result = this.roleRepo.saveAll(roles);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
