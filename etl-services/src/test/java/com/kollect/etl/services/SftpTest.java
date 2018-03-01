package com.kollect.etl.services;

import java.io.File;
import java.util.List;

import com.kollect.etl.services.impl.SftpService;
import com.kollect.etl.util.FileUtils;

import app.Sftp;

public class SftpTest {
  
  public static void main( String[] args ) throws Throwable {
    Sftp sftp = new Sftp("app.kollectvalley.my", 5438, "filetransfer", "&#letrans!@", "/YYC");
    ISftpServiceProvider sftpServ = new SftpService();
    final String dirName = "diffOut";
    final String outDir = "../diffOut";
    final File outFile = new File(outDir);
    final List<String> fileList = new FileUtils().getFileList(outFile);
    final String finalFtpDst = sftp.getDstDir() + "/" + dirName;
    sftp.execute(sftpServ, fileList, outDir, finalFtpDst);

  }

}
