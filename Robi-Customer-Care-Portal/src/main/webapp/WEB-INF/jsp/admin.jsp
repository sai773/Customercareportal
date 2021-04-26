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
        <img src="images/newashield.png" alt=""   width="160" height="80"  class="navbar-brandss">
    </div>
 	<div class="col-md-9"><div class="navbar-header">
    	  <h1 class="navbar-brand">Fraud Management Portal</h1>
 	</div></div>
 </div>
 
 
   
 <div id="login" >
    <div class="container" class="col-md-8" >
            <div id="login-row" class="row justify-content-center align-items-center" >
                <div id="login-column" class="col-md-6">
               
                    <div id="login-box" class="col-md-12">
                       <form name="loginForm" method="post" action="adminDetailsForm">
                           
                            <br>
                            
                            <div id="loginMsg" style="color: #FF0000;margin-left:22%">${errorMessage}</div>
                           
                            <div class="form-group">
                                <input type="text" name="username" id="username" placeholder="Username"  class="form-control short">
                           
                            </div>
                            <div class="form-group">
                                <input type="password" name="password" placeholder="Password" autocomplete="off" id="password"  class="form-control short">
                            </div>
                           
                            <div class="form-group">
                         		 <input type="submit" name="Login" style="margin-right:30px;margin-top:4%"  class="btn btn-primary btn-lg text-white float-right gradient" value="Login" >
                            </div>
                           
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script>
if ( window.history.replaceState ) {
  window.history.replaceState( null," Robi", "/RobiCC/admin" );
}
</script>
</body>
</html>