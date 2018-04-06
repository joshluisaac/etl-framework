package com.kollect.etl.controller;

import com.kollect.etl.entity.ConfigParent;
import com.kollect.etl.entity.DcConfig;
import com.kollect.etl.entity.DcConfigProp;
import com.kollect.etl.service.DcConfigService;
import com.kollect.etl.util.load.config.xml.DcConfigGenerator;
import com.kollect.etl.util.load.config.xml.DcXml;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


@Controller
public class DcConfigController {
  
  private static final Logger LOG = LoggerFactory.getLogger(DcConfigController.class);

  @Autowired
  private DcConfigService service;

  @RequestMapping(value = "dcConfigProp", method = RequestMethod.POST)
  public String dcConfigProp(@RequestParam(required = false) Integer id,@RequestParam String destination_table, @RequestParam String description,
      @RequestParam String sourceFileName, @RequestParam String table_sequence_name, @RequestParam String generated_key,
      @RequestParam Integer column_delimiter, @RequestParam Integer date_format,
      @RequestParam String descriptorFileName, @RequestParam(required = false) boolean includeLoadExecution, @RequestParam(required = false) boolean delOption, Model model) {

    DcConfigProp prop = new DcConfigProp(destination_table, table_sequence_name, generated_key, column_delimiter,
        date_format, sourceFileName, description, descriptorFileName, includeLoadExecution, delOption);

    if(id != null) {
      prop.setId(id);
    }
    int updateCount = service.updateDcConfigProp(prop);
    
    if (updateCount == 0) {
      this.service.insertDcConfigProp(prop);
    }
    
    return "redirect:/dctablesettings";
  }

  @RequestMapping("/dctablesettings")
  // @ResponseBody
  public Object dctablesettings(@RequestParam(required = false) Integer id, @RequestParam (required = false) String type, Model model) {
    if(type == null) {
      type = "default";
    }
    model.addAttribute("pageTitle", "DataConnector - Table Settings");
    ConfigParent configP = new ConfigParent(id);
    
    if(type.equals("json")) {
      List<Object> result = new ArrayList<Object>();
      result.add(this.service.getDcConfigParent(configP));
      if(id != null) {
        result.add(this.service.getDcConfigParent(configP).get(0));
      }
      return result;
    } else {
      model.addAttribute("dcconfigList", this.service.getDcConfigParent(configP));
      if(id != null) {
        model.addAttribute("dcconfigSeletedList", this.service.getDcConfigParent(configP).get(0));
      } 
      return "dctParentconfig";
    }
  }
  
  @RequestMapping(value="/preview", produces = "application/xml")
  public Object preview(@RequestParam(required = false) Integer id, @RequestParam String download, Model model, HttpServletResponse response) throws IOException {
    model.addAttribute("pageTitle", "DataConnector - Table Settings");
    List<Object> list = service.getConfigListForExport(id);
    DcConfigGenerator xmlGen = new DcConfigGenerator(list);
    DcXml dcXml = xmlGen.generate();
    String fileName = dcXml.getFileName();

    String uploadDir = "./etl-server/uploads/";
    Path path = Paths.get(uploadDir + fileName);
    Files.write(path, dcXml.getRawByte());
    response.setContentType("application/xml");
    
    if(download.equals("yes")) {
      response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
    } else {
      response.setHeader("Content-Disposition", String.format("inline; filename=\"" + fileName +"\""));
    }
    try(ServletOutputStream out = response.getOutputStream();){
      
      long numberOfBytesWritten = Files.copy(path, out);
      LOG.debug("Number of bytes viewed/written: {} bytes", numberOfBytesWritten);
      out.flush();
    }
    return "dctParentconfig";
  }
  
  
  @RequestMapping("/dcconfig")
  public Object getDcConfigById(@RequestParam(required = false) Integer global_config_id, Model model) {
    ConfigParent configP = new ConfigParent(global_config_id);
    DcConfig config = new DcConfig();
    config.setGlobalConfigId(global_config_id);
    model.addAttribute("pageTitle", "DataConnector");
    model.addAttribute("dcconfigParent", this.service.getDcConfigParent(configP).get(0));
    model.addAttribute("dcconfigList", this.service.getDcConfigByParentId(global_config_id));
    return "dcconfig";
  }

  @RequestMapping("/dcconfigForUpdate")
  public Object getDcConfigForUpdate(@RequestParam Integer id, @RequestParam(required = false) Integer global_config_id,
      Model model) {
    ConfigParent configP = new ConfigParent(global_config_id);
    DcConfig config = new DcConfig();
    config.setGlobalConfigId(global_config_id);
    config.setId(id);
    model.addAttribute("pageTitle", "DataConnector");
    model.addAttribute("dcconfigParent", this.service.getDcConfigParent(configP).get(0));
    model.addAttribute("dcconfigList", this.service.getDcConfigByParentId(global_config_id));
    model.addAttribute("dcconfigSelectedList", this.service.getDcConfigById(id).get(0));
    
    return "dcconfig";
  }

  @RequestMapping("/dcconfigApi")
  @ResponseBody
  public Object getDcConfigByIdApi(@RequestParam(required = false) Integer id,
      @RequestParam(required = false) Integer global_config_id, Model model) {
    ConfigParent configP = new ConfigParent(global_config_id);
    DcConfig config = new DcConfig();
    config.setGlobalConfigId(global_config_id);
    if (id != null) {
      config.setId(id);
    }
    List<Object> result = new ArrayList<Object>();
    result.add(this.service.getDcConfigParent(configP).get(0));
    result.add(this.service.getDcConfigById(config));
    result.add(this.service.getDcConfigByParentId(config));
    return result;
  }

  @RequestMapping(value = "/dcconfig", method = RequestMethod.POST)
  public String addDcConfig(@RequestParam Integer id, @RequestParam String column_name,
      @RequestParam String remark,  
      @RequestParam String column_default_value, 
      @RequestParam int global_config_id,
      @RequestParam(required = false) int column_start_position, 
      @RequestParam int column_end_position,
      @RequestParam int column_data_handler, 
      @RequestParam(required = false) boolean column_is_key,
      @RequestParam(required = false) boolean column_is_iexternal,
      @RequestParam(required = false) boolean column_is_optional,
      @RequestParam(required = false) boolean column_is_cached,
      @RequestParam(required = false) boolean disable,
      @RequestParam(required = false) String lookup_query,
      @RequestParam(required = false) String look_insert_query,
      @RequestParam(required = false) String look_insert_key_query
      
      ) {

    DcConfig config = new DcConfig(global_config_id, remark, column_start_position, column_end_position, column_data_handler,
        column_name, column_default_value, lookup_query, look_insert_query, look_insert_key_query, column_is_key, column_is_iexternal, column_is_optional,
        column_is_cached, disable);
    this.service.deleteDcConfig(id);
    this.service.saveConfig(config);
    return "redirect:/dcconfig?global_config_id=" + global_config_id;
  }

}
