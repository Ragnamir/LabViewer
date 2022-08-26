package com.mera.labViewer.Users;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mera.labViewer.Lab.AllLabs;
import com.mera.labViewer.Utils.Consts;

@Configuration
public class UserConfig {
	@Bean
	@Qualifier("user")
	public UserTable getUserTable() {
		/*ObjectMapper mapper = new ObjectMapper();
		
		User user1 = new User();
		user1.setName("Georgiy Fedorov");
		user1.getHosts().add("192.168.37.101");
		
		User user2 = new User();
		user2.setName("Alexander Scherbakov");
		user2.getHosts().add("192.168.37.102");*/
		
		UserTable userTable = new UserTable();
		/*userTable.getUsers().add(user1);
		userTable.getUsers().add(user2);*/
		
		//ObjectMapper mapper = new ObjectMapper();
		/*try {
			mapper.writeValue(new File(Consts.USER_CONFIG_FILE), userTable);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		//UserTable userTable = new UserTable();
		/*ObjectMapper mapper = new ObjectMapper();
		
		try {
			userTable = mapper.readValue(new File(Consts.USER_CONFIG_FILE), UserTable.class);
			System.out.println(userTable);
		} catch (IOException e) {
			System.out.println(Consts.LAB + Consts.ERROR_LOADING_CONFIG);
		}*/
		
		return userTable;
	}
}
