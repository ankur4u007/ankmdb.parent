var App = angular.module('App', []);
 
App.controller('mediaController',function($scope, $http, $sce) {
	$scope.$watch(function($scope){
		return $scope.postUrl;
	},function(newvalue){
		$scope.serverUrl = $scope.postUrl;
		$scope.serverUrlResource = $scope.serverUrl+'resources'+'/';
		$http.get($scope.serverUrl+'getAllMedia')
  			.success(function(response){
  				$scope.meidaResults = $scope.removeReferer(response);
  				}
  			);
	});
	$scope.displayImgUrl = function(snippet) {
		  return $sce.trustAsHtml(snippet);
		};  
	$scope.removeReferer = 	function removeReferer(response){
		var imgProp={};
		imgProp.style= ";width:50px; height:60px;";
			for(entityBo in response){
				if(response[entityBo].imageUrl != undefined){
					response[entityBo].imageUrl = ReferrerKiller.imageHtml(response[entityBo].imageUrl,imgProp);
				} else {
					response[entityBo].imageUrl = ReferrerKiller.imageHtml($scope.serverUrlResource+'image404.1.jpg',imgProp);
				}
			}
			return response;
		};	
});

function removeReferer(response){
	for(entityBo in response){
		if(response[entityBo].imageUrl != undefined){
			response[entityBo].imageUrl = ReferrerKiller.imageHtml(response[entityBo].imageUrl);
		}
	}
	return response;
};