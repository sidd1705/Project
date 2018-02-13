var myApp = angular.module("ecoPaperApp");

myApp.controller("printingController", function ($scope, $http, $uibModal, uiSortableMultiSelectionMethods, toastr) {


	function init() {

		$scope.getAllPrintingSchedule();
	}

	$scope.getAllPrintingSchedule = function () {

		var promise = $http.get("/getAllPrintingSchedule");

		var promiseResponse = function (response) {

			$scope.printingScheduleData = response.data;

			if ($scope.printingScheduleData.length == 0) {

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

	function getSelectedIndex(id) {
		for (var i = 0; i < $scope.printingScheduleData.length; i++)
			if ($scope.printingScheduleData[i].id == id)
				return i;
		return -1;
	};


	$scope.btnAddPrintingSchedule = function (id) {
		var index = getSelectedIndex(id);
		var printing = $scope.printingScheduleData[index];

		$scope.printingLineId = printing.printingLineId;
		$scope.headerId = printing.headerId;
		$scope.lineId = printing.lineId;

		$scope.orderNumber = printing.orderNumber;
		$scope.orderedDate = printing.orderedDate;

		$scope.itemName = printing.itemName;
		$scope.itemSize = printing.itemSize;
		$scope.designNo = printing.designNo;
		$scope.designName = printing.designName;
		$scope.paperColor = printing.paperColor;
		$scope.paperGsm = printing.paperGsm;
		$scope.printType = printing.printType;
		$scope.color1 = printing.color1;
		$scope.color2 = printing.color2;

		$scope.itemDesc = printing.itemDesc;
		$scope.orderedQty = printing.orderedQty;
		$scope.printingRequired = printing.printingRequired;
		$scope.mfgRequired = printing.mfgRequired;

		$scope.percentage= printing.percentage;
		$scope.pcsPerSheet = printing.pcsPerSheet;
		$scope.noOfSheetsToPrint = printing.noOfSheetsToPrint;
		$scope.extraNoOfSheetsToPrint = printing.extraNoOfSheetsToPrint;
		$scope.sheetsInKg = printing.sheetsInKg;


		$scope.machineId = printing.machineId;
		$scope.paperLotNum = printing.paperLotNum;
		$scope.operator = printing.operator;

		$scope.noOfSheetsPrinted = printing.noOfSheetsPrinted;
		$scope.remainingSheetsToPrint = printing.remainingSheetsToPrint;
		$scope.actualPrintingDate = printing.actualPrintingDate;
		$scope.scheduleDate = printing.scheduleDate;
		$scope.flowStatusCode = printing.flowStatusCode;
		console.log("----------------------------------------------");
		console.log("$scope.flowStatusCode="+$scope.flowStatusCode);
        console.log("$scope.scheduleDate="+$scope.scheduleDate);
        console.log("$scope.actualPrintingDate="+$scope.actualPrintingDate);
        console.log("$scope.remainingSheetsToPrint="+$scope.remainingSheetsToPrint);
        console.log("$scope.noOfSheetsPrinted="+$scope.noOfSheetsPrinted);
        console.log("$scope.operator="+$scope.operator);
        console.log("$scope.paperLotNum="+$scope.paperLotNum);
        console.log("$scope.machineId="+$scope.machineId);
        console.log("$scope.sheetsInKg="+$scope.sheetsInKg);
        console.log("$scope.extraNoOfSheetsToPrint="+$scope.extraNoOfSheetsToPrint);
        console.log("$scope.noOfSheetsToPrint="+$scope.noOfSheetsToPrint);
        console.log("$scope.pcsPerSheet="+$scope.pcsPerSheet);
        console.log("$scope.mfgRequired="+$scope.mfgRequired);
        console.log("$scope.printingRequired="+$scope.printingRequired);
        console.log("$scope.orderedQty="+$scope.orderedQty);
        console.log("$scope.itemDesc="+$scope.itemDesc);
        console.log("$scope.color2="+$scope.color2);
        console.log("$scope.color1="+$scope.color1);
        console.log("$scope.printType="+$scope.printType);
        console.log("$scope.paperGsm="+$scope.paperGsm);
        console.log("$scope.paperColor="+$scope.paperColor);
        console.log("$scope.designName="+$scope.designName);
        console.log("$scope.designNo="+$scope.designNo);
        console.log("$scope.itemSize="+$scope.itemSize);
        console.log("$scope.itemName="+$scope.itemName);
        console.log("$scope.orderedDate="+$scope.orderedDate);
        console.log("$scope.orderNumber="+$scope.orderNumber);
        console.log("$scope.lineId="+$scope.lineId);
        console.log("$scope.headerId="+$scope.headerId);
        console.log("$scope.printingLineId="+$scope.printingLineId);
		$scope.modalInstance = $uibModal.open({
			ariaLabelledBy: 'modal-title',
			ariaDescribedBy: 'modal-body',
			templateUrl: 'templates/templateModals/addPrintingSchedule.html',
			backdrop: false,
			controller: 'addPrintingSchedule',
			controllerAs: '$ctrl',
			/* size: 'lg',*/
			windowClass: 'app-modal-window',
			scope: $scope,
			resolve: {

			}
		});


	}

	$scope.getPrintingPositionPriority = function () {

		var printingTableData = $scope.printingScheduleData;
		var index = 0;

		$scope.setPrintingPositionPriority = [];

		angular.forEach(printingTableData, function (value, key) {
			index = index + 1;

			$scope.setPrintingPositionPriority.push({
				priorityId: index,
				printingLineId: value.printingScheduleAll.printingLineId
			});
		});

		var size = $scope.setPrintingPositionPriority.length;

		var printingSchedulePriority = $scope.setPrintingPositionPriority;

		var promise = $http.post("/updatePrintingSchedulePriority", printingSchedulePriority);

		var promiseResponse = function (response) {

			toastr.success('Priority updated Successfully !', 'Success');
			$scope.getAllPrintingSchedule();
		}
		var promiseRejection = function (rejection) {

			toastr.error('Failed to update Priority!', 'Error');
		}
		promise.then(promiseResponse, promiseRejection);

	}


	//	$scope.sortableOptions = {
	//			
	//			   update: function(e, ui) {
	//				  
	//				   $scope.getPrintingPositionPriority();
	//				   
	//				   console.log(e);
	//			    },
	//			   stop: function(e, ui) {
	//			     
	//				   
	//			   }
	//	};



	$scope.sortableOptions = uiSortableMultiSelectionMethods.extendOptions({

		update: function (e, ui) {


		},
		stop: function (e, ui) {

			$scope.getPrintingPositionPriority();
		}

	});

	init();
});

//---------------------------------------------------------------------------------------//


myApp.controller("addPrintingSchedule", function ($scope, $http, $uibModalInstance, toastr) {


	function init() {

		$scope.scheduleDate = new Date();
		$scope.actualPrintingDate = new Date();

		loadMachineMaster();
		
		$scope.noOfSheetsToPrint= Math.round(($scope.orderedQty / $scope.pcsPerSheet));
	}

	$scope.dateOptions = {
		minDate: new Date()
	};

	$scope.openScheduleDateDatePicker = function () {
		$scope.scheduleDatePopup = true;
	};

	$scope.openActualPrintingDateDatePicker = function () {
		$scope.actualPrintingDatePopup = true;
	};



	function loadMachineMaster() {

		var promise = $http.get("/loadPrintingMachines");

		var promiseResponse = function (response) {

			$scope.printingMachines = response.data;
		}
		var promiseRejection = function (rejection) {

			toastr.error('Failed to load Printing Machines!', 'Error');
		}
		promise.then(promiseResponse, promiseRejection);
	}

	
	$scope.btnUpdatePrintingDetails = function () {

		if ($scope.flowStatusCode == 'Approved' && $scope.noOfSheetsPrinted <= $scope.extraNoOfSheetsToPrint) {

			updatePrintingScheduleDetails();
		} else if ($scope.flowStatusCode == 'Partially Completed' && $scope.noOfSheetsPrinted <= $scope.remainingSheetsToPrint) {

			updatePrintingScheduleDetails();
		} else {

			toastr.error('Incorrect value for No. of Sheets Printed !', 'Error');
		}
	}

	 $scope.calculateFields = function(){
			
			$scope.extraNoOfSheetsToPrint= Math.round((($scope.noOfSheetsToPrint * $scope.percentage/100)+ $scope.noOfSheetsToPrint));

			var calcSheetsInKg = $scope.extraNoOfSheetsToPrint *0.015;	
			$scope.sheetsInKg = calcSheetsInKg.toFixed(2);
			//$scope.sheetsInKg= (parseInt(calcSheetsInKg)).toFixed(2);
	 }
	 
	 
	function updatePrintingScheduleDetails() {

		var printingSchedule = {

			'printingLineId': $scope.printingLineId,
			'headerId': $scope.headerId,
			'lineId': $scope.lineId,
			'pcsPerSheet': $scope.pcsPerSheet,
			'noOfSheetsToPrint': $scope.noOfSheetsToPrint,
			'percentage':$scope.percentage,
			'extraNoOfSheetsToPrint': $scope.extraNoOfSheetsToPrint,
			'sheetsInKg': $scope.sheetsInKg,
			'machineId': $scope.machineId,
			'paperLotNum': $scope.paperLotNum,
			'operator': $scope.operator,
			'actualPrintingDate': $scope.actualPrintingDate,
			'noOfSheetsPrinted': $scope.noOfSheetsPrinted,
			'remainingSheetsToPrint': $scope.remainingSheetsToPrint,
			'scheduleDate': $scope.scheduleDate
		};

		var promise = $http.post("/updatePrintingScheduleDetails", printingSchedule);


		var promiseResponse = function (response) {

			toastr.success('Printing Schedule get updated for Order Number: ' + $scope.orderNumber + ' !', 'Success');
			$scope.getAllPrintingSchedule();
			$uibModalInstance.close('save');
		}
		var promiseRejection = function (rejection) {

			toastr.error('Failed to update Printing Schedule!', 'Error');
		}
		promise.then(promiseResponse, promiseRejection);
	}

	$scope.cancelModal = function () {

		$uibModalInstance.dismiss('close');
	}



	init();


});