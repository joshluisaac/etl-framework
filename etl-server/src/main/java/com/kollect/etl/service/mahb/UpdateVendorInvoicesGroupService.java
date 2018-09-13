package com.kollect.etl.service.mahb;

import com.kollect.etl.dataaccess.UpdateVendorInvoicesGroupDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class UpdateVendorInvoicesGroupService {
    @Autowired
    private UpdateVendorInvoicesGroupDao UpdateVendorInvoicesGroup;


    public int UpdateVendorInvoicesGroup1(Object object) {
        return this.UpdateVendorInvoicesGroup.insertVendorInvoicesGroup1(object);
    }

    public int UpdateVendorInvoicesGroup2(Object object) {
        return this.UpdateVendorInvoicesGroup.insertVendorInvoicesGroup2(object);
    }

    public int UpdateVendorInvoicesGroup3(Object object) {
        return this.UpdateVendorInvoicesGroup.insertVendorInvoicesGroup3(object);
    }

    public int UpdateVendorInvoicesGroup4(Object object) {
        return this.UpdateVendorInvoicesGroup.insertVendorInvoicesGroup4(object);
    }

    public int UpdateVendorInvoicesGroup5(Object object) {
        return this.UpdateVendorInvoicesGroup.insertVendorInvoicesGroup5(object);
    }
    public int UpdateVendorInvoicesGroup6(Object object) {
        return this.UpdateVendorInvoicesGroup.insertVendorInvoicesGroup6(object);
    }
    public int UpdateVendorInvoicesGroup7(Object object) {
        return this.UpdateVendorInvoicesGroup.insertVendorInvoicesGroup7(object);
    }
    public int UpdateVendorInvoicesGroup8(Object object) {
        return this.UpdateVendorInvoicesGroup.insertVendorInvoicesGroup8(object);
    }
    public int UpdateVendorInvoicesGroup9(Object object) {
        return this.UpdateVendorInvoicesGroup.insertVendorInvoicesGroup9(object);
    }
    public int UpdateVendorInvoicesGroup10(Object object) {
        return this.UpdateVendorInvoicesGroup.insertVendorInvoicesGroup10(object);
    }
    public int UpdateVendorInvoicesGroup11(Object object) {
        return this.UpdateVendorInvoicesGroup.insertVendorInvoicesGroup11(object);
    }
    public int UpdateVendorInvoicesGroup12(Object object) {
        return this.UpdateVendorInvoicesGroup.insertVendorInvoicesGroup12(object);
    }
}
