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
  <script type='text/javascript' src='mootools-core-1.4.5-nocompat.js'></script>
    
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
     		"EZY_Merchant_Code__c":$("#mcode").val(),
     		"email":$("#email").val(),
     		"EZY_CATEGORY__c":$("#category").val(),
         	"EZY_SUB_CATEGORY__c":$("#sub_category").val(),
         	"EZY_DETAILS__c":$("#details").val(),
         	"Subject":$("#subject").val(),
         	"Description":$("#desc").val(),
         	"attach_body":$("#attachbody").val(),		// convert image to base64string 
         	"attach_name":$("#attachname").val(),
         	"attach_body1":$("#attachbody1").val(),		// convert image to base64string 
         	"attach_name1":$("#attachname1").val(),
         	"attach_body2":$("#attachbody2").val(),		// convert image to base64string 
         	"attach_name2":$("#attachname2").val()
     	});
     	var jsonobj=JSON.stringify(datas);
     	document.getElementById('base64textarea').value=jsonobj;
		$http({method: 'post', url: "addcase", params : null, data:jsonobj,  cache: false}).
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
	 }
});
    

</script> 

<script type='text/javascript'>//<![CDATA[ 
window.addEvent('load', function() {
var handleFileSelect = function(evt) {
    var files = evt.target.files;
    var file = files[0];

    if (files && file) {
        var reader = new FileReader();
		document.getElementById("attachname").value=file.name;
        reader.onload = function(readerEvt) {
            var binaryString = readerEvt.target.result;
            document.getElementById("attachbody").value = btoa(binaryString);
        };

        reader.readAsBinaryString(file);
    }
};
var handleFileSelect1 = function(evt) {
    var files = evt.target.files;
    var file = files[0];

    if (files && file) {
        var reader = new FileReader();
		document.getElementById("attachname1").value=file.name;
        reader.onload = function(readerEvt) {
            var binaryString = readerEvt.target.result;
            document.getElementById("attachbody1").value = btoa(binaryString);
        };

        reader.readAsBinaryString(file);
    }
};
var handleFileSelect2 = function(evt) {
    var files = evt.target.files;
    var file = files[0];

    if (files && file) {
        var reader = new FileReader();
		document.getElementById("attachname2").value=file.name;
        reader.onload = function(readerEvt) {
            var binaryString = readerEvt.target.result;
            document.getElementById("attachbody2").value = btoa(binaryString);
        };

        reader.readAsBinaryString(file);
    }
};
if (window.File && window.FileReader && window.FileList && window.Blob) {
    document.getElementById('filePicker').addEventListener('change', handleFileSelect, false);
    document.getElementById('filePicker2').addEventListener('change', handleFileSelect1, false);
    document.getElementById('filePicker3').addEventListener('change', handleFileSelect2, false);
	
} else {
    alert('The File APIs are not fully supported in this browser.');
}
});//]]>  

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
<script language="javascript">
function showSubcate(str)
{ 
	var a ="";
	a +="<option value=\"0\">-----Select Sub Category-----</option>";
	if (str == 'ACCOUNT RELATED')
	{
		a +="<option value=\"Sign up\">Sign up</option>";
		a +="<option value=\"Login\">Login</option>";
		a +="<option value=\"Change of account details\">Change of account details </option>";
		a +="<option value=\"Others Account\">Others Account</option>";
	}
	else if (str == 'PRODUCT RELATED')
	{
		a += "<option value=\"Create Product\">Create Product</option>";
		a += "<option value=\"Upload Products\">Upload Products</option>";
		a += "<option value=\"Edit Product\">Edit Product</option>";
  		a += "<option value=\"Size & Weight\">Size & Weight</option>";
  		a +="<option value=\"Others Product\">Others Product</option>";
  	}
	else if (str == 'SALES CHANNEL RELATED')
	{
		a +="<option value=\"Connections\">Connections</option>";
  		a +="<option value=\"Orders\">Orders</option>";
  		a +="<option value=\"Others Sales Channel\">Others Sales Channel</option>";
	}
	else if (str == 'INBOUND SHIPMENT RELATED')
		{
		a +="<option value=\"Create Inbound Shipment\">Create Inbound Shipment</option>";
  		a +="<option value=\"Upload Shipment\">Upload Shipment</option>";
  		a +="<option value=\"Edit Shipment\">Edit Shipment</option>";
  		a +="<option value=\"Inbound Shipment Receipt\">Inbound Shipment Receipt</option>";
  		a +="<option value=\"Cancel Shipment\">Cancel Shipment</option>";
  		a +="<option value=\"Others Inbound\">Others Inbound</option>";
  	}
	else if (str == 'ORDER RELATED')
		{
		a +="<option value=\"Create order\">Create order</option>";
  		a +="<option value=\"Edit Order\">Edit Order</option>";
  		a +="<option value=\"Cancel Order\">Cancel Order</option>";
  		a +="<option value=\"End customer - Product issues\">End customer - Product issues</option>";
  		a +="<option value=\"End customer - Delivery Issues\">End customer - Delivery Issues</option>";
  		a +="<option value=\"Order Returns\">Order Returns</option>";
  		a +="<option value=\"Others Order\">Others Order</option>";
  		}
	else if (str == 'INVOICING RELATED')
		{
		a +="<option value=\"Charges\">Charges</option>";
  		a +="<option value=\"Payments\">Payments</option>";
  		a +="<option value=\"Others Invoicing\">Others Invoicing</option>";
  		}	
	else if (str == 'PLATFORM RELATED')
		{
		a +="<option value=\"Data mismatch between virtual and physical\">Data mismatch between virtual and physical</option>";
  		a +="<option value=\"Dashboard\">Dashboard</option>";
  		a +="<option value=\"Reports\">Reports</option>";
  		a +="<option value=\"Emails & Notifications\">Emails & Notifications</option>";
		a +="<option value=\"Others Platform\">Others Platform</option>";
		}
	else if (str == 'OTHERS')
		{
		a +="<option value=\"Suggestion\">Suggestion</option>";
  		a +="<option value=\"Feedback\">Feedback</option>";
		a +="<option value=\"Promotions\">Promotions</option>";
		a +="<option value=\"Others\">Others</option>";
		}
	else if (str == 'WAREHOUSE')
		{
		a +="<option value=\"Warehouse Shipment Receipt\">Warehouse Shipment Receipt</option>";
		a +="<option value=\"Stored goods\">Stored goods</option>";
		a +="<option value=\"ASN\">ASN</option>";
		a +="<option value=\"Order - Pre wave\">Order - Pre wave</option>";
		a +="<option value=\"Order - Post wave\">Order - Post wave</option>";
		a +="<option value=\"Warehouse Returns\">Warehouse Returns</option>";
		a +="<option value=\"Inventory Removal\">Inventory Removal</option>";
		a +="<option value=\"Returned Items (unsucessful orders)\">Returned Items (unsucessful orders)</option>";
		a +="<option value=\"Others Warehouse\">Others Warehouse</option>";
	   }
   else if (str == 'DELIVERY RELATED')
		{
		a +="<option value=\"Order\">Order</option>";
		a +="<option value=\"Others Delivery\">Others Delivery</option>";
	   }
   else if (str == 'TECHNOLOGY RELATED')
		{
		a +="<option value=\"Marketing website\">Marketing website</option>";
		a +="<option value=\"Warehousing IT\">Warehousing IT</option>";
		a +="<option value=\"Platform IT\">Platform IT</option>";
		a +="<option value=\"Delivery IT\">Delivery IT</option>";
		a +="<option value=\"Others Technology\">Others Technology</option>";
	   }
   else if (str == 'FINANCE RELATED')
		{
		a +="<option value=\"Payment\">Payment</option>";
		a +="<option value=\"Others Finance\">Others Finance</option>";
	   }
   
	document.getElementById("sub_category").innerHTML=a;
	}
	
