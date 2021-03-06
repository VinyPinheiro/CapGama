/*****************************
* Class name: RegisterMember (.java) 
 * 
 * Purpose: Controller class to create Member object and persist in database
 *****************************/

package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MemberDao;
import exception.AddressException;
import exception.DaoException;
import exception.MemberException;
import exception.RegisterMemberException;
import exception.UfException;
import model.Address;
import model.Member;
import model.UF;

public class RegisterMember extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	/**
	 * Method to receive POST data and verify and send result to client
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");  
		PrintWriter out = response.getWriter();  
		
		DateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd");

		if(request.getParameter("password").equals(request.getParameter("password_confirmation")))
		{
			try {
				Member member = new Member(
						Integer.parseInt(request.getParameter("id")),
						request.getParameter("name"), 
						sourceFormat.parse(request.getParameter("birthdate")), 
						request.getParameter("password"), 
						request.getParameter("phone"), 
						request.getParameter("dad_phone"), 
						new Address(
								request.getParameter("address"), 
								Integer.parseInt(request.getParameter("number")), 
								request.getParameter("complement"), 
								request.getParameter("zipcode"),
								request.getParameter("city"),
								new UF(request.getParameter("state"))
								)
						);
				MemberDao member_dao = new MemberDao(member);
				
				member_dao.register();

				final String error = "<script>alert(\"Cadastrado com Sucesso!\");locate.href='/home.jsp'</script>";
				out.print(error);
			} catch (MemberException e) {
				final String error = "<script>alert(\""+e.getMessage()+"\");history.go(-1);</script>";
				out.print(error);
			} catch (NumberFormatException e) {
				final String error = "<script>alert(\""+e.getMessage()+"\");history.go(-1);</script>";
				out.print(error);
			} catch (ParseException e) {
				final String error = "<script>alert(\""+e.getMessage()+"\");history.go(-1);</script>";
				out.print(error);
			} catch (AddressException e) {
				final String error = "<script>alert(\""+e.getMessage()+"\");history.go(-1);</script>";
				out.print(error);
			} catch (UfException e) {
				final String error = "<script>alert(\""+e.getMessage()+"\");history.go(-1);</script>";
				out.print(error);
			} catch (DaoException e) {
				final String error = "<script>alert(\""+e.getMessage()+"\");history.go(-1);</script>";
				out.print(error);
			} catch (SQLException e) {
				final String error = "<script>alert(\""+e.getMessage()+"\");history.go(-1);</script>";
				out.print(error);
			}
		} else{
			final String error = "<script>alert(\"Senhas Diferem\");</script>";
			out.print(error);
		}
	}
}
