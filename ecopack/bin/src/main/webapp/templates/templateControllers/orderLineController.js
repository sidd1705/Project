var myApp = angular.module("ecoPaperApp");

myApp
		.controller(
				"orderLineController",
				[
						'$scope',
						'$http',
						'$uibModal',
						function($scope, $http, $uibModal) {

							function init() {
								$scope.ordered_date = new Date();
								$scope.booked_date = new Date();

								initOrderNumber();
								loadCustomerDetails();

								$scope.addNewDisabled = true;

							}
							
							
							$scope.dateOptions1 = {
						 		    //dateDisabled: disabled,
						 		    formatYear: 'yy',
						 		    maxDate: new Date(2050, 5, 22),
						 		    minDate: new Date(),
						 		    startingDay: 1,
						 		    minDateChange: date => {
						 		    	$scope.dateOptions.minDate = $scope.ordered_date;
						 			}
						    };
							// '2017-10-24 11:28:06'
							
							$scope.yyDate =function(){
							
								var dateTime = new Date($scope.ordered_date);
								dateTime = moment(dateTime).format("YYYY-MM-DD HH:mm:ss");
								console.log(dateTime);
									
								var new_date = moment(dateTime, "YYYY-MM-DD").add(21,'days').format();
								console.log(new_date);
								$scope.booked_date = new Date(new_date);
								$scope.dateOptions = {
							 		    //dateDisabled: disabled,
							 		    formatYear: 'yy',
							 		    maxDate: new Date(2050, 5, 22),
							 		    minDate: new Date(new_date),
							 		    startingDay: 1
							 		    
							    };
								
							}
							

							$scope.openOrdered_dateDatePicker = function() {
								$scope.ordered_datePopup = true;
							};

							$scope.openbooked_dateDatePicker = function() {
								$scope.booked_datePopup = true;
							};

							function initOrderNumber() {

								var promise = $http.get("/onPageLoadOrderLine");

								var promiseResponse = function(response) {

									$scope.order_number = response.data;
								}
								var promiseRejection = function(rejection) {

									var msg = "Error: Sorry, Something Went Wrong: Please try again later !";
								}
								promise.then(promiseResponse, promiseRejection);
							}

							// function loadCustomerDetails() {
							//
							// var promise = $http.get("/loadCustomerDetails");
							//
							// var promiseResponse = function(response) {
							//
							// $scope.customerDetails = response.data;
							// }
							// var promiseRejection = function(rejection) {
							//
							// var msg = "Error: Sorry, Something Went Wrong:
							// Please try again later !";
							// }
							// promise.then(promiseResponse, promiseRejection);
							// }

							function loadCustomerDetails() {

								var arr = new Array();

								var promise = $http.get("/loadCustomerDetails");

								var promiseResponse = function(response) {

									var customerDetails = response.data;

									angular.forEach(customerDetails, function(
											value, key) {
										arr.push({
											customerId : value.customer_id,
											customerName : value.customer_name
										});
									});

									$scope.customerList = arr;

								}
								var promiseRejection = function(rejection) {

									var msg = "Error: Sorry, Something Went Wrong: Please try again later !";
								}
								promise.then(promiseResponse, promiseRejection);
							}

							$scope.selectedCustomer = function(str) {
								if (str != null) {

									console.log(str.originalObject.customerId);
									console
											.log(str.originalObject.customerName);
									$scope.customer_id = str.originalObject.customerId;
									$scope.customer_name = str.originalObject.customerName;
								}

							}

							//				   
							// $scope.selectCustomer=function(){
							//						
							// var customer_id =
							// $scope.selectedCustomer.customer_id;
							// var customer_name =
							// $scope.selectedCustomer.customer_name;
							// $scope.customer_id=customer_id;
							// $scope.customer_name=customer_name;
							// // console.log(customer_id+" : "+customer_name);
							// }

							$scope.btnSaveOrder = function() {

								var orderDetails = {
									'order_number' : $scope.order_number,
									'customer_type' : $scope.customer_type,
									'customer_id' : $scope.customer_id,
									'customer_name' : $scope.customer_name,
									'ordered_date' : $scope.ordered_date,
									'booked_date' : $scope.booked_date,
									'sales_person' : $scope.sales_person,
									'currency' : $scope.currency
								// 'flow_status_code' : $scope.flow_status_code,
								};
								var promise = $http.post("/saveOrderInfo",
										orderDetails);

								var promiseResponse = function(response) {

									alert("Order Details saved Successfully");
									$scope.disableInput = true; // disable
									// customer name
									$scope.addNewDisabled = false; // enable
									// add new
									// button

								}
								var promiseRejection = function(rejection) {
									var msg = "Error! A problem has been occured while submitting your data.";
								}
								promise.then(promiseResponse, promiseRejection);
							}

							function getSelectedIndex(id) {

								for (var i = 0; i < $scope.lineItemDetails.length; i++)
									if ($scope.lineItemDetails[i].id == id)
										return i;
								return -1;
							}
							;

							$scope.btnDeleteRow = function(id) {
								var result = confirm('Are you sure?');
								if (result === true) {

									var index = getSelectedIndex(id);
									var lineItems = $scope.lineItemDetails[index];

									var line_id = lineItems.line_id;

									var promise = $http.get("/deleteLineItem",
											{
												params : {
													"line_id" : line_id
												}
											});

									var promiseResponse = function(response) {

										alert("Deleted Successfully...");
										$scope.getAllLineItems();
									}
									var promiseRejection = function(rejection) {

										alert("Failure");
									}
									promise.then(promiseResponse,
											promiseRejection);
								}
								;
							}

							$scope.getAllLineItems = function() {

								var promise = $http.get("/getAllLineDetails", {
									params : {
										"order_number" : $scope.order_number
									}
								});

								var promiseResponse = function(response) {

									$scope.lineItemDetails = response.data;
									$scope.calcTotal();
									var total_order_value = $scope.total_order_value;

								}
								var promiseRejection = function(rejection) {
									var msg = "Error! your data can not delete.";
								}
								promise.then(promiseResponse, promiseRejection);

							}

							$scope.calcTotal = function() {

								var total = 0;

								var lineItemDetails = $scope.lineItemDetails;

								for (var i = 0; i < lineItemDetails.length; i++) {

									var order_value = lineItemDetails[i].order_value;
									total = total + order_value;
								}
								$scope.total_order_value = total;
							}

							$scope.loadItemMaster = function() {

								var promise = $http
										.get(
												"/getItemMasterDetails",
												{
													params : {
														"item_type" : $scope.customer_type
													}
												});

								var promiseResponse = function(response) {

									$scope.itemMaster = response.data;
								}
								var promiseRejection = function(rejection) {

									alert('Failure');
								}
								promise.then(promiseResponse, promiseRejection);
							}

							$scope.btnAddLineItems = function() {

								$scope.item_id = "";
								$scope.item_desc = "";
								$scope.ordered_qty = "";
								$scope.order_value = "";
								$scope.item_size = "";
								$scope.paper_type = "";
								$scope.paper_gsm = "";
								$scope.print_type = "";
								$scope.sheets_in_kg = "";

								$scope.color_1 = "";
								$scope.color_2 = "";
								$scope.paper_color = "";
								$scope.buttonAction = "AddNew";
								$scope.loadItemMaster();

								$scope.modalInstance = $uibModal
										.open({
											ariaLabelledBy : 'modal-title',
											ariaDescribedBy : 'modal-body',
											templateUrl : 'templates/templateModals/editLineItems.html',
											controller : 'lineItemsController',
											controllerAs : '$ctrl',
											size : 'lg',
											scope : $scope,
											resolve : {

											}
										});
							}

							$scope.btnEditLineItems = function(id) {

								var index = getSelectedIndex(id);
								var lineItems = $scope.lineItemDetails[index];

								$scope.header_id = lineItems.header_id;
								$scope.line_id = lineItems.line_id;
								$scope.item_id = lineItems.item_id;
								$scope.item_name = lineItems.item_name;
								$scope.item_desc = lineItems.item_desc;
								$scope.ordered_qty = lineItems.ordered_qty;
								$scope.order_value = lineItems.order_value;
								$scope.item_size = lineItems.item_size;
								$scope.paper_type = lineItems.paper_type;
								$scope.paper_gsm = lineItems.paper_gsm;
								$scope.print_type = lineItems.print_type;
								$scope.sheets_in_kg = lineItems.sheets_in_kg;
								$scope.in_stock = lineItems.in_stock;
								$scope.printing_required = lineItems.printing_required;
								$scope.mfg_required = lineItems.mfg_required;
								$scope.color_1 = lineItems.color_1;
								$scope.color_2 = lineItems.color_2;
								$scope.paper_color = lineItems.paper_color;
								$scope.buttonAction = "EditUpdate";

								$scope.modalInstance = $uibModal
										.open({
											ariaLabelledBy : 'modal-title',
											ariaDescribedBy : 'modal-body',
											templateUrl : 'templates/templateModals/editLineItems.html',
											controller : 'lineItemsController',
											controllerAs : '$ctrl',
											size : 'lg',
											scope : $scope,
											resolve : {

											}
										});
							}

							init();
						} ]);

