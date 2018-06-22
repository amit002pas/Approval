var auditApp = angular.module("auditApp", ['angularUtils.directives.dirPagination']);

auditApp.controller("auditController", function($scope, $http ){


	var urlString = window.location.href;
	console.log(urlString);
	var url = new URL(urlString);
	$scope.token = url.searchParams.get("token");
	console.log($scope.token);
	const config = {
			headers: {
				'Authorization': $scope.token
			}
	};

	$scope.storeData=function(data){
		console.log(data);
		$scope.info1=data;
		console.log("inside store Data");
		console.log($scope.info1);
	};

	$scope.goToPage = function(value){
		console.log("inside->"+value);
		if(value==1){
			console.log("m page");
			$http.get('http://172.17.15.230:8888/maintenancePage?token='+$scope.token).then(function (response){
				console.log("maintenancePage"+response);
			});
		}
		else if(value==2){
			console.log('a page');
			$http.get('http://172.17.15.230:8888/auditPage?token='+$scope.token).then(function (response){
				console.log("audit Page");
			});
		}

	}




	$scope.statusCount = function(){
		$http.get('http://172.17.15.230:8888/approval/audit/status', config).then(function (response) {
			$scope.statusInfo = response.data;
			console.log(response);
		});
	}

	$scope.maintenanceInfos = function(){
		$http.get('http://172.17.15.230:8888/approval/audit/all', config).then(function (response) {
			$scope.maintenanceInfosData = response.data;
			console.log($scope.maintenanceInfosData);
		});
	}

	$scope.maintenanceInfos();
	$scope.statusCount();

	$scope.loadValue = function(value){
		$scope.row = value;	
	}



	$scope.scheduleTableUpdate=function(row){
		$('#loaderRow'+$scope.info1.maintenanceAuditId).removeClass("hidden");
		$('#approveButton'+$scope.info1.maintenanceAuditId).addClass("hidden");
		$('#rejectButton'+$scope.info1.maintenanceAuditId).addClass("hidden");
		let param = {
				auditId:$scope.info1.maintenanceAuditId,
				sumInfoId:$scope.info1.maintenanceSchedule.sumInfoId,
				startDateTime: $scope.info1.maintenanceSchedule.startDateTime,
				expiryDate: $scope.info1.maintenanceSchedule.expiryDate,
				endDateTime:$scope.info1.maintenanceSchedule.endDateTime
		};
		console.log("row :"+row);
		if(row==-1){
			let request={
					id:$scope.info1.maintenanceAuditId,
					isApproved:-1
			}
			$http.put('http://172.17.15.230:8888/approval/audit/updateIsApproved', request, config).then(function(response){
				console.log("done");
				$scope.maintenanceInfos();
				$scope.statusCount();
			});
		}
		else{
			console.log(param);			
			$http.post('http://172.17.15.230:8888/approval/maintenance/update', param, config).then(function(response){
				let result=response.data;
				let status=result.addUpdateResponse.addUpdateAppResponse.status;
				
				console.log("Status->"+status);
				console.log($scope.info1);
				if(status=="Success"){
					if(result.adtResponse!=null){
						let request={
								id:$scope.info1.maintenanceAuditId,
								link:result.adtResponse
						}
						$http.put('http://172.17.15.230:8888/approval/audit/updateLink', request, config).then(function(response){
							console.log("done");
							$scope.maintenanceInfos();
							$scope.statusCount();
						});
						let isApprovedRequest={
								id:$scope.info1.maintenanceAuditId,
								isApproved:1
						}
						$http.put('http://172.17.15.230:8888/approval/audit/updateIsApproved', isApprovedRequest, config).then(function(response){
							console.log("done");
							$scope.maintenanceInfos();
							$scope.statusCount();
						});

					}
					else{
						window.alert("Error:diffMirgrationId is not generated!");
						let request={
								id:$scope.info1.maintenanceAuditId,
								isApproved:3
						}
						$http.put('http://172.17.15.230:8888/approval/audit/updateIsApproved', request, config).then(function(response){
							console.log("done");
							$scope.maintenanceInfos();
							$scope.statusCount();
						});

					}

				}
				else{
					let description = result.addUpdateResponse.addUpdateAppResponse.description;
					console.log(description);
					window.alert(description);
					console.log($scope.info1);
					let request={
							id:$scope.info1.maintenanceAuditId,
							isApproved:2
					}
					$http.put('http://172.17.15.230:8888/approval/audit/updateIsApproved', request, config).then(function(response){
						console.log("done");
						$scope.maintenanceInfos();
						$scope.statusCount();
					});
				}
			});
		}
	};


});