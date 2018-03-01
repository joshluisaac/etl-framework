package com.kollect.etl.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.google.gson.Gson;
import com.kollect.etl.service.CsvUploadService;

@Controller
public class dsvUploadController {

	private static final Logger LOG = LoggerFactory.getLogger(dsvUploadController.class);

	public MultipartFile paths;
	@Autowired
	CsvUploadService csvUploadService;

	// @PostMapping("/dsv")
	@RequestMapping(value = "/dsv", method = RequestMethod.POST)
	public String singleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes RedAtt)
			throws IOException {

		paths = file;
		String extChecker = FilenameUtils.getExtension(paths.getOriginalFilename());
		System.out.println(extChecker);
		if (!extChecker.equals("csv")) {
			// return not supported file format.
			RedAtt.addFlashAttribute("status", "Only CSV file format is allowed!");
			return "redirect:/datavisualiser";
		} else {
			// csvUploadService.buildListOfMap(paths);
		}
		return "redirect:/dsv";
	}

	@GetMapping(value = "/viewdsvJson")
	@ResponseBody
	public Object viewDsvJson() throws IOException {
		return csvUploadService.buildListOfMap(paths);
	}

	@GetMapping(value = "/dsv")
	// @ResponseBody
	public Object CSVDisplay(Model model) throws IOException {

		if (paths != null) {

			List<Map<String, String>> x = csvUploadService.buildListOfMap(paths);
			System.out.println("Printing...." + x);
			model.addAttribute("csvIndex", x);
			model.addAttribute("sizeArray", "" + (csvUploadService.arrSize - 1));
		}

		return "dsv";

	}

}