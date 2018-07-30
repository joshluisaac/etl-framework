package com.kollect.etl.services;

import java.io.File;
import java.util.List;

import com.kollect.etl.util.FileUtils;
import com.powerapps.services.ISftpService;
import com.powerapps.services.Sftp;
import com.powerapps.services.SftpService;

public class SftpTest {
  
  public static void main( String[] args ) throws Throwable {
    ISftpService service = new SftpService();
    Sftp sftp = new Sftp(service,"app.kollectvalley.my", 5438, "filetransfer", "&#letrans!@", "/YYC");
    final String dirName = "diffOut";
    final String outDir = "../diffOut";
    final File outFile = new File(outDir);
    final List<String> fileList = new FileUtils().getFileList(outFile);
    final String finalFtpDst = sftp.getDstDir() + "/" + dirName;
    sftp.execute(fileList, outDir, finalFtpDst);

  }

}
