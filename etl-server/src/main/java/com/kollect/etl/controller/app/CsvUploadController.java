package com.kollect.etl.controller.app;

import java.io.IOException;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.kollect.etl.service.app.CsvUploadService;

@Controller
public class CsvUploadController {
	public MultipartFile paths;
	@Autowired
	private CsvUploadService csvUploadService;

	@GetMapping("/datavisualiser")
	public String getCsv(){
		return "dsv";
	}

	@RequestMapping(value = "/dsv", method = RequestMethod.POST)
	public String singleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes RedAtt){
		paths = file;
		String extChecker = FilenameUtils.getExtension(paths.getOriginalFilename());
		if (!extChecker.equals("csv")) {
			// return not supported file format.
			RedAtt.addFlashAttribute("status", "Only CSV file format is allowed!");
			return "redirect:/datavisualiser";
		}
		return "redirect:/dsv";
	}

	@GetMapping(value = "/viewdsvJson")
	@ResponseBody
	public Object viewDsvJson() throws IOException {
		return csvUploadService.buildListOfMap(paths);
	}

	@GetMapping(value = "/dsv")
	public Object CSVDisplay(Model model) throws IOException {
		if (paths != null) {
			model.addAttribute("csvIndex", csvUploadService.buildListOfMap(paths));
			model.addAttribute("sizeArray", "" + (csvUploadService.arrSize - 1));
		}

		return "dsv";

	}

}