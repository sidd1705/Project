var myApp = angular.module("ecoPaperApp");

myApp.controller("orderLineController", ['$scope','$rootScope', '$http', 'toastr', '$uibModal', function ($scope,$rootScope,$http, toastr, $uibModal) {

	function init() {

		$scope.orderedDate = new Date();
		
		$scope.addNewDisabled = true;

		loadCustomerDetails();
		loadSalesExecutiveDetails();
		
		$scope.lineDetails = [];
	}

	$scope.format = 'yyyy/MM/dd';

	$scope.openOrderedDateDatePicker = function () {
		$scope.orderedDatePopup = true;
	};

	
    $scope.checkOrderNumberExists =function(){
		
		var promise = $http.get("/checkOrderNumberIsExists", {
			params: {
				"orderNumber": $scope.orderNumber
			}
		});
		var promiseResponse = function (response) {

			var status= response.data;
			
			if(status == 1){
				toastr.error('Order Number Already exist', 'Error');
				$scope.orderNumber ="";
			}	
		}
		var promiseRejection = function (rejection) {

			toastr.error('Failed to Check Order Number exist or not !', 'Error');
		}
		
		promise.then(promiseResponse, promiseRejection);
    }
		

	function loadCustomerDetails() {

		var arr = new Array();

		var promise = $http.get("/loadCustomerDetails");

		var promiseResponse = function (response) {

			var customerDetails = response.data;

			angular.forEach(customerDetails, function (
				value, key) {
				arr.push({
					customerId: value.customerId,
					customerName: value.customerName
				});
			});

			$scope.customerList = arr;

		}
		var promiseRejection = function (rejection) {

			toastr.error('Failed to load Customer Details !', 'Error');
		}
		promise.then(promiseResponse, promiseRejection);
	}

	$scope.selectedCustomer = function (str) {
		if (str != null) {

			$scope.customerId = str.originalObject.customerId;
			$scope.customerName = str.originalObject.customerName;
		}

	}

	function loadSalesExecutiveDetails() {

		var arr = new Array();

		var promise = $http.get("/loadSalesExecutiveDetails");

		var promiseResponse = function (response) {

			var salesExecutiveDetails = response.data;

			angular.forEach(salesExecutiveDetails, function (
				value, key) {
				arr.push({
					salesExecutiveId: value.empId,
					salesExecutiveName: value.empFullname
				});
			});

			$scope.salesExecutiveList = arr;

		}
		var promiseRejection = function (rejection) {

			toastr.error('Failed to load Sales Executive Details !', 'Error');
		}
		promise.then(promiseResponse, promiseRejection);
	}

	$scope.selectedSalesExecutive = function (str) {
		if (str != null) {

			$scope.salesExecutiveId = str.originalObject.salesExecutiveId;
			$scope.salesExecutiveName = str.originalObject.salesExecutiveName;
		}

	}

	$scope.btnSaveOrder = function () {
		
		var orderDetails = {
				'orderNumber': $scope.orderNumber,
				'customerType': $scope.customerType,
				'customerId': $scope.customerId,
				'orderedDate': $scope.orderedDate,
				'salesExecutiveId': $scope.salesExecutiveId,
				'currency': $scope.currency,
				'totalOrderValue' : $scope.totalOrderValue
			};
		var lineDetails = $scope.lineDetails;
		
		var orderLineDetails = {
			
				'orderDetails' : orderDetails,
				'lineDetails' : lineDetails
		};
		var promise = $http.post("/saveOrder", orderLineDetails);

		var promiseResponse = function (response) {

			toastr.success('Order Saved Successfully !', 'Success');
			resetOrderLineForm();
		}
		var promiseRejection = function (rejection) {

			toastr.error('Failed to Save Order !', 'Error');
		}
		promise.then(promiseResponse, promiseRejection);
	}
	
	function resetOrderLineForm(){
		
		$scope.orderNumber ='';
		$scope.customerType='';
		$scope.orderedDate= new Date();
		$scope.currency ='';
		 $scope.totalOrderValue ='';
		$scope.$broadcast('angucomplete-alt:clearInput');
		
		$scope.lineDetails=[];
		
	}
	
	 $scope.calcTotalOrderValue = function () {

			var total = 0;
			var order = $scope.lineDetails;

			for (var i = 0; i < order.length; i++) {

				var orderValue = order[i].orderValue;
				total = total + orderValue;
			}
			$scope.totalOrderValue = total;
		}
	
	$scope.btnDeleteRow=function(order){
		
		var result = confirm('Are you sure?');
        if (result === true) {
		  
        	var index = $scope.lineDetails.indexOf(order);
        	$scope.lineDetails.splice(index, 1);
        	
        	 $scope.calcTotalOrderValue();
        }
	}
	

	$scope.loadItemMaster = function () {

		var promise = $http
			.get(
				"/getItemMasterDetails", {
					params: {
						"itemType": $scope.customerType
					}
				});

		var promiseResponse = function (response) {

			$scope.itemMaster = response.data;
		}
		var promiseRejection = function (rejection) {

			toastr.error('Failed to load Item Master !', 'Error');
		}
		promise.then(promiseResponse, promiseRejection);
	}

	$scope.btnAddLineItems = function () {

		$scope.itemId = "";
		$scope.itemName = "";
		$scope.itemSize = "";
		
		$scope.designNo = "";
		$scope.designName = "";
		$scope.paperColor = "";
		$scope.paperGsm = "";
		$scope.printType = "";
		$scope.color1 = "";
		$scope.color2 = "";

		$scope.pcsPerSheet = "";
		$scope.pcsPerHour = "";
		$scope.productCode = "";
		
		$scope.itemDesc = "";
		$scope.paperTypeId = "";
	
		$scope.orderValue = "";
		$scope.orderedQty = "";
		
		$scope.inStock = "";
		$scope.printingRequired = "";
		$scope.mfgRequired = "";
		
		$scope.buttonAction = "AddNew";
		$scope.loadItemMaster();

		$scope.modalInstance = $uibModal
			.open({
				ariaLabelledBy: 'modal-title',
				ariaDescribedBy: 'modal-body',
				templateUrl: 'templates/templateModals/editLineItems.html',
				backdrop: false,
				controller: 'lineItemsController',
				controllerAs: '$ctrl',
				size: 'lg',
				scope: $scope,
				resolve: {

				}
			});
	}

	$scope.btnEditLineItems = function (order) {

		$scope.selectedLineDetails = order;
			
		$scope.itemId = order.itemId;
		$scope.itemName = order.itemName,
		$scope.itemSize = order.itemSize;
		
		$scope.designNo = order.designNo;
		$scope.designName = order.designName;
		$scope.paperColor = order.paperColor;
		$scope.paperGsm = order.paperGsm;
		$scope.printType = order.printType;
		$scope.color1 = order.color1;
		$scope.color2 = order.color2;
		
		$scope.pcsPerSheet = order.pcsPerSheet;
		$scope.pcsPerHour =order.pcsPerHour;
		$scope.productCode = order.productCode;


		$scope.itemDesc = order.itemDesc;
		$scope.paperTypeId = order.paperTypeId;
		
		$scope.orderValue = order.orderValue;
		$scope.orderedQty = order.orderedQty;

		$scope.inStock = order.inStock;
		$scope.printingRequired = order.printingRequired;
		$scope.mfgRequired = order.mfgRequired;
		
		
		$scope.buttonAction = "EditUpdate";

		$scope.modalInstance = $uibModal
			.open({
				ariaLabelledBy: 'modal-title',
				ariaDescribedBy: 'modal-body',
				templateUrl: 'templates/templateModals/editLineItems.html',
				backdrop: false,
				controller: 'lineItemsController',
				controllerAs: '$ctrl',
				size: 'lg',
				scope: $scope,
				resolve: {

				}
			});
	}

	init();
}]);