/*--------------------------- Modal Controller--------------------------------------------------*/

myApp.controller("lineItemsController", function($scope, $http,
		$uibModalInstance) {

	function init() {

		$scope.order_number = $scope.order_number;
		$scope.item_type = $scope.customer_type;

		initSelectList();
	}

	function initSelectList() {

		$scope.inStock = [ {
			value : 1,
			label : 'Yes'
		}, {
			value : 2,
			label : 'No'
		} ];

		$scope.mfgRequired = [ {
			value : 1,
			label : 'Yes'
		}, {
			value : 2,
			label : 'No'
		} ];

		$scope.printRequired = [ {
			value : 1,
			label : 'Yes'
		}, {
			value : 2,
			label : 'No'
		} ];

		if ($scope.in_stock == "Yes") {
			$scope.in_stock = $scope.inStock[0].label;
		} else if ($scope.in_stock == "No") {
			$scope.in_stock = $scope.inStock[1].label;
		}

		if ($scope.printing_required == "Yes") {
			$scope.printing_required = $scope.printRequired[0].label;
		} else if ($scope.printing_required == "No") {
			$scope.printing_required = $scope.printRequired[1].label;
		}

		if ($scope.mfg_required == "Yes") {
			$scope.mfg_required = $scope.mfgRequired[0].label;
		} else if ($scope.mfg_required == "No") {
			$scope.mfg_required = $scope.mfgRequired[1].label;
		}

		// $scope.selectedItem = $scope.selectedItem[$scope.item_id].item_name;

	}

	function getSelectedIndex(id) {
		for (var i = 0; i < $scope.lineItemDetails.length; i++)
			if ($scope.lineItemDetails[i].id == id)
				return i;
		return -1;
	}
	;

	$scope.btnSaveLineDetails = function() {

		if ($scope.in_stock == "Yes") {
			$scope.printing_required = "No";
			$scope.mfg_required = "No";
		}

		var action = $scope.buttonAction;

		if (action == "AddNew") {

			var lineItems = {
				'order_number' : $scope.order_number,
				'item_id' : $scope.item_id,
				'item_desc' : $scope.item_desc,
				'order_value' : $scope.order_value,
				'ordered_qty' : $scope.ordered_qty,
				'item_size' : $scope.item_size,
				'paper_type' : $scope.paper_type,
				'paper_gsm' : $scope.paper_gsm,
				'print_type' : $scope.print_type,
				'sheets_in_kg' : $scope.sheets_in_kg,
				'in_stock' : $scope.in_stock,
				'printing_required' : $scope.printing_required,
				'mfg_required' : $scope.mfg_required,
				'color_1' : $scope.color_1,
				'color_2' : $scope.color_2,
				'paper_color' : $scope.paper_color
			};

			var promise = $http.post("/saveItemLineDetails", lineItems);

			var promiseResponse = function(response) {
				var status = response.data;

				$scope.getAllLineItems();
				$uibModalInstance.close('save');

			}
			var promiseRejection = function(rejection) {

				alert("fail");
			}
			promise.then(promiseResponse, promiseRejection);

		} else if (action == "EditUpdate") {

			var lineItems = {
				'header_id' : $scope.header_id,
				'line_id' : $scope.line_id,
				'item_id' : $scope.item_id,
				'item_desc' : $scope.item_desc,
				'order_value' : $scope.order_value,
				'ordered_qty' : $scope.ordered_qty,
				'item_size' : $scope.item_size,
				'paper_type' : $scope.paper_type,
				'paper_gsm' : $scope.paper_gsm,
				'print_type' : $scope.print_type,
				'sheets_in_kg' : $scope.sheets_in_kg,
				'printing_required' : $scope.printing_required,
				'mfg_required' : $scope.mfg_required,
				'in_stock' : $scope.in_stock,
				'color_1' : $scope.color_1,
				'color_2' : $scope.color_2,
				'paper_color' : $scope.paper_color
			};
			var promise = $http.post("/updateLineItemDetails", lineItems);

			var promiseResponse = function(response) {

				$scope.getAllLineItems();
				$uibModalInstance.close('save');
			}
			var promiseRejection = function(rejection) {

				$uibModalInstance.close('save');
			}
			promise.then(promiseResponse, promiseRejection);

		}

	}

	$scope.cancelModal = function() {

		$uibModalInstance.dismiss('close');
	}

	init();

});
