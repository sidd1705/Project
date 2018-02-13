var myApp = angular.module("ecoPaperApp");

myApp.controller("productionController",function($scope,$http,$uibModal){
	

	function init(){
		
		$scope.getAllProductionSchedule();
	
		
	  }
	
	
	$scope.getAllProductionSchedule =function(){
		
		var promise = $http.get("/getAllProductionSchedule");

		var promiseResponse = function(response) {
			var getSize =response.data.length;
			
			if(getSize==0){
				alert("Data Not Found for Production Schedule....");
				 $scope.productionSchedule = response.data;
			}
			else{
				
				 $scope.productionSchedule = response.data;
			}
		}

		var promiseRejection = function(rejection) {
			alert("Some Error Occured....");
		}
		promise.then(promiseResponse, promiseRejection);
		
	}
	
     	
	  function getSelectedIndex(id){
			for(var i=0; i<$scope.productionSchedule.length; i++)
				if($scope.productionSchedule[i].id==id)
					return i;
				return -1;	
		};
		
		
		
		
		$scope.btnAddProductionSchedule =function(id) {
			 
		    var index = getSelectedIndex(id);
			var production = $scope.productionSchedule[index];
			
			
			
			$scope.production_line_id = production.productionScheduleAll.production_line_id;
			$scope.header_id = production.orderDetails.header_id;
			$scope.order_number = production.orderDetails.order_number;
			$scope.line_id = production.productionScheduleAll.line_id;
			$scope.item_name =production.lineItemsDetail.item_name;
			$scope.paper_color = production.lineItemsDetail.paper_color;
			$scope.print_type = production.lineItemsDetail.print_type;
//		    $scope.item_code = production.item_code;
			$scope.color_1 = production.lineItemsDetail.color_1;
			$scope.color_2 = production.lineItemsDetail.color_2;
//		    $scope.design = production.design;
			$scope.ordered_qty = production.lineItemsDetail.ordered_qty;
			$scope.paper_type = production.lineItemsDetail.paper_type;
			$scope.sheets_in_kg = production.lineItemsDetail.sheets_in_kg;
			$scope.machine_number = production.productionScheduleAll.machine_number;
			$scope.operator = production.productionScheduleAll.operator;
			
	        //$scope.actual_production_date = new Date(production.actual_production_date);
		    $scope.schedule_production_date = production.productionScheduleAll.schedule_date; 
	        $scope.actual_production_date = production.productionScheduleAll.actual_production_date;
			$scope.production_qty = production.productionScheduleAll.production_qty;
			$scope.remaining_production_qty=production.productionScheduleAll.remaining_production_qty;
			$scope.flow_status_code = production.productionScheduleAll.flow_status_code;
			
			if($scope.production_qty == 0)
			{
				$scope.modalInstance = $uibModal.open({
					 ariaLabelledBy: 'modal-title',
					 ariaDescribedBy: 'modal-body',
					 templateUrl: 'templates/templateModals/addProductionSchedule.html',
					 controller :'addProductionSchedule',
					 controllerAs: '$ctrl',
					 size: 'lg',
					 scope: $scope,
					 resolve: {
					 
					 }
					 });
			}
			else{
				
				alert("Unable to Modify Submitted Production Schedule");
			}
		 
		 }
		

		$scope.getProductionPositionPriority =function(){
		    
			var tableData = $scope.productionSchedule;
			var index = 0;
			
			$scope.setProductionPositionPriority = [];

			angular.forEach(tableData, function(value, key) {
				index = index + 1;

				$scope.setProductionPositionPriority.push({ 
					                                      priority_id :  index ,
					                                      production_line_id: value.productionScheduleAll.production_line_id 
					                  });
			});
		
			var size =$scope.setProductionPositionPriority.length;
//			console.clear();
//			console.log("Size = "+size);
//			console.log($scope.setProductionPositionPriority);
			
			var productionPositionPriority =$scope.setProductionPositionPriority;
			
		    var promise = $http.post("/updateProductionSchedulePriority",productionPositionPriority);
			 
			var promiseResponse=function(response){
				
				  alert("Updated Successfully....");
				  $scope.getAllPrintingSchedule(); 
			 }
			 var promiseRejection=function(rejection){
				 
				 alert("Failure");
			 }
	       promise.then(promiseResponse,promiseRejection);
	      	
		}
		
		$scope.sortableOptions = {
				
				   update: function(e, ui) {
					   //alert('hello');
				    },
				   stop: function(e, ui) {
				     
					   $scope.getProductionPositionPriority ();
					   //alert('hello');
				   }
				 };
	
	
	init();
});

//---------------------------------------------------------------------------------------//


myApp.controller("addProductionSchedule",function($scope,$http,$uibModalInstance){
	
	
    function init() {
    	
    	        $scope.schedule_production_date = new Date();
    	   	    $scope.actal_production_date = new Date(); 
    	   	    $scope.dateOptions = {
					   minDate: new Date() 
					};
   	          
    }
    
    $scope.openScheduleProductionDatePicker = function() {
		    $scope.schedule_production_datePopup= true;
     };
		 
     $scope.openActualProductionDatePicker = function() {
	    $scope.actual_production_datePopup= true;
	 }; 
    
//$scope.btnSaveProductionDetails=function(){
//	
//	var productionSchedule={
//			
//			   'line_id'      :$scope.line_id,
//			   'header_id'    :$scope.header_id,
//			   'ordered_qty'  : $scope.ordered_qty,
//			   'production_qty'         :$scope.production_qty,
//			   'actual_production_date'  :$scope.actal_production_date,
//			   'operator'       :$scope.operator,
//			   'machine_number' :$scope.machine_number,
//	    	   'flow_status_code'    :$scope.flow_status_code
//	    	 
//		 };
//	 var promise = $http.post("saveProductionSchedule",productionSchedule);
//	
//	 var promiseResponse=function(response){
//		
//		 alert("Saved Successfully....");
//		 $scope.getAllProductionSchedule();
//		 ngDialog.close();
//
//	 }
//	 var promiseRejection=function(rejection){
//		 
//		 alert("Failure");
//	 }
//     promise.then(promiseResponse,promiseRejection);
//     
//}

  $scope.btnUpdateProductionDetails =function(){
	  
	  
	  var productionSchedule={
			   'production_line_id' : $scope.production_line_id,
			   'line_id'      :$scope.line_id,
			   'header_id'    :$scope.header_id,
			   'ordered_qty'  : $scope.ordered_qty,
			   'remaining_production_qty' :$scope.remaining_production_qty,
			   'production_qty'         :$scope.production_qty,
			   'schedule_date' : $scope.schedule_production_date,
			   'actual_production_date'  :$scope.actal_production_date,
			   'operator'       :$scope.operator,
			   'machine_number' :$scope.machine_number,
	    	   'flow_status_code'    :$scope.flow_status_code
	    	 
		 };
	 
	  console.log(productionSchedule);
	  
	 var promise = $http.post("updateProductionSchedule",productionSchedule);
	 
	 
	 var promiseResponse=function(response){
		 
		
		 alert("Production schedule get updated for order  "+$scope.order_number);
		 $scope.getAllProductionSchedule();
		 $uibModalInstance.close('save'); 
	 }
	 var promiseRejection=function(rejection){
		 
		 alert("Failure");
	 }
    promise.then(promiseResponse,promiseRejection);
	  	  
  }

  $scope.cancelModal = function(){
		 
		 $uibModalInstance.dismiss('close');
	 }
  
init();

	
});


