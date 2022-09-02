package com.mera.labViewer.UIController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UIController {
    @RequestMapping("/labviewer")
    public String getWebSocket() {
        return "ws-broadcast";
    }
}
