var myApp = angular.module("ecoPaperApp");

myApp.controller("employeeMasterController", ['$scope', '$http', 'toastr', '$uibModal', function ($scope, $http, toastr, $uibModal) {

	function init(){
		
		$scope.loadAllEmployeeDetails();
		$scope.dob =new Date();
		$scope.joinDate =new Date();
		$scope.resignDate =new Date();
	    $scope.imLoading = true;
	}
	
	
	$scope.loadAllEmployeeDetails = function() {

		var promise = $http.post("/getAllEmployeeDetails");

		var promiseResponse = function(response) {

			$scope.imLoading = false;
			$scope.employeeMasterDetails = response.data;
			 
			if($scope.employeeMasterDetails.length ==0){
				
				$scope.datanotfound = true;
			}
			else{
				$scope.datanotfound = false;
				
				$scope.totalItems = $scope.employeeMasterDetails.length;
				  $scope.currentPage = 1;
				  $scope.itemsPerPage = 10;
				  $scope.maxSize=2;

				  $scope.$watch("currentPage", function() {
				    setPagingData($scope.currentPage);
				  });

				  function setPagingData(page) {
				    var pagedData = $scope.employeeMasterDetails.slice(
				      (page - 1) * $scope.itemsPerPage,
				      page * $scope.itemsPerPage
				    );
				    $scope.employeeDetailsOnPage = pagedData;
				  }  
			}
			
		}
		var promiseRejection = function(rejection) {
			
			toastr.error('Failed to load Employee Details !', 'Error');
    		$log.error(rejection.data.exception);
		}
		promise.then(promiseResponse, promiseRejection);

	}
	
	
	$scope.btnAddEmployee =function(){
		
		 $scope.empId = "";
		 $scope.empNumber = "";
		 $scope.empFullname="";
		 $scope.empAddress="";
		 $scope.mobileNumber="";
		 $scope.emailId="";
		 $scope.roleId="";
		 $scope.managerId="";
		 $scope.isActive="";
		 
		 $scope.dob = new Date();
		 $scope.joinDate=new Date();
		 $scope.resignDate =new Date();
		 
		$scope.buttonAction ="Add";
		$scope.modalInstance = $uibModal.open({
			 ariaLabelledBy: 'modal-title',
			 ariaDescribedBy: 'modal-body',
			 templateUrl: 'templates/templateModals/editEmployee.html',
			 backdrop: false,
			 keyboard  : false,
			 controller :'employeeModalCtrl',
			 controllerAs: '$ctrl',
			 size: 'lg',
			 scope: $scope,
			 resolve: {
			 
			 }
		});
		
	 }
	
	
	$scope.btnEditEmployee = function(emp) {
		
		 $scope.empId = emp.empId;
		 $scope.empNumber = emp.empNumber;
		 $scope.empFullname = emp.empFullname;
		 $scope.empAddress = emp.empAddress;
		 $scope.gender = emp.gender;
		 $scope.dob = new Date(emp.dob);
		 $scope.mobileNumber = emp.mobileNumber;
		 $scope.emailId = emp.emailId;
		 $scope.joinDate = new Date(emp.joinDate);
		 $scope.roleId = emp.roleId;
		 $scope.managerId = emp.managerId;
		 
		 $scope.isActive = emp.isActive;
		 
		 if(emp.resignDate !=null){
			 $scope.resignDate=new Date(emp.resignDate);
		 }
		
		 $scope.buttonAction ="Edit";
		 	 
		$scope.modalInstance = $uibModal.open({
		 ariaLabelledBy: 'modal-title',
		 ariaDescribedBy: 'modal-body',
		 templateUrl: 'templates/templateModals/editEmployee.html',
		 backdrop: false,
		 keyboard  : false,
		 controller :'employeeModalCtrl',
		 controllerAs: '$ctrl',
		 size: 'lg',
		 scope: $scope,
		 resolve: {
		 
		 }
		 });
	}
	
	init();
	
	
}]);


myApp.controller("employeeModalCtrl",function($scope,$http,$log,$uibModalInstance,toastr){
	 
	 function init(){
		 
		 getAllUserRoles();
		 loadAllManagers(); 
		 
		 $scope.isActiveOptions = [               
		        { id: 0 , status:'No' },
		        { id: 1 , status:'Yes' }
		 ];
		
	 }
	 
	
	 
	 $scope.openDobDatePicker = function() {
	    $scope.dobPopup= true;
	 }; 
	 $scope.openJoinDateDatePicker = function() {
		$scope.joinDatePopup= true;
	 }; 
	 $scope.openResignDateDatePicker = function() {
	    $scope.resignDatePopup= true;
	 }; 
	 
	 function getAllUserRoles() {

			var promise = $http.post("/getAllUserRoles");

			var promiseResponse = function(response) {

				$scope.userRoles = response.data;
				 if($scope.userRoles.length ==0){
						
						toastr.warning('User roles are not found !', 'Warning');
				}
			}
			var promiseRejection = function(rejection) {

				toastr.error('Failed to User Roles Details !', 'Error');
			}
			promise.then(promiseResponse, promiseRejection);
	}
			 
	 function loadAllManagers() {

			var promise = $http.post("/getAllManagers");

			var promiseResponse = function(response) {

				$scope.employeeManager = response.data;	

               if($scope.employeeManager.length ==0){
					
					toastr.warning('Managers are not found !', 'Warning');
				}
			}
			var promiseRejection = function(rejection) {

				toastr.error('Failed to Load Superviser Details !', 'Error');
       		$log.error(rejection.data.exception);
			}
			promise.then(promiseResponse, promiseRejection);
	}
	 	 

	 $scope.btnSaveEmployee=function(){
		
		 var employeeMaster = {
				 'empId':$scope.empId,
				 'empNumber':$scope.empNumber,
				 'empFullname':$scope.empFullname,
				 'empAddress':$scope.empAddress, 
				 'gender':$scope.gender,
				 'dob':$scope.dob,
				 'mobileNumber':$scope.mobileNumber,
				 'emailId':$scope.emailId,
				 'joinDate':$scope.joinDate,
				 'roleId':$scope.roleId,
				 'managerId':$scope.managerId,
				 'isActive':$scope.isActive,
				 'resignDate':$scope.resignDate,
				 'createdBy' : $scope.currentUser.userId,
				 'lastUpdatedBy' :$scope.currentUser.userId
		 };

		if( $scope.buttonAction=="Add") {
			
			var promise = $http.post("/saveEmployeeDetails", employeeMaster);

			var promiseResponse = function(response) {
        		
        		toastr.success('Employee Saved Successfully !', 'Success');
        		$scope.loadAllEmployeeDetails();
        		$uibModalInstance.close('save'); 
        	}
			var promiseRejection = function(rejection) {
        		
        		toastr.error('Failed to Save !', 'Error');
        	}
        	promise.then(promiseResponse, promiseRejection);
		}
		else if( $scope.buttonAction=="Edit"){
			        	
			var promise = $http.post("/updateEmployeeDetails", employeeMaster);

        	var promiseResponse = function(response) {

        		toastr.success('Updated Successfully !', 'Success');
        		$scope.loadAllEmployeeDetails();
        		$uibModalInstance.close('save'); 
        	}
        	var promiseRejection = function(rejection) {
        		
        		toastr.error('Failed to Update !', 'Error');
        		$log.error(rejection.data.exception);
        	}
        	promise.then(promiseResponse, promiseRejection);
		}	        
		 
	 }
	 
	 $scope.cancelModal = function(){
	 
		 $uibModalInstance.dismiss('close');
	 }
	 
	
	 
	 init();
});



