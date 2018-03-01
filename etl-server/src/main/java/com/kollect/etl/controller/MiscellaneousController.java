package com.kollect.etl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MiscellaneousController {
	
	  @GetMapping("/misc")
	  public String datavisualiser() {
	    return "misc";
	  }

}
