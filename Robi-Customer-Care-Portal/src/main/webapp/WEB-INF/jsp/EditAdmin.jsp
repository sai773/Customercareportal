<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*"%>
<%@ page import="com.ashield.pojoClasses.*"%>
<%@ page import="com.ashield.logs.*"%>

<!DOCTYPE html>
<html> 
<head>
<meta http-equiv="Content-Security-Policy" content="default-src *; script-src * 'unsafe-inline' 'unsafe-eval' ;style-src * 'unsafe-inline';
">

<meta charset="utf-8">
<title>Robi</title>
<!-- <link rel="shortcut icon" href="images/favicon.ico" type="image/x-icon">  -->
<link rel="shortcut icon" href="images/favicon.ico">

<link rel="stylesheet" type="text/css" href="css/newlogin.css" />
<script src="js/jquery-3.5.1.min.js"></script>
<script src="js/jquery.validate.min.js"></script>
<script src="js/disableFunctionalKeys.js"></script> 


<link href="css/bootstrap.min.css" rel="stylesheet" >
<link href="css/bootstrap.min.css.map" >

<script src="js/bootstrap.min.js"></script>
<!------ Include the above in your HEAD tag ---------->
</head>
<style>
table.paleBlueRows {
  font-family: "Times New Roman", Times, serif;
  border: 1px solid #FFFFFF;
  
  text-align: center;
  border-collapse: collapse;
}
table.paleBlueRows td, table.paleBlueRows th {

  border: 5px solid #FFFFFF;
  padding: 2px 2px;
  width: 170px;
  height: 50px;
}
table.paleBlueRows tbody td {
  font-size: 18px;
  background:#D0E4F5;
}
table.paleBlueRows thead {
  background: #204a87;
  border-bottom: 5px solid #FFFFFF;
}
table.paleBlueRows tr:nth-child(even) {
  background: #D0E4F5;
}
table.paleBlueRows thead th {
  font-size: 18px;
  font-weight: bold;
  color: #FFFFFF;
  text-align: center;
}
  border-left: 2px solid #FFFFFF;	
table.paleBlueRows thead th:first-child {
  border-left: none;
}

table.paleBlueRows tfoot td {
  font-size: 14px;
}
</style>

<script type="text/javascript">
  window.onload = function() {
    document.addEventListener("contextmenu", function(e){
      e.preventDefault();
    }, false);
    document.addEventListener("keydown", function(e) {
    //document.onkeydown = function(e) {
      // "I" key
      if (e.ctrlKey && e.shiftKey && e.keyCode == 73) {
        disabledEvent(e);
      }
      // "J" key
      if (e.ctrlKey && e.shiftKey && e.keyCode == 74) {
        disabledEvent(e);
      }
      // "S" key + macOS
      if (e.keyCode == 83 && (navigator.platform.match("Mac") ? e.metaKey : e.ctrlKey)) {
        disabledEvent(e);
      }
      // "U" key
      if (e.ctrlKey && e.keyCode == 85) {
        disabledEvent(e);
      }
      // "F12" key
      if (event.keyCode == 123) {
        disabledEvent(e);
      }
    }, false);
    function disabledEvent(e){
      if (e.stopPropagation){
        e.stopPropagation();
      } else if (window.event){
        window.event.cancelBubble = true;
      }
      e.preventDefault();
      return false;
    }
  };
  
  window.history.forward();
  
        function noBack() {
            window.history.forward();
        }
</script>
<script>

$(document).ready(function(){
	$('.selectOne').on('change', function() {
	   $('.selectOne').not(this).prop('checked', false);
	  
	  
	});
	
	 $('.selectOne').click(function(){
		 
         if($(this).prop('checked') == true){
            $('input[type="submit"]').prop('disabled', false);
         }else{
              $('input[type="submit"]').prop('disabled', true);
            //  alert("please select the check box")
       }
    });
});
	

