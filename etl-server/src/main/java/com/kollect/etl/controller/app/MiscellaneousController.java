package com.kollect.etl.controller.app;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MiscellaneousController {

	/**
	 * GET request mapping for the misc page to the right html
	 * @return
	 */

	  @GetMapping("/misc")
	  public String misc() {
	    return "misc";
	  }

}
