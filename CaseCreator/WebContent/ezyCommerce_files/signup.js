var app = angular.module('ezyCommerce', ['angularValidator', 'noCAPTCHA']);

app.controller('myController', ['$scope', '$rootScope', '$http', '$window','$timeout', function($scope, $rootScope, $http, $window,$timeout){

        $scope.scrollPageToTop = function () {
                $window.scroll(0,0);
        };
        $scope.back = function() {
                $scope.scrollPageToTop();
                if ($scope.currentStep > 1) $scope.currentStep--;
        };
    $scope.verifyEmail = function() {
        var data = {
            "email"             : $scope.model.email,
            "companyRegNumber"  : $scope.model.companyRegNumber
        };

        var url = "/merchants/validate";
        var isProceedNxtStep = true;
        var deferred = $.Deferred();
        $http({method: 'post', url: url, params : null, data: data,  cache: false}).
            success(function(data, status, headers, config) {
                if (data.success.length) {
                    deferred.resolve(data);
                     $scope.emailAvailableError=false;
                     $scope.emailAvailableSuccess = true;
                }
                else {
                    deferred.reject(data);
                   

                    if (data && data.errors[4107]) {
                        $scope.emailAvailableError=true;
                        $scope.emailAvailableSuccess = false;
                    }
                    
                }
            }).
            error(function(data, status, headers, config) {
                deferred.reject(data);
            });
        return deferred.promise();
    };

    $scope.checkRetypePasswordError = function(password,reTypePw){
      //  if($scope.model.retypePwd)
     
      if(typeof reTypePw=='undefined' || reTypePw=="" || typeof password=='undefined' || password=="" )
        {
            return false;
        }
        else
        {
          
                if(reTypePw.length <8)
                {
                     $scope.pwErrorMessage = $scope.validationMessages.invalidPasswordLength;
                        return true;
                }
                if (password != reTypePw) {
                        $scope.pwErrorMessage = $scope.validationMessages.pwmissmatch;
                        return true;
                }
               
        }    



    }

    $scope.checkEmailAvail = function()
    {
            $("#x,#y,#z,#p, .emailuserlbl label.has-error").remove();
            $("#accountSetupEmail").removeClass("has-error");
            
          // check by regex
            $scope.emailFormatError=false;
        
            var patt = new RegExp("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
            var res = patt.test($scope.model.email);
            if(res){
                 $scope.verifyEmail().done(function(data){
                       $scope.emailFormatError=false;
                    }).fail(function(data) {
                       $scope.emailFormatError=true;
                            if (data && data.errors[4107]) {
                                $('#accountSetupEmail').after('<label id="x" class="control-label has-error validationMessage">'+ $scope.validationMessages.emailAvailable +'</label>').closest('div').addClass('has-error');
                            
                            }
                            else if (data &&  data.errors[4041]) {
                                $('#accountSetupEmail').after('<label id="y" class="control-label has-error validationMessage">'+ data.errors[4041] +'</label>').closest('div').addClass('has-error');
                            
                                 
                            }
                            else if (data && data.errors[4096]) {
                                $('#account-setup-company-regno').after('<label id="z" class="control-label has-error validationMessage">'+ data.errors[4096] +'</label>').closest('div').addClass('has-error');
                            
                            }
                            
                        
                    });
        }
           else
           {
                $scope.emailFormatError=true;
                $('#accountSetupEmail').after('<label id="p" class="control-label has-error validationMessage"> Invalid Email Format </label>').closest('div').addClass('has-error');
            }  
          
        

    }


        $scope.next = function (){
        	
            $scope.scrollPageToTop();
        if ($scope.currentStep == 1) {

            /*$scope.emailAvailableError=false;
            $scope.emailAvailableSuccess=false;

            //verify email
            $scope.verifyEmail()
                .done(function(data){
                    
                    
                    $scope.currentStep++;
                    $scope.emailAvailableError=false;

                }).fail(function(data) {

                    
                    
                  //  $scope.showError(data);
                    if (data && data.errors[4107]) {
                        $scope.emailAvailableError=true;
                         $scope.emailAvailableSuccess=false;
                        $('#accountSetupEmail').focus().after('<label class="control-label has-error validationMessage">'+ $scope.validationMessages.emailAvailable +'</label>').closest('div').addClass('has-error');
                    }
                    else
                    {
                         $scope.emailAvailableError=false;
                         $scope.emailAvailableSuccess=true;
                    }
                    if (data &&  data.errors[4041]) {
                        $('#accountSetupEmail').focus().after('<label class="control-label has-error validationMessage">'+ data.errors[4041] +'</label>').closest('div').addClass('has-error');
                    }
                    if (data && data.errors[4096]) {
                        $('#account-setup-company-regno').focus().after('<label class="control-label has-error validationMessage">'+ data.errors[4096] +'</label>').closest('div').addClass('has-error');
                    }
                });*/
        	$scope.currentStep++;
        }
        else if ($scope.currentStep == 2)
             $scope.currentStep++;
        else if ($scope.currentStep == 3)
             $scope.isAgreed = false;

    };

        $scope.isPasswordMatch = function(password, reTypePw) {
        
                if (typeof reTypePw == 'undefined')
                {
                     $scope.pwErrorMessage = $scope.validationMessages.required;
                     return false;
                }
                if(reTypePw.length <8)
                {
                     $scope.pwErrorMessage = $scope.validationMessages.invalidPasswordLength;
                        return false;
                }
                if (password != reTypePw) {
                        $scope.pwErrorMessage = $scope.validationMessages.pwmissmatch;
                        return false;
                }
                return true;
        };

        $scope.isYearFormat = function(compFoundedYear)
        {
            if(compFoundedYear.length==0)
                return true;
            if(compFoundedYear.length <4){
                    $scope.yearErrorMessage = $scope.validationMessages.invalidYearLength;
                    return false;
            } 
            var patt = new RegExp("^[0-9]*$");
            var res = patt.test(compFoundedYear);
            if(!res){$scope.yearErrorMessage = $scope.validationMessages.invalidnumber;
                return false;
            }
            return true;
        }

        $scope.isOnChanFormat = function(onlineChannel)
        {
            if(onlineChannel.length==0)
                return true;
            var patt = new RegExp("^[0-9]*$");
            var res = patt.test(onlineChannel);
            if(!res){$scope.onChannelErrorMessage = $scope.validationMessages.invalidnumber;
                return false;
            }
            if(onlineChannel>100){
              $scope.onChannelErrorMessage = $scope.validationMessages.invalidOnlineChannelsMax;
                return false  
            }
            return true;

        }

         $scope.isOverseasCountriesFormat = function(overseasCountries)
        {
            if(overseasCountries.length==0)
                return true;
            var patt = new RegExp("^[0-9]*$");
            var res = patt.test(overseasCountries);
            if(!res){
                $scope.overseasCountriesErrorMessage = $scope.validationMessages.invalidnumber;
                return false;
            }
            if(overseasCountries>100){
              $scope.overseasCountriesErrorMessage = $scope.validationMessages.invalidOnlineChannelsMax;
                return false  
            }
            return true;

        }

        $scope.checkSalesChannelMaxLimit = function(salesChannelLink){
            if(salesChannelLink.length==0)
                return true;
            var patt = new RegExp("^[0-9]*$");
            var res = patt.test(salesChannelLink);
            if(!res){
                $scope.checkSalesChannelMaxLimitErrorMessage = $scope.validationMessages.invalidnumber;
                return false;
            }
            if(salesChannelLink>100){
              $scope.checkSalesChannelMaxLimitErrorMessage = $scope.validationMessages.salesChannelMaxLimit;
                return false  
            }
            return true;

        }




        $scope.isSKUsHandledFormat = function(noOfUniqueSkus)
        {
            if(noOfUniqueSkus.length==0)
                return true;
            var patt = new RegExp("^[0-9]*$");
            var res = patt.test(noOfUniqueSkus);
            if(!res){$scope.skusHandledError = $scope.validationMessages.invalidnumber;
                return false;
            }
            if(noOfUniqueSkus>20000){
              $scope.skusHandledError = $scope.validationMessages.invalidSkusHandledMax;
                return false  
            }
            return true;
        }
        $scope.isDomestOrderFormat=function(domesticOrdersPercentage)
        {
             if(domesticOrdersPercentage.length==0)
                return true;
            var patt = new RegExp("^[0-9]*$");
            var res = patt.test(domesticOrdersPercentage);
            if(!res){$scope.domesticOrdErrorMessage = $scope.validationMessages.invalidnumber;
                return false;
            }
            if(domesticOrdersPercentage>100){
              $scope.domesticOrdErrorMessage = $scope.validationMessages.invalidOnlineChannelsMax;
                return false  
            }
            return true;
        }
        $scope.isPasswordFormat = function(password)
        {
                
                if (typeof password == 'undefined')
                {
                     $scope.pwErrorMessage1 = $scope.validationMessages.required;
                     return false;
                }

                if(password.length <8){
                    $scope.pwErrorMessage1 = $scope.validationMessages.invalidPasswordLength;
                    return false;
                }
                //~`!#$%\^&*+=\-\[\]\\';,/{}|\\":<>\?
                //var patt = new RegExp("^(?=.*[A-Z])(?=.*\\d)(?=.*[!~`@#$%^&*\(\)\_+=\-{}|\":<>;,/?]).*$");
                var patt = /^(?=.*\d)(?=.*[A-Z])(?=.*[^a-zA-Z0-9])(?!.*\s).{8,20}$/;
                var res = patt.test(password);
                if(!res){$scope.pwErrorMessage1 = $scope.validationMessages.invalidPasswordFormat;
                    return false;
                }
                return true;
        }

        $scope.isRegEmailMatched=function(companyRegNumber,email)
        {
            if (companyRegNumber == email) {
                    $scope.regErrorMessage = $scope.validationMessages.emailRegMatch;
                    return false;
            } 
            return true;
        }

        $scope.parseCountryName = function (countryCode) {
                if (!countryCode) return "";
                for (var index = 0; index < $scope.countryList.length; index++) {
                        if ($scope.countryList[index].countryCode == countryCode) return $scope.countryList[index].countryName;
                }
        };
        $scope.setDefaultCountry = function() {
            var defaultCountryCode = "SG";
            for (var index = 0; index < $scope.countryList.length; index++) {
                if ($scope.countryList[index].countryCode == defaultCountryCode)
                    $scope.model.countryCode =  $scope.countryList[index].countryCode;
               
            }
        }
        $scope.setDefaultDialCode = function()
        {
            var defaultCountryCode = "SG";
            for (var index = 0; index < $scope.countryList.length; index++) {
                if ($scope.countryList[index].countryCode == defaultCountryCode){
                    $scope.model.dialCode =  $scope.countryList[index].dialCode;
                    $scope.model.contactNoCountryCode = $scope.countryList[index].dialCode;
                    break;
                }
            }
        }

        $rootScope.removeNotificationMsgs = function() {
                $rootScope.notificationMessages.splice(0, $rootScope.notificationMessages.length);

        }

        $scope.showError = function(msg) {
                $rootScope.notificationMessages = [];
                angular.forEach(msg, function(item){
                    $rootScope.notificationMessages.push(item);
                });
                $scope.scrollPageToTop();
        };

    $scope.clearAll = function () {
        $scope.model = {
            merFirstName :"Gulab",merLastName : "Singh",
            companyName : "Galvantrix",companyRegType : "",companyRegNumber : "",
            securityQuestion : "aswsede",securityAnswer : "asdfasdf",
            addressLine1 : "address1",addressLine2 : "",city : "city",state : "",zipCode : "232323",countryCode : "",landline : "",contactNoCountryCode : "",contactPhone : "23234322",
            revPerAnnum : "",permanentEmp : "",temporaryEmp : "",compFoundedYear : "2000",
            companyWebsite : "",ordersPerMonth : "",merCategory : "",avgUnitsPerOrder : "",domesticOrdersPercentage : "",noOfUniqueSkus : "",noOfOnlineChannels : "",
            salesChannelLink : "",noOfOverseasSelling : "",membershipPlan : "",
            promoCode : "",
            isExistingCustomer : '',
            corpAccountNumber : "",
            email : "glbsng@gmail.com", password : "Glb@2010", retypePwd : "Glb@2010",
            gRecaptchaResponse:"",
            applySme:""
        };
        $rootScope.notificationMessages = [];

        $scope.isAgreed          = false;
        $scope.isSuccess         = false;
        $scope.isCaptchaVerified = true;
        $scope.currentStep       = 1;
    };

        $scope.closeModal  = function() {
                $('#successModal').modal('hide');
                $scope.clearAll();
        }

        $scope.getSignupCategory = function(param){
                if (!_.isEmpty(param)) {
                        var catSplit = _.map(param.join(',').split(','),function(n) {
                                return (_.findWhere($scope.categoryOptions,{value:n}))?(_.findWhere($scope.categoryOptions,{value:n}).name):'';
                        })
                        if(catSplit.length) {
                           if (catSplit.length > 2) {
                              var returnString = _.take(catSplit,2).join(',')+' +'+(catSplit.length-2)+' Others.';
                                      $scope.itemsToDispay =  (catSplit.join(','));
                                return returnString;
                           }else{
                                return catSplit.join(',')
                           }
                        }
                }else{
                        return $scope.constants.notAvailableText;
                }
        }
        
        $scope.signup = function () {
       
                if ($scope.model.merCategory.join) $scope.model.merCategory = $scope.model.merCategory.join(',');
                var url = "addupdatemerchant";
                
                var datas = ({
                	"ActionType":"SIGNUP", //MERCHANT, CONTACT, SIGNUP
                	"EZY_Merchant_Code__c":$scope.model.companyRegNumber,
                	"EZY_Existing_Customer__c":$scope.model.isExistingCustomer,
                	"EZY_Merchant_Category__c":$scope.model.merCategory,
                	"NumberOfEmployees":$scope.model.permanentEmp,
                	"EZY_Account_Number__c":$scope.model.corpAccountNumber,
                	"EZY_Temporary_Employee__c":$scope.model.temporaryEmp,
                	"Name":$scope.model.companyName,
                	"EZY_Is_Active__c":"",
                	"EZY_Revenue__c":$scope.model.revPerAnnum,
                	"EZY_Company_Registration_Type__c":$scope.model.companyRegType,
                	"EZY_Active_Date__c":"",
                	"EZY_Company_Registration_Number__c":$scope.model.companyRegNumber,
                	"EZY_Inactive_Date__c":"",
                	"EZY_Company_Founded_Year__c":$scope.model.compFoundedYear,
                	"EZY_Membership_Plan__c":$scope.model.membershipPlan,
                	"EZY_Credit_Terms_in_Days__c":"",
                	"EZY_Security_Question__c":$scope.model.securityQuestion,
                	"EZY_Security_Answer__c":$scope.model.securityAnswer,
                	"EZY_Locale__c":"",
                	"EZY_Domestic_Orders_Percentage__c":$scope.model.domesticOrdersPercentage,
                	"EZY_Average_Units_Order__c":$scope.model.avgUnitsPerOrder,
                	"EZY_No_of_Unique_SKU_s__c":$scope.model.noOfUniqueSkus,
                	"EZY_No_Of_Online_Channels__c":$scope.model.noOfOnlineChannels,
                	"EZY_Sales_Chanel_Link__c":$scope.model.salesChannelLink,
                	"EZY_No_Of_Overseas_Selling__c":$scope.model.noOfOverseasSelling,
                	"EZY_Orders_Month__c":$scope.model.ordersPerMonth,
                	"EZY_Promotional_Code__c":$scope.model.promoCode,
                	"EZY_Plan_Code__c":"",
                	"EZY_Subsidy_Granted__c":"",
                	"EZY_Created_Date__c":"",
                	"EZY_Subsidy_End_Date__c":"",
                	"EZY_Modified_Date__c":"",
                	"EZY_Subsidy_Start_Date__c":"",
                	"EZY_Country_Code__c":"",
                	"Account_Phone__c":"",
                	"Website":$scope.model.companyWebsite,
                	"EZY_Apply_SME__c":$scope.model.applySme,
                	"Account_Creation_Date__c":"",
                	"Reclassification_Indicator__c":"",
                	"email":$scope.model.email,
                	"FirstName":$scope.model.merFirstName,
                	"LastName":$scope.model.merLastName,
                	"Phone":$scope.model.contactPhone,
                	"Country__c":$scope.model.countryCode,
                	"Address_1__c":$scope.model.addressLine1,
                	"Address_2__c":$scope.model.addressLine2,
                	"City_Name__c":$scope.model.city,
                	"State__c":$scope.model.state,
                	"Postal_Code__c":$scope.model.zipCode,
                	"MobilePhone":""                	
                	
                });
            	var jsonobj=JSON.stringify(datas);
            	document.getElementById("jsoncode").value=jsonobj;
                $http({method: 'post', url: url, params : null, data:jsonobj,  cache: false}).
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
        };

        $scope.getCountryList = function () {
                var url = "/content/country.json";
                $http({method: 'get', url: url, params : null, data: null,  cache: false}).
                    success(function(data, status, headers, config) {
                            angular.forEach(data, function(item){
                                $scope.countryList.push(item);
                            }) ;
                            $scope.setDefaultCountry();
                            $scope.setDefaultDialCode();
                    }).
                    error(function(data, status, headers, config) {
                           
                    });
        };

        $scope.loadConstants = function() {

        $scope.validationMessages = {
              required        : "This field is required",
              invalidemail    : "Invalid Email Format",
              emailtaken      : "Email Address is Already taken",
              pwmissmatch     : "Your passwords don't seem to match! Please re-enter",
              emailRegMatch   : "Email and Company Registration No should not match",
              emailAvailable  : "Looks like you are already registered with us! You can go ahead and login.",
              invalidnumber   : "This field requires number",
              invalidweight   : "Weight should be less than 30 kg and maximum of 2 decimals",
              invalidhscode   : "Minimum 6 digit number required",
              invalidphone    : "Invalid Phone Number",
              invalidPasswordLength : "Minimum 8 characters required",
              invalidPasswordFormat : "Atleast 1 number, 1 upper case alphabet and 1 special character required",
              invalidYearLength : "Minimum 4 digits required",
              invalidOnlineChannelsMax : "Maximum value should be 100",
              invalidSkusHandledMax : "Maximum value should be 20000",
              captchaError    : "Please verify Captcha",
              salesChannelMaxLimit:"Max Limit is 100"
        };

                $scope.constants = {
                        notAvailableText : "NA"
                };

                $scope.compRegTypes     = ['UEN' , 'GSTN'];
                $scope.unitsRange       = ['less than 250', '250-499', '500-1000', '1001-2000','2001-3000','Above 3000'];
                $scope.orderUnitsRange  = ['less than 2', '2-3', '4-5', '6-10','Above 10'];
                $scope.revenueRange     = ['less than SGD 500,000','SGD 500,000 - 999,999','SGD 1,000,000 - 1,999,999','SGD 2,000,000 - 5,000,000','Above SGD 5,000,000'];
                $scope.categoryOptions  = [
                                                {name:"Electronics", value:"A" },
                                                {name:"Apparel", value:"B"},
                                                {name:"Baby", value:"C"},
                                                {name:"Toys", value:"D"},
                                                {name:"Home and Life Style", value:"E" },
                                                {name:"Kitchen", value:"F"},
                                                {name:"Sports", value:"G"},
                                                {name:"Fashion", value:"H"},
                                                {name:"Shoes", value:"I"},
                                                {name:"Footwear", value:"J"},
                                                {name:"Beauty and health", value:"K" },
                                                {name:"Travel", value:"L"},
                                                {name:"Accessories", value:"M"},
                                                {name:"Others", value:"N"}
                                           ];
        };

        $scope.init = function() {

         /*       $timeout(function () {
                    $('[data-toggle="tooltip"]').tooltip();
                }, 1000);
*/
                $scope.countryList = [];
                $scope.getCountryList();
                $scope.loadConstants();
                $scope.emailAvailableError=false;
                $scope.emailAvailableSuccess = false;
                $scope.emailFormatError=false;

                $scope.clearAll();
                $scope.pwErrorMessage1 = "";

                $scope.model.isExistingCustomer=false;
                $scope.model.companyRegType="UEN";
                $scope.setDefaultDialCode();
        };
                  // For popover 
            $('[data-toggle="popover"]').popover({
                    title: function() {
                          return '<span class="glyphicon glyphicon-cross"></span>';
                    },
                    html:true
            });

            $('body').on('click', function (e) {
                $('[data-toggle="popover"]').each(function () {
                                    
                    if ((($(e.target).attr('class'))=='glyphicon glyphicon-cross') || (!$(this).is(e.target) && $(this).has(e.target).length === 0 && $('.popover').has(e.target).length === 0)) {
                        $(this).popover('hide');
                    }
                });
            });


/*        $scope.toggleTooltip = function() {

            if($('#carrier-popover-productCat').hasClass('in')) {
                
                $('.popoverHldr .popover').removeClass('in');
            } else {

                $('.popoverHldr .popover').not('carrier-popover-productCat').removeClass('in');
                $('#carrier-popover-productCat').addClass('in');
            }
        }*/

        $scope.init();
}]);

app.directive('notificationMessages', [function () {
        return {
                restrict: 'A',
                replace:true,
                controller:function(){

                },
                template:'<div class="container-fluid" id="removeNotificationMsgs">'+
                '<div class="row" ng-repeat="showMsg in $root.notificationMessages track by $index">'+
                '<div class="col-xs-12 col-sm-5 col-md-5 notificationRow" ng-if="$root.notificationMessages.length > 1 && $index==0">'+
                '<div class="pull-left col-xs-1 col-md-1 col-sm-1 glyphicon glyphicon-noti-RightArrow collapsed"  data-toggle="collapse" data-target=".showAllMsgs"></div>'+
                '<div class="pull-left col-xs-9 col-md-9 col-sm-9">{{showMsg}}</div>'+
                '<div class="pull-right col-xs-1 col-md-1 col-sm-1" ng-if="$index==0"><a href="javascript:void(0)" ng-click="removeNotificationMsgs()"><span class="glyphicon glyphicon-notificationClose pull-right notificationClose"></span></a></div>'+
                '<div class="clear"></div>'+
                '</div>'+
                '<div class="col-xs-12 col-sm-5 col-md-5 notificationRow collapse showAllMsgs" ng-if="$root.notificationMessages.length > 1 && $index >= 1">'+
                '<div class="pull-left col-xs-1 col-md-1 col-sm-1"></div>'+
                '<div class="pull-left col-xs-9 col-md-9 col-sm-9">{{showMsg}}</div>'+
                '<div class="pull-right col-xs-1 col-md-1 col-sm-1">&nbsp;</div>'+
                '<div class="clear"></div>'+
                '</div>'+
                '<div class="col-xs-12 col-sm-5 col-md-5 notificationRow" ng-if="$root.notificationMessages.length==1 && $index==0">'+
                '<div class="pull-left col-xs-1 col-md-1 col-sm-1"></div>'+
                '<div class="pull-left col-xs-9 col-md-9 col-sm-9">{{showMsg}}</div>'+
                '<div class="pull-right col-xs-1 col-md-1 col-sm-1"><a href="javascript:void(0)" ng-click="removeNotificationMsgs()"><span class="glyphicon glyphicon-notificationClose pull-right notificationClose"></span></a></div>'+
                '<div class="clear"></div>'+
                '</div>'+
                '</div>'+
                '</div>'
        };
}]);
