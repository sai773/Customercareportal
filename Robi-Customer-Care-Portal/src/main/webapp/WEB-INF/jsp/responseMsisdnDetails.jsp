<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*"%>
<%@ page import="com.ashield.helper.*"%>
<%@ page import="com.ashield.logs.*"%>
<%@ page import="com.ashield.pojoClasses.*"%>
<%@ page import="com.ashield.RobiCustomerCarePortal.*"%>
<%@ page import="java.util.TimeZone.*" %>
<%@ page import="java.text.*" %>
<%@ page import="java.time.*" %>
<%@ page import ="java.time.ZoneId" %>
<%@ page import="java.time.ZonedDateTime" %>
<%@ page import="java.time.format.DateTimeFormatter"%>




<html>
<head>
<meta http-equiv="Content-Security-Policy" content="default-src *; script-src * 'unsafe-inline' 'unsafe-eval' ;style-src * 'unsafe-inline';
">
<meta name="viewport" content="width=device-width, initial-scale=1">


<title>Robi</title>
 <link rel="shortcut icon" href="images/favicon.ico">
<link rel="stylesheet" type="text/css" href="css/style.css" />
<script src="js/jquery-3.5.1.min.js"></script>
<script src="js/jquery.validate.min.js"></script>

<script src="js/msdropdown/jquery.dd.min.js"></script>
<link href="css/bootstrap.min.css" rel="stylesheet" >
<link href="css/bootstrap.min.css.map" >

<script src="js/bootstrap.min.js"></script>
 <script type="text/javascript">
$(document).ready(function(){
	 searchFromSubmit();
	 $("#countryname").msDropdown(); 
 });
 </script>
 <style>
.process{
	background-image: url('css/ajax-loader.gif');
	background-repeat: no-repeat;
	padding-left: 20px;
}"src/main/webapp/WEB-INF/jsp/responseMsisdnDetails.jsp"
 p{
  font-family: 'Open Sans', sans-serif;
  font-size: 12px;
  font-weight:bold;
 }

 <style>
#tablecontent {
	height: auto;
	overflow-y: auto;
	width: auto;
}
#searchtable {
	border-radius: 9px;
	color: black;
	text-align: center;
	margin-bottom: 11px;
	margin-left: 2px;
	margin-right: 2%;
}
#searchtable thead th {
	/* background-color: rgb(81, 130, 187); */
	background-color:#f92d1f;
	color: #fff;
	font-family: Arial;
	font-size: 11px;
	font-weight: 700;
	border-bottom-width: 0;
}
/* Heading and Column Style */
 #searchtable tr, #searchtable th {
	border-top-width: 2px;
	border-top-style: solid;
	border-width: 2px;
	border-style: solid;
	border-color: rgb(81, 130, 187);
	
} 

