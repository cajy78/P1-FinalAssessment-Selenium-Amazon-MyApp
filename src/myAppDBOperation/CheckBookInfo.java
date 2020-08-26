package myAppDBOperation;
import java.sql.*;
import java.util.List;

public class CheckBookInfo {

	private static Connection con;
	
	public static boolean checkNameInfo(List<String> bookNames)
	{
		boolean dataExists = false;
		try
		{
			con = CreateConnection.InitiateDBConnection();
			Statement stm = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stm.executeQuery("select book_name from mostgifted_anabooks");
			
			for(String name : bookNames)
			{
				while(rs.next())
				{
					if(name.equals(rs.getString(1)))
						dataExists = true;
				}
				rs.beforeFirst();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			dataExists = false;
		}
		return dataExists;
	}
	
	public static boolean checkCatInfo(List<String> bookCategories)
	{
		boolean dataExists = false;
		try
		{
			con = CreateConnection.InitiateDBConnection();
			Statement stm = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stm.executeQuery("select category_name from book_categories");
			
			for(String name : bookCategories)
			{
				while(rs.next())
				{
					if(name.equals(rs.getString(1)))
						dataExists = true;
				}
				rs.beforeFirst();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			dataExists = false;
		}
		return dataExists;
	}
}
