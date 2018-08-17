package com.kollect.etl.service.app;

import java.io.IOException;

import com.kollect.etl.util.dataconnector.DcStats2Json;

public class DataConnectorNotification {
	
	private  DcStats2Json dcStats;
	
	public DataConnectorNotification() throws IOException {
		this.dcStats = new DcStats2Json();
	}
}
