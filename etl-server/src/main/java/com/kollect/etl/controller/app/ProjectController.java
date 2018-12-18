package com.kollect.etl.controller.app;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import com.kollect.etl.service.app.ProjectManagementService;




@Controller
public class ProjectController {

	
	@Autowired
	private ProjectManagementService projectManagementService;
									

	

	@GetMapping("/project")
    public Object loadProjectPage() {
        return this.projectManagementService.loadProjectPage();
    }
	

	
	@GetMapping("/projectshow")
    public ResponseEntity<String> showAllProjects() {
		return this.projectManagementService.getProjectsList();
    }
	
	
	
	@PostMapping("/project")
	public ResponseEntity<String> manipulateProject(@RequestParam Integer action, @RequestParam Integer id, @RequestParam String name,
											 		@RequestParam String customer,@RequestParam String description){
		switch (action) {
			case 1:
				return this.projectManagementService.addProject(name, customer, description);
			case 2:
				return this.projectManagementService.updateProject(id, name, customer, description);
			case 3:
				return this.projectManagementService.deleteProject(id);
			default:
				return new ResponseEntity<>("Unkhown action", HttpStatus.FAILED_DEPENDENCY);
		}

	}
}
