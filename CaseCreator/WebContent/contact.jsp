<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Salesforce Case</title>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script> 
   <script type="text/javascript" src="./ezyCommerce_files/bootstrap.min.js"></script>
    <script type="text/javascript" src="./ezyCommerce_files/angular.min.js"></script> 
<script type="text/javascript"> 
var app = angular.module('caseapp', []);

app.controller('casecontroller', function($scope, $http) {
	 $scope.signup = function () {
		 if($("#mcode").val()=="" || $("#email").val()=="")
 		{
 			alert("Merchant code and email cannot be left blank");
 			return;
 		}else{
		 var datas = ({
			"ActionType":"CONTACT", //MERCHANT, CONTACT, BOTH, CONTACT_DELETE
     		"EZY_Merchant_Code__c":$("#mcode").val(),
     		"email":$("#email").val(),
     		"FirstName":"",
        	"LastName":$("#lname").val(),
        	"Phone":$("#phone").val(),
        	"Country__c":$("#country").val(),
        	"Address_1__c":$("#add1").val(),
        	"City_Name__c":$("#city").val(),
        	"State__c":"",
        	"Postal_Code__c":$("#zipcode").val(),
        	"MobilePhone":"",
        	"EZY_Role_1__c":$("#role1").is(':checked'),
        	"EZY_Role_2__c":$("#role2").is(':checked'),
        	"EZY_Role_3__c":$("#role3").is(':checked')
     	});
     	var jsonobj=JSON.stringify(datas);
     	document.getElementById('base64textarea').value=jsonobj;
     	
		 $http({method: 'post', url: "addupdatemerchant", params : null, data:jsonobj,  cache: false}).
         success(function(data, status, headers, config) {
        	 
        	 if(data.isSuccess)
          	{
          		alert(data.message+"; return id:["+data.id+"]");
             }else{
             	alert(data.message);
             }
             }).
             error(function(data, status, headers, config) {
             	alert(data+" error "+status);
             });
	 	}
	 };
	 
	 $scope.deletecontact = function () {
		 if($("#mcode").val()=="" || $("#email").val()=="")
 		{
 			alert("Merchant code and email cannot be left blank");
 			return;
 		}else{
		 var datas = ({
			"ActionType":"CONTACT_DELETE", //MERCHANT, CONTACT, BOTH, CONTACT_DELETE
     		"EZY_Merchant_Code__c":$("#mcode").val(),
     		"email":$("#email").val()
     	});
     	var jsonobj=JSON.stringify(datas);
     	document.getElementById('base64textarea').value=jsonobj;
     	
		 $http({method: 'post', url: "addupdatemerchant", params : null, data:jsonobj,  cache: false}).
         success(function(data, status, headers, config) {
        	 
        	 if(data.isSuccess)
          	{
          		alert(data.message+"; return id:["+data.id+"]");
             }else{
             	alert(data.message);
             }
             }).
             error(function(data, status, headers, config) {
             	alert(data+" error "+status);
             });
	 	}
	 };
});
    
    
</script> 
<style>
    * {
      font-family: sans-serif;
	  font-size:12px;
    }
	.form-control{display:block;width:100%;height:28px;12px;font-size:14px;line-height:1.42857143;color:#555;background-color:#fff;background-image:none;border:1px solid #ccc;border-radius:4px;-webkit-box-shadow:inset 0 1px 1px rgba(0,0,0,.075);box-shadow:inset 0 1px 1px rgba(0,0,0,.075);-webkit-transition:border-color ease-in-out .15s,-webkit-box-shadow ease-in-out .15s;-o-transition:border-color ease-in-out .15s,box-shadow ease-in-out .15s;transition:border-color ease-in-out .15s,box-shadow ease-in-out .15s}.form-control:focus{border-color:#66afe9;outline:0;-webkit-box-shadow:inset 0 1px 1px rgba(0,0,0,.075),0 0 8px rgba(102,175,233,.6);box-shadow:inset 0 1px 1px rgba(0,0,0,.075),0 0 8px rgba(102,175,233,.6)}
	select.input-sm{height:30px;line-height:30px}textarea.input-sm,select[multiple].input-sm{height:auto}.input-lg,.form-horizontal .form-group-lg .form-control{height:20px;padding:10px 16px;font-size:18px;line-height:1.33;border-radius:6px}select.input-lg{height:46px;line-height:46px}textarea.input-lg,select[multiple].input-lg{height:auto}.has-feedback{position:relative}.form-control{display:inline-block;width:auto;vertical-align:middle}
	.input-group{display:inline-table;vertical-align:middle}.form-inline .input-group .input-group-addon,.form-inline .input-group .input-group-btn,.form-inline .input-group .form-control{width:auto}.form-inline .input-group>.form-control{width:100%}
	.btn-primary {
	background-color: #2b9db6;
	border: none;
	background-image: none;
	border-radius:6px;padding:10px;
	color:#ffffff;
	font-size:14px;
}
</style>
</head>
<body>
<div align="center" style="margin-top: 50px;" ng-app="caseapp" ng-controller="casecontroller"> 
    
        <form action="testservlet" method="post" enctype="multipart/form-data">
        	<table width="100%" align="center"><tr>
<td width="10%"></td>
<td width="45%">
  <table align="left">
  <tr>
        <td colspan="2"><ul><li style="display:inline"><a href="CreateCase.jsp">Case</a>&nbsp;&nbsp;&nbsp;</li><li style="display:inline"><a href="ezyCommerce.html">Account</a></li></ul></td>
      </tr>
  <tr>
        <td width="100"></td>
        <td height="60"></td>
      </tr>
      <tr>
        <td width="100">
          Role</td>
        <td><label><input type="Checkbox"  id="role1" name="role1" value="1">&nbsp;&nbsp;Role1</label>&nbsp;&nbsp;&nbsp;
        <label><input type="Checkbox"  id="role2" name="role2" value="1">&nbsp;&nbsp;Role2</label>&nbsp;&nbsp;&nbsp;
        <label><input type="Checkbox"  id="role3" name="role3" value="1">&nbsp;&nbsp;Role3</label></td>
      </tr>
      <tr>
        <td width="100">
          Merchant Code</td>
        <td><input type="text" class="form-control" size="25" maxlength="15" name="mcode" id="mcode" value="" style="min-width:370px"></td>
      </tr>
     <!--  <tr>
        <td width="100">
         First Name</td>
        <td><input type="text" class="form-control" size="25" maxlength="15" id="fname" name="fname" value="" style="min-width:370px"></td>
      </tr> -->
      <tr>
        <td width="100">
          Name</td>
        <td><input type="text" class="form-control" size="25" maxlength="15" id="lname" name="lname" value="" style="min-width:370px"></td>
      </tr>
      
      <tr>
        <td>Email</td>
        <td><input type="text" class="form-control" size="25" maxlength="50" id="email" name="email" value="" style="min-width:370px"></td>
      </tr>
	  <tr>
        <td>Phone</td>
        <td><input type="text" class="form-control" size="25" maxlength="50" id="phone" name="phone" value="" style="min-width:370px"></td>
      </tr>
      <tr>
        <td>Country</td>
		<td>
		  <input type="text" class="form-control" size="25" maxlength="50" id="country" name="country" value="" style="min-width:370px">
		</td>
      </tr>
		<tr>
	    <td>City</td>
        <td><input type="text" class="form-control" size="25" maxlength="50" name="city" id="city" value="" style="min-width:370px"></td>
        </tr>
        <tr>
	    <td>Zip Code</td>
        <td><input type="text" class="form-control" size="25" maxlength="50" name="zipcode" id="zipcode" value="" style="min-width:370px"></td>
        </tr>
		<tr>
		<td>Address 1</td>
        <td><textarea rows="4" class="form-control" cols="50" style="height:80px" name="add1" id="add1" value=""></textarea></td>
        </tr>
		<tr>
		<td></td>
        <td height="20"></td>
        </tr>
		<tr>
		<td></td>
        <td align="center"><input type="button" id="getData" value="Save" ng-click="signup()" class="form-control"/>  
            <input type="reset" name="save2" value="Cancel" class="form-control">
            
            <input type="button" value="Delete Contact" ng-click="deletecontact()" class="form-control"/> </td>
        </tr>
    </table>
  </td>
  <td width="45%"><div id="output" style="width:300px;height:400px;">
  <div>
        <h1>Output Json String</h1>
        <textarea id="base64textarea" cols="60" rows="25" class="form-control" style="height:400px"></textarea>
    </div>
  </div></td>
  </tr>
  </table>

        	
        	
        </form>
 
    </div>
</body>
</html>