</script>
<body oncontextmenu="return false;">
<div class="row">
 	<div class="col-md-10">
        <img src="images/Robi_Axiata_logo.png" alt=""   width="140" height="140"  class="navbar-brand">
    </div>
    <div class="col-md-2">
       	<img src="images/airtel.png" alt=""  width="100" height="100"   class=" navbar-brands">
    </div>
 </div>
 
 <div class="row" style="margin-left:25%; border: 2px solid gray;margin-top:-7%; margin-bottom:10px;margin-right:25%">
 	<div class="col-md-3" >
         <img src="images/newashield.png" alt=""   width="160" height="80"  class="navbar-brandss">
    </div>
 	<div class="col-md-9"><div class="navbar-header">
      <h1 class="navbar-brand">Fraud Management Portal</h1>
 	</div></div>
 </div>
 
<div class="row" style="margin-top:3%;">
 <div class="col-md-9">
 
	<label style="float:left; margin-left:57%;color: black; font-size: 26px; font-weight:bold;" class="log">Welcome &nbsp<%=session.getAttribute("uname")%>
			<%! String user=null; %>
				 <%
					String userid = (String) session.getAttribute("uname");
		
			 		if (userid == null) {
						response.sendRedirect("login.jsp");
						return;
					}
				%> 
	</label>
	</div>
	
	
 <div class="col-md-3">

	<label style="float:center; margin-left:26%;" class="log">
	<a href="admin" style="text-decoration: none; color: #060613;"><button class="exit-btn" style="width: 150px;height:46px; color:#fff;  background-color:#f92d1f;font-weight:bold;font-size:16px" >Logout</button></a>
	</label>
	</div>
	</div>

	<div class="row">
	<div class="col-md-9">
	<div id="errodiv" style="color:red; margin-top:2%;margin-left: 46.5%;margin-bottom:-2%;"><label id="errorMsg"></label><br/></div>
	           <div id="loginMsg" style="color: #FF0000;margin-left:59%">${errorMessage}</div>
		
		</div>	
		</div>

<form name="AddForm" method="post" action="adminUsrAdd">
	<div style="margin-top:2%;margin-left:60.5%;">
		<input type="submit" name="adduser"  value="Add User" style="width: 150px;height:46px; color:#fff;  background-color:#f92d1f;font-weight:bold;font-size:18px;"/>
	</div>
</form>

 <form name="EditForm" method="post" action="adminEditForm">
	<div style="margin-top:-2.9%;margin-left:70%;">
  		 <input type="submit" id="submitbtn1" name="Edit"  value="Disable User" disabled='disabled'  style="width: 150px;height:46px; color:#fff;  background-color:#f92d1f;font-weight:bold;font-size:18px;margin-left:2%;"  >
 		<input type="submit" id="submitbtn"name="Edit" value="Change Password" disabled='disabled' style="width: 170px;height:46px; color:#fff;  background-color:#f92d1f;font-weight:bold;font-size:18px;margin-left:2%; "/>
 	
   	</div>


<% 
  List<LoginDetails> data;
  LoginDetails login=null;
 String Username=null;
  String Status=null;
  String Password=null;
  
	 data=(List<LoginDetails>) request.getAttribute("tableData"); 
    %>
    
<table class="paleBlueRows" style="margin-left:31%;margin-top:3%;">
<thead>
<tr>
<th>Select</th>
<th>UserName</th>
<th>Status</th>
</tr>
</thead>

	<%
 	 try {
		 if(data!=null && data.size()!=0)
        {
			 Iterator<LoginDetails> it = data.iterator();
			while(it.hasNext()){
				login = it.next();
				Username=login.getUsername();
				Status=login.getStatus();
				Password=login.getPassword();
			%>

<tbody>
<tr>
<td><input type="checkbox" name="username"  id="terms" class="selectOne" value="<%=Username%>" /></td><td><%=Username%></td><td><%=Status%></td></tr>

 </tbody>
<% 	
				}
        }
	else{
	
		Logging.getLogger().info("CC GUI NO Table Data found");

	}
		      
			 	
 }catch (Exception e)
{
	ErrorLogger.getLogger().error("Exception in AdminDetails : ",e);

}
%>

</table> 
</form> 
<script>
   $(document).ready(function() {
	  $('#submitbtn').click(function() {
		 
		  alert("Are you sure change password!") 
		

	  });
	  });
  $(document).ready(function() {
	  $('#submitbtn1').click(function() {
		 
		  alert("Are you sure you want to disable this user") 
		

	  });
	  });
  </script>
  
 </body>
</html>