//var myApp = angular.module("ecoPaperApp",['ui.router','ui.bootstrap','ngAnimate','ui.sortable','ui.sortable.multiselection','ngAnimate','ui.grid', 'ui.grid.pagination', 'ui.grid.selection', 'ui.grid.exporter']);

var myApp = angular.module("ecoPaperApp",[]);
myApp.config(['$qProvider', function ($qProvider) {
    $qProvider.errorOnUnhandledRejections(false);
}]);

