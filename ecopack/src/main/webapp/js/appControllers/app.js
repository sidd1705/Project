var myApp = angular.module("ecoPaperApp",['ngAnimate','ui.bootstrap','ui.router','ui.sortable','ui.sortable.multiselection','toastr','angucomplete-alt','ui.grid', 'ui.grid.pagination', 'ui.grid.selection', 'ui.grid.exporter']);

myApp.config(function(toastrConfig) {
	  angular.extend(toastrConfig, {
		  
		  closeButton: true,
		  progressBar: true,
		  timeOut: 2000  
	  });
});

myApp.config(['$qProvider', function ($qProvider) {
    $qProvider.errorOnUnhandledRejections(false);
}]);


myApp.run(function($rootScope) {
	  $rootScope.typeOf = function(value) {
	    return typeof value;
	  };
	})

	.directive('stringToNumber', function() {
	  return {
	    require: 'ngModel',
	    link: function(scope, element, attrs, ngModel) {
	      ngModel.$parsers.push(function(value) {
	        return '' + value;
	      });
	      ngModel.$formatters.push(function(value) {
	        return parseFloat(value);
	      });
	    }
	  };
	});


