package com.kollect.etl.incubation;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class LargeFileComparator {

  static final Logger LOG = LoggerFactory.getLogger(LargeFileComparator.class);

  private static final String FILE_1 = "/home/joshua/martian/_info_tech/datasets/Crimes_-_2001_to_present.csv";
  private static final String FILE_2 = "/home/joshua/martian/_info_tech/datasets/Crimes_-_2001_to_present.csv";

  public static void main(String[] args) throws Exception {
    long start = System.currentTimeMillis();
    LargeFileComparator fc = new LargeFileComparator();
    // int lines = fc.countLines(new File(FILE_1));
    // LOG.info("File contains {} lines", lines);

    fc.compareByteStream(new File(FILE_1), new File(FILE_2));
    LOG.info("{} ms", (System.currentTimeMillis() - start));
  }

  public int countLines(final File f) throws IOException {
    byte[] b = new byte[2048];
    int bytesRead = 0;
    int count = 0;
    boolean isBufferEmpty = true;
    try (BufferedInputStream buf = new BufferedInputStream(new FileInputStream(f));) {
      while ((bytesRead = buf.read(b)) != -1) {
        isBufferEmpty = false;
        for (int i = 0; i < bytesRead; i++) {
          // if the character read is a Line Feed (LF) AKA new line character ('\n') then
          // increment count by 1
          // i'm using 10 which is the decimal representation of LF
          if (b[i] == 10)
            count = count + 1;
        }
      }
    }
    return (!isBufferEmpty && count == 0) ? 0 : count;
  }

  public int compareByteStream(final File file1, final File file2) throws Exception {
    int count = 0;
    int readCount = 1;

    // set byte allocation size to 10000000 bytes used to provide data caching so as
    // to prevent frequent reads to physical disk
    int byteLength = 10000000; // 10000000

    try (BufferedInputStream buf1 = new BufferedInputStream(new FileInputStream(file1));
        BufferedInputStream buf2 = new BufferedInputStream(new FileInputStream(file2));) {

      byte[] byteFragment1 = new byte[byteLength];
      byte[] byteFragment2 = new byte[byteLength];
      long fileLength = file1.length();
      while ((count * byteLength) < fileLength) {

        // read portions of file1 & file2
        int actualBytesRead1 = buf1.read(byteFragment1, 0, byteLength);
        int actualBytesRead2 = buf2.read(byteFragment2, 0, byteLength);

        System.out.println(Arrays.toString(byteFragment1));
        System.out.println("Got here");

        // log what is going on
        // LOG.info("Read up to {} bytes and {} actual bytes", (readCount * byteLength),
        // actualBytesRead1);
        // LOG.info("Read up to {} bytes and {} actual bytes", (readCount * byteLength),
        // actualBytesRead2);

        // increment counters
        count = count + 1;
        readCount = readCount + 1;

        // get hash value of byte-array
        // long hashVal = MurmurHash.hash64(byteFragment1, 10000000);
        // long hashVal2 = MurmurHash.hash64(byteFragment2, 10000000);

        // boolean isEqual = Arrays.equals(byteFragment1, byteFragment1);
        // LOG.info("{}", isEqual);

        // System.out.println(hashVal);
      }

    }

    return 0;
  }

}
