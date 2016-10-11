<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@page import="java.io.IOException"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="model.Member"%>
<%@page import="java.util.List"%>
<%@page import="dao.MemberDao"%>
<%@page import="java.util.ArrayList"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Consulta Membros</title>
</head>

<script>
	function selectMember(member){
		//opener.document.getElementById('id').value=id;
		//opener.document.getElementById('name').value=name;
		window.print("Entro no metodo selectMember");
		request.getSession().setAttibute("memberView", member);
		window.print("selectMember: depois do request");
		RequestDispatcher rs = request.getRequestDispatcher("view_member.jsp");
		rs.forward(request, response);
	}
</script>

<body>
	 <div>
	 	<%@ include file="header.jsp" %>
	 </div>
	 
	 <form method="POST" action="searchMember">
		 <fieldset>
		 	<legend>Pesquisa por Nome</legend>
			 <div>
			 	<table style='width:100%'>
					 <tr>
						 <td><input type="text" name="searchName"></td>
						 <td><button type="submit" name="operation" value="searchByName">Pesquisar</button></td>
					 </tr>
				</table>
			 </div>
		 </fieldset>
	 </form>
	 <form method="POST" action="searchMember">
		 <fieldset>
		 	<legend>Pesquisa por Id</legend>
			 <div>
			 	<table style='width:100%'>
					 <tr>
						 <td><input type="text" name="searchId"></td>
						 <td><button type="submit" name="operation" value="searchById">Pesquisar</button></td>
					 </tr>
				</table>
			 </div>
		 </fieldset>
	 </form>
		 <fieldset>
		 	<legend>Resultado da Pesquisa de Membros</legend>
			 <br><br>
			 <div>
				 <table style='width:60%'>
					  <%					    
							// List of pending registrations.
							ArrayList<Member> listMembersFound = (ArrayList<Member>) request.getAttribute("listMemberFound");							
							String error = String.valueOf(request.getSession().getAttribute("error"));
					  		
							// Validation
					  		if(listMembersFound == null || listMembersFound.size() == 0){
								out.print("<td><center>Nenhum membro encontrado!</center></td>");
					  		} else if(!error.equals("null")){
					  			out.print("<td><center>"+error+"</center></td>");							
							} else {
								for (Member member : listMembersFound) {
									out.print("<td><a>"+member.getId()+"</a></td>");
									request.getSession().setAttribute("memberView", member);
									out.print("<th><a href='view_member.jsp'>"+member.getName()+"</a></th>");
									out.print("</tr>");												
								}		
							}
					  %>
				 </table>
			 </div>
		 </fieldset>
	</body>
</html>


	
	