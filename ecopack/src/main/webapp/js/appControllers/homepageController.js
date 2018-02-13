

myApp.config(['$stateProvider','$urlRouterProvider','$locationProvider',function($stateProvider, $urlRouterProvider, $locationProvider){
	
	$stateProvider
//	.state('home', {
//        url:'/', 
//		templateUrl : 'templates/orderLineDetails.html',
//	    controllerUrl:'templates/templateControllers/orderLineController.js'
//    })
    .state('main', {
		templateUrl : 'templates/orderLineDetails.html',
	    controllerUrl:'templates/templateControllers/orderLineController.js'
    })
    .state('manageOrders', {
		templateUrl : 'templates/manageOrders.html',
	    controllerUrl:'templates/templateControllers/manageOrdersController.js'
    })
    .state('showLineItems', {
    	templateUrl: 'templates/showLineItemsForOrder.html',
    	params: {
    		selectedOrderLine: null
    	}
    })
    .state('printingTemplate', {
		templateUrl : 'templates/printingSchedule.html',
	    controllerUrl:'templates/templateControllers/printingController.js'
    })
    .state('productionTemplate', {

		templateUrl : 'templates/productionSchedule.html',
        controllerUrl:'templates/templateControllers/productionController.js'
    })
    .state('oeeReportTemplate', {

		templateUrl : 'templates/oeeReport.html',
        controllerUrl:'templates/templateControllers/oeeController.js'
    })
//     .state('dashboardTemplate', {
//
//    	 templateUrl : 'templates/dashboradReport.html',
//    	 controllerUrl:'templates/templateControllers/dashboardController.js'
//    })
    .state('orderReportTemplate', {

    	 templateUrl : 'templates/orderReport.html',
    	 controllerUrl:'templates/templateControllers/orderReportController.js'
    })
     .state('printingReportTemplate', {

    	 templateUrl : 'templates/printingReport.html',
    	 controllerUrl:'templates/templateControllers/printingReportController.js'
    })
    .state('otpdashboardTemplate', {

    	 templateUrl : 'templates/otpReport.html',
    	 controllerUrl:'templates/templateControllers/otpReportController.js'
    })
     .state('employeeMasterTemplate', {

    	 templateUrl : 'templates/employeeMaster.html',
    	 controllerUrl:'templates/templateControllers/employeeMasterController.js'
    })
    .state('logoutLogin', {
		url:'/logout',
		templateUrl : 'loginPage.html',
	    controllerUrl:'js/appControllers/loginController.js'
    });
	
	$urlRouterProvider.otherwise('/');
	$locationProvider.html5Mode(true);
	
}]);
   

myApp.factory('userDetailsFactory',function($http) {
    
	var userFactory={};
	
	userFactory.getLoggedInUser =function(){
     
		var promise = $http.get("/getLoggedInUserDetails");
		var promiseResponse = function(response) {
			return response;
		}
		var promiseRejection = function(rejection) {
			return rejection;
		}
		promise.then(promiseResponse, promiseRejection);
		
		return promise;
	}
	
   return userFactory;
});

myApp.controller("homepageController",['$scope','$http','toastr','userDetailsFactory',function($scope,$http,toastr,userDetailsFactory){

	
	function init(){
		
		$scope.userAuthority=null;
		getLoggedInUser();
	}
	  
	 function getLoggedInUser() {
		
		 userDetailsFactory.getLoggedInUser().then(function(response) {
				
			 $scope.currentUser = response.data;
			 console.log( $scope.currentUser);
			 $scope.userName = $scope.currentUser.userName;
			 $scope.userAuthority = $scope.currentUser.role;
			 
		 }).catch(function(rejection) { 
			 
			 toastr.error('Failed to load Logged In User Details !', 'Error');
		 });
	 }
	  
     
	 $scope.logout =function(){
	    	
	    	location.href = '/logout';		
	 }
	 
	 

	 init();
}]);




	

