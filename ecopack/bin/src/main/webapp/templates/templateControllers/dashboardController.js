var myApp = angular.module("ecoPaperApp");


myApp.controller("dashboardController", function($scope, $http, $compile, $timeout) {

	function init() {

		$scope.getAllPrintingSchedule();
	}
	
	
	$scope.printingSchedule = {
	    paginationPageSizes: [25, 50, 75],
	    paginationPageSize: 25,
		    columnDefs: [
		    	{ field: 'line_id', visible: false},
		    	{ field: 'header_id', visible: false},
		    	{ field: 'line_number', visible: false},
		    	{ field: 'item_id'},
		    	{ field: 'item_name'},
		    	{ field: 'item_desc'},
		    	{ field: 'order_value'},
		    	{ field: 'ordered_qty'},
		    	{ field: 'due_date'},
		    	{ field: 'ready_date', visible: false},
		    	{ field: 'dispatch_date', visible: false},
		    	{ field: 'cancelled_date', visible: false},
		    	{ field: 'cancelled_flag', visible: false},
		    	{ field: 'item_size', visible: false},
		    	{ field: 'paper_type'},
		    	{ field: 'paper_gsm'},
		    	{ field: 'print_type'},
		    	{ field: 'sheets_in_kg'},
		    	{ field: 'printing_required'},
		    	{ field: 'mfg_required'},
		    	{ field: 'in_stock'},
		    	{ field: 'color_1'},
		    	{ field: 'color_2'},
		    	{ field: 'paper_color'},
		    	{ field: 'on_time_delivery', visible: false},
		    	{ field: 'flow_status_code'},
		    	{ field: 'creation_date', visible: false},
		    	{ field: 'created_by', visible: false},
		    	{ field: 'last_update_date', visible: false},
		    	{ field: 'last_update_by', visible: false},
		    	{ field: 'attribute1', visible: false},
		    	{ field: 'attribute2', visible: false},
		    	{ field: 'attribute3', visible: false},
		    	{ field: 'attribute4', visible: false},
		    	{ field: 'attribute5', visible: false},
		    	{ field: 'attribute6', visible: false},
		    	{ field: 'attribute7', visible: false},
		    	{ field: 'attribute8', visible: false},
		    	{ field: 'attribute9', visible: false},
		    	{ field: 'attribute10', visible: false}
		    ],
		    enableGridMenu: true,
		    enableSelectAll: true,
		    exporterCsvFilename: 'myFile.csv',
		    exporterPdfDefaultStyle: {fontSize: 9},
		    exporterPdfTableStyle: {margin: [30, 30, 30, 30]},
		    exporterPdfTableHeaderStyle: {fontSize: 10, bold: true, italics: true, color: 'red'},
		    exporterPdfHeader: { text: "Order Line Details", style: 'headerStyle' },
		    exporterPdfFooter: function ( currentPage, pageCount ) {
		      return { text: currentPage.toString() + ' of ' + pageCount.toString(), style: 'footerStyle' };
		    },
		    exporterPdfCustomFormatter: function ( docDefinition ) {
		      docDefinition.styles.headerStyle = { fontSize: 22, bold: true };
		      docDefinition.styles.footerStyle = { fontSize: 10, bold: true };
		      return docDefinition;
		    },
		  //  exporterPdfOrientation: 'portrait',
		    exporterPdfOrientation: 'landscape',
		    exporterPdfPageSize: 'LETTER',
		    exporterPdfMaxGridWidth: 500,
		    exporterCsvLinkElement: angular.element(document.querySelectorAll(".custom-csv-link-location")),
		    onRegisterApi: function(gridApi){
		      $scope.gridApi = gridApi;
		    }
		  };
	
	 
   $scope.getAllPrintingSchedule= function(){
		
		var promise = $http.get("/api/admin/getDashboard");
		
		var promiseResponse = function(response) {
		
           var getSize =response.data.length;
			
			if(getSize==0){
				alert("Data Not found for Printing Schedule....");
				 $scope.printingSchedule.data = response.data;
			}
			else{
				
				 $scope.printingSchedule.data = response.data;
				// console.log(response.data);
			}
		}

		var promiseRejection = function(rejection) {
		
			console.log(rejection);
			alert(rejection.data.error+":"+rejection.data.message);
		}
		promise.then(promiseResponse, promiseRejection);
	}
	
	
	$scope.printingSchedule = {
		    paginationPageSizes: [25, 50, 75],
		    paginationPageSize: 25,
			    columnDefs: [
			    	{ field: 'line_id', visible: false},
			    	{ field: 'header_id', visible: false},
			    	{ field: 'line_number', visible: false},
			    	{ field: 'item_id',width:80},
			    	{ field: 'item_name', width:200},
			    	{ field: 'item_desc' , width:200},
			    	{ field: 'order_value', visible: false},
			    	{ field: 'ordered_qty', width:200},
			    	{ field: 'due_date',visible: false},
			    	{ field: 'ready_date', visible: false},
			    	{ field: 'dispatch_date', visible: false},
			    	{ field: 'cancelled_date', visible: false},
			    	{ field: 'cancelled_flag', visible: false},
			    	{ field: 'item_size', visible: false},
			    	{ field: 'paper_type',width:100},
			    	{ field: 'paper_gsm',width:100},
			    	{ field: 'print_type',width:100},
			    	{ field: 'sheets_in_kg',width:100},
			    	{ field: 'printing_required',width:100},
			    	{ field: 'mfg_required',width:100},
			    	{ field: 'in_stock',width:100},
			    	{ field: 'color_1',width:100},
			    	{ field: 'color_2',width:100},
			    	{ field: 'paper_color',width:100},
			    	{ field: 'on_time_delivery', visible: false},
			    	{ field: 'flow_status_code',width:100},
			    	{ field: 'creation_date', visible: false},
			    	{ field: 'created_by', visible: false},
			    	{ field: 'last_update_date', visible: false},
			    	{ field: 'last_update_by', visible: false},
			    	{ field: 'attribute1', visible: false},
			    	{ field: 'attribute2', visible: false},
			    	{ field: 'attribute3', visible: false},
			    	{ field: 'attribute4', visible: false},
			    	{ field: 'attribute5', visible: false},
			    	{ field: 'attribute6', visible: false},
			    	{ field: 'attribute7', visible: false},
			    	{ field: 'attribute8', visible: false},
			    	{ field: 'attribute9', visible: false},
			    	{ field: 'attribute10', visible: false}
			    ],
			    enableGridMenu: true,
			    enableSelectAll: true,
			    exporterCsvFilename: 'myFile.csv',
			    exporterPdfDefaultStyle: {fontSize: 9},
			    exporterPdfTableStyle: {margin: [30, 30, 30, 30]},
			    exporterPdfTableHeaderStyle: {fontSize: 10, bold: true, italics: true, color: 'red'},
			    exporterPdfHeader: { text: "Order Line Details", style: 'headerStyle' },
			    exporterPdfFooter: function ( currentPage, pageCount ) {
			      return { text: currentPage.toString() + ' of ' + pageCount.toString(), style: 'footerStyle' };
			    },
			    exporterPdfCustomFormatter: function ( docDefinition ) {
			      docDefinition.styles.headerStyle = { fontSize: 22, bold: true };
			      docDefinition.styles.footerStyle = { fontSize: 10, bold: true };
			      return docDefinition;
			    },
			  //  exporterPdfOrientation: 'portrait',
			    exporterPdfOrientation: 'landscape',
			    exporterPdfPageSize: 'LETTER',
			    exporterPdfMaxGridWidth: 500,
			    exporterCsvLinkElement: angular.element(document.querySelectorAll(".custom-csv-link-location")),
			    onRegisterApi: function(gridApi){
			      $scope.gridApi = gridApi;
			    }
//			    scrollbars: {
//			        NEVER: 0,
//			        ALWAYS: 1,
//			        WHEN_NEEDED: 2
//			      }
			  };
		
	
//	$scope.getAllPrintingSchedule= function() {
//		
//		var promise = $http.get("/getAllPrintingSchedule");
//		
//		var promiseResponse = function(response){
//		
//           var getSize =response.data.length;
//			
//			if(getSize==0){
//				alert("Data Not found for Printing Schedule....");
//				 $scope.printingSchedule.data = response.data;
//			}
//			else{
//				
//				// $scope.printingSchedule.data = response.data;
//				
//				
//				 var printingScheduleData = response.data;
//				 
//				 var  printingDashboard=[];
//				 
//				
//				 angular.forEach(printingScheduleData, function (value, key, obj) {
//					 			      
//					     printingDashboard.push(angular.merge({}, value.orderDetails, value.lineItemsDetail,value.printingScheduleAll));
//		            });
//				 
//				 $scope.printingSchedule.data=printingDashboard;
//			}
//		}
//
//		var promiseRejection = function(rejection) {
//			
//			console.log("Exception Path : "+rejection.data.path);
//			console.log("Exception : "+rejection.data.exception);
//			console.log("Exception Message : "+rejection.data.message);
//		    alert(rejection.data.message);
//		}
//		promise.then(promiseResponse, promiseRejection);
//	}
	
	


	init();

});
