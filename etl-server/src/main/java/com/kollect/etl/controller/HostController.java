package com.kollect.etl.controller;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kollect.etl.entity.Host;
import com.kollect.etl.service.HostService;

@Controller
public class HostController {
	@Autowired
	private HostService hostService;

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
	public String getHostById(@RequestParam(required = false) Integer id, Model model) {
		model.addAttribute("pageTitle", "DataConnector");
		model.addAttribute("hostList", this.hostService.viewHost(null));
		List<Host> hosts = this.hostService.getHostById(id);
		if (hosts.size() > 0)
			model.addAttribute("hostEditList", hosts.get(0));
		else
			model.addAttribute("hostEditList", null);

		return "hostForm";
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
		this.hostService.deleteHost(id);
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
	public String addHost(@RequestParam(required = false) Integer id, @RequestParam String name,
			@RequestParam String fqdn, @RequestParam String username, @RequestParam String host, @RequestParam int port,
			@RequestParam String publicKey, @RequestParam(required = false) Timestamp createdAt,
			@RequestParam(required = false) Timestamp updatedAt) {

		Host newHost = new Host(name, fqdn, username, host, port, publicKey, createdAt, updatedAt);

		boolean insertFlag = false;
		if (id != null)
			newHost.setId(id);
		int updateCount = hostService.updateHost(newHost);

		if (updateCount == 0) {
			if (updateCount == 0)
				this.hostService.insertHost(newHost);
			insertFlag = true;
		}
		if (insertFlag)
			return "redirect:/host";
		return "redirect:/host?id=" + id;
	}
}