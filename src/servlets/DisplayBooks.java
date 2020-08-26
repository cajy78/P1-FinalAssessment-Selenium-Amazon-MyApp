package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.sql.*;
import myAppDBOperation.CreateConnection;

/**
 * Servlet implementation class DisplayBooks
 */
@WebServlet("/DisplayBooks")
public class DisplayBooks extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException 
	{
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		try
		{
			String search = request.getParameter("searchVia");
			String query = "";
			
			if(search.equals("viaBookID"))
			{
				String bookID = request.getParameter("bookID");
				if(bookID.equals(""))
				{
					pw.write("<h1>Book Info Page</h1>");
					pw.write("<p style='color:red'>Please enter a Book ID</p>");
					pw.write("<p style='color:red'>Book ID field cannot be left blank while searching for Books via ID</p>");
					pw.write("<br><br><a href='index.html' id='goHome'>ABCXYZ Book Master</a>");
				}
				else
				{
					query = "Select * from mostgifted_anabooks where book_id='"+bookID+"'";
				}
			}
			else if(search.equals("viaBookName"))
			{
				String bookName = request.getParameter("bookName");
				if(bookName.equals(""))
				{
					pw.write("<h1>Book Info Page</h1>");
					pw.write("<p style='color:red'>Please enter a Book Name</p>");
					pw.write("<p style='color:red'>Book Name field cannot be left blank while searching for Books via Names</p>");
					pw.write("<br><br><a href='index.html' id='goHome'>ABCXYZ Book Master</a>");
				}
				else
				{
					query = "Select * from mostgifted_anabooks where book_name LIKE '%"+bookName+"%'";
				}
			}
			else if(search.equals("allBookCategories"))
			{
				
				query = "Select * from book_categories";
			}
			else if(search.equals("allBooks"))
			{
				query = "Select * from mostgifted_anabooks";
			}

			if(!query.equals(""))
			{
				Connection con = CreateConnection.InitiateDBConnection();
				Statement stm = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
				ResultSet rs = stm.executeQuery(query);
				if(rs.next())
				{
					pw.write("<h1>Book Info Page</h1>");
					pw.write("<table border=1>");
					pw.write("<thead><tr><th>ID</th><th>Name / Category</th></tr></thead>");
					pw.write("<tbody>");
					rs.beforeFirst();
					while(rs.next())
					{
						pw.write("<tr>"
									+"<td>"+ rs.getString(1)+"</td>"
									+ "<td>"+ rs.getString(2)+"</td>"
									+"</tr>");
					}
					pw.write("</tbody>");
					pw.write("</table>");
					pw.write("<br><br><a href='index.html' id='goHome'>ABCXYZ Book Master</a>");
				}
				else
				{
					pw.write("<h1>Product Info Page</h1>");
					pw.write("<p style='color:red'>No data exists with the current selection</p>");
					pw.write("<p style='color:red'>Please enter correct Book ID Or Name Or Category</p>");
					pw.write("<br><br><a href='index.html' id='goHome'>ABCXYZ Book Master</a>");
				}
			}
		}
		catch(Exception e)
		{
			pw.write("Exception has occurred");
			e.printStackTrace();
		}
	}
}