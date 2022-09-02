package com.mera.labViewer.WebSocket;

import com.mera.labViewer.Lab.AllLabs;
import com.mera.labViewer.ScheduledConfigUpdater.ScherduledUpdater;
import com.mera.labViewer.ScheduledRequest.ScheduledRequester;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class UIDataController {

    private final AllLabs allLabs;
    private final ScherduledUpdater scherduledUpdater;
    private Logger logger = LoggerFactory.getLogger(UIDataController.class);

    @Autowired
    UIDataController(AllLabs allLabs, ScherduledUpdater scherduledUpdater) {
        this.scherduledUpdater = scherduledUpdater;
        this.allLabs = allLabs;
    }

    @MessageMapping("/updatelab")
    public void updateLab(AllLabs labs) throws Exception {
        logger.info("Got request for updating labs: " + labs.toString());
        allLabs.updateLabs(labs);
        scherduledUpdater.saveLabConfig();
    }

    @MessageMapping("/updatemessage")
    public void updateMessage(AllLabs labs) throws Exception {
        logger.info("Got request for updating messages: " + labs.toString());
        allLabs.updateMessages(labs);
    }

    @MessageMapping("/redeploybeacon")
    public void updateMessage(String ip) throws Exception {
        ip = ip.replaceAll("\"", "");
        logger.info("Got request for redeploying beacon to " + ip);
        Runtime.
                getRuntime().
                exec("cmd /c start \"\" obs\\deploy.bat " + ip);
    }

    @MessageMapping("/getdata")
    @SendTo("/topic/data")
    public AllLabs sendLabData() {
        logger.info("Got request for data " + allLabs.toString());
        return allLabs;
    }
}
