function searchFromSubmit() {
		$("#dataSerachForm").validate({
		 	onfocusout : function(element) {
					$(element).valid();
				},
			    errorPlacement: function(error, element) {
		            if (element.attr("name") == "msisdn") {
		            	 $('#searchResult').css("display", "none");
		                $("#errorMsg").html(error);
		            }    
		            else {
		            	  error.insertAfter(element);
		            }
		        }, 
				rules : {
					msisdn : {
						    required : true,
						    minlength:11,
							remote : {
								url : "MSISDNValidate",
								type : "post",
								data : {
									msisdn : function() {
										$('#searchResult').css("display", "none");
										return $("#msisdn").val();
									}
								}
							}
						}
				},
				messages : {
						msisdn : {
							required :"Mobile number required",
							minlength:"Enter minimum 11 characters",
							remote : "No information available for this Mobile Number"
						}
				},
				submitHandler : function(form) {
					var proccessObj=$("#process");
					proccessObj.addClass("process");
					$.ajax({
							url :"RobiMsisdnDataReports",
							type : "post",
							async:true,
							cache: false,
							data : {
								msisdn : $('#msisdn').val(),
								
							},
							success : function(result) {
								$('body').css("display", "block");
								$('body').html(result);
							},complete:function(){
								proccessObj.removeClass("process");
							}
						});
				return false;
				}
			});
		}
