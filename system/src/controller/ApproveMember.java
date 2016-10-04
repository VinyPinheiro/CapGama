/*****************************
* Class name: ApproveMember (.java) 
 * 
 * Purpose: Controller class for the member approves requesting member registration.
 *****************************/

package controller;

import java.awt.Window;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MemberDao;
import exception.DaoException;
import exception.MemberException;
import model.Member;

public class ApproveMember extends HttpServlet {
	
	/**
	 * Method to GET receive data and perform the same process that data received by POST method.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * Method to receive POST data and verify and send result to client
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		try {		
			String approve = request.getParameter("approve"); 
			 			
			String approve1 = String.valueOf(request.getParameter("approve").substring(7));
			
			System.out.println("approve= "+approve1);
			int memberId = Integer.parseInt(approve1); 

			// Receive the user who approved or rejected the request.
			String stringUser = String.valueOf(request.getSession().getAttribute("userName"));
			int codeUser = Integer.parseInt(stringUser);
			
			ArrayList<Member> requesterMembers = (ArrayList<Member>) request.getSession().getAttribute("requesterMembers");
			//int memberId = requesterMembers.get(1).getId();
			
			if(request.getParameter("approve").contains("Aceitar")) {
				approve = "Ativo";
				
				System.out.println("Member Position Aprovado: "+ memberId);
				System.out.println("RequesterMembers: "+ requesterMembers.size());
				
				int idMembro = requesterMembers.get(memberId).getId();
				
				Member member = new Member(idMembro, approve);
				MemberDao member_dao = new MemberDao(member);
				System.out.println("Member ID Aprovado: "+ idMembro);
								
				member_dao.approveMember(member, codeUser);
				response.sendRedirect("approve_member.jsp");
			
			} else if(request.getParameter("approve").contains("Recusar")) {
				approve = "Recusado";
				
				System.out.println("Member Position Reprovado: "+ memberId);
				System.out.println("RequesterMembers: "+ requesterMembers.size());
				
				int idMembro = requesterMembers.get(memberId).getId();
			
				Member member = new Member(idMembro, approve);
				MemberDao member_dao = new MemberDao(member);
				
				System.out.println("Member Id Recusado: "+ memberId);
				member_dao.rejectMember(member, codeUser);
				response.sendRedirect("approve_member.jsp");
				
		    } else {
		    	// Nothing to do!
		    }
						
        } catch (SQLException e) {            
            e.printStackTrace();	
		} catch (NumberFormatException e) {
			final String error = "<script>alert('Erro ao recuperar ao Aprovar Membro.'); history.go(-1);</script>";
			out.print(error);
			e.printStackTrace();
		} catch (MemberException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
