package com.kollect.etl.controller.app;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kollect.etl.service.app.ValidatorConfigManagement;




@Controller
public class ValidatorConfigController {

	@Autowired
	ValidatorConfigManagement vManagementService;
	
	@GetMapping("/validator")
    public Object loadValidatorPage(Model model) {
		return this.vManagementService.loadValidatorPage(model);
    }
	
	
	
	@GetMapping("/validatorshow")
    public ResponseEntity<String> showAllValidators() {
		return this.vManagementService.getValidatorsList();
    }

	
	@GetMapping("/validationitemshow")
    public ResponseEntity<String> showValidatorItems(@RequestParam Integer validator_id) {
		return this.vManagementService.getValidatorItemsList(validator_id);
    }
	
	
	@GetMapping("/validator/{action}")
    public ResponseEntity<String> getComboInfo(@PathVariable Integer action,@RequestParam(required=false) Integer pid) {
		switch (action) {
			case 5:
				return this.vManagementService.fillValidatorCombo(pid);
			default:
				return new ResponseEntity<>("Unkhown action", HttpStatus.FAILED_DEPENDENCY);
		}
    }
	
	
	
	
	@PostMapping("/validator")
	public ResponseEntity<String> manipulate(@RequestParam HttpServletRequest request){
		return this.vManagementService.mainpulateValidator(request);
	}
	
}
