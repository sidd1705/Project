var myApp = angular.module("ecoPaperApp");

myApp.filter('total', function () {
	return function (input, object, property) {
		var i =  input.length;
			var total = 0;
			while (i--)
				total += input[i][object][property];
			return total;
		}
});

myApp.controller("oeeController", function($scope, $http, $filter,toastr,$state) {


	function init() {
		
		$scope.oeePlanDate = new Date();

		$scope.plan = defaultOeeProductionPlan;
		
		loadMachineTypes();
		loadMachineMaster();
		loadProductCodes();

		$scope.reportStatus = null;
	}

	$scope.oeePlanDatePicker = function() {
		$scope.oeePlanDatePopup = true;
	};

	$scope.staticData = {
		'prod_plan' : 'Prodution Plan',
		'actual_prod' : 'Actual Production',
		'prod_loss' : 'Production Loss',
		'time_loss' : 'Time Loss(In Minutes)'
	};

	var defaultOeeProductionPlan = {
		'prodPlanFirstHour' : 0,
		'prodPlanSecondHour' : 0,
		'prodPlanThirdHour' : 0,
		'prodPlanFourthHour' : 0,
		'prodPlanFifthHour' : 0,
		'prodPlanSixthHour' : 0,
		'prodPlanSeventhHour' : 0,
		'prodPlanEightthHour' : 0,
		'actualProdFirstHour' : 0,
		'actualProdSecondHour' : 0,
		'actualProdThirdHour' : 0,
		'actualProdFourthHour' : 0,
		'actualProdFifthHour' : 0,
		'actualProdSixthHour' : 0,
		'actualProdSeventhHour' : 0,
		'actualProdEightthHour' : 0,
		'prodLossFirstHour' : 0,
		'prodLossSecondHour' : 0,
		'prodLossThirdHour' : 0,
		'prodLossFourthHour' : 0,
		'prodLossFifthHour' : 0,
		'prodLossSixthHour' : 0,
		'prodLossSeventhHour' : 0,
		'prodLossEightthHour' : 0,
		'timeLossFirstHour' : 0,
		'timeLossSecondHour' : 0,
		'timeLossThirdHour' : 0,
		'timeLossFourthHour' : 0,
		'timeLossFifthHour' : 0,
		'timeLossSixthHour' : 0,
		'timeLossSeventhHour' : 0,
		'timeLossEightthHour' : 0,
		'totalProductionPlan' : 0,	
		'totalActualProduction' : 0,
		'totalProductionLoss' : 0,
		'totalTimeLoss' : 0
//		'operator':'',
//		'maintenanceOperator':'',
//		'productionSupervisor':'',
//		'productionManager':'',
//		'machineId':'',
//		'machineTypeId':'',
//		'productId1':'',
//		'productId2':'',
//		'productionPlanId':'',
//		'shift':''
	};


	function resetLossReport() {
		var lossReport = {
			machineId : 0,
			operatorId : 0,
			shiftId : 0,
			lossReportDate : '',
			machineLossId : 0,
			lossReportId : 0,
			firstHour : 0,
			secondHour : 0,
			thirdHour : 0,
			fourthHour : 0,
			fifthHour : 0,
			sixthHour : 0,
			seventhHour : 0,
			eightthHour : 0,
			totalHours : 0
		};

		return lossReport;
	}

	function loadMachineTypes() {
		var promise = $http.get("/loadMachineTypes");

		var promiseResponse = function(response) {

			$scope.machineTypes = response.data;
		}
		var promiseRejection = function(rejection) {
			toastr.error('Failed lo load Machine Types !', 'Error');

		}
		promise.then(promiseResponse, promiseRejection);
	}

	function loadMachineMaster() {
		var promise = $http.get("/loadMachines");

		var promiseResponse = function(response) {

			$scope.machineMaster = response.data;
		}
		var promiseRejection = function(rejection) {

			toastr.error('Failed lo load Machines !', 'Error');
		}
		promise.then(promiseResponse, promiseRejection);
	}

	function loadProductCodes() {
		var promise = $http.get("/loadProductCodes");

		var promiseResponse = function(response) {

			$scope.product1 = response.data;
			//$scope.product2 = response.data;
		}
		var promiseRejection = function(rejection) {
			toastr.error('Failure to load Product Codes !', 'Error');
			
		}
		promise.then(promiseResponse, promiseRejection);
	}

	$scope.getPcsPerHour =function(productCodeId1){
		
		$scope.prods = $filter('filter')($scope.product1, { productCodeId: productCodeId1 });
		$scope.pcsPerHour =$scope.prods[0].pcsPerHour;
	}
	

	$scope.selectMachineType = function() {

		$scope.machineTypeId = $scope.selectedMachineType.machineTypeId;

		var promise = $http.get("/loadLossTypesForMachine", {
			params : {
				"machineTypeId" : $scope.machineTypeId
			}
		});
		var promiseResponse = function(response) {

			$scope.lossTypes = response.data;
		}
		var promiseRejection = function(rejection) {
			toastr.error('Failed to Load Loss Types for Selected Machine', 'Error');

		}
		promise.then(promiseResponse, promiseRejection);
	}

	$scope.btnLoadScheduledPlan = function() {

		$scope.machineTypeId = $scope.selectedMachineType.machineTypeId;

		var data = {
			'mrnNumber' : $scope.mrnNumber,
			'machineTypeId' : $scope.machineTypeId,
			'machineId' : $scope.machineId,
			'productionScheduleDate' : $scope.oeePlanDate,
			'shift' : $scope.shift,
			'productCodeId' : $scope.productCodeId1,
			'productCodeId2' : $scope.productCodeId2
		}
		var promise = $http.post("/getScheduledProduction", data);

		var promiseResponse = function(response) {

			var oeeReportData = response.data;
			
			var productionSchedule = {};
			var oeeProductionPlan = {};

			var oeeLossReport = [];
			var oeeLossReportObject = {};
			
			if (oeeReportData.length > 1) {

				var tempProductionSchedule = {};
				var tempOeeProductionPlan = [];
				var tempOeeLossReport = [];

				var filteredOeeProductionPlan = null;
				var filteredOeeLossReport = null;

				var oeeReportDataFiltered = [];


				angular.forEach(oeeReportData, function(value, key) {

					if (key == 0) {
						tempProductionSchedule = value.productionSchedule;
					}
					tempOeeProductionPlan.push(value.oeeProductionPlan);
					tempOeeLossReport.push(value.lossReport);
				});

				filteredOeeProductionPlan = $filter('filter')(tempOeeProductionPlan, {
					machineTypeId : $scope.machineTypeId
				});
				filteredOeeLossReport = $filter('filter')(tempOeeLossReport, {
					machineTypeId : $scope.machineTypeId
				});

				var tempObj;
				var temp1;
				var temp2;

				if (filteredOeeProductionPlan.length == filteredOeeLossReport.length && filteredOeeProductionPlan.length != 0 && filteredOeeLossReport != 0) {

					for (var i = 0; i < filteredOeeProductionPlan.length; i++) {

						temp1 = filteredOeeProductionPlan[i];
						temp2 = filteredOeeLossReport[i];
						tempObj = {
							'productionSchedule' : tempProductionSchedule,
							'oeeProductionPlan' : temp1,
							'lossReport' : temp2
						};
						oeeReportDataFiltered.push(tempObj);
					}
				}
				oeeReportData = oeeReportDataFiltered;

				if (oeeReportData.length == $scope.lossTypes.length) {
					
					toastr.success('Schedule found in OEE Report !', 'Success');
					var lossReport = [];
					angular.forEach(oeeReportData, function(value, key) {

						$scope.reportStatus = "Update";

						if (key == 0) {
							oeeProductionPlan = value.oeeProductionPlan;
							productionSchedule = value.productionSchedule;
						}
						$scope.pcsPerHour = productionSchedule.pcsPerHour;
						$scope.productionQty = productionSchedule.productionQty;
						$scope.orderNumber= productionSchedule.orderNumber;
						
						lossReport.push(value.lossReport);
					});
					$scope.plan = oeeProductionPlan;
					
					angular.forEach($scope.lossTypes, function(value, key) {

						var lossType = value;
						var machineLossId = lossType.machineLossId;

						angular.forEach(lossReport, function(value, key) {

							var lossReportObj = value;
							var machineLossIdInReport = lossReportObj.machineLossId;

							if (machineLossId === machineLossIdInReport) {

								oeeLossReportObject = {
									'lossTypes' : lossType,
									'lossReport' : lossReportObj
								};
								oeeLossReport.push(oeeLossReportObject);
							}
						});

					});
					$scope.oeeLossReport = oeeLossReport;
				} else if (filteredOeeProductionPlan.length == 0 && filteredOeeLossReport == 0) {
					toastr.success('Machine Changed !', 'Success');
					
					oeeReportData = response.data;
					
					$scope.reportStatus = "Insert";
					angular.forEach(oeeReportData, function(value, key) {

						if (key == 0) {
							oeeProductionPlan = value.oeeProductionPlan;
							productionSchedule = value.productionSchedule;
						}

					});
					$scope.pcsPerHour = productionSchedule.pcsPerHour;
					$scope.productionQty = productionSchedule.productionQty;
					$scope.orderNumber= productionSchedule.orderNumber;

					$scope.plan = defaultOeeProductionPlan;

					angular.forEach($scope.lossTypes, function(value, key) {

						var lossType = value;
						var lossReportObj = resetLossReport();

						oeeLossReportObject = {
							'lossTypes' : lossType,
							'lossReport' : lossReportObj
						};
						oeeLossReport.push(oeeLossReportObject);
					});

					$scope.oeeLossReport = oeeLossReport;
				}

			} else if (oeeReportData.length == 0) {
				toastr.warning('Schedule not found !', 'Warning');
				$state.reload();
				
			} else if (oeeReportData.length == 1) {
				toastr.success('Found in Production Schedule !', 'Success');
				
				$scope.reportStatus = "Insert";
				angular.forEach(oeeReportData, function(value, key) {

					if (key == 0) {
						oeeProductionPlan = value.oeeProductionPlan;
						productionSchedule = value.productionSchedule;
					}

				});
				$scope.pcsPerHour = productionSchedule.pcsPerHour;
				$scope.productionQty = productionSchedule.productionQty;
				$scope.orderNumber= productionSchedule.orderNumber;

				$scope.plan = defaultOeeProductionPlan;

				angular.forEach($scope.lossTypes, function(value, key) {

					var lossType = value;

					var lossReportObj = resetLossReport();

					oeeLossReportObject = {
						'lossTypes' : lossType,
						'lossReport' : lossReportObj
					};
					oeeLossReport.push(oeeLossReportObject);
				});

				$scope.oeeLossReport = oeeLossReport;

			}

		}
		var promiseRejection = function(rejection) {

			toastr.error('Failure !', 'Error');
		}
		promise.then(promiseResponse, promiseRejection);

	}
	
	$scope.losstotal ={
			'sumTimeLossFirstHour':0,
			'sumTimeLossSecondHour':0,
			'sumTimeLossThirdHour':0,
			'sumTimeLossFourthHour':0,
			'sumTimeLossFifthHour':0,
			'sumTimeLossSixthHour':0,
			'sumTimeLossSeventhHour':0,
			'sumTimeLossEightthHour':0,
	}
	
	$scope.matchTimeLossReport =function(){
		
		
		var calculatedTimeLoss = [ $scope.plan.timeLossFirstHour, $scope.plan.timeLossSecondHour,$scope.plan.timeLossThirdHour,$scope.plan.timeLossFourthHour,$scope.plan.timeLossFifthHour,$scope.plan.timeLossSixthHour,$scope.plan.timeLossSeventhHour,$scope.plan.timeLossEightthHour];
		var enteredTimeLoss = [ $scope.losstotal.sumTimeLossFirstHour, $scope.losstotal.sumTimeLossSecondHour,$scope.losstotal.sumTimeLossThirdHour,$scope.losstotal.sumTimeLossFourthHour,$scope.losstotal.sumTimeLossFifthHour,$scope.losstotal.sumTimeLossSixthHour,$scope.losstotal.sumTimeLossSeventhHour,$scope.losstotal.sumTimeLossEightthHour];
		
		// compare calculatedTimeLoss & enteredTimeLoss for each hour
		return calculatedTimeLoss.length == enteredTimeLoss.length && calculatedTimeLoss.every(function(element, index) {
		    return element === enteredTimeLoss[index]; 
		});
	}

	$scope.saveOEEReport = function() {
		$scope.machineTypeId = $scope.selectedMachineType.machineTypeId;

		var data = {
			'mrnNumber' : $scope.mrnNumber,
			'machineId' : $scope.machineId,
			'productionScheduleDate' : $scope.oeePlanDate,
			'shift' : $scope.shift,
			'productCodeId' : $scope.productCodeId1,
			'productCodeId2' : $scope.productCodeId2,
			'machineTypeId' : $scope.machineTypeId,
		}

		var oeeReport = {
			'productionSchedule' : data,
			'oeeProductionPlan' : $scope.plan,
			'oeeLossReportList' : $scope.oeeLossReport,
			'machineTypeId' : $scope.machineTypeId,
			'currentUserId' : $scope.currentUser.userId
		}
		
		if($scope.isTimeLossReportMatched === true && $scope.reportStatus === 'Insert') {

			var promise = $http.post("/saveOEEReport", oeeReport);

			var promiseResponse = function(response) {
				toastr.success('OEE Report Saved Successfully !', 'Success');
				
				$state.reload();
				
			}
			var promiseRejection = function(rejection) {
				toastr.error('Failed to Save OEE Report!', 'Error');
			
			}
			promise.then(promiseResponse, promiseRejection);
		} else if($scope.isTimeLossReportMatched === true && $scope.reportStatus === 'Update') {

			var promise = $http.post("/updateOEEReport", oeeReport);

			var promiseResponse = function(response) {
				toastr.success('OEE Report Updated Successfully!', 'Success');
				$state.reload();
				
			}
			var promiseRejection = function(rejection) {
				toastr.error('Failed to Update OEE Report!', 'Error');
			
			}
			promise.then(promiseResponse, promiseRejection);
		}else{
			toastr.warning('Unable to save : Calculated Loss does not Mateched with OEE Loss Report!', 'Warning');
		}
	}


	init();

});