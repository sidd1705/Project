var myApp = angular.module("ecoPaperApp");

myApp.config(function ($qProvider) {
    $qProvider.errorOnUnhandledRejections(false);
});

myApp.controller("orderReportController", function($scope, $http, $compile, $timeout,toastr) {

	function init() {

		$scope.getAllOrderDetails
		();
	}
	
	 $scope.formatters = {};
	$scope.orderReport = {
			enableFiltering: true,
	    paginationPageSizes: [25, 50, 75],
	    paginationPageSize: 25,
		    columnDefs: [
		    	
		    	{ field: 'orderNumber'},		    	
		    	{ field: 'customerType'},
		    	{field:'customerName'},
		    	{ field: 'salesExecutiveId'},
		    	{ field: 'orderedDate'},
		    	{ field: 'dueDate'},
		    	{ field: 'currency'},
		    	{ field: 'totalOrderValue'},
		    	{ field:'flowStatusCode'},
		    	{ field: 'creationDate'},
		    	{ field: 'closedDate'}	    	
		    ],
		    enableGridMenu: true,
		    enableSelectAll: true,
		    exporterCsvFilename: 'OrderReport.csv',
		    exporterPdfDefaultStyle: {fontSize: 9},
		    exporterPdfTableStyle: {margin: [30, 30, 30, 30]},
		    exporterPdfTableHeaderStyle: {fontSize: 10, bold: true, italics: true, color: 'red'},
		    exporterPdfHeader: { text: "Order Report", style: 'headerStyle' },
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
		    exporterExcelFilename: 'OrderReport.xlsx',
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
		        "fill": { "type": "pattern", "patternType": "solid", "fgColor": "FFFFC7CE" },
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
		        cols.push({ value: 'Order Report', metadata: {style: formatterId.id} });
		        sheet.data.push(cols);
		    },
		    
		    exporterFieldFormatCallback: function(grid, row, gridCol, cellValue) {
		     // set metadata on export data to set format id. See exportExcelHeader config above for example of creating
		     // a formatter and obtaining the id
		     var formatterId = null;
		     if (cellValue && typeof cellValue === 'string' && cellValue.startsWith('R')) {
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
	 
   $scope.getAllOrderDetails= function(){
		
		var promise = $http.get("/api/admin/getOrderDashboard");
		
		var promiseResponse = function(response) {
		
           var getSize =response.data.length;
			
			if(getSize==0){
				toastr.warning('Data Not found for Order Details !', 'Warning');
				 $scope.orderReport.data = response.data;
			}
			else{
				
				 $scope.orderReport.data = response.data;
				
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
