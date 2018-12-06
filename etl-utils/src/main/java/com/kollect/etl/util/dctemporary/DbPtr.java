package com.kollect.etl.util.dctemporary;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.stream.JsonWriter;


public class DbPtr {
	public DbPtr(String databaseURL,int databasePort, String databaseName, String databaseUser, String databasePass)
	{
		this.dbCon = null;
		this.dbName=databaseName;
		this.dbURL=databaseURL;
		this.dbPort=databasePort;
		this.dbUser=databaseUser;
		this.dbPass=databasePass;
		
	}	
	

	public void InitConnection(String dbDriver, String conIdentifier ) {
		this.dbDriver = dbDriver;
		this.conIdentifier=conIdentifier;
		try {
			
			Class.forName(this.dbDriver);
			System.out.println("JDBC Driver for "+this.conIdentifier+" is available in app");
		} catch (ClassNotFoundException e) {

			System.out.println("Where is your "+this.conIdentifier+" JDBC Driver? Include in your library path!");
			e.printStackTrace();
			return;
		}
	}

	
	
	public boolean Connect()
	{
		if (this.dbCon == null)
		{
			try {
				String connectionStr="jdbc:"+this.conIdentifier+"://"+this.dbURL+":"+this.dbPort+"/"+this.dbName;
				//System.out.println(connectionStr);
				this.dbCon = DriverManager.getConnection(connectionStr, this.dbUser,this.dbPass);
				System.out.println("Connected to the "+this.conIdentifier+" server successfully.");
	
			} catch (SQLException e) {
		
				System.out.println("Connection Failed! Check output console, error is: "+ e. getMessage());
				return false;
	
			}
		}
		return true;
	}
	

	public Boolean DisConnect()
	{
		try {
			if(this.dbCon.isClosed()) {
				this.dbCon.close();
				this.dbCon=null;
			}
			return true;
		}
		catch (SQLException e) {
			System.out.println("Cant close the connection: "+ e. getMessage());
			return false;
		}
	}
	

	public Boolean ExecuteNoneQuery(String query) throws SQLException {
		if (this.Connect()) {
			try {
				Statement state = this.dbCon.createStatement();
				state.executeUpdate(query,0);
				this.DisConnect();
				return true;
			}
			
			catch(SQLException e) {
				System.out.println("There is error on your query: \n"+ e. getMessage());
				throw e;
			}

		}
		else
			return false;
	}
	

	public int ExecuteNoneQuery1(String query) throws SQLException {
        ResultSet result = null;
        int candidateId = 0;
		if (this.Connect()) {
			try {
				PreparedStatement pstmt = this.dbCon.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
	            int rowAffected = pstmt.executeUpdate();
	            if(rowAffected == 1)
	            {
	                result = pstmt.getGeneratedKeys();
	                if(result.next())
	                    candidateId = result.getInt(1);
	            }

			}
			
			catch(SQLException e) {
				
				System.out.println("There is error on your query: \n"+ e. getMessage());
				throw e;
			}

		}
		return candidateId;		
	}
	
	
	
	public Boolean ExecuteQuery(String query) throws SQLException {
		this.resultSet=null;
		if (this.Connect()) {
			try {
				Statement state = this.dbCon.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				this.resultSet = state.executeQuery(query);
				return true;
			}
			catch(SQLException e) {
				System.out.println("There is error on your query: \n"+ e. getMessage());
				throw e;
			}
		}
		return false;
	}
	
	public void QueryJsonResult(JsonWriter json,String query) throws Exception {
		if (this.Connect()) {
			try{
				Statement state = this.dbCon.createStatement();
				ResultSet result = state.executeQuery(query);
				Convertor.convertToJSON(json,result);
				this.DisConnect();
			}
			catch (Exception e) {
				System.out.println("Problem occured: "+e. getMessage());
				throw e;
			}
		}
	}
	
	public HashMap<Integer, String> FillComboBox(String query){
		HashMap<Integer, String> combo = new HashMap<Integer,String>();
		if (this.Connect()) {
			try{

				Statement state = this.dbCon.createStatement();
				ResultSet result = state.executeQuery(query);
				while(result.next()) {
					combo.put(result.getInt(1), result.getString(2));
				}
				this.DisConnect();
			}
			catch (SQLException e) {
				System.out.println("Problem occured: "+e. getMessage());
			}
		}
		return combo;
	}
	