function showDetails(str)
{ 
	var a ="";
	a +="<option value=\"0\">-----Select Details-----</option>";
	if (str == 'Sign up')
	{
		
		a +="<option value=\"Unable to signup\">Unable to signup</option>";
		a +="<option value=\"Email address is not accepted\">Email address is not accepted</option>";
		a +="<option value=\"Have not received verification email\">Have not received verification email</option>";
		a +="<option value=\"Unable to login to my account after email verification (implies manager has not activated the account)\">Unable to login to my account after email verification (implies manager has not activated the account)</option>";
		a +="<option value=\"Others\">Others</option>";
	}
	else if (str == 'Login')
	{
		a += "<option value=\"Lost my username\">Lost my username</option>";
		a += "<option value=\"Lost my password\">Lost my password</option>";
		a += "<option value=\"Unable to login\">Unable to login</option>";
		a +="<option value=\"Others\">Others</option>";
  	}
	else if (str == 'Change of account details')
	{
		a +="<option value=\"Unable to change contact information\">Unable to change contact information</option>";
		a +="<option value=\"Others\">Others</option>";
	}
	else if (str == 'Others Account')
		{
		a +="<option value=\"Need help with account\">Need help with account</option>";
  		a +="<option value=\"Unable to set preferences\">Unable to set preferences</option>";
		a +="<option value=\"Others\">Others</option>";
  		}
	else if (str == 'Upload Products' || str=='Upload Shipment')
		{
		a +="<option value=\"Unable to download template\">Unable to download template</option>";
  		a +="<option value=\"Unable to upload the file\">Unable to upload the file</option>";
  		a +="<option value=\"Errors in file\">Errors in file</option>";
		a +="<option value=\"Others\">Others</option>";
  		}
	else if (str == 'Connections')
		{
		a +="<option value=\"Unable to connect to a sales channel\">Unable to connect to a sales channel</option>";
		a +="<option value=\"Others\">Others</option>";
  		}
	else if (str == 'Orders')
		{
		a +="<option value=\"Orders are not imported to EZC\">Orders are not imported to EZC</option>";
  		a +="<option value=\"Order status not updated to sales channel\">Order status not updated to sales channel</option>";
		a +="<option value=\"Mismatch in order attribute for imported orders\">Mismatch in order attribute for imported orders</option>";
		a +="<option value=\"Others\">Others</option>";
		}
	else if (str == 'Inbound Shipment Receipt')
		{
		a +="<option value=\"Damaged Products\">Damaged Products</option>";
		a +="<option value=\"Missing Products\">Missing Products</option>";
		a +="<option value=\"Mismatch in inventory\">Mismatch in inventory</option>";
		a +="<option value=\"Labelling\">Labelling</option>";
		a +="<option value=\"Others\">Others</option>";
	   }
   else if (str == 'Cancel Shipment')
		{
		a +="<option value=\"Unable to cancel shipment\">Unable to cancel shipment</option>";
		a +="<option value=\"Others\">Others</option>";
	   }
   else if (str == 'Create order')
		{
		a +="<option value=\"Unable to create/approve an order\">Unable to create/approve an order</option>";
		a +="<option value=\"Others\">Others</option>";
		}
   else if (str == 'Edit Order')
		{
		a +="<option value=\"Unable to edit order\">Unable to edit order</option>";
		a +="<option value=\"Edit Order after approval\">Edit Order after approval</option>";
		a +="<option value=\"Others\">Others</option>";
	   }
   else if (str == 'End customer - Product issues')
		{
		a +="<option value=\"Order not received\">Order not received</option>";
		a +="<option value=\"Order packaging tampered\">Order packaging tampered</option>";
		a +="<option value=\"Items damaged\">Items damaged</option>";
		a +="<option value=\"Item quantity discrpancy\">Item quantity discrpancy</option>";
		a +="<option value=\"Wrong item\">Wrong item</option>";
		a +="<option value=\"Packaging not correct\">Packaging not correct</option>";
		a +="<option value=\"Others\">Others</option>";
	   }
	else if (str == 'End customer - Delivery Issues')
		{
		a +="<option value=\"Customer has not received the order\">Customer has not received the order</option>";
		a +="<option value=\"Unable to track an order\">Unable to track an order</option>";
		a +="<option value=\"Delivery time has exceeded the SLA\">Delivery time has exceeded the SLA</option>";
		a +="<option value=\"Packing slip\">Packing slip</option>";
		a +="<option value=\"Others\">Others</option>";
	   } 
	else if (str == 'Order Returns')
		{
		a +="<option value=\"Unable to initiate return\">Unable to initiate return</option>";
  		a +="<option value=\"Unable to download the label\">Unable to download the label</option>";
		a +="<option value=\"Others\">Others</option>";
  		} 
	else if (str == 'Charges')
		{
		a +="<option value=\"Storage\">Storage</option>";
		a +="<option value=\"Handling\">Handling</option>";
		a +="<option value=\"Delivery\">Delivery</option>";
		a +="<option value=\"Adjustments\">Adjustments</option>";
		a +="<option value=\"Others\">Others</option>";
	   }
	else if (str == 'Payments')
		{
		a +="<option value=\"Discrepancies in payment\">Discrepancies in payment</option>";
		a +="<option value=\"Payment not reflected\">Payment not reflected</option>";
		a +="<option value=\"Bank related issues\">Bank related issues</option>";
		a +="<option value=\"Others\">Others</option>";
	   } 
	else if (str == 'Others Invoicing')
		{
		a +="<option value=\"Unable to initiate return\">Unable to download monthly statement</option>";
		a +="<option value=\"Others\">Others</option>";
  		}
	else if (str == 'Warehouse Shipment Receipt')
		{
		a +="<option value=\"No ASN against received shipment\">No ASN against received shipment</option>";
		a +="<option value=\"Carton quantity mismatch, Carton not sealed, Carton damaged\">Carton quantity mismatch, Carton not sealed, Carton damaged</option>";
		a +="<option value=\"Under-receipt\">Under-receipt</option>";
		a +="<option value=\"Over-receipt\">Over-receipt</option>";
		a +="<option value=\"Over-sized / Over-weight products\">Over-sized / Over-weight products</option>";
		a +="<option value=\"Damaged Products\">Damaged Products</option>";
		a +="<option value=\"Unidentified Products (No labels/barcodes, Non-readable barcodes)\">Unidentified Products (No labels/barcodes, Non-readable barcodes)</option>";
		a +="<option value=\"SKU not in ASN\">SKU not in ASN</option>";
		a +="<option value=\"Others\">Others</option>";
	   }
	else if (str == 'Stored goods')
		{
		a +="<option value=\"Products damaged while  in storage\">Products damaged while  in storage</option>";
		a +="<option value=\"Missing Products\">Missing Products</option>";
		a +="<option value=\"Expired stock (through physical inspection)\">Expired stock (through physical inspection)</option>";
		a +="<option value=\"Others\">Others</option>";
	   } 
	else if (str == 'ASN')
		{
		a +="<option value=\"Unable to initiate return\">Cancel ASN</option>";
		a +="<option value=\"Others\">Others</option>";
  		}  
	else if (str == 'Order - Pre wave' || str=='Order - Post wave' || str=='Inventory Removal')
		{
		a +="<option value=\"Products missing / damaged\">Products missing / damaged</option>";
		a +="<option value=\"Others\">Others</option>";
  		}	
	else if (str == 'Warehouse Returns')
		{
		a +="<option value=\"Mismatch between expected and received\">Mismatch between expected and received</option>";
		a +="<option value=\"Product category not accepted for returns\">Product category not accepted for returns</option>";
		a +="<option value=\"Product unfulfillable condition\">Product unfulfillable condition</option>";
		a +="<option value=\"Others\">Others</option>";
	   }
	else if (str == 'Returned Items (unsucessful orders)')
		{
		a +="<option value=\"End Customer has refused delivery and returned to warehouse\">End Customer has refused delivery and returned to warehouse</option>";
		a +="<option value=\"Products damaged in transit and returned to warehouse\">Products damaged in transit and returned to warehouse</option>";
		a +="<option value=\"Others\">Others</option>";
	   } 
	else if (str == 'Order')
		{
		a +="<option value=\"End Customer has refused delivery\">End Customer has refused delivery</option>";
		a +="<option value=\"Unsucessful delivery\">Unsucessful delivery</option>";
		a +="<option value=\"Products held at customs\">Products held at customs</option>";
		a +="<option value=\"Unable to deliver, returned to warehouse\">Unable to deliver, returned to warehouse</option>";
		a +="<option value=\"Others\">Others</option>";
	   }   
	 else if (str == 'Marketing website')
		{
		a +="<option value=\"Website not loading\">Website not loading</option>";
		a +="<option value=\"Others\">Others</option>";
	   } 
	else if (str == 'Payment')
		{
		a +="<option value=\"Unable to add payments\">Unable to add payments</option>";
		a +="<option value=\"Others\">Others</option>";
	   } 	   
	document.getElementById("details").innerHTML=a;
	}
	

