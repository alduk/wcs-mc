var app = angular.module('wcs-mc', []);

app.controller('CatalogListCtrl', function($scope, $http) {
	$http.get("catalog/json").success(function(data) {
		$scope.catalogs = data;
		// console.log(data);
		// console.log($scope.catalogs1);
	});
	$scope.orderProp = "id";
	$scope.reverse = false;
});