	public ArrayList<String> getTableNames() {
		//int count=0;
		ArrayList<String> arr = new ArrayList<String>();
		if (this.Connect()) {
			try {
				String table[] = { "TABLE" };
				DatabaseMetaData metaData = this.dbCon.getMetaData();
				ResultSet result = metaData.getTables(null, null, null, table);

				while(result.next()) {
					arr.add(result.getString("TABLE_NAME"));
					//System.out.println(result.getString("TABLE_NAME"));
					//++count;
				}
				//System.out.println("count is: "+count);
			}
			catch(SQLException e) {
				System.out.println("Connection Failed! Check output console, error is: \n"+ e. getMessage());
			}
		}
		return arr;
	}

	
	public ArrayList<String> getColumnName(String tableName) {
		//int count=0;
		ArrayList<String> arr = new ArrayList<String>();
		if (this.Connect()) {
			try {
				Statement state = this.dbCon.createStatement();
				ResultSet result = state.executeQuery("SELECT column_name, is_nullable,data_type,character_maximum_length,numeric_precision,numeric_precision_radix FROM information_schema.columns WHERE table_schema='public' AND table_name='"+tableName+"'");
				
				while(result.next()) {
					arr.add(result.getString(1));
					//System.out.println(result.getString(1)+" - "+result.getString(2)+" - "+result.getString(3)+" - "+result.getString(4)+" - "+result.getString(5)+" - "+result.getString(6));
					//++count;
				}
				//System.out.println("count is: "+count);
			}
			catch(SQLException e) {
				System.out.println("Connection Failed! Check output console, error is: \n"+ e. getMessage());
			}
		}
		return arr;
	}
	
	
	
	
	public void getTableNames(int a) {
		//int count=0;
		if (this.Connect()) {
			try {
				Statement query = this.dbCon.createStatement();
				ResultSet result = query.executeQuery("SELECT table_name FROM information_schema.tables WHERE table_schema='public' AND table_type='BASE TABLE'");

				while(result.next()) {
					//System.out.println(result.getString("TABLE_NAME"));
				//	++count;
				}
				//System.out.println("count is: "+count);
			}
			catch(SQLException e) {
				System.out.println("Connection Failed! Check output console, error is: \n"+ e. getMessage());
			}
		}
	}
	
	
	
	public void getColumnName1(String tableName) {
		//int count=0;
		if (this.Connect()) {
			try {
				Statement query = this.dbCon.createStatement();
				ResultSet result = query.executeQuery("SELECT column_name, is_nullable,data_type,character_maximum_length,numeric_precision,numeric_precision_radix FROM information_schema.columns WHERE table_schema='public' AND table_name='"+tableName+"'");
				
				while(result.next()) {
					//System.out.println(result.getString(1)+" - "+result.getString(2)+" - "+result.getString(3)+" - "+result.getString(4)+" - "+result.getString(5)+" - "+result.getString(6));
					//++count;
				}
				//System.out.println("count is: "+count);
			}
			catch(SQLException e) {
				System.out.println("Connection Failed! Check output console, error is: \n"+ e. getMessage());
			}
		}
	}
	
	public void getIndexes2(String tableName) {
		//int count=0;
		if (this.Connect()) {
			try {
				Statement query = this.dbCon.createStatement();
				ResultSet result = query.executeQuery("show indexes from "+tableName);
				
				while(result.next()) {
					//System.out.println(result.getString(1)+" - "+result.getString(2)+" - "+result.getString(3)+" - "+result.getString(4)+" - "+result.getString(5)+" - "+result.getString(6));
					//++count;
				}
				//System.out.println("count is: "+count);
			}
			catch(SQLException e) {
				System.out.println("Connection Failed! Check output console, error is: \n"+ e. getMessage());
			}
		}
	}

	
	
