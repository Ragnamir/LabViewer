package com.mera.labViewer.ScheduledRequest;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.mera.labViewer.WebSocket.UIDataController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mera.labViewer.Lab.*;
import com.mera.labViewer.Users.*;
import com.mera.labViewer.Utils.Consts;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class ScheduledRequester {
	
	private final AllLabs allLabs;
	private final UserTable userTable;

	private final WebClient client;
	private SimpMessagingTemplate template;

	private Logger logger = LoggerFactory.getLogger(ScheduledRequester.class);
	
	@Autowired
	public ScheduledRequester(AllLabs allLabs,
							  UserTable userTable,
							  SimpMessagingTemplate template) {
		super();
		this.template = template;
		this.client = WebClient.create();
		this.allLabs = allLabs;
		this.userTable = userTable;
	}

	public void updateLabPCState(LabPC labPC, UserNameData[] recievedHosts) {
		logger.debug(Consts.REPLY_URL + labPC.getIpAddress());
		ArrayList<String> users = new ArrayList<String>();
		if ( (recievedHosts.length > 0) && (Consts.UNREACHABLE.equals(recievedHosts[0].getName())) ) {
			users.add(Consts.UNREACHABLE);
		} else {
			for (UserNameData recievedHost : recievedHosts) {
				ArrayList<String> hosts = new ArrayList<String>();
				hosts.add(recievedHost.getName());
				User user = new User(userTable.getUserByHost(recievedHost.getName()), hosts);
				users.add(user.getName());
			}
		}

		if (!labPC.getState().containsAll(users) || (!users.containsAll(labPC.getState()))) {
			labPC.setState(users);
			allLabs.setUpdated(true);
		}
	}

	@Scheduled(fixedDelay = 5000)
	public void updateLabs() {

		logger.debug(Consts.SCHEDULED_TASK_INPROGRESS);

		if (allLabs.isUpdated()){
			allLabs.setUpdated(false);
			logger.debug(Consts.UPDATING_FRONT);
			template.convertAndSend("/topic/data",allLabs);
		}

		//logger.debug(allLabs.toString());


		for(Lab lab : allLabs.getLabs()) {
			logger.debug(Consts.PROCESSING_LAB + lab.getName());
			for(LabPC labPC : lab.getLabPCs()) {

				String url = Consts.PROTOCOL + labPC.getIpAddress() + Consts.PORT + Consts.NAMES_URL;
				logger.debug(Consts.REQUEST_URL + labPC.getIpAddress());

				client
						.get()
						.uri(url).
						retrieve()
						.bodyToMono(UserNameData[].class)
						.onErrorReturn(new UserNameData[]{new UserNameData(Consts.UNREACHABLE)})
						.subscribe(userNameData -> {updateLabPCState(labPC, userNameData);});
			}
		}
		
	}
}
