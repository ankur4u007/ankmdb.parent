<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html ng-app="App" >
<head>
<link href="resources/bootstrap.min.css" rel="stylesheet">
  <script src="resources/angular.min.js"></script>
  <script src="resources/app.js"></script>
  <script src="resources/bootstrap.min.js"></script>
  <script src="resources/refKill.js"></script>
</head>
<body ng-controller="mediaController"> 
<input type="hidden" ng-init='postUrl="<c:out value="${url}"/>"' ng-model="postUrl" />
<table class="table table-striped table-hover" > 
  <thead>
  	<tr> 
  		<th></th>
  		<th>FileName</th>
  		<th>Source</th>
  		<th>Imdb Name</th>
  		<th>Imdb Rating</th>
  	</tr>
  </thead>
  <tbody>
  <tr ng-repeat="entityBo in meidaResults">
  		<td><span ng-bind-html="displayImgUrl(entityBo.imageUrl)"></span></td>
    	<td width="40%"> <div ng-repeat="mediaBo in entityBo">{{ mediaBo.name }}</div></td>
    	<td> <div ng-repeat="mediaBo in entityBo">{{ mediaBo.sourceMachine }}</div></td>
    	<td> <a href="{{ entityBo.referenceUrl }}">{{ entityBo.referenceName }}</a></td> 
    	<td>{{ entityBo.rating }}</td>	
  </tr>
 </tbody>
</table>

</body>
</html>