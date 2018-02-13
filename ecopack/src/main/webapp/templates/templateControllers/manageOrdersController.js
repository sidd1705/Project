var myApp = angular.module("ecoPaperApp");

myApp.controller("manageOrdersController", ['$scope', '$http', 'toastr', '$uibModal','$state', function ($scope, $http, toastr, $uibModal,$state) {

	
	function init(){
		
		$scope.getAllEnteredOrders();
	}
	
	$scope.getAllEnteredOrders = function(){
		
		var promise = $http.get("/getAllEnteredOrders");

		var promiseResponse = function (response) {

			$scope.enteredOrders = response.data;

			if ($scope.enteredOrders.length == 0) {
				$scope.datanotfound = true;
			} else {
				$scope.datanotfound = false;
			}
		}
		var promiseRejection = function (rejection) {

			toastr.error('Failed to load Printing Schedule!', 'Error');
		}
		promise.then(promiseResponse, promiseRejection);
	}
	
	$scope.manageOrder = function(event,order){
		
		var statusAction = event.target.id;
		var headerId =  order.orderDetails.headerId
		var remark='';
		
		if(statusAction === 'Approved'){
			var promise = $http.get("/manageOrder",{
			params :{
				'headerId': headerId,
				'statusAction': statusAction,
				'remark':remark
			}
			});

			var promiseResponse = function (response) {
  
				var status = response.data;
		
				if(status == 1){
					toastr.success('Order Approved Successfully!', 'Success');
				}else if(status == 0){
					toastr.error('Failed to Approve Order!', 'Error');
				}
				$scope.getAllEnteredOrders();
			}
			var promiseRejection = function (rejection) {

				toastr.error('Failed to Approve Order', 'Error');
			}
			promise.then(promiseResponse, promiseRejection);
		}
		else if(statusAction ==='Rejected'){
			
			$scope.headerId = headerId;
			$scope.statusAction =  statusAction;
			
			$scope.modalInstance = $uibModal.open({
				 ariaLabelledBy: 'modal-title',
				 ariaDescribedBy: 'modal-body',
				 templateUrl: 'templates/templateModals/rejectionNote.html',
				 backdrop: false,
				 keyboard  : false,
				 controller :'rejectOrdersController',
				 controllerAs: '$ctrl',
				 size: 'sm',
				 scope: $scope,
				 resolve: {
				 
	
				 }
				 });
		}

	}
	
	$scope.showOrderLineItems =function(order){
		
		$state.go('showLineItems', {selectedOrderLine: order});
	}
	init();
	
}]);


myApp.controller("lineItemsForSelectedOrderCtrl", function ($scope,$rootScope,$http,$state,$stateParams,toastr) {
	
	
	function init(){
		
		$rootScope.$emit("loadLineItemsForSelectedOrder");
	}
	
	$scope.manageLineItem=function(event,headerId,lineId){
	
		var statusAction = event.target.id;
		var remark="";
		var promise = $http.get("/manageLineItem",{
			params :{
				'headerId':headerId,
				'lineId':lineId,
				'statusAction':statusAction,
				'remark':remark
			}
			});

			var promiseResponse = function (response) {
				$rootScope.$emit("loadLineItemsForSelectedOrder");
  				var status = response.data;
  				
  				if(status==1)
				toastr.success('Line Item Approved Successfully!', 'Success');
  				else
  					toastr.success('Line Item Rejected Successfully!', 'Success');
				
			}
			var promiseRejection = function (rejection) {

				toastr.error('Failed to Approve Order', 'Error');
			}
			promise.then(promiseResponse, promiseRejection);
	}
	
	
	
	$rootScope.$on("loadLineItemsForSelectedOrder",function(){
		
		var data = $stateParams.selectedOrderLine; 
		//var headerId = data.orderDetails.headerId;
		
		var orderNumber = data.orderDetails.orderNumber;
		
		var promise = $http.get("/getActionAllLineDetails", {
			params: {
				"orderNumber": orderNumber
			}
		});

		var promiseResponse = function (response) {
			$scope.orderLineDetails = response.data;
		}
		var promiseRejection = function (rejection) {
		toastr.error('Failed to load Order Line Details !', 'Error');
		}
		promise.then(promiseResponse, promiseRejection);
		
	});

    $scope.btnBack = function () {
		
	    $state.go('manageOrders');
	};
	
	init();
	
});

myApp.controller("rejectOrdersController", function ($scope, $http, $uibModalInstance, toastr) {


	function init() {


	}

	$scope.btnSubmit =function(){
		
		var promise = $http.get("/manageOrder",{
		 params :{
			'headerId': $scope.headerId,
			'statusAction': $scope.statusAction,
			'remark':$scope.rejectionNote
		 }
	    });

	    var promiseResponse = function (response) {

	    	var status = response.data;
			if(status == 1){
				toastr.success('Order Rejected Successfully!', 'Success');
			}else if(status == 0){
				toastr.error('Failed to Reject Order', 'Error');
			}
	    	$uibModalInstance.close('save'); 
	    	$scope.$emit("loadLineItemsForSelectedOrder");
	    	$scope.getAllEnteredOrders();
	    }
	   	var promiseRejection = function (rejection) {

	   		toastr.error('Failed to Reject Order', 'Error');
	   	}
	   	promise.then(promiseResponse, promiseRejection);
	}
	
	$scope.cancelModal = function () {

		$uibModalInstance.dismiss('close');
	}



	init();


});