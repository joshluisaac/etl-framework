package com.kollect.etl.controller;

import com.kollect.etl.service.HostService;
import com.kollect.etl.service.IReadWriteServiceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;

@Controller
public class HostController {
    private IReadWriteServiceProvider rwProvider;
    private String dataSource;
    private HostService hostService;

	@Autowired
    public HostController(IReadWriteServiceProvider rwProvider, @Value("${app.datasource_uat_8}") String dataSource,
                       HostService hostService){
        this.rwProvider = rwProvider;
        this.dataSource = dataSource;
        this.hostService = hostService;
    }

	/**
	 * HTTP GET request to retrieve all hosts and hosts by id
	 *
	 * @param id
	 *			id of the host that is necessary when editing a host
	 * @param model
	 *            a data structure of objects which needs to be rendered to view
     *
	 * @return hostForm pre-loaded with data for the id given
	 */

	@GetMapping("/host")
	public Object getHostById(@RequestParam(required = false) Integer id, Model model) {
		return this.hostService.getHost(id, model);
	}

    /**
     * HTTP GET request to delete host by id
     *
     * @param id
     *			id of the host that is necessary when editing a host
     *
     * @return redirects to the host URL.
     */
	@GetMapping("/deletehost")
	public String deleteHostbyId(@RequestParam(required = false) Integer id) {
		this.rwProvider.executeQuery(dataSource, "deleteHost", id);
		return "redirect:/host";
	}

    /**
     * HTTP POST request mapping to create or update hosts
     *
     * @param id
     *            primary key of host, needed during insert/update
     * @param name
     *            name of host
     * @param fqdn
     *            fully qualified domain name
     * @param username
     *            username of the host
     * @param host
     *            the host
     * @param port
     *            host server port
     * @param publicKey
     *            the public key for the host
     * @param createdAt
     *            timestamp for host creation
     * @param updatedAt
     *            timestamp for host edit
     *
     * @return redirects to the newly added host or edited host
     */

	@PostMapping("/host")
	public Object addHost(@RequestParam(required = false) Integer id, @RequestParam String name,
			@RequestParam String fqdn, @RequestParam String username, @RequestParam String host, @RequestParam int port,
			@RequestParam String publicKey, @RequestParam(required = false) Timestamp createdAt,
			@RequestParam(required = false) Timestamp updatedAt) {
	    return this.hostService.addUpdateHost(id, name, fqdn, username, host, port, publicKey, createdAt, updatedAt);
	}
}