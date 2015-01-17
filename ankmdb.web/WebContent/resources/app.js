var App = angular.module('App', []);
 
App.controller('mediaController',function($scope, $http, $sce) {
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

function printobj(obj){
	var a="";
	for(val in obj){
		var c ="";
		for(valb in obj[val]){
			c+=" "+valb+ ":"+obj[val][valb];
		}
		a+=" "+val + ":" + obj[val]+":"+c;
	}
	return a;
}
