package com.kollect.etl.controller.app;


import javax.servlet.http.HttpServletRequest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


import com.kollect.etl.service.app.DataValidationService;



@Controller
public class ValidationController {


	@Autowired
	private DataValidationService dataValidationServce;
	
	
	@GetMapping("/validation")
    public Object loadDatabasePage(Model model) {
		return this.dataValidationServce.loadValidationPage(model);
    }

	
	
	@PostMapping("/validation")
	public ResponseEntity<String> validationProcess(HttpServletRequest request){
		int action = Integer.valueOf(request.getParameter("action"));

		switch (action) {
			case 4:
				return this.dataValidationServce.validationProcess(request);
			default:
				return new ResponseEntity<>("Unkhown action", HttpStatus.FAILED_DEPENDENCY);
		}
	}
}



	
	

