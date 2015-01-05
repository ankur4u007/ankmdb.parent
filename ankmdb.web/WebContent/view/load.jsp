<!doctype html>
<html ng-app="App" >
<head>
<link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css" rel="stylesheet">
  <script src="resources/angular-1.0.2.js"></script>
  <script src="resources/app.js"></script>
  <script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>
</head>
<body ng-controller="mediaController">

<table class="table table-striped table-hover">
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
    	<td><img ng-src="{{ entityBo.imageUrl || serverUrlResource+'image404.1.jpg' }}" style="width: 30px; height: 30px"/></td>   
    	<td width="40%"> <div ng-repeat="mediaBo in entityBo">{{ mediaBo.name }}</div></td>
    	<td> <div ng-repeat="mediaBo in entityBo">{{ mediaBo.sourceMachine }}</div></td>
    	<td> <a href="{{ entityBo.referenceUrl }}">{{ entityBo.referenceName }}</a></td> 
    	<td>{{ entityBo.rating }}</td>	
  </tr>
 </tbody>
</table>

</body>
</html>