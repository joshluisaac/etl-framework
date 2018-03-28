package com.kollect.connection.pool;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kollect.etl.util.FileUtils;

/**
 * @author Andres.Cespedes
 * @version 1.0 $Date: 24/01/2015
 * @since 1.7
 */
public class Metadata {
  
  private static final Logger LOG = LoggerFactory.getLogger(Metadata.class);

    static Connection connection = null;
    static DatabaseMetaData metadata = null;

    // Static block for initialization
    static {
        try {
            connection = new DataSource().initiateConnection("kollectvalley");
        } catch (SQLException e) {
            System.err.println("There was an error getting the connection: "
                    + e.getMessage());
        }

        try {
            metadata = connection.getMetaData();
        } catch (SQLException e) {
            System.err.println("There was an error getting the metadata: "
                    + e.getMessage());
        }
    }

    /**
     * Prints in the console the general metadata.
     * 
     * @throws SQLException
     */
    public static void printGeneralMetadata() throws SQLException {
        System.out.println("Database Product Name: "
                + metadata.getDatabaseProductName());
        System.out.println("Database Product Version: "
                + metadata.getDatabaseProductVersion());
        System.out.println("Logged User: " + metadata.getUserName());
        System.out.println("JDBC Driver: " + metadata.getDriverName());
        System.out.println("Driver Version: " + metadata.getDriverVersion());
        System.out.println("\n");
    }

    /**
     * 
     * @return Arraylist with the table's name
     * @throws SQLException
     */
    public static List<String> getTablesMetadata() throws SQLException {
        String table[] = { "TABLE", "VIEW" };
        ResultSet rs = null;
        List<String> tables = null;
        // receive the Type of the object in a String array.
        rs = metadata.getTables(null, null, null, table);
        tables = new ArrayList<String>();
        while (rs.next()) {
            tables.add(rs.getString("TABLE_NAME"));
        }
        return tables;
    }

    /**
     * Prints in the console the columns metadata, based in the Arraylist of
     * tables passed as parameter.
     * 
     * @param tables
     * @throws SQLException
     */
    public static void getColumnsMetadata(List<String> tables)
            throws SQLException {
      StringBuilder sb = new StringBuilder();
        ResultSet rs = null;
        // Print the columns properties of the actual table
        for (String actualTable : tables) {
            rs = metadata.getColumns(null, null, actualTable, null);
            String actualObject = actualTable.toUpperCase();
            //System.out.println(actualObject);
            while (rs.next()) {
              String row = actualObject + "|" + rs.getString("COLUMN_NAME") + "|" + rs.getString("TYPE_NAME") + "|" + rs.getString("COLUMN_SIZE");
                //System.out.println(row);
                sb.append(row + "\n");
            }
            LOG.info("Read metadata for object {}", actualObject);
            sb.append("\n");
            sb.append("\n");
        }
        String fileName = "META_DATA_" + System.currentTimeMillis() + ".csv";
        new FileUtils().writeTextFile(fileName, sb);
        Class<?> clazz = com.kollect.etl.util.FileUtils.class;
        Method[] m =  clazz.getDeclaredMethods();
        for(Method x : m) {
          x.getName();
        }

    }

    /**
     * 
     * @param args
     */
    public static void main(String[] args) {
        try {
            printGeneralMetadata();
            // Print all the tables of the database scheme, with their names and
            // structure
            getColumnsMetadata(getTablesMetadata());
        } catch (SQLException e) {
            System.err
                    .println("There was an error retrieving the metadata properties: "
                            + e.getMessage());
        }
    }
}
