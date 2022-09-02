package com.mera.labViewer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication

public class LabViewerApplication {

	public static void main(String[] args) {
		/*ObjectMapper mapper = new ObjectMapper();
		
		try {
			mapper.writeValue(new File("LabsConfig.json"), allLabs);
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		
		/*AllLabs allLabs = null;
		try {
			allLabs = mapper.readValue(new File("LabsConfig.json"), AllLabs.class);
			System.out.println(allLabs);
		} catch (IOException e) {
			 TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		SpringApplication.run(LabViewerApplication.class, args);
	}

}
