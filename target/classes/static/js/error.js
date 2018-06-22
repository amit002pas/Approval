var errorApp = angular.module("errorAppModule", []);

errorApp.controller("errorController", function($window){
	$window.alert("Token Expired/ Invalid Token");
});

