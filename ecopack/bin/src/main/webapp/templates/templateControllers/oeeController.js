var myApp = angular.module("ecoPaperApp");

//myApp.config(['$mdDateLocaleProvider', function ($mdDateLocaleProvider) {
//    $mdDateLocaleProvider.formatDate = function (date) {
//        return date ? moment(date).format('YYYY/MM/DD') : '';
//    };
//    $mdDateLocaleProvider.parseDate = function (dateString) {
//        var m = moment(dateString, 'YYYY/MM/DD', true);
//        return m.isValid() ? m.toDate() : new Date(NaN);
//    };
//}]);


myApp.controller("oeeController",function($scope,$http){
	
	
	  function init(){	
		    $scope.oeePlanDate = new Date(); 
		  
			$scope.oeePlanDatePicker = function() {
  		    $scope.oeePlanDatePopup= true;
  		 }; 
		  loadMachineTypes();
	    }
	    
	    $scope.staticData ={
	    		'prod_plan':'Prodution Plan',
	    		'actual_prod' :'Actual Production',
	    		'prod_loss' :'Production Loss'
	    };
	  
	    $scope.plan = {
	    	   
	    	   'prod_plan_first_hour': 0,
	    	   'prod_plan_second_hour': 0,
	    	   'prod_plan_third_hour' : 0,
	    	   'prod_plan_fourth_hour': 0,
	    	   'prod_plan_fifth_hour' : 0,
	    	   'prod_plan_sixth_hour'   : 0,
	    	   'prod_plan_seventh_hour' : 0,
	    	   'prod_plan_eightth_hour' : 0,
	    	   'total_production_plan' : 0	, 
	    	   'actual_prod_first_hour':0,
	    	   'actual_prod_second_hour':0,
	    	   'actual_prod_third_hour' :0,
	    	   'actual_prod_fourth_hour':0,
	    	   'actual_prod_fifth_hour' :0,
	    	   'actual_prod_sixth_hour'   :0,
	    	   'actual_prod_seventh_hour' :0,
	    	   'actual_prod_eightth_hour' :0,
	    	   'total_actual_production' :0,
	    	   'prod_loss_first_hour':0,
	    	   'prod_loss_second_hour':0,
	    	   'prod_loss_third_hour' :0,
	    	   'prod_loss_fourth_hour':0,
	    	   'prod_loss_fifth_hour' :0,
	    	   'prod_loss_sixth_hour' :0,
	    	   'prod_loss_seventh_hour' :0,
	    	   'prod_loss_eightth_hour' :0,
	    	   'total_production_loss'    :0	    
	     };
	  
	  
	 function loadMachineTypes(){
		  
		  var promise = $http.get("/loadMachineTypes");
			
			 var promiseResponse=function(response) {	
				 
			    $scope.machineTypes = response.data;
			     
			 }
			 var promiseRejection=function(rejection){
				 
				 alert("Failure");
				
			 }
	        promise.then(promiseResponse,promiseRejection);  
	  }
	  
	 
	 $scope.selectMachine =function(){
		    	
		    $scope.machineID = $scope.selectedMachine.machine_id;
		  //$scope.machineName = $scope.selectedMachine.machine_Name;
		    		    
		    var promise = $http.get("/loadLossReport",{ params:{"machine_ID":$scope.machineID}});
			
			 var promiseResponse=function(response) {	
				 
			 var lossTypeReport = response.data;
			 
			 // console.log(lossTypeReport.length);
			  //console.log(lossTypeReport);
			  
			  $scope.lossTypeReport=lossTypeReport;
			  
			 }
			 var promiseRejection=function(rejection){
				 
				 alert("Failure");
				
			 }
	        promise.then(promiseResponse,promiseRejection);
	         
	 }
	 
	 $scope.btnSavePlan = function(){
		 
        var productionPlanDetails =$scope.plan;
        
       
		 var promise = $http.post("/saveOEEReportPlanDetails",productionPlanDetails);
			
		 var promiseResponse=function(response){

			 alert("Success");
		 }
		 var promiseRejection=function(rejection){
			
			 alert("Failure");
		 }
        promise.then(promiseResponse,promiseRejection);
        
	 }
	  

	 $scope.btnSaveOEELossReport =function(){
		 
		 var lossTypeReport = $scope.lossTypeReport;
		
		// console.log(lossTypeReport);
		 
		 var promise = $http.post("/saveOEELossReport",lossTypeReport);
			
		 var promiseResponse=function(response){

			 alert("Success");
		 }
		 var promiseRejection=function(rejection){
			
			 alert("Failure");
		 }
        promise.then(promiseResponse,promiseRejection);
		
	 }
	
	  
	 init();
	  
});
