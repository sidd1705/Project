var myApp = angular.module("ecoPaperApp",['ui.router','ui.bootstrap','ngAnimate','ui.sortable','ui.sortable.multiselection','ngAnimate','ui.grid', 'ui.grid.pagination', 'ui.grid.selection', 'ui.grid.exporter','angucomplete-alt']);

myApp.config(['$stateProvider','$urlRouterProvider','$locationProvider',function($stateProvider, $urlRouterProvider, $locationProvider){
	
	$stateProvider
	.state('home', {
        url:'/', 
		templateUrl : 'templates/orderLineDetails.html',
	    controllerUrl:'templates/templateControllers/orderLineController.js'
    })
    .state('main', {
		templateUrl : 'templates/orderLineDetails.html',
	    controllerUrl:'templates/templateControllers/orderLineController.js'
    })
    .state('printingTemplate', {
		templateUrl : 'templates/printingSchedule.html',
	    controllerUrl:'templates/templateControllers/printingController.js'
    })
    .state('productionTemplate', {

		templateUrl : 'templates/productionSchedule.html',
        controllerUrl:'templates/templateControllers/productionController.js'
    })
    .state('oeeTemplate', {

		templateUrl : 'templates/oeeReport.html',
        controllerUrl:'templates/templateControllers/oeeController.js'
    })
     .state('dashboardTemplate', {

    	 templateUrl : 'templates/dashboradReport.html',
    	 controllerUrl:'templates/templateControllers/dashboardController.js'
    })
    .state('logoutLogin', {
		url:'/logout',
		templateUrl : 'loginPage.html',
	    controllerUrl:'js/appControllers/loginController.js'
    });
	
	$urlRouterProvider.otherwise('/');
	$locationProvider.html5Mode(true);
	
}]);
   


myApp.controller("homepageController",['$scope','$http',function($scope,$http){

	
	function init(){
		
		$scope.login();
	}
	
	 $scope.login = function() {
			
		
			var promise = $http.get('/userDetails');

			var promiseResponse = function(response) {
				
				var user = response.data;
				
				if(user.authenticated == true){

					$scope.user = user;
	
					console.log($scope.user);
				}else{
					alert('Authetication Failed !');
				}
				
				
			}
			var promiseRejection = function(rejection) {
				
				alert("Failed to load User Details !");
			}
			promise.then(promiseResponse, promiseRejection);
		}
     
	 $scope.logout =function(){
	    	
	    	location.href = '/logout';		
	 }

	 init();
}]);




	

