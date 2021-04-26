<!DOCTYPE html>
<html> 
<head>
<meta http-equiv="Content-Security-Policy" content="default-src *; script-src * 'unsafe-inline' 'unsafe-eval' ;style-src * 'unsafe-inline';
">
 <meta http-equiv="X-Frame-Options" content="deny">

<meta charset="utf-8">
<title>Robi</title>
<!-- <link rel="shortcut icon" href="images/favicon.ico" type="image/x-icon">  -->
<link rel="shortcut icon" href="images/favicon.ico">

<link rel="stylesheet" type="text/css" href="css/newuser.css" />
<script src="js/jquery-3.5.1.min.js"></script>
<script src="js/jquery.validate.min.js"></script>
<script src="js/disableFunctionalKeys.js"></script> 
<link href="css/bootstrap.min.css" rel="stylesheet" >
<link href="css/bootstrap.min.css.map" >

<script src="js/bootstrap.min.js"></script>
<!------ Include the above in your HEAD tag ---------->
</head>

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

<body oncontextmenu="return false;">

 
 <div class="row">
 	<div class="col-md-10">
         <img src="images/Robi_Axiata_logo.png" alt=""   width="140" height="140"  class="navbar-brand">
    </div>
        
 	<div class="col-md-2">
         <img src="images/airtel.png" alt=""  width="100" height="100"   class=" navbar-brands">
     </div>
 </div>
 
 <div class="row" style="margin-left:25%; border: 2px solid gray;margin-top:-7%;margin-bottom:10px;margin-right:25%">
 	<div class="col-md-3">
        <img src="images/newashield.png" alt="" style="margin-left:6%"  width="160" height="80"  class="navbar-brandss">
    </div>
 	<div class="col-md-9"><div class="navbar-header">
      <h1 class="navbar-brand" style="margin-left:-2%">Fraud Management Portal</h1>
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

	 <form name="BackForm" method="post" action="adminBackBtn">
		<label style="float:center; margin-left: 25%;" class="log">
		<input type="submit" name ="btnname" value="Back" style="width: 150px;height:46px; color:#fff;  background-color:#f92d1f;font-weight:bold;font-size:16px" onclick="return BackForm()">

		</label>
	</form>
	</div>
	</div>
 
 <%
 String userName = (String) request.getParameter("username");
 %>
   
   <div id="login" >
    	 <div class="container" class="col-md-8" >
            <div id="login-row" class="row justify-content-center align-items-center" >
                <div id="login-column" class="col-md-6">
               
                    <div id="login-box" class="col-md-12">
                       <form name="PassForm" method="post" action="adminPassForm">
                           
                            <br>
                            <h1 style="font-size:18px;font-weight:bold;margin-left:35%;">Change Password</h1>
                            <div id='message' style="color: #FF0000;margin-left:42%;margin-top:5px;"></div>
                       		 <div id="loginMsg" style="color: #FF0000;margin-left:30%">${errorMessage}</div>
                           
                            <div class="form-group">
                                <input type="text" name="username" id="username" autocomplete="off" value="<%=userName%>"  class="form-control short" readonly>
                            </div>
                            
                            <div class="form-group">
                                <input type="password" name="password" placeholder="Password" autocomplete="off" id="password"  class="form-control short" />
                            </div>
                            
                             <div class="form-group">
                          	   <input type="password" name="conform_password" placeholder="Confirm Password"  autocomplete="off" id="confirm_password"  class="form-control short" />
                           </div>
                           
                           <script>
                        	 $('#password, #confirm_password,#enter').on('keyup', function () {
                        	  
                        	   if ($('#password').val() == $('#confirm_password').val()) {
                        		 
                        	     $('#message').html('Matching').css('color', 'green');
                        	    
                        	   } else 
                        	     $('#message').html('Not Matching').css('color', 'red');
							 });
                        	 
                           </script>
                           
                            <div class="form-group">
                            
                                 <input type="submit"  name="Add"  value="save" style="margin-right:30px;margin-top:3%"  class="btn btn-primary btn-lg text-white float-right gradient" value="Login" >
                            </div>
                          
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>

</body>
</html>