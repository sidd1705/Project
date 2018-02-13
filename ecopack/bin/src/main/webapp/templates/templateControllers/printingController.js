var myApp = angular.module("ecoPaperApp");

myApp.controller("printingController",function($scope,$http,$uibModal,uiSortableMultiSelectionMethods){
	

	function init(){
		
		$scope.getAllPrintingSchedule();  
		
	}

	$scope.getAllPrintingSchedule= function(){
		
		var promise = $http.get("/getAllPrintingSchedule");
		
		var promiseResponse = function(response) {
		
           var getSize =response.data.length;
			
			if(getSize==0){
				alert("Data Not found for Printing Schedule....");
				 $scope.printingSchedule = response.data;
			}
			else{
				
				 $scope.printingSchedule = response.data;
//				 console.log(response.data);
			}
		}

		var promiseRejection = function(rejection) {
			alert("Some Error Occured....");
		}
		promise.then(promiseResponse, promiseRejection);
	}

	   function getSelectedIndex(id){
			for(var i=0; i<$scope.printingSchedule.length; i++)
				if($scope.printingSchedule[i].id==id)
					return i;
				return -1;	
		};
		
		
	$scope.btnAddPrintingSchedule =function(id){	 
	    var index = getSelectedIndex(id);
		var printing = $scope.printingSchedule[index];
		
		$scope.printing_line_id = printing.printingScheduleAll.printing_line_id;
		$scope.header_id = printing.printingScheduleAll.header_id;
		$scope.order_number = printing.orderDetails.order_number;
		$scope.ordered_date = printing.orderDetails.ordered_date;
		$scope.line_id = printing.printingScheduleAll.line_id;
		$scope.item_name = printing.lineItemsDetail.item_name;
//		$scope.design_id = printing.design_id;
//		$scope.design_name = printing.ordered_item;
		$scope.paper_type = printing.lineItemsDetail.paper_type;
		$scope.paper_gsm = printing.lineItemsDetail.paper_gsm;
		$scope.print_type = printing.lineItemsDetail.print_type;
		$scope.color_1 = printing.lineItemsDetail.color_1;
		$scope.color_2 = printing.lineItemsDetail.color_2;
		$scope.item_size = printing.lineItemsDetail.item_size;
		$scope.ordered_qty = printing.lineItemsDetail.ordered_qty;
		$scope.machine_number = printing.printingScheduleAll.machine_number;
		$scope.operator = printing.printingScheduleAll.operator;
		$scope.paper_lot_num = printing.printingScheduleAll.paper_lot_num;
		$scope.pcs_per_sheet = printing.printingScheduleAll.pcs_per_sheet;
		$scope.no_of_sheets_to_print = printing.printingScheduleAll.no_of_sheets_to_print;
		 
//		$scope.actual_printing_date = new Date(printing.actual_printing_date);
//      $scope.schedule_date = new Date(printing.schedule_date);
         
		$scope.actual_printing_date = printing.printingScheduleAll.actual_printing_date;
		$scope.no_of_sheets_printed = printing.printingScheduleAll.no_of_sheets_printed;
		$scope.remaining_sheets_to_print = printing.printingScheduleAll.remaining_sheets_to_print;
		$scope.schedule_date = printing.printingScheduleAll.schedule_date;
		$scope.flow_status_code = printing.printingScheduleAll.flow_status_code;
		
		if($scope.no_of_sheets_printed == 0){
			
			$scope.modalInstance = $uibModal.open({
				 ariaLabelledBy: 'modal-title',
				 ariaDescribedBy: 'modal-body',
				 templateUrl: 'templates/templateModals/addPrintingSchedule.html',
				 controller :'addPrintingSchedule',
				 controllerAs: '$ctrl',
				 size: 'lg',
				 scope: $scope,
				 resolve: {
				 
				 }
				 });
		}
		else{
			alert("Unable to Modify Submitted Printing Schedule");
		}
		 
			 
	 }
	
	$scope.getPrintingPositionPriority =function(){
	   
		var tableData = $scope.printingSchedule;
		var index = 0;
		
		$scope.setPrintingPositionPriority = [];

		angular.forEach(tableData, function(value, key) {
			index = index + 1;

			$scope.setPrintingPositionPriority.push({ 
				                                      priority_id :  index ,
				                                      printing_line_id: value.printingScheduleAll.printing_line_id
				                  });
		});
	
		var size =$scope.setPrintingPositionPriority.length;
		console.clear();
		console.log("Size = "+size);
		console.log($scope.setPrintingPositionPriority);
		
		var printingSchedulePriority =$scope.setPrintingPositionPriority;
		
	    var promise = $http.post("/updatePrintingSchedulePriority",printingSchedulePriority);
		 
		var promiseResponse=function(response){
			
			  alert("Updated Successfully....");
			  $scope.getAllPrintingSchedule(); 
		 }
		 var promiseRejection=function(rejection){
			 
			 alert("Failure");
		 }
       promise.then(promiseResponse,promiseRejection);
      	
	}
	
	
//	$scope.sortableOptions = {
//			
//			   update: function(e, ui) {
//				   //alert('hello');
//			    },
//			   stop: function(e, ui) {
//			     
//				   //alert('hello');
//				   $scope.getPrintingPositionPriority();
//			   }
//	};
	
	$scope.sortingLog = [];
	
	$scope.sortableOptions = uiSortableMultiSelectionMethods.extendOptions({
	    stop: function(e, ui) {
	      // this callback has the changed model
	      var logEntry = tmpList.map(function(i){
	        return i.value;
	      }).join(', ');
	      $scope.sortingLog.push('Stop: ' + logEntry);
	      $scope.getPrintingPositionPriority();
	    }
	  });
	
	
	var tmpList = [];
	  
	  for (var i = 1; i <= 6; i++){
	    tmpList.push({
	      text: 'Item ' + i,
	      value: i
	    });
	  }
	  
	  $scope.list = tmpList;
	
	angular.element('[ui-sortable]').on('ui-sortable-selectionschanged', function (e, args) {
	    var $this = $(this);
	    var selectedItemIndexes = $this.find('.ui-sortable-selected')
	    .map(function(i, element){
	      return $(this).index();
	    })
	    .toArray();
	    console.log(selectedItemIndexes);
	    
	    var selectedItems = $.map(selectedItemIndexes, function(i) {
	      return $scope.list[i]
	    });
	    console.log(selectedItems);
	  });
	
	
	
	
	
	init();
});

