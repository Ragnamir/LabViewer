package com.mera.labViewer.WebSocket;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
 
@Controller
public class WebSocketController {
	@RequestMapping("/labviewer")
    public String getWebSocket() {
        return "ws-broadcast";
    }
}
