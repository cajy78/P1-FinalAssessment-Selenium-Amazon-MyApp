package myAppDBOperation;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

public class InsertBookInfo {

	private static Connection con;
	
	public static boolean storeBookInformaton(List<String> categories, List<String> names)
	{
		boolean complete = false;
		PreparedStatement psCat, psName;
		try
		{
			con = CreateConnection.InitiateDBConnection();
			con.setAutoCommit(false);
			psCat = con.prepareStatement("insert into book_categories(cat_id, category_name) values(?,?)");
			psName = con.prepareStatement("insert into mostgifted_AnAbooks(book_id, book_name) values(?,?)");

			int cat = 100;
			for(String i: categories)
			{
				String catid = "CAT_"+cat;
				psCat.setString(1, catid);
				psCat.setString(2, i);
				psCat.addBatch();
				cat+=1;
			}
			psCat.executeBatch();
			int bk = 100;
			for(String i: names)
			{
				String bid = "BKIN_"+bk;
				//System.out.println(i);
				psName.setString(1, bid);
				psName.setString(2, i);
				psName.addBatch();
				bk+=1;
			}
			psName.executeBatch();
			con.commit();
			complete = true;
			psCat.close();
			psName.close();
			con.close();
		}
		catch(Exception e)
		{
			complete = false;
			try
			{
				con.rollback();
				e.printStackTrace();
			}
			catch(Exception ie)
			{
				ie.printStackTrace();
			}
		}
		finally
		{
			try{
			con.close();
			}catch(Exception e){e.printStackTrace();}
		}
		return complete;
	}
}
