var myApp = angular.module("ecoPaperApp");


myApp.controller("otpReportController", function($scope, $http, $compile, $timeout,toastr) {

	function init() {

		$scope.getAllOtpDetails();
	}
	
	 $scope.formatters = {};
	
	$scope.otpReport = {
			enableFiltering: true,
	    paginationPageSizes: [25, 50, 75],
	    paginationPageSize: 25,
		    columnDefs: [
		    	{ field: 'headerId',visible:false},
		    	{ field: 'orderNumber'},
		    	{ field: 'orderedDate'},
		    	{ field: 'dueDate'},
		    	{ field: 'itemId',visible: false},		    
		    	{ field: 'mfgRequired',visible: false},	 
		    	{ field: 'itemName'},	 
		    	{ field: 'printingLineId',visible:false},
		    	//{ field: 'salesExecutiveId'},
		    	//{ field: 'totalOrderValue'},
		    	
		    	{ field: 'actualPrintingDate'},
		    	{field:'readyDate'},
		    	{ field: 'dispatchedDate'}		       	
		    ],
		    enableGridMenu: true,
		    enableSelectAll: true,
		    exporterCsvFilename: 'OtpReport.csv',
		    exporterPdfDefaultStyle: {fontSize: 9},
		    exporterPdfTableStyle: {margin: [30, 30, 30, 30]},
		    exporterPdfTableHeaderStyle: {fontSize: 10, bold: true, italics: true, color: 'red'},
		    exporterPdfHeader: { text: "Otp Report", style: 'headerStyle' },
		    exporterPdfFooter: function ( currentPage, pageCount ) {
		      return { text: currentPage.toString() + ' of ' + pageCount.toString(), style: 'footerStyle' };
		    },
		    exporterPdfCustomFormatter: function ( docDefinition ) {
		      docDefinition.styles.headerStyle = { fontSize: 22, bold: true };
		      docDefinition.styles.footerStyle = { fontSize: 10, bold: true };
		      return docDefinition;
		    },
		    exporterPdfOrientation: 'portrait',
		    exporterPdfPageSize: 'LETTER',
		    exporterPdfMaxGridWidth: 500,
		    exporterCsvLinkElement: angular.element(document.querySelectorAll(".custom-csv-link-location")),
		    exporterExcelFilename: 'OtpReport.xlsx',
		    exporterExcelSheetName: 'Sheet1',
		    exporterExcelCustomFormatters: function ( grid, workbook, docDefinition ) {
		 
		      var stylesheet = workbook.getStyleSheet();
		      var stdStyle = stylesheet.createFontStyle({
		        size: 9, fontName: 'Calibri'
		      });
		      var boldStyle = stylesheet.createFontStyle({
		        size: 9, fontName: 'Calibri', bold: true
		      });
		      var aFormatDefn = {
		        "font": boldStyle.id,
		        "alignment": { "wrapText": true }
		      };
		      var formatter = stylesheet.createFormat(aFormatDefn);
		      // save the formatter
		      $scope.formatters['bold'] = formatter;
		 
		      aFormatDefn = {
		        "font": stdStyle.id,
		        "fill": { "type": "pattern", "patternType": "solid", "fgColor": "FFFFFFFF" },
		        "alignment": { "wrapText": true }
		      };
		      var singleDefn = {
		        font: stdStyle.id,
		        format: '#,##0.0'
		      };
		      formatter = stylesheet.createFormat(aFormatDefn);
		      // save the formatter
		      $scope.formatters['red'] = formatter;
		 
		      Object.assign(docDefinition.styles , $scope.formatters);
		 
		      return docDefinition;
		    },
		    exporterExcelHeader: function (grid, workbook, sheet, docDefinition) {
		        // this can be defined outside this method
		        var stylesheet = workbook.getStyleSheet();
		        var aFormatDefn = {
		          "font": { "size": 11, "fontName": "Calibri", "bold": true },
		          "alignment": { "wrapText": true }
		        };
		        var formatterId = stylesheet.createFormat(aFormatDefn);
		 
		        // excel cells start with A1 which is upper left corner
		        sheet.mergeCells('B1', 'C1');
		        var cols = [];
		        // push empty data
		        cols.push({ value: '' });
		        // push data in B1 cell with metadata formatter
		        cols.push({ value: 'OTP Report', metadata: {style: formatterId.id} });
		        sheet.data.push(cols);
		    },
		    
		    exporterFieldFormatCallback: function(grid, row, gridCol, cellValue) {
		     // set metadata on export data to set format id. See exportExcelHeader config above for example of creating
		     // a formatter and obtaining the id
		     var formatterId = null;
		     if (cellValue && typeof cellValue === 'string' && cellValue.startsWith('W')) {
		       formatterId = $scope.formatters['red'].id;
		     }
		 
		     if (formatterId) {
		       return {metadata: {style: formatterId}};
		     } else {
		       return null;
		     }
		   },
		   
		    onRegisterApi: function(gridApi){
		      $scope.gridApi = gridApi;
		    }
		  };
	 
   $scope.getAllOtpDetails= function(){
		
		var promise = $http.get("/api/admin/getOtpDashboard");
		
		var promiseResponse = function(response) {
		
           var getSize =response.data.length;
			
			if(getSize==0){
				toastr.warning('Data Not found for OTP Details !', 'Warning');
				 $scope.otpReport.data = response.data;
			}
			else{
				
				 $scope.otpReport.data = response.data;
			}
		}

		var promiseRejection = function(rejection) {
		
			console.log(rejection);
			alert(rejection.data.error+":"+rejection.data.message);
		}
		promise.then(promiseResponse, promiseRejection);
	}
	
	init();

});
