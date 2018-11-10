let login = angular.module('login', []);
let config = {headers: {'Content-Type': 'application/json; charset=utf-8;'}};

login.controller('loginController', function ($scope, $http, $window) {

    $scope.getCurrentUserInfo = function () {
        $scope.userName = "";
        $scope.userRole = "";
    };

    $scope.signIn = function () {
        let data = {
            name: $scope.name,
            password: $scope.password
        };
        if ($scope.name == null || $scope.password == null || $scope.password === "" || $scope.name === "") {
            $scope.message = "Enter name and password";
        } else {
            $http.post("signIn", data, config).then(function (response) {
                if (!response.data) $scope.message = "Name or password incorrect";
                else $window.location.href = '/user'
            });
        }
    };
});

login.directive('navBar', function () {
    return {restrict: 'E', templateUrl: 'navbar.html'}
});