/* Padding and font style */
#searchtable td {
font-family: sans-serif;
font-size: 12px;
color: #000;
font-weight:400;
text-align: center;
height:25px;
}
#searchtable th{
	padding: 5px 10px;
	font-size: 11px;
	font-weight: 700;
	font-family: Arial;
	color: rgb(177, 106, 104); 
	
	
}
#searchtable tr:nth-child(even) {
	background: ; #FFF;
}
#searchtable tr:nth-child(odd) {
	background:#c6d1c5231242250;
	
}
b{
  font-family: 'Open Sans', sans-serif;
  font-size: 12px;
  font-weight:bold;
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
</head>
<body oncontextmenu="return false;">
<script>
function fromReset(){
	$('#searchResult').css("display", "none");
}
 function OnCountryChange(x) {
	var ddl = document.getElementById('countryname');
	var txt = document.getElementById('msisdn');
	var opr=document.getElementById('operatorType');
	if (ddl.value == "Select Country") {
	txt.value = '';
	txt.readOnly = true;
	}
	else {
	txt.value = x;
	txt.readOnly = false; 
	}
	if(x==""){
		$('#test').remove();
		$('#operatorType').append("<option value='Robi'>Robi-Telecom</option>");
		}
	}
function doCheck(e) {
	var keycode;
	var len;
	var msdlen;
	len=$('#countryname').val().length;
	msdlen=$('#msisdn').val().length;
	if(len==msdlen){
		if (e.which === 8) {
		e.preventDefault();
		}
	}
	} 

</script>

<div class="row">
 	<div class="col-md-10">
         <img src="images/Robi_Axiata_logo.png" alt=""   width="140" height="140"  class="navbar-brand">
     </div>
     <div class="col-md-2">
        <img src="images/airtel.png" alt=""  width="100" height="100" overflow: hidden;  class=" navbar-brands">
      </div>
 </div>
 
<div class="row" style="margin-left:25%;overflow: hidden; border: 2px solid gray;margin-top:-7%;margin-bottom:10px;margin-right:25%">
 	<div class="col-md-3">
         <img src="images/newashield.png" alt=""   width="160" height="90"  class="navbar-brandss">
    </div>
 	<div class="col-md-9"><div class="navbar-header">
     	 <h1 class="navbar-brand">Fraud Management Portal</h1>
 	</div></div>
 </div>
 
 <div class="row" style="margin-top:3%;">
 <div class="col-md-9">
 
	<label style=" margin-left:57%;color: black; font-size: 26px; font-weight:bold;" class="log">Welcome &nbsp<%=session.getAttribute("uname")%>
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

	<label style=" margin-left:26%;" class="log">
	<a href="login" style="text-decoration: none; color: #060613;"><button class="exit-btn" style="width: 150px;height:46px; color:#fff;  background-color:#f92d1f;font-weight:bold;font-size:16px" >Logout</button></a>
	</label>
	</div>
	</div>
<div id="errodiv" style="color:red;margin-top:2%; width: 45%; overflow: hidden; margin-left: 44.5%;margin-bottom:-40px"><label id="errorMsg"></label><br/></div>
			           <div id="loginMsg" style="color: #FF0000;margin-left:40%">${errorMessage}</div>
		

<div class="serachForm"  style=" margin-left:180px;margin-right: auto;margin-top:-3%;">
<form name="AddForm" method="post" action="RobiMsisdnDataReports">

	<table >
	<tr><td></td>&nbsp&nbsp&nbsp<td  style="color: #3D90F8;"></td></tr>
	<% String msisdn=(String)request.getAttribute("msisdn");
	String[] splitted = msisdn.split("88");

	%>
	<tr>
		<td><p style="font-size:30px;margin-top:2%;">Search By Phone Number &nbsp +88</p></td>			
	<td>
  	<input type="text"  placeholder="Enter your number" value=<%=splitted[1]%> autocomplete="off" style="width:320px;height:46px;border: 1px solid;" class="form-control" id="msisdn" name="msisdn" maxlength="11" required>
	<script>
	
	</script>
	<td>&nbsp&nbsp</td>
	
	<td><input type="submit" value="Search"
							style="width: 150px;height:46px;" onsubmit="return searchFromSubmit()" /></td>
		<td>&nbsp&nbsp</td>
		
		<!--td><input type="submit" name="clears" value="Clear"
							style="width: 150px;height:46px; " id="clears"  /></td-->
		
		<td><p class="exampletext">Ex:01816123456</p></td>
	</table>
	
 	<span id="process"  style="height: 50px;display: inherit;margin-left: 107px;margin-top: 10px; "></span>
 </form>
</div>


<%!
List<cdr_logs> data;
cdr_logs subPurReq = null;
String Status=null;
String purchaseId=null;
String ProductId=null;
String networktype=null;

String MSISDN=null;
String bua=null;
String deviceos=null;
String spname=null;
 Date reqtime=null;
String bua1=null;
String networktype1=null;

%>
	<%
	data=(List<cdr_logs>) request.getAttribute("tableData"); 
	
	%>


	<!-- <span id="process"  style="height: 50px;display: inherit;margin-left: 107px;margin-top: 10px;"></span> -->
	<div id="tablecontent" style="margin-bottom:25px;">
	<fieldset style="bordseer-radius: 10px;border-color: rgb(81, 130, 187);">
			<table id="searchtable"  rules="all" style="color:#333333;width:99%;margin-left:10px; margin-bottom:1%;" border="1" cellpadding="1" cellspacing="1" style="">
			<thead>
			<th><b>Purchase ID</b></th> 
			<th><b>Request Date Time</b></th> 
			
			<th><b>SP Name</b></th>
			<th><b>Product Name</b></th>
			<th><b>Network Type</b></th>
			
			<th><b>Browser</b></th>
			<th><b>Device OS</b></th>
			<th><b>Result Code</b></th>
			
			</thead>
			<%
			try {
				
			 if(data!=null && data.size()!=0)
	           {
				
	         Iterator<cdr_logs> it = data.iterator();
				while(it.hasNext()){
				subPurReq = it.next();
				
				purchaseId=subPurReq.getId();
				ProductId=subPurReq.getProductname();
				spname=subPurReq.getSpname();
				MSISDN=subPurReq.getMsisdn();
				networktype=subPurReq.getNetworktype();
				networktype1=subPurReq.getNetworktype1();
				
				
				deviceos=subPurReq.getDeviceos();
				Status=subPurReq.getResultcode();
				bua1= subPurReq.getBua1();
				
				reqtime=subPurReq.getRequestTime();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		        Date gmt = new Date(sdf.format(reqtime));   
				if(Status.equalsIgnoreCase("0"))
					 Status="charge Success";
				else{
					 Status="Charge Unsuccesfull"; 
				}
			 %> 
		 	 <tr>
		        <td><%=purchaseId%></td> 
		        <td><%=gmt%></td> 
		        <td><%=spname%></td>
		        <td><%=ProductId%></td>
		   	 <%
		     if(networktype1!=null){
		     %> 
		    	 <td><%=networktype1%></td>
		      <%} 
		     else{
		    	 %>
		      	 <td><%=networktype%></td>
		       <% 
		     }
		    %>
		      <td><%=bua1%></td>
		      <td><%=deviceos%></td>
		      <td  id="status"><%=Status%></td>
		   </tr> 
		<% } %>
	<%}
		else{
	%>
			<tr>
				<td colspan='9' align="center" style="background-color: #fff"><p style="margin-top: 5px;font-family: initial;">No
						Records Found..</p></td>
			</tr>
			
			<%}

} catch (Exception e)
{
	ErrorLogger.getLogger().error("Exception in MsisdnDetails : ",e);
}
%>
</table>
</fieldset>
</div><div id="searchResult" style="margin-left: 1%;margin-right: 1%;margin-top:1%;"></div>


</body>
</html>