/*--------------------------- Modal Controller--------------------------------------------------*/

myApp.controller("lineItemsController", function ($scope, $http, toastr, $uibModalInstance) {

	function init() {

     	$scope.changeDueDate();
		$scope.orderNumber = $scope.orderNumber;
		$scope.itemType = $scope.customerType;

		initSelectList();
		loadPaperTypeMaster();
	}

	function initSelectList() {

		$scope.inStockJson = [{
			inStock: 'Yes',
			inStockLabel: 'Yes'
		}, {
			inStock: 'No',
			inStockLabel: 'No'
		}];

		$scope.printRequiredJson = [{
			printingRequired: 'Yes',
			printingRequiredLabel: 'Yes'
		}, {
			printingRequired: 'No',
			printingRequiredLabel: 'No'
		}];

		$scope.mfgRequiredJson = [{
			mfgRequired: 'Yes',
			mfgRequiredLabel: 'Yes'
		}, {
			mfgRequired: 'No',
			mfgRequiredLabel: 'No'
		}];

	}
	
	$scope.openDueDateDatePicker = function () {
		$scope.dueDatePopup = true;
	};

	$scope.orderDateOptions = {
		// dateDisabled: disabled,
		formatYear: 'yy',
		maxDate: new Date(2050, 5, 22),
		minDate: new Date(),
		startingDay: 1,
		minDateChange: date => {
			$scope.dateOptions.minDate = $scope.orderedDate;
		}
	};

	$scope.changeDueDate = function () {

		var dateTime = new Date($scope.orderedDate);
		dateTime = moment(dateTime).format("YYYY-MM-DD HH:mm:ss");

		var new_date = moment(dateTime, "YYYY-MM-DD").add(21, 'days').format();

		$scope.dueDate = new Date(new_date);
		$scope.dueDateOptions = {
			// dateDisabled: disabled,
			formatYear: 'yy',
			maxDate: new Date(2050, 5, 22),
			minDate: new Date(new_date),
			startingDay: 1
		};

	}

	
	$scope.calculateFields =function(itemSize,printType,itemName){
		
		var promise = $http.get("/calculateLineDetailsFields", {
				params: {
					"itemSize": itemSize,
					"printType":printType,
					"itemName":itemName
				}
			});

		var promiseResponse = function (response) {

			$scope.pcsPerHour = response.data.pcsPerHour;
			$scope.pcsPerSheet = response.data.pcsPerSheet;
			$scope.productCode = response.data.productCode;
		}
		var promiseRejection = function (rejection) {

			toastr.error('Failed to load Item PCS Hours !', 'Error');
		}
		promise.then(promiseResponse, promiseRejection);
	}


	function loadPaperTypeMaster() {

		var promise = $http.get("/getPaperTypeMasterDetails");

		var promiseResponse = function (response) {

			$scope.paperTypes = response.data;
		}
		var promiseRejection = function (rejection) {

			toastr.error('Failed to load Paper Types !', 'Error');
		}
		promise.then(promiseResponse, promiseRejection);
	}


	$scope.btnSaveLineDetails = function () {

		if ($scope.inStock == "Yes") {
			$scope.printingRequired = "No";
			$scope.mfgRequired = "No";
		}

		var lineItems = {
					'itemId': $scope.itemId,
					'itemName': $scope.itemName,
					'itemSize':$scope.itemSize,
					
					'designNo':$scope.designNo,
					'designName':$scope.designName,
					'paperColor':$scope.paperColor,
					'paperGsm':$scope.paperGsm,
					'printType':$scope.printType,
					'color1':$scope.color1,
					'color2':$scope.color2,
					
					'pcsPerSheet':$scope.pcsPerSheet,
					'pcsPerHour':$scope.pcsPerHour,
					'productCode':$scope.productCode,
					
					'itemDesc': $scope.itemDesc,
					'paperTypeId': $scope.paperTypeId,
					
					'orderValue': $scope.orderValue,
					'orderedQty': $scope.orderedQty,
					
					'inStock': $scope.inStock,
					'printingRequired': $scope.printingRequired,
					'mfgRequired': $scope.mfgRequired,		
					'dueDate':$scope.dueDate,
				};

		    if($scope.buttonAction == "AddNew"){
		    	$scope.lineDetails.push(lineItems);
		    	
		    	$scope.calcTotalOrderValue();
				
				$uibModalInstance.close('save');
		    }else if($scope.buttonAction == "EditUpdate"){
		    	
		    	var index = $scope.lineDetails.indexOf($scope.selectedLineDetails);
				
		    	$scope.lineDetails[index] = lineItems;
		    	
		    	$scope.calcTotalOrderValue();
		    	
		    	$uibModalInstance.close('save');
		    }
			
	}

	$scope.cancelModal = function () {

		$uibModalInstance.dismiss('close');
	}

	init();

});