</script>
</head>
<body>
<div align="center" style="margin-top: 50px;" ng-app="caseapp" ng-controller="casecontroller"> 
    
        <form action="testservlet" method="post" enctype="multipart/form-data">
        
        	<table width="100%" align="center" ><tr>
<td width="10%"></td>
<td width="45%">
  <table align="left">
  <tr>
        <td colspan="2"><ul><li style="display:inline"><a href="contact.jsp">Contact</a>&nbsp;&nbsp;&nbsp;</li><li style="display:inline"><a href="ezyCommerce.html">Account</a></li></ul></td>
      </tr>
  <tr>
        <td width="100"></td>
        <td height="60"></td>
      </tr>
      <tr>
        <td width="100">
          Merchant Code</td>
        <td><input type="text" size="25" maxlength="15" name="mcode" id="mcode" value="" style="min-width:370px" class="form-control"></td>
      </tr>
       <tr>
        <td>Email</td>
        <td><input type="text" size="25" maxlength="50" id="email" name="email" value="" style="min-width:370px" class="form-control"></td>
      </tr>
	  <tr>
	  
      <!-- <tr>
        <td width="100">
          Name</td>
        <td><input type="text" size="25" maxlength="15" id="name" name="name" value="" style="min-width:370px" class="form-control"></td>
      </tr>
     
        <td>Phone</td>
        <td><input type="text" size="25" maxlength="50" id="phone" name="phone" value="" style="min-width:370px" class="form-control"></td>
      </tr> -->
      <tr>
        <td>Category</td>
		<td>
		  <select name="t6" style="min-width:370px" id="category" onclick="showSubcate(this.value)" class="form-control">
            <option value="0"> - Select Category - </option>
		    <option value="ACCOUNT RELATED">ACCOUNT RELATED</option>
		    <option value="PRODUCT RELATED">PRODUCT RELATED</option>
			<option value="SALES CHANNEL RELATED">SALES CHANNEL RELATED</option>
		    <option value="INBOUND SHIPMENT RELATED">INBOUND SHIPMENT RELATED</option>
		    <option value="ORDER RELATED">ORDER RELATED</option>
		    <option value="INVOICING RELATED">INVOICING RELATED</option>
		    <option value="PLATFORM RELATED">PLATFORM RELATED</option>
		    <option value="OTHERS">OTHERS</option>
		    <option value="WAREHOUSE">WAREHOUSE</option>
			<option value="DELIVERY RELATED">DELIVERY RELATED</option>
			<option value="TECHNOLOGY RELATED">TECHNOLOGY RELATED</option>
			<option value="FINANCE RELATED">FINANCE RELATED</option>
		    </select>
		</td>
      </tr>
      <tr>
	  <td>Sub Category</td>
        <td> <select name="sub_category" id="sub_category" style="min-width:370px" onclick="showDetails(this.value)" class="form-control">
            <option value="0"> - Select Sub Category - </option>
		    </select></td>
        </tr>
		<tr>
	  <td>Details</td>
        <td> <select  name="details" id="details" style="min-width:370px" class="form-control">
            <option value="0"> - Select Details - </option>
		    </select></td>
        </tr>
		<tr>
	    <td>Subject</td>
        <td><input type="text" size="25" maxlength="50" name="subject" id="subject" value="" style="min-width:370px" class="form-control"></td>
        </tr>
		<tr>
		<td>Description</td>
        <td><textarea rows="4" cols="50" style="height:80px" name="description" id="desc" value="" class="form-control"></textarea></td>
        </tr>
        <tr>
		<td>Attachment1</td>
        <td><input type="file" id="filePicker"></td>
        </tr>
        <tr>
		<td>Attachment2</td>
        <td><input type="file" id="filePicker2"></td>
        </tr>
        <tr>
		<td>Attachment3</td>
        <td><input type="file" id="filePicker3"></td>
        </tr>
		<tr>
		<td></td>
        <td height="20"></td>
        </tr>
		<tr>
		<td></td>
        <td align="center"><input type="button" id="getData" value="Save" ng-click="signup()" class="form-control"/>  
            <input type="reset" name="save2" value="Cancel" class="form-control"></td>
        </tr>
    </table>
  </td>
  <td width="45%"><div id="output" style="width:300px;height:400px;">
  <div>
        <h1>Output Json String</h1>
        <textarea id="base64textarea" cols="60" rows="25" class="form-control" style="height:400px"></textarea>
        
        <input type="hidden" name="attachname" id="attachname">
        <input type="hidden" name="attachbody" id="attachbody">
        <input type="hidden" name="attachname1" id="attachname1">
        <input type="hidden" name="attachbody1" id="attachbody1">
        <input type="hidden" name="attachname2" id="attachname2">
        <input type="hidden" name="attachbody2" id="attachbody2">
    </div>
  </div></td>
  </tr>
  </table>

        	
        	
        </form>
 
    </div>
</body>
</html>