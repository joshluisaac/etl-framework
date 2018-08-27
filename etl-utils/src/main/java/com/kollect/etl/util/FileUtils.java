package com.kollect.etl.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * A utility providing file helper methods
 *
 * @author Josh Nwankwo
 */

public class FileUtils {

    private static final Logger LOG = LoggerFactory.getLogger(FileUtils.class);

    public void writeTextFile(final String fileName, final Object data) {
        writeTextFile(fileName, data, true);
    }

    public void writeTextFile(final String fileName, final Object data, final boolean append) {
        File file = new File(fileName);
        createFileIfNotExists(file);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, append))) {
            bw.write(data.toString());
            LOG.info("Wrote file {} to disk", fileName);
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    // returns the number of rows written
    public int writeListToFile(final File file, final List<String> data, final boolean append) {
        createFileIfNotExists(file);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, append))) {
            int rowCount = data.size();
            for (int i = 0; i < rowCount; i++) {
                if (i == (rowCount - 1)) {
                    bw.write(data.get(i));
                } else {
                    bw.write(data.get(i) + "\n");
                }
            }
            LOG.info("Wrote file {} to disk", file.getName());
            return rowCount;
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
        return -1;
    }

    // Retrieves the list of files in a particular directory, should throw an exception if dir isn't found
    public List<String> getFileList(final File dir) {
        List<String> list = new ArrayList<>();
        if (dir.isDirectory()) {
            String[] v = dir.list();
            Preconditions.checkNotNull(v);
            list.addAll(Arrays.asList(v));
        }
        return list;
    }


    public List<String> readFile(final String fileName) throws IOException {
        try (BufferedReader bufReader = new BufferedReader(
                new InputStreamReader(new FileInputStream(new File(fileName))));) {
            String line;
            List<String> list = new ArrayList<>();
            while ((line = bufReader.readLine()) != null) {
                list.add(line);
            }
            return list;
        }
    }


    public List<String> readFile(final File f) throws IOException {
        try (BufferedReader bufReader = new BufferedReader(
                new InputStreamReader(new FileInputStream(f)));) {
            String line;
            List<String> list = new ArrayList<>();
            while ((line = bufReader.readLine()) != null) {
                list.add(line);
            }
            return list;
        }
    }


    /**
     * Reads a file and appends only those lines ending with specified suffix to list
     *
     * @return returns a list of strings with lines ending with specified suffix
     * @throws IOException ioexception
     */
    public List<String> readFileLinesEndsWith(final String fileName, final String suffix) throws IOException {
        try (BufferedReader bufReader = new BufferedReader(
                new InputStreamReader(new FileInputStream(new File(fileName))));) {
            String line;
            List<String> list = new ArrayList<>();
            while ((line = bufReader.readLine()) != null) {
                if (line.endsWith(suffix)) list.add(line);
            }
            return list;
        }
    }


    /**
     * Reads a file and appends only those lines starting with specified prefix to list
     *
     * @return returns a list of strings with lines starts with specified prefix
     */
    public List<String> readFileLinesStartsWith(final String fileName, final String prefix) throws IOException {
        try (BufferedReader bufReader = new BufferedReader(
                new InputStreamReader(new FileInputStream(new File(fileName))))) {
            String line;
            List<String> list = new ArrayList<>();
            while ((line = bufReader.readLine()) != null) {
                if (line.startsWith(prefix)) list.add(line);
            }
            return list;
        }
    }


    @SuppressWarnings("unused")
    private void createFileIfNotExists(File f) {
        if (!f.exists()) {
            try {
                boolean isCreated = f.createNewFile();
                LOG.debug("Created file {}", new Object[]{f.getAbsolutePath()});
            } catch (IOException e) {
                LOG.error(e.getMessage(), e);
            }
        }
    }


    public boolean createDirIfNotExists(File f) {
        boolean isCreated = false;
        if (!f.exists()) {
            try {
                isCreated = f.mkdir();
                LOG.debug("Created directory {}", new Object[]{f.getAbsolutePath()});
            } catch (SecurityException e) {
                LOG.error(e.getMessage(), e);
            }
        }
        return isCreated;
    }


    public boolean deleteFile(File file) {
        return delete(file);
    }

    private boolean delete(File f) {
        boolean isDeleted = false;
        String fileName = f.getName();
        if (!f.exists()) {
            LOG.debug("File or directory named {} does not exist", new Object[]{fileName});
        } else {
            if (f.isDirectory()) {
                deleteDir(f);
            } else {
                isDeleted = f.delete();
                LOG.info("File {} has been deleted from {}", new Object[]{fileName, f.getAbsolutePath()});
            }
        }
        return isDeleted;
    }


    private void deleteDir(File f) {
        if (f.isDirectory()) {
            String[] dirList = f.list();
            Preconditions.checkNotNull(dirList);
            int dirSize = dirList.length;
            if (dirSize == 0) {
                f.delete();
                LOG.debug("Directory {} has been deleted from {}", new Object[]{f.getName(), f.getAbsolutePath()});
            } else if (dirSize > 0) {
                for (int p = 0; p < dirSize; p++) {
                    delete(new File(f.getAbsolutePath() + "/" + dirList[p]));
                }
            }
        }
    }


    public URL getClassPathResource(String fileName) {
        return getClass().getClassLoader().getResource("routesConfig.json");
    }

    /**
     * Retrieves a list of files from a directory path which starts with a specified prefix
     *
     * @param directory path
     * @param prefix    the file prefix
     * @return returns a list of strings with lines starting with specified prefix
     */
    public List<String> getFilesStartsWith(File dir, String prefix) {
        Preconditions.checkNotNull(dir);
        Preconditions.checkNotNull(prefix);
        List<String> list = new ArrayList<>();
        for (String file : new FileUtils().getFileList(dir)) {
            if (file.startsWith(prefix)) list.add(file);
        }
        return list;
    }

    /**
     * Gets a File object based on its path relative to the classpath
     *
     * @param path the path of the file relative to the classpath
     * @return A file handler
     */
    public File getFileFromClasspath(String path){
        //use the current classloader
        ClassLoader classLoader = getClass().getClassLoader();
        return new File(classLoader.getResource(path).getFile());
    }
    
    
    public static void streamOrientedCopy(File src, File dest) throws IOException {
      try(InputStream in = new FileInputStream(src);
          OutputStream out = new FileOutputStream(dest);
          ){
        
        byte[] buffer = new byte[1024];
        int bytesRead;
        
        while( (bytesRead = in.read(buffer)) > 0 ){
            out.write(buffer, 0, bytesRead);
        }
        
       
        
      }
     
    }
    
    
    public static String readFile(String path, Charset encoding) 
        throws IOException 
      {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
      }
    



}
