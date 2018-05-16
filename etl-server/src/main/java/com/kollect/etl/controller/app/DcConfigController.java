package com.kollect.etl.controller.app;

import com.kollect.etl.entity.app.ConfigParent;
import com.kollect.etl.entity.app.DcConfig;
import com.kollect.etl.entity.app.DcConfigProp;
import com.kollect.etl.service.app.DcConfigService;
import com.kollect.etl.util.load.config.xml.DcConfigGenerator;
import com.kollect.etl.util.load.config.xml.DcXml;
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
  /**
   * HTTP POST request to either update or add DC configuration properties
   *
   * @param id
   *        the id of user that is added if id is given
   * @param destination_table
   *        destination table at which the retrieved file is going to
   * @param description
   *        description of the file
   * @param sourceFileName
   *        name of the source file
   * @param table_sequence_name
   *        sequence that is used to increase the numbering
   * @param generated_key
   * @param column_delimiter
   *        column delimiter that structures the data
   * @param date_format
   *        the date format followed in extracted file
   * @param descriptorFileName
   * @param includeLoadExecution
   *        flag to decide on including load execution
   * @param delOption
   *        flag to decide on delete option
   * @param model
   *        a data structure of objects which is rendered to view
   * @return
   *        redirection to HTTP GET dctablesettings
   */
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

  /**
   * HTTP GET request to
   *
   * @param  id
   * @param  type
   *        type of file format
   * @param model
   *        a data structure of objects which is rendered to view
   *
   * @return
   *        returns to dctParentconfig template
   */
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

  /**
   * HTTP GET request to view dctParentconfig
   *
   * @param id
   * @param download
   *        download decision (yes OR no)
   * @param model
   *        data structure of objects which is rendered to view
   * @param response
   * @exception IOException
   *            throws IO exception on input error
   * @return
   *        returns to dctParentconfig template
   */
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

  /**
   * HTTP GET request to return to dcconfig form, get DC by id
   *
   * @param global_config_id
   * @param model
   *        data structure of objects which is rendered to be view
   * @return
   *        returns to dcconfig template
   */
  @GetMapping(value="/dcconfig")
  public Object getDcConfigById(@RequestParam(required = false) Integer global_config_id, Model model) {
    ConfigParent configP = new ConfigParent(global_config_id);
    DcConfig config = new DcConfig();
    config.setGlobalConfigId(global_config_id);
    model.addAttribute("pageTitle", "DataConnector");
    model.addAttribute("dcconfigParent", this.service.getDcConfigParent(configP).get(0));
    model.addAttribute("dcconfigList", this.service.getDcConfigByParentId(global_config_id));
    return "dcconfig";
  }

  /**
   * HTTP GET request to return to dcconfig form for update purpose
   *
   * @param id
   * @param global_config_id
   * @param model
   *        data structure of objects which is rendered to view
   * @return
   *        returns to dcconfig template
   */
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

  /**
   * HTTP GET request to
   *
   * @param id
   * @param global_config_id
   * @param model
   *        data structure of objects which is rendered to view
   * @return
   *         return result which holds the
   */
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
  
  /**
   * HTTP POST request to create data connector (DC) configuration
   * 
   * @param id
   *        id of
   * @param column_name
   *        identifies the column name
   * @param remark
   *        remark on the newly created DC
   * @param column_default_value
   *        identifies the default value of the column
   * @param global_config_id
   *        specifies the global configiration id
   * @param column_start_position
   *        specifies the column start position
   * @param column_end_position
   *        specifies the column end position
   * @param column_data_handler
   *
   * @param column_is_key
   *         flag to determine if the associated column is the
   * @param column_is_iexternal
   *        flag to determine if the associated column is the
   * @param column_is_optional
   *        flag to determine if the associated column is the
   * @param column_is_cached
   *        flag to determine if the associated column is the
   * @param disable
   * @param lookup_query
   *
   * @param look_insert_query
   * @param look_insert_key_query
   * 
   * @return redirection URL to HTTP GET dcconfig
   *  
   */
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
