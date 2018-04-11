package com.kollect.etl.service;

import com.kollect.etl.entity.Host;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.sql.Timestamp;
import java.util.List;

@Service
public class HostService {
    private IReadWriteServiceProvider rwProvider;
    private String dataSource;

	@Autowired
    public HostService(IReadWriteServiceProvider rwProvider, @Value("${app.datasource_uat_8}") String dataSource){
        this.rwProvider = rwProvider;
        this.dataSource = dataSource;
    }

	public Object getHost(Integer id, Model model){
        model.addAttribute("hostList", this.rwProvider.executeQuery(dataSource, "viewHost", null));
        List<Host> hosts = this.rwProvider.executeQuery(dataSource, "getHostById", id);
        if (hosts.size() > 0)
            model.addAttribute("hostEditList", hosts.get(0));
        else
            model.addAttribute("hostEditList", null);

        return "hostForm";
    }

    public Object addUpdateHost(Integer id, String name,
                                String fqdn, String username, String host, int port,
                                String publicKey, Timestamp createdAt,
                                Timestamp updatedAt){
        Host newHost = new Host(name, fqdn, username, host, port, publicKey, createdAt, updatedAt);

        boolean insertFlag = false;
        if (id != null)
            newHost.setId(id);
        int updateCount = this.rwProvider.updateQuery(dataSource, "updateHost", newHost);

        if (updateCount == 0) {
            this.rwProvider.insertQuery(dataSource, "insertHost", newHost);
            insertFlag = true;
        }
        if (insertFlag)
            return "redirect:/host";
        return "redirect:/host?id=" + id;
    }
}
