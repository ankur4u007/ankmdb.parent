var App = angular.module('App', []);
 
App.controller('mediaController', function($scope, $http) {
	$scope.serverUrl = 'http://localhost:8080/ankmdb.web/';
	$scope.serverUrlResource = 'http://localhost:8080/ankmdb.web/resources/';
  $http.get($scope.serverUrl+'getAllMedia')
  			.success(function(response){
  				$scope.meidaResults = response;
  				}
  			);
});