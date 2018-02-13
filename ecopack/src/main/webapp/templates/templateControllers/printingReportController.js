var myApp = angular.module("ecoPaperApp");


myApp.controller("printingReportController", function($scope, $http, $compile, $timeout,toastr) {

	function init() {

		$scope.getAllPrintingSchedule();
	}
	
    $scope.formatters = {};
    
	$scope.printingSchedule = {
			enableFiltering: true,
			paginationPageSizes: [25, 50, 75],
	    paginationPageSize: 25,
		    columnDefs: [
		    	{ field: 'orderNumber'},
		    	{ field: 'orderedDate'},
		    	{ field: 'itemId',visible: false},		    	
		    	{ field: 'itemDesc'},
		    	{ field: 'orderedQty'},
		    	{ field: 'printingRequired'},	 
		    	{ field: 'mfgRequired',visible: false},	 
		    	{ field: 'itemName',visible: false},	 
		    	{ field: 'itemSize'},	 
		    	{ field: 'designNo',visible: false},	 
		    	{ field: 'designName'},	 
		    	{ field: 'paperColor',visible: false},
		    	{ field: 'paperGsm'},
		    	{ field: 'printType'},
		    	{ field: 'color1'},
		    	{ field: 'color2'},
		    	{ field: 'printingLineId',visible:false},
		    	{ field: 'headerId',visible:false},
		    	{ field: 'itemId',visible:false},
		    	{ field: 'pcsPerSheet'},
		    	{ field: 'noOfSheetsToPrint',visible:false},
		    	{field:'extraNoOfSheetsToPrint',visible:false},
		    	{ field: 'sheetsInKg',visible:false},
		    	{ field: 'machineId',visible:false},
		    	{ field: 'paperLotNum'},
		    	{ field: 'operator'},
		    	{ field: 'noOfSheetsPrinted',visible:false},
		    	{ field: 'remainingSheetsToPrint',visible:false},
		    	{ field: 'actualPrintingDate',visible:false},
		    	{ field: 'scheduleDate',visible:false}    	
		    ],
		    enableGridMenu: true,
		    enableSelectAll: true,
		    exporterCsvFilename: 'printingReport.csv',
		    exporterPdfDefaultStyle: {fontSize: 9},
		    exporterPdfTableStyle: {margin: [30, 30, 30, 30]},
		    exporterPdfTableHeaderStyle: {fontSize: 10, bold: true, italics: true, color: 'red'},
		    exporterPdfHeader: { text: "Printing Report", style: 'headerStyle' },
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
		    exporterExcelFilename: 'printingReport.xlsx',
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
		      
		      var aFormatDefnBlue ={
		        "font": boldStyle.id,
		        "alignment": { "wrapText": true }
		      };
		      
		      var aFormatDefnBrown ={
				        "font": boldStyle.id,
				        "alignment": { "wrapText": true }
			   };
		      
		      var aFormatDefnGray ={
				        "font": boldStyle.id,
				        "alignment": { "wrapText": true }
			   };
		      var aFormatDefnOrange ={
				        "font": boldStyle.id,
				        "alignment": { "wrapText": true }
			   };
		      var aFormatDefnGreen ={
				        "font": boldStyle.id,
				        "alignment": { "wrapText": true }
			   };
		      var aFormatDefnYellow ={
				        "font": boldStyle.id,
				        "alignment": { "wrapText": true }
			   };
		      var aFormatDefnCyan ={
				        "font": boldStyle.id,
				        "alignment": { "wrapText": true }
			   };
		      var aFormatDefnBlack ={
				        "font": boldStyle.id,
				        "alignment": { "wrapText": true }
			   };
		      var aFormatDefnMagneta ={
				        "font": boldStyle.id,
				        "alignment": { "wrapText": true }
			   };
		      var aFormatDefnSilver ={
				        "font": boldStyle.id,
				        "alignment": { "wrapText": true }
			   };
		      var aFormatDefnLavendar ={
				        "font": boldStyle.id,
				        "alignment": { "wrapText": true }
			   };
		      var aFormatDefnViolet ={
				        "font": boldStyle.id,
				        "alignment": { "wrapText": true }
			   };
		      var aFormatDefnPink ={
				        "font": boldStyle.id,
				        "alignment": { "wrapText": true }
			   };
		      
		      
		      var formatter = stylesheet.createFormat(aFormatDefn);
		      var formatterBlue = stylesheet.createFormat(aFormatDefnBlue);
		      var formatterBrown = stylesheet.createFormat(aFormatDefnBrown);
		      var formatterGray = stylesheet.createFormat(aFormatDefnGray);
		      var formatterOrange = stylesheet.createFormat(aFormatDefnOrange);
		      var formatterGreen = stylesheet.createFormat(aFormatDefnGreen);
		      var formatterYellow = stylesheet.createFormat(aFormatDefnYellow);
		      var formatterCyan = stylesheet.createFormat(aFormatDefnCyan);
     	      var formatterMagneta = stylesheet.createFormat(aFormatDefnMagneta);
		      var formatterBlack = stylesheet.createFormat(aFormatDefnBlack);
		      var formatterSilver = stylesheet.createFormat(aFormatDefnSilver);
		      var formatterLavendar = stylesheet.createFormat(aFormatDefnLavendar);
		      var formatterViolet = stylesheet.createFormat(aFormatDefnViolet);
		      var formatterPink = stylesheet.createFormat(aFormatDefnPink);

		      // save the formatter
		      $scope.formatters['bold'] = formatter;

		      aFormatDefn = {
		        "font": stdStyle.id,
		        "fill": { "type": "pattern", "patternType": "solid", "fgColor": "FFFF0000" },
		        "alignment": { "wrapText": true }
		      }; 
		      
		      aFormatDefnBlue = {
		        "font": stdStyle.id,
		        "fill": { "type": "pattern", "patternType": "solid", "fgColor": "FFC9912B" },
		        "alignment": { "wrapText": true }
		      };
		      
		      aFormatDefnBrown={
				        "font": stdStyle.id,
				        "fill": { "type": "pattern", "patternType": "solid", "fgColor": "FF6A180F" },
				        "alignment": { "wrapText": true }
				      };
		      
		      aFormatDefnGray={
				        "font": stdStyle.id,
				        "fill": { "type": "pattern", "patternType": "solid", "fgColor": "FF545454" },
				        "alignment": { "wrapText": true }
				      };
		      aFormatDefnOrange={
				        "font": stdStyle.id,
				        "fill": { "type": "pattern", "patternType": "solid", "fgColor": "FFFF8C00" },
				        "alignment": { "wrapText": true }
				      };
		      aFormatDefnGreen={
				        "font": stdStyle.id,
				        "fill": { "type": "pattern", "patternType": "solid", "fgColor": "FF25BF58" },
				        "alignment": { "wrapText": true }
				      };
		      aFormatDefnYellow={
				        "font": stdStyle.id,
				        "fill": { "type": "pattern", "patternType": "solid", "fgColor": "FFFFFF00" },
				        "alignment": { "wrapText": true }
				      };
		      aFormatDefnCyan={
				        "font": stdStyle.id,
				        "fill": { "type": "pattern", "patternType": "solid", "fgColor": "FF00FFFF" },
				        "alignment": { "wrapText": true }
				      };

		      aFormatDefnMagneta={
				        "font": stdStyle.id,
				        "fill": { "type": "pattern", "patternType": "solid", "fgColor": "FFFF00FF" },
				        "alignment": { "wrapText": true }
				      };
		      aFormatDefnBlack={
				        "font": stdStyle.id,
				        "fill": { "type": "pattern", "patternType": "solid", "fgColor": "FF000000" },
				        "alignment": { "wrapText": true }
				      };
		      aFormatDefnSilver={
				        "font": stdStyle.id,
				        "fill": { "type": "pattern", "patternType": "solid", "fgColor": "FFC0C0C0" },
				        "alignment": { "wrapText": true }
				      };
		      aFormatDefnLavendar={
				        "font": stdStyle.id,
				        "fill": { "type": "pattern", "patternType": "solid", "fgColor": "FFE6E6FA" },
				        "alignment": { "wrapText": true }
				      };
		      aFormatDefnViolet={
				        "font": stdStyle.id,
				        "fill": { "type": "pattern", "patternType": "solid", "fgColor": "FFEE82EE" },
				        "alignment": { "wrapText": true }
				      };
		      aFormatDefnPink={
				        "font": stdStyle.id,
				        "fill": { "type": "pattern", "patternType": "solid", "fgColor": "FFFFC0CB" },
				        "alignment": { "wrapText": true }
				      };
		      var singleDefn = {
		        font: stdStyle.id,
		        format: '#,##0.0'
		      };
		      formatter = stylesheet.createFormat(aFormatDefn);
		      formatterBlue = stylesheet.createFormat(aFormatDefnBlue);
		      formatterBrown = stylesheet.createFormat(aFormatDefnBrown);
		      formatterGray = stylesheet.createFormat(aFormatDefnGray);
		      formatterOrange = stylesheet.createFormat(aFormatDefnOrange);
	          formatterGreen = stylesheet.createFormat(aFormatDefnGreen);
     	      formatterYellow = stylesheet.createFormat(aFormatDefnYellow);
     	     formatterCyan = stylesheet.createFormat(aFormatDefnCyan);
     	     formatterMagneta = stylesheet.createFormat(aFormatDefnMagneta);
     	     formatterBlack = stylesheet.createFormat(aFormatDefnBlack);
     	     formatterSilver = stylesheet.createFormat(aFormatDefnSilver);
     	     formatterLavendar = stylesheet.createFormat(aFormatDefnLavendar);
     	     formatterViolet = stylesheet.createFormat(aFormatDefnViolet);
     	     formatterPink = stylesheet.createFormat(aFormatDefnPink);
     	
		      // save the formatter
		      $scope.formatters['red'] = formatter;
		       $scope.formatters['blue'] = formatterBlue;
		       $scope.formatters['brown'] = formatterBrown;
		       $scope.formatters['gray'] = formatterGray;
		       $scope.formatters['orange'] = formatterOrange;
		       $scope.formatters['green'] = formatterGreen;
		       $scope.formatters['yellow'] = formatterYellow;
		       $scope.formatters['cyan'] = formatterCyan;
		       $scope.formatters['magneta'] = formatterMagneta;
		       $scope.formatters['black'] = formatterCyan;
		       $scope.formatters['silver'] = formatterCyan;
		       $scope.formatters['lavender'] = formatterCyan;
		       $scope.formatters['violet'] = formatterCyan;
		       $scope.formatters['pink'] = formatterCyan;
		     
		       

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
		        cols.push({ value: 'Printing Report', metadata: {style: formatterId.id} });
		        sheet.data.push(cols);
		    },
		    exporterFieldFormatCallback: function(grid, row, gridCol, cellValue) {
		     // set metadata on export data to set format id. See exportExcelHeader config above for example of creating
		     // a formatter and obtaining the id
		     var formatterId = null;

		     switch(cellValue){
		     case 'Red':
		    	 formatterId = $scope.formatters['red'].id;
		    	 break;
		     case 'Gold':
		    	 formatterId = $scope.formatters['blue'].id;
		    	 break;	 
		     case 'Brown':
		    	 formatterId = $scope.formatters['brown'].id;
		    	 break;	
		     case 'Gray':
		    	 formatterId = $scope.formatters['gray'].id;
		    	 break;	
		     case 'Orange':
		    	 formatterId = $scope.formatters['orange'].id;
		    	 break;	
		     case 'Green':
		    	 formatterId = $scope.formatters['green'].id;
		    	 break;	
		     case 'Yellow':
		    	 formatterId = $scope.formatters['yellow'].id;
		    	 break;	
		     case 'Cyan':
		    	 formatterId = $scope.formatters['cyan'].id;
		    	 break;	
		     case 'Magneta':
		    	 formatterId = $scope.formatters['magneta'].id;
		    	 break;	
		     case 'Black':
		    	 formatterId = $scope.formatters['black'].id;
		    	 break;	
		     case 'Silver':
		    	 formatterId = $scope.formatters['silver'].id;
		    	 break;	
		     case 'Lovender':
		    	 formatterId = $scope.formatters['lovender'].id;
		    	 break;	
		     case 'Violet':
		    	 formatterId = $scope.formatters['violet'].id;
		    	 break;	
		     case 'Pink':
		    	 formatterId = $scope.formatters['pink'].id;
		    	 break;	
		    	 

		    	 
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

	
	 
   $scope.getAllPrintingSchedule= function(){
		
		var promise = $http.get("/api/admin/getDashboard");
		
		var promiseResponse = function(response) {
		
           var getSize =response.data.length;
			
			if(getSize==0){
				toastr.warning('Data Not found for Printing Report Details !', 'Warning');
				 $scope.printingSchedule.data = response.data;
			}
			else{
				
				 $scope.printingSchedule.data = response.data;
				
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
