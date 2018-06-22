var maintenanceApp = angular.module("maintenanceAppMolude", ['angularUtils.directives.dirPagination']);
var globalObj="";

maintenanceApp.controller("maintenanceController", function($scope, $http, $window ){
	var urlString = window.location.href;
	console.log(urlString);
	var url = new URL(urlString);
	$scope.token = url.searchParams.get("token");
	console.log($scope.token);
	var config = {
			headers: {
				'Authorization': $scope.token
			}
	};
	
	$scope.goToPage = function(value){
		if(value==1){
			$http.get('http://172.17.15.230:8888/maintenancePage?token='+$scope.token).then(function (response){
				console.log("maintenancePage");
			});
		}
		else if(value==2){
			$http.get('http://172.17.15.230:8888/auditPage?token='+$scope.token).then(function (response){
				console.log("audit Page");
			});
		}
		
	}
	
	$scope.query = {};
	$scope.queryBy = 'sumInfoId';
	$scope.flag='true';
	console.log("From Date "+$scope.fromDate);
	console.log("To Date "+$scope.toDate);	
	$scope.search=function(sumInfo,startDate,endDate){
		console.log("^^^ real sum info "+$scope.form.SumINFO);
		console.log("^^^^^^suminfo "+sumInfo);
		console.log("^^^^^^startdate "+startDate);
		console.log("^^^^^^enddate "+endDate);
		if($scope.form.type == 'SumINFO'){
			console.log("inside if");
			$scope.flag='false';
			$scope.list=[];
			console.log("fdvf");
			console.log("search button clicked");
			$http.get('http://172.17.15.230:8888/approval/maintenance/'+sumInfo, config).then(function(response){
				$scope.data1=response.data;
				console.log($scope.data1);
				console.log("^^^^^^"+$scope.data1.appResponse.status);
				if($scope.data1.appResponse.status=="Failure" && $scope.data1.appResponse.description=="Sum Info ID is not Valid")
					$window.alert("SumInfo not Valid");

				if($scope.data1.appResponse.status=="Success" && $scope.data1.appResponse.description=="No Windows Present for Sum Info ID"){
					$window.alert("No record found for this SumInfo");
				}
				for(var i=0;i<$scope.data1.appResponse.responseData.maintenanceWindows.length;i++){
					$scope.list.push($scope.data1.appResponse.responseData.maintenanceWindows[i]);
				}
			},function(error){
				console.log("error");
				$window.alert("SumInfo not Found");
			});
		}
		if($scope.form.type == 'Window Date'){
			console.log("inside else");
			$scope.list=[];
			$http.get('http://172.17.15.230:8888/approval/maintenance/all', config).then(function (response) {
				$scope.maintenanceInfoList = response.data;
				console.log(response);
				console.log($scope.maintenanceInfoList.length);

				console.log(new Date());
				var todayDate = new Date();
				$scope.upcomingList=[];

				var sd=new Date(startDate);
				var ed=new Date(endDate);
				console.log(sd);
				console.log(   "to^^^^^^^^^^^^ "+sd.getTime()    );
				console.log("ffrom^^^^^^^^^^^^ "+ed.getTime());
				for(var i=0;i<$scope.maintenanceInfoList.length;i++){
					console.log("inside loop");
					var startDateFromList = new Date($scope.maintenanceInfoList[i].effectiveDate);
					//var endDateFromList = new Date($scope.maintenanceInfoList[i].expiryDate);

					if(startDateFromList.getTime()>=sd.getTime() && startDateFromList.getTime()<=ed.getTime()){
						$scope.list.push($scope.maintenanceInfoList[i]);
					}
				}
				if($scope.list.length<=0)
					$window.alert("No Maintenance Found for this Range");	
				console.log("^^^^ "+$scope.list.length);

			});
			$scope.form.fromDate=null;
			$scope.form.toDate=null;
		}
		$scope.form.fromDate='';
		$scope.form.toDate='';
		$scope.form.sumINFO='';
	}
	$scope.sort = function(keyname){
		console.log("inside sort");
		$scope.sortKey = keyname;   //set the sortKey to the param passed
		$scope.reverse = !$scope.reverse; //if true make it false and vice versa
	}
	$scope.maintenanceInfos = function(){
		console.log("ok ok");
		$http.get('http://172.17.15.230:8888/approval/maintenance/all', config).then(function (response) {
			$scope.maintenanceInfoList = response.data;
			console.log(response);
			console.log($scope.maintenanceInfoList.length);

			console.log(new Date());
			var todayDate = new Date();
			$scope.upcomingList=[];
			$scope.ongoingList=[];
			$scope.prevList=[];
			$scope.superList=[];

			for(var i=0;i<$scope.maintenanceInfoList.length;i++){
				var startDateFromList = new Date($scope.maintenanceInfoList[i].effectiveDate);
				var endDateFromList = new Date($scope.maintenanceInfoList[i].expiryDate);

				if(startDateFromList.getTime()<=todayDate.getTime() && endDateFromList.getTime()>=todayDate.getTime()){
					$scope.ongoingList.push($scope.maintenanceInfoList[i]);
				}
				else if(endDateFromList.getTime()<=todayDate.getTime()){
					$scope.prevList.push($scope.maintenanceInfoList[i]);
				}
				else if(startDateFromList.getTime()>=todayDate.getTime()){
					$scope.upcomingList.push($scope.maintenanceInfoList[i]);
				}
			}

			$scope.superList.push($scope.upcomingList);
			$scope.superList.push($scope.ongoingList);
			$scope.superList.push($scope.prevList);
			console.log("^^^^ "+$scope.upcomingList.length);
			console.log("^^^^ "+$scope.ongoingList.length);
			console.log("^^^^ "+$scope.prevList.length);

		});

	}
	$scope.maintenanceInfos();

	$scope.loadValue = function(value){
		$scope.row = value;	
	}

});	
