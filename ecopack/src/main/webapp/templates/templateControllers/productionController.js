var myApp = angular.module("ecoPaperApp");

myApp.controller("productionController", function ($scope, $http, $uibModal, toastr) {


	function init() {

		$scope.getProductionSchedule();
		$scope.productionScheduleDate = new Date();
	}


	$scope.getProductionSchedule = function () {

		var promise = $http.get("/getProductionSchedule");

		var promiseResponse = function (response) {

			$scope.productionScheduleData = response.data;

			if ($scope.printingScheduleData.length == 0) {

				$scope.datanotfound = true;
			} else {
				$scope.datanotfound = false;

			}

		}

		var promiseRejection = function (rejection) {

			toastr.error('Failed to Production Schedule !', 'Error');
		}
		promise.then(promiseResponse, promiseRejection);

	}


	$scope.btnAddProductionSchedule = function (production) {

		$scope.headerId = production.headerId;
		$scope.orderNumber = production.orderNumber;
		$scope.orderedDate = production.orderedDate;
		
		$scope.itemName = production.itemName;
		$scope.itemSize = production.itemSize;
		$scope.designNo = production.designNo;
		$scope.designName = production.designName;
		$scope.paperColor = production.paperColor;
		$scope.paperGsm = production.paperGsm;
		$scope.printType = production.printType;
		$scope.color1 = production.color1;
		$scope.color2 = production.color2;

		$scope.itemDesc = production.itemDesc;
		$scope.orderedQty = production.orderedQty;
		$scope.mfgRequired = production.mfgRequired;
		$scope.pcsPerHour=production.pcsPerHour;
		
		
		$scope.printingLineId = production.printingLineId;
		$scope.pcsPerSheet = production.pcsPerSheet;
		$scope.extraNoOfSheetsToPrint = production.extraNoOfSheetsToPrint;
		$scope.noOfSheetsPrinted = production.noOfSheetsPrinted;
		$scope.actualPrintingDate = production.actualPrintingDate;

		$scope.flowStatusCode = production.flowStatusCode;
		$scope.productCode=production.productCode;
		 $scope.flowStatusCode = production.flowStatusCode;
		 $scope.productionLineId = production.productionLineId;
		$scope.remainingProductionQty = production.remainingProductionQty;
		
		$scope.productionQty = $scope.noOfSheetsPrinted * $scope.pcsPerSheet;
		
		if($scope.remainingProductionQty == 0){
		
			 var requiredTimeInHHMM = ($scope.productionQty/$scope.pcsPerHour).toFixed(2);

			 $scope.requiredTimeHrs=requiredTimeInHHMM.split('.')[0];
			 $scope.requiredTimeMin=(requiredTimeInHHMM.split('.')[1]*0.6).toFixed(0);
		}else{
			
			 var requiredTimeInHHMM = ($scope.remainingProductionQty/$scope.pcsPerHour).toFixed(2);

			 $scope.requiredTimeHrs=requiredTimeInHHMM.split('.')[0];
			 $scope.requiredTimeMin=(requiredTimeInHHMM.split('.')[1]*0.6).toFixed(0);
		}
		
		
		
		 

         


		var length = $scope.itemSize.length;
		var itemSize = $scope.itemSize;
		var temp ="";
		switch(length){
		
		 case 3  : 
			          temp = $scope.itemSize+"MM TULIP";
			          break;
		 case 4  : 
			 		for (var i = (length - 1); i >= 0; i--){
			 			 var currentChar = itemSize[i];
			 			 if(i==2) {
							  temp = "*"+currentChar+temp;
						  }else {
							  temp = currentChar+temp;
						  }  
				    }
			 		if($scope.itemName.startsWith("I")){
			 			temp = "I"+temp;
			 		}
			 		break;
		 case 6  :
			 		if($scope.itemSize.includes(".")){
				  
		 			for (var i = (length - 1); i >= 0; i--){
		 				var currentChar = itemSize[i];

		 				if(i==2) {
		 						temp = "*"+currentChar+temp;
		 				}else {
		 						temp = currentChar+temp;
		 		        }  
		        	}
		 			console.log(temp);
		        }else{
			        	for (var i = (length - 1); i >= 0; i--){
			 				var currentChar = itemSize[i];
			 				if(i==4 || i==2) {
			 						temp = "*"+currentChar+temp;
			 				}else {
			 				temp = currentChar+temp;
			 		        }  
			        	}
			        	if($scope.itemName.startsWith("I")){
			        		temp = "I"+temp;
			        	}
		        }
			 		break;
		 case 7  :
		 		for (var i = (length - 1); i >= 0; i--){
		 				var currentChar = itemSize[i];
		 				if(i==5 || i==3) {
		 						temp = "*"+currentChar+temp;
		 				}else {
		 				temp = currentChar+temp;
		 		        }  
	            }
		 		break;
		 default :
			    temp= null;
		 		break;
			 		
		}
		
		$scope.generatedProductCode = temp;
		
		$scope.modalInstance = $uibModal.open({
			ariaLabelledBy: 'modal-title',
			ariaDescribedBy: 'modal-body',
			templateUrl: 'templates/templateModals/addProductionSchedule.html',
			backdrop: false,
			controller: 'addProductionSchedule',
			controllerAs: '$ctrl',
			/* size: 'lg',*/
			windowClass: 'app-modal-window',
			scope: $scope,
			resolve: {

			}
		});


	}

	init();
});

