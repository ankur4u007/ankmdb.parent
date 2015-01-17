<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html ng-app="App" >
<head>
<link href="resources/bootstrap.min.css" rel="stylesheet">
  <script src="resources/angular.min.js"></script>
  <script src="resources/app.js"></script>
</head>
<body ng-controller="mediaController"> 
<input type="hidden" ng-init='postUrl="<c:out value="${url}"/>"' ng-model="postUrl" />
<table class="table table-striped table-hover header-fixed" > 
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
  <tr ng-repeat="entityBO in meidaResults">
  		<td>
  			<iframe style="border 1px solid #ff0000" 
  					name="{{ entityBO.imageUrl + '#####' + mediaBO.sourceMachine}}"
  					scrolling="no" 
  					frameborder="no" 
  					allowtransparency="true" 
  					height="80px" 
  					width="60px"
  					src="resources/icon.html"
  					 >
  			</iframe>
  		</td>
    	<td width="40%"> <div ng-repeat="mediaBO in entityBO">{{ mediaBO.name }}</div></td>
    	<td> <div ng-repeat="mediaBO in entityBO">{{ mediaBO.sourceMachine }}</div></td>
    	<td> <a href="{{ entityBO.referenceUrl }}">{{ entityBO.referenceName }}</a></td> 
    	<td>{{ entityBo.rating }}</td>	
  </tr>
 </tbody>
</table>

</body>
</html>