//---------------------------------------------------------------------------------------//


myApp.controller("addPrintingSchedule",function($scope,$http, $uibModalInstance){
	
	
	    function init()
	    {
	       /*Initialize date to Calendar in ngDialog modal*/
	    	 $scope.dos = new Date();
 	   	    $scope.actual_printing_date = new Date(); 
 	   	    $scope.dateOptions = {
				   minDate: new Date() 
				};
    		 
	    	
	    }
	    $scope.openDoapDatePicker = function() {
		    $scope.doapPopup= true;
		 }; 
		 
		 $scope.openDosDatePicker = function() {
 		    $scope.dosPopup= true;
 		 }; 
 		 
	    

    $scope.btnUpdatePrintingDetails =function(){
    	
    	
    	var printingSchedule={
    			 
    	           'printing_line_id' : $scope.printing_line_id,
				   'header_id'        :$scope.header_id,
				   'line_id'          :$scope.line_id,
				   'machine_number' :$scope.machine_number,
				   'operator'       :$scope.operator,
				   'paper_lot_num'  :$scope.paper_lot_num,
//				   'design_id'      :$scope.design_id,
//				   'design_name'    :$scope.design_name,
				   'pcs_per_sheet'  :$scope.pcs_per_sheet,
				   'no_of_sheets_to_print'  :$scope.no_of_sheets_to_print,
				   'actual_printing_date'    :$scope.actual_printing_date,
		    	   'no_of_sheets_printed'   :$scope.no_of_sheets_printed,
		    	   'remaining_sheets_to_print' :$scope.remaining_sheets_to_print,
		    	   'schedule_date'  :      $scope.dos
		    
			 };
    	
    	
		 var promise = $http.post("updatePrintingScheduleDetails",printingSchedule);
		 
		
		 var promiseResponse=function(response){
			
			 alert("Printing schedule get updated for order  " +$scope.order_number);
			 $scope.getAllPrintingSchedule();
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