// ---------------------------------------------------------------------------------------//


myApp.controller("addProductionSchedule", function ($scope, $http, $uibModalInstance, toastr) {


	function init() {

		$scope.scheduleDate = new Date();
		loadMachineMaster();
		loadProductCodes();
	}
	
	$scope.dateOptions = {
		minDate: new Date()
	};
	$scope.openScheduleDateDatePicker = function () {
		$scope.scheduleDatePopup = true;
	};
	
	function loadMachineMaster() {

		var promise = $http.get("/loadProductionMachines");

		var promiseResponse = function (response) {

			$scope.machineMaster = response.data;

		}
		var promiseRejection = function (rejection) {

			toastr.error('Failed to load Machines !', 'Error');
		}
		promise.then(promiseResponse, promiseRejection);
	}

	function loadProductCodes() {

		var promise = $http.get("/loadProductCodes");

		var promiseResponse = function (response) {

			$scope.productCodeMaster = response.data;

			initProductCode($scope.productCodeMaster);
		}
		var promiseRejection = function (rejection) {

			toastr.error('Failed to load Product Codes !', 'Error');
		}
		promise.then(promiseResponse, promiseRejection);
	}

    function initProductCode(productCodeMaster){
    	
    	var product = findProductCode(productCodeMaster);
    	 $scope.productCodeId = product.productCodeId;
    	 $scope.pcsPerHour = product.pcsPerHour;
    	 
	 }
    
    function findProductCode(productCodeMaster) {
        var product=null;
        angular.forEach(productCodeMaster, function(value,key) {
        	 if(value.productCode === $scope.generatedProductCode)
        		 product = value;
        });
        return product;
    }

//	$scope.calcRequiredHours = function(){
//		var requiredHours = $scope.productionQty / $scope.pcsPerHour;
//		return requiredHours.toFixed(2);  // round of decimal value upto 2 decimal places
//	}

    function calculateShifts(){
    	
    	var noOfShifts = ($scope.requiredTimeHrs/8).toFixed(2);
    	
    	var totalShifts =noOfShifts.split('.')[0];
		
    	var remainingHours = $scope.requiredTimeHrs - (8 * totalShifts);
    	
    	var totalProductionPerShift = $scope.pcsPerHour * 8;
		 
    	var remainingProduction = $scope.productionQty;
    	for(var i=1;i <=totalShifts ;i++){
    		
    		 console.log("Shift: "+ i +" Production: "+ totalProductionPerShift);
    		 remainingProduction  = remainingProduction - totalProductionPerShift;
    	}
    
        if(remainingProduction > 0){
        	console.log(remainingProduction);
        }
    }
    
    $scope.checkMrnNumberExists =function(){
		
		var promise = $http.get("/checkMrnNumberIsExists", {
			params: {
				"mrnNumber": $scope.mrnNumber
			}
		});
		var promiseResponse = function (response) {

			var status= response.data;
			
			if(status == 1){
				toastr.error('MRN Number Already exist', 'Error');
				$scope.mrnNumber ="";
			}	
		}
		var promiseRejection = function (rejection) {

			toastr.error('Failed to Check MRN Number exist or not !', 'Error');
		}
		
		promise.then(promiseResponse, promiseRejection);
    }
    
	$scope.saveProductionScheduleDetails = function() {
		
		var requiredHours = $scope.requiredTimeHrs+":"+$scope.requiredTimeMin;
		
		calculateShifts();

		var productionSchedule = {
			'productionLineId': $scope.productionLineId,
			'printingLineId': $scope.printingLineId,
			'mrnNumber' : $scope.mrnNumber,
			'shift': $scope.shift,
			'machineId': $scope.machineId,
			'productCodeId': $scope.productCodeId,
			'pcsPerHour':$scope.pcsPerHour,
			'operator': $scope.operator,
			'productionQty': $scope.productionQty,
			'productionScheduleDate': $scope.productionScheduleDate,
			'productCode': $scope.productCode,
//			'requiredTimeHrs' : $scope.requiredTimeHrs,
//			'requiredTimeMin':$scope.requiredTimeMin,
			'requiredHours' : requiredHours,
			'remainingProductionQty':$scope.remainingProductionQty
		};

		var promise = $http.post("/saveProductionScheduleDetails", productionSchedule);

		var promiseResponse = function (response) {

			var status = response.data;
			if(status === 1){
				
				toastr.success('Production Schedule Saved Successfully !', 'Success');
				$scope.getProductionSchedule();
				$uibModalInstance.close('save');
			}else{
				toastr.warning('Machine is Busy for this Schedule date.Plese choose another date !', 'Warning');
			}
			
		}
		var promiseRejection = function (rejection) {

			toastr.error('Failed to Save Production Schedule !', 'Error');
		}
		promise.then(promiseResponse, promiseRejection);
	}

	$scope.cancelModal = function () {

		$uibModalInstance.dismiss('close');
	}

	init();
	
});