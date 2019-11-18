var reg_phone = /^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\d{8}$/;
var reg_email = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
var reg_username = /^[\u4e00-\u9fa5_a-zA-Z0-9]{2,20}$/;
var reg_password = /^[a-zA-Z0-9]{6,20}$/;
var email = false;
var phone = false;
var username =  false;
var pwd = false;
var pwd_conf = false;


$(document).ready(function(e) {
	var width = window.innerWidth;
	var height = window.innerHeight;
	var scale = 0.6228;
	$("#c1").click(function(){
		$("body").css("background-color",$(this).css("background-color"));
		});
	$("#c2").click(function(){
		$("body").css("background-color",$(this).css("background-color"));
		});
	$("#c3").click(function(){
		$("body").css("background-color",$(this).css("background-color"));
		});
	$("#c4").click(function(){
		$("body").css("background-color",$(this).css("background-color"));
		});
	$("#c5").click(function(){
		$("body").css("background-color",$(this).css("background-color"));
		});
	$("#main").css("width",scale*width+"px").css("height","auto").css("border","1px dashed #CCC");
	$("#top").css("width",scale*width+"px").css("height",89/659*height+"px");
	$("#mess").css("width",500/1366*width+"px").css("height",87/659*height+"px").css("line-height",87/659*height+"px").css("margin-left",140/1366*width+"px");
	$("#apart0").css("width",850/1366*width+"px").css("height",8/659*height+"px");
	$("#bg_color").css("width",250/1366*width+"px").css("height",25/659*height+"px");
	$("#select").css("width",50/1366*width+"px").css("height",23/659*height+"px").css("line-hight",23/659*height+"px");
	$("#c1").css("width",35/1366*width+"px").css("height",23/659*height+"px")
	$("#c2").css("width",35/1366*width+"px").css("height",23/659*height+"px");
	$("#c3").css("width",35/1366*width+"px").css("height",23/659*height+"px");
	$("#c4").css("width",35/1366*width+"px").css("height",23/659*height+"px");
	$("#c5").css("width",35/1366*width+"px").css("height",23/659*height+"px");
	$("#apart1").css("width",850/1366*width+"px").css("height",65/659*height+"px");
	$("#content").css("width",850/1366*width+"px").css("height",650/659*height+"px").css("border-left","1px dashed #CCC").css("border-right","1px dashed #CCC");
	$("#fill_in").css("width",700/1366*width+"px").css("height",540/659*height+"px");
	$("#register").css("width",300/1366*width+"px").css("height",40/659*height+"px").css("line-height",40/659*height+"px").css("margin-left",170/1366*width+"px");
	$("#login_").css("width",300/1366*width+"px").css("height",40/659*height+"px").css("line-height",40/659*height+"px").css("margin-left",170/1366*width+"px");
	$("#footer").css("width",850/1366*width+"px").css("height",80/659*height+"px").css("line-height",80/659*height+"px").css("padding-left",233/1366*width+"px");
	$("#audio").css("width",300/1366*width+"px").css("height",60/659*height+"px").css("bottom",50/659*height+"px").css("right",3/1366*width+"px");
	$("#FlashID").css("width",850/1366*width+"px").css("height",850/659*height+"px");
	$("#fish02").css("width",850/1366*width+"px").css("height",850/659*height+"px");
	

	
	
	
	
		
	$("#Phone").blur(function(){
		$("#phoneSpan").html("");
	if(reg_phone.test($(this).val())){
		$("#phoneSpan").css("color","#0C3");
		$("#phoneSpan").html(" OK");
		phone = true;
		}else{
			$("#phoneSpan").css("color","#F00");
			$("#phoneSpan").html("手机号码无效");
			phone = false;
			}
	});
	
	$("#email").blur(function(){
		$("#emailSpan").html("");
	if(reg_email.test($(this).val())){
	     $("#emailSpan").css("color","#0C3");
		$("#emailSpan").html(" OK");
		email = true;
		}else{
			$("#emailSpan").css("color","#F00");
			$("#emailSpan").html("邮箱无效");
			email = false;
			}
	});
	
	
	
	$("#username").blur(function(){
		$("#usernameSpan").html("");
		if(reg_username.test($(this).val())){
			$("#usernameSpan").css("color","#0C3");
			$("#usernameSpan").html(" OK");
			username = true;
			}
			else{
			$("#usernameSpan").css("color","#F00");
			$("#usernameSpan").html("用户名长度为2-20个字符");
			username = false;
			}
	});
	
	
	
	$("#password").blur(function(){
		$("#passwordSpan").html("");
		if($(this).val()==''){
			$("#passwordSpan").css("color","#F00");
			$("#passwordSpan").html("密码不能为空");
			pwd = false;
			}
		else if(reg_password.test($(this).val())){
			$("#passwordSpan").css("color","#0C3");
			$("#passwordSpan").html(" OK");
			pwd = true;
			}
			
			else{
				$("#passwordSpan").css("color","#F00");
			    $("#passwordSpan").html("密码长度为6-20位数组字母组合字符");
				}
	});
	
	
	
	$("#passwordConfirm").blur(function(){
		$("#passwordConfirmSpan").html("");
		if($(this).val()==''){
			$("#passwordConfirmSpan").css("color","#F00");
		    $("#passwordConfirmSpan").html("校验密码不能为空");
		    pwd_conf = false;
		    }
	   else if($(this).val()==$("#password").val()){
		    $("#passwordConfirmSpan").css("color","#0C3");
		    $("#passwordConfirmSpan").html(" OK");
		    pwd_conf = true;
		   }else{
			    $("#passwordConfirmSpan").css("color","#F00");
		        $("#passwordConfirmSpan").html("两次输入的密码不一致");
		        pwd_conf = false;
			   }
	});
	


		$("#regist").click(function(){
			if(reg_phone.test($("#Phone").val())){
				$("#phoneSpan").css("color","#0C3");
				$("#phoneSpan").html(" OK");
				phone = true;
				}else{
					$("#phoneSpan").css("color","#F00");
					$("#phoneSpan").html("手机号码无效");
					phone = false;
					}
			
			
			if(reg_email.test($("#email").val())){
			     $("#emailSpan").css("color","#0C3");
				$("#emailSpan").html(" OK");
				email = true;
				}else{
					$("#emailSpan").css("color","#F00");
					$("#emailSpan").html("邮箱无效");
					email = false;
					}
			
			
			if(reg_username.test($("#username").val())){
				$("#usernameSpan").css("color","#0C3");
				$("#usernameSpan").html(" OK");
				username = true;
				}
				else{
				$("#usernameSpan").css("color","#F00");
				$("#usernameSpan").html("用户名长度为6-20位非中文字符");
				username = false;
				}
			
			
			if($("#password").val()==''){
				$("#passwordSpan").css("color","#F00");
				$("#passwordSpan").html("密码不能为空");
				pwd = false;
				}
			else if(reg_password.test($("#password").val())){
				$("#passwordSpan").css("color","#0C3");
				$("#passwordSpan").html(" OK");
				pwd = true;
				}
				
				else{
					$("#passwordSpan").css("color","#F00");
				    $("#passwordSpan").html("密码长度为6-20位数组字母组合字符");
					}
			
			
			if($("#passwordConfirm").val()==''){
				$("#passwordConfirmSpan").css("color","#F00");
			    $("#passwordConfirmSpan").html("校验密码不能为空");
			    pwd_conf = false;
			    }
		   else if($("#passwordConfirm").val()==$("#password").val()){
			    $("#passwordConfirmSpan").css("color","#0C3");
			    $("#passwordConfirmSpan").html(" OK");
			    pwd_conf = true;
			   }else{
				    $("#passwordConfirmSpan").css("color","#F00");
			        $("#passwordConfirmSpan").html("两次输入的密码不一致");
			        pwd_conf = false;
				   }
			
			if(email==true&&phone==true&&username==true&&pwd_conf==true){
				$("#register_form").submit();
			}
			
			
		})


});