	public void getIndexes(String tableName) {
		if (this.Connect()) {
			try {
				System.out.println("salam");
				DatabaseMetaData metaData = this.dbCon.getMetaData();
//				ResultSet result = metaData.getIndexInfo(null, null, tableName, true, false);
				ResultSet result = metaData.getPrimaryKeys(null, null, tableName);
				ResultSetMetaData rsMetaData= result.getMetaData();

				int colCount = rsMetaData.getColumnCount();
				
				while(result.next()) {
					for (int i=1;i<=colCount;i++)
						System.out.print(result.getString(i)+" - ");
					System.out.println();
				}
			}
			catch(SQLException e) {
				System.out.println("Connection Failed! Check output console, error is: \n"+ e. getMessage());
			}
		}
	}	
	
	
	
    public void getColumnsMetadata1(String tables) {
		if (this.Connect()) {
	    	try {
		  	DatabaseMetaData metadata=this.dbCon.getMetaData();
	    	ResultSet result = metadata.getColumns(null, null, tables, null);
			ResultSetMetaData rsMetaData= result.getMetaData();
			int colCount = rsMetaData.getColumnCount();
        	for (int i=1;i<colCount;i++)
				System.out.print(rsMetaData.getColumnName(i)+" - ");
			System.out.println();

			while (result.next()) {
	
	        	for (int i=1;i<colCount;i++)
					System.out.print(result.getString(i)+" - ");
				System.out.println();
	        }
	      }
	      catch(SQLException e) {
			System.out.println("Connection Failed! Check output console, error is: \n"+ e. getMessage());
	      }
		}
	}

    public ArrayList<ColumnMetaData> getColumnsMetadata(String tables) {
    	ArrayList<ColumnMetaData> colMetaData = new ArrayList<ColumnMetaData>();
    	//int count=0;
		if (this.Connect()) {
	    	try {
			  	DatabaseMetaData metadata=this.dbCon.getMetaData();
		    	ResultSet result = metadata.getColumns(null, null, tables, null);
				//ResultSetMetaData rsMetaData= result.getMetaData();
				//int colCount = rsMetaData.getColumnCount();
	
				while (result.next()) {
					//System.out.println(result.getString("column_name")+" - "+result.getString("type_name")+" - "+result.getString("column_def")+" - "+result.getString("nullable")+" - "+result.getString("nullable"));

					ColumnMetaData newCol = new ColumnMetaData();
					int columnType = 1;
					switch(result.getString("type_name")){
						case "int8":
						case "int4":
						case "int2":
						case "bigserial":
							columnType =1;
							break;
						case "numeric":
							columnType=2;
							break;
						case "varchar":
						case "char":
						case "text":
							columnType=3;
							break;
						case "timestamp":
							columnType=4;
							break;
						case "bool": 
							columnType=5;
							break;
					}

					//newCol.fillData(result.getString("column_name"), columnType, Integer.valueOf(result.getString("nullable")), result.getString("column_def"));
					newCol.colName=result.getString("column_name");
					newCol.colType=columnType;
					newCol.isNull=Integer.valueOf(result.getString("nullable"));
					newCol.defaultVal=result.getString("column_def");
					colMetaData.add(newCol);
		        }
				//System.out.println("count is: "+count);
	      }
	      catch(SQLException e) {
	    	  System.out.println("Connection Failed! Check output console, error is: \n"+ e. getMessage());
	      }
		}
		return colMetaData;
	}
    
    public void Commit(){
    	try {
    		this.dbCon.commit();
    	}
    	catch (SQLException e) {}
    	
    }

    public void RollBack(){
    	try {
    		this.dbCon.rollback();
    	}
    	catch (SQLException e) {}
    	
    }
    
    
    public void StartTransaction() {
    	try {
    		this.dbCon.setAutoCommit(false);
    	}
    	catch (SQLException e) {}
    }

    public void EndTransaction() {
    	try {
    		this.dbCon.setAutoCommit(true);
    	}
    	catch (SQLException e) {}
    }

    
	private Connection dbCon;
	private String dbURL;
	private int dbPort;
	private String dbName;
	private String dbUser;
	private String dbPass;
	

	private String dbDriver;
	private String conIdentifier;
	
	public ResultSet resultSet;
}




