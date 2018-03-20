package com.kollect.etl.service;

import com.kollect.etl.dataaccess.UpdateVendorInvoicesGroupDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateVendorInvoicesGroupService {
    @Autowired
    private UpdateVendorInvoicesGroupDao UpdateVendorInvoicesGroup;
}
