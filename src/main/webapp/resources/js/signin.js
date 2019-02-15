let login = angular.module('login', []);
let config = {headers: {'Content-Type': 'application/json; charset=utf-8;'}};

login.controller('loginController', function ($scope, $http, $window) {

    $scope.getCurrentUserInfo = function () {
        $scope.userName = "";
        $scope.userRole = "";
    };

    $scope.signIn = function () {
        if ($scope.name == null || $scope.password == null || $scope.password === "" || $scope.name === "") {
            $scope.message = "Введите имя пользователя и пароль";
        } else {
            $http.get("/fias/sign-in?name=" + $scope.name + "&password=" + $scope.password, config)
                .then(function (response) {
                    if (!response.data) $scope.message = "Имя пользователя или пароль указаны неверно";
                    else $window.location.href = '/fias/user'
                });
        }
    };
});

login.directive('navBar', function () {
    return {restrict: 'E', templateUrl: 'navbar.html'}
});