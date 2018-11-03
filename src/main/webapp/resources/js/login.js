let login = angular.module('login', []);
let config = {headers: {'Content-Type': 'application/json; charset=utf-8;'}};

login.controller('loginController', function ($scope, $http) {

    $scope.getCurrentUserInfo = function () {
        $http.get("rest/getCurrentUserInfo", config).then(function (response) {
            if (Array.isArray(response.data) && response.data.length > 0) {
                $scope.userName = response.data[0];
                $scope.userRole = response.data[1];
            }
        });
    };
});

login.directive('navBar', function () {
    return {restrict: 'E', templateUrl: 'navbar.html'}
});