package com.mera.labViewer.Lab;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mera.labViewer.Utils.Consts;

@Configuration
public class LabConfig {
	@Bean
	@Qualifier("lab")
	public AllLabs getAllLabs() {
		/*ArrayList<LabPC> labPCs1 = new ArrayList<LabPC>();
		labPCs1.add(new LabPC("192.168.37.101", "core", "", ""));
		labPCs1.add(new LabPC("192.168.37.102", "db", "", ""));
		labPCs1.add(new LabPC("192.168.375.103", "admin", "", ""));
		labPCs1.add(new LabPC("192.168.37.104", "client", "", ""));
		Lab lab1 = new Lab("First", labPCs1);
		
		ArrayList<LabPC> labPCs2 = new ArrayList<LabPC>();
		labPCs2.add(new LabPC("192.168.37.211", "core", "", ""));
		labPCs2.add(new LabPC("192.168.37.212", "db", "", ""));
		labPCs2.add(new LabPC("192.168.37.213", "admin", "", ""));
		labPCs2.add(new LabPC("192.168.37.214", "client", "", ""));
		Lab lab2 = new Lab("First", labPCs2); 
		
		ArrayList<Lab> labs = new ArrayList<Lab>();
		labs.add(lab1);
		labs.add(lab2);*/
		
		
		AllLabs allLabs = new AllLabs();
		
		/*ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(new File(Consts.USER_CONFIG_FILE), allLabs);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		/*AllLabs allLabs = null;
		try {
			allLabs = mapper.readValue(new File(Consts.LAB_CONFIG_FILE), AllLabs.class);
			System.out.println(allLabs);
		} catch (IOException e) {
			System.out.println(Consts.LAB + Consts.ERROR_LOADING_CONFIG);
		}
		
		System.out.println(Consts.LAB + Consts.CONFIG_LOADED);*/
		//AllLabs allLabs = new AllLabs();
		return allLabs;
	}
}
