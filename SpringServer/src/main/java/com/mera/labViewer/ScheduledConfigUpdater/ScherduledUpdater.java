package com.mera.labViewer.ScheduledConfigUpdater;

import java.io.File;
import java.io.IOException;

import com.mera.labViewer.ScheduledRequest.ScheduledRequester;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mera.labViewer.Lab.AllLabs;
import com.mera.labViewer.Users.UserTable;
import com.mera.labViewer.Utils.Consts;

@Component
public class ScherduledUpdater {
	
	private AllLabs allLabs;
	private UserTable userTable;

	private Logger logger = LoggerFactory.getLogger(ScheduledRequester.class);

	@Autowired
	public ScherduledUpdater(/*@Qualifier("lab")*/ AllLabs allLabs, /*@Qualifier("user")*/ UserTable userTable) {
		super();
		this.allLabs = allLabs;
		this.userTable = userTable;
	}

	public void saveLabConfig() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(new File(Consts.LAB_CONFIG_FILE), allLabs); //заново записываем всё. Сделано ради сохранения сообщений
		} catch (IOException e) {
			logger.info(Consts.LAB + Consts.ERROR_LOADING_CONFIG);
		}
	}
	
	@Scheduled(fixedDelay = 60000)
	public void updateConfig() {
		ObjectMapper mapper = new ObjectMapper();
		
		AllLabs loadedLabs = null;
		try {
			loadedLabs = mapper.readValue(new File(Consts.LAB_CONFIG_FILE), AllLabs.class);
			allLabs.updateLabs(loadedLabs);
			
			mapper.writeValue(new File(Consts.LAB_CONFIG_FILE), allLabs); //заново записываем всё. Сделано ради сохранения сообщений
		} catch (IOException e) {
			logger.info(Consts.LAB + Consts.ERROR_LOADING_CONFIG);
		}

		logger.info(Consts.LAB + Consts.CONFIG_LOADED);
		
		try {
			UserTable loadedUserTable = mapper.readValue(new File(Consts.USER_CONFIG_FILE), UserTable.class);
			userTable.updateUserTable(loadedUserTable);
			//System.out.println(userTable);
		} catch (IOException e) {
			logger.info(Consts.USER + Consts.ERROR_LOADING_CONFIG);
		}

		logger.info(Consts.USER + Consts.CONFIG_LOADED);
	}
}
