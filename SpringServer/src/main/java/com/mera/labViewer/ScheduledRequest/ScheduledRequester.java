package com.mera.labViewer.ScheduledRequest;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mera.labViewer.Lab.*;
import com.mera.labViewer.Users.*;
import com.mera.labViewer.Utils.Consts;

@Component
public class ScheduledRequester {
	
	private AllLabs allLabs;
	private UserTable userTable;
	
	@Autowired
    private TaskExecutor executor;
	
	private final RestTemplate restTemplate;
	
	@Autowired
	public ScheduledRequester(RestTemplateBuilder restTemplateBuilder, @Qualifier("lab") AllLabs allLabs, @Qualifier("user") UserTable userTable) {
		super();
		this.restTemplate = restTemplateBuilder.build();
		this.allLabs = allLabs;
		this.userTable = userTable;
	}
	
	public Runnable requestLabPCState(LabPC labPC) {
		return () -> {
			ObjectMapper mapper = new ObjectMapper();
			String url = Consts.PROTOCOL + labPC.getIpAddress() + Consts.PORT + Consts.NAMES_URL;
			try {
				System.out.println("Requesting data on: ");
				System.out.println(labPC);
				System.out.println("in process: " + Thread.currentThread().getName());
				//System.out.println(url);
				String response = this.restTemplate.getForObject(url, String.class);
				System.out.println(response);
				UserNameData[] recievedHosts = mapper.readValue(response, UserNameData[].class);
				ArrayList<String> users = new ArrayList<String>();
				for (UserNameData recievedHost : recievedHosts) {
					ArrayList<String> hosts = new ArrayList<String>();
					hosts.add(recievedHost.getName());
					//System.out.println(userTable.toString());
					User user = new User(userTable.getUserByHost(recievedHost.getName()), hosts); 
					users.add(user.getName());
				}
				labPC.setState(mapper.writeValueAsString(users));
			} 
			catch(ResourceAccessException e ) {
				try {
					labPC.setState(mapper.writeValueAsString(Consts.UNREACHABLE));
				} catch (JsonProcessingException e1) {
					e1.printStackTrace();
				}
				//e.printStackTrace();
			}
			catch(JsonProcessingException e ) {
				//e.printStackTrace();
			}
		};
	}

	@Scheduled(fixedDelay = 5000)
	public void updateLabs() {
		
		System.out.println(Consts.SCHEDULED_TASK_INPROGRESS);
		//System.out.println(allLabs);
		//ObjectMapper mapper = new ObjectMapper();
		for(Lab lab : allLabs.getLabs()) {
			System.out.println(Consts.PROCESSING_LAB + lab.getName());
			for(LabPC labPC : lab.getLabPCs()) {
				executor.execute(requestLabPCState(labPC));
				/*String url = Consts.PROTOCOL + labPC.getIpAddress() + Consts.PORT + Consts.NAMES_URL;
				try {
					System.out.println(url);
					String response = this.restTemplate.getForObject(url, String.class);
					System.out.println(response);
					UserNameData[] recievedHosts = mapper.readValue(response, UserNameData[].class);
					ArrayList<String> users = new ArrayList<String>();
					for (UserNameData recievedHost : recievedHosts) {
						ArrayList<String> hosts = new ArrayList<String>();
						hosts.add(recievedHost.getName());
						System.out.println(userTable.toString());
						User user = new User(userTable.getUserByHost(recievedHost.getName()), hosts); 
						users.add(user.getName());
					}
					labPC.setState(mapper.writeValueAsString(users));
				} 
				catch(ResourceAccessException e ) {
					try {
						labPC.setState(mapper.writeValueAsString(Consts.UNREACHABLE));
					} catch (JsonProcessingException e1) {
						e1.printStackTrace();
					}
					//e.printStackTrace();
				}
				catch(JsonProcessingException e ) {
					//e.printStackTrace();
				}*/
			}
		}
		
	}
}
