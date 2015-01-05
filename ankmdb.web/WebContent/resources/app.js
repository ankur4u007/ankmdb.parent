var App = angular.module('App', []);
 
App.controller('mediaController', function($scope, $http) {
	$scope.$watch(function($scope){
		return $scope.postUrl;
	},function(newvalue){
		$scope.serverUrl = $scope.postUrl;
		$scope.serverUrlResource = $scope.serverUrl+'resources'+'/';
		$http.get($scope.serverUrl+'getAllMedia')
  			.success(function(response){
  				$scope.meidaResults = response;
  				}
  			);
	});
	
});