package com.kollect.etl.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kollect.etl.dataaccess.HostDao;
import com.kollect.etl.entity.Host;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class HostService {
	@Autowired
	private HostDao hostDao;

	public int insertHost(Object object) {
		return this.hostDao.insertHost(object);
	}

	public List<Object> viewHost(Object object) {
		return this.hostDao.viewHost(object);
		// TODO Auto-generated method stub

	}

	public List<Host> getHostById(Object object) {
		return this.hostDao.getHostById(object);
	}

	public int updateHost(Object object) {
		return this.hostDao.updateHost(object);
	}
	
	public int deleteHost(Object object) {
		return this.hostDao.deleteHost(object);
	}

	public Object getHost(@RequestParam(required = false) Integer id, Model model){
        model.addAttribute("hostList", this.viewHost(null));
        List<Host> hosts = this.getHostById(id);
        if (hosts.size() > 0)
            model.addAttribute("hostEditList", hosts.get(0));
        else
            model.addAttribute("hostEditList", null);

        return "hostForm";
    }

    public Object addUpdateHost(@RequestParam(required = false) Integer id, @RequestParam String name,
                                @RequestParam String fqdn, @RequestParam String username, @RequestParam String host, @RequestParam int port,
                                @RequestParam String publicKey, @RequestParam(required = false) Timestamp createdAt,
                                @RequestParam(required = false) Timestamp updatedAt){
        Host newHost = new Host(name, fqdn, username, host, port, publicKey, createdAt, updatedAt);

        boolean insertFlag = false;
        if (id != null)
            newHost.setId(id);
        int updateCount = this.updateHost(newHost);

        if (updateCount == 0) {
            this.insertHost(newHost);
            insertFlag = true;
        }
        if (insertFlag)
            return "redirect:/host";
        return "redirect:/host?id=" + id;
    }
}
