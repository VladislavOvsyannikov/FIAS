var clicker = angular.module('clicker', []);
var config = {headers: {'Content-Type': 'application/json;charset=utf-8;'}};

var counter = 0;
var message = "Please login after registration";

clicker.controller('getController', function ($scope, $http, $location, $window) {

    $scope.getUsername = function () {
        var url = "getUsername";
        $http.get(url, config).then(function (response) {
            $scope.username = response.data;
        });
    };

    $scope.getChampions = function () {
        var url = "getChampions";
        $http.get(url, config).then(function (response) {
            $scope.champions = response.data;
        });
    };

    $scope.getCurrentUser = function () {
        var url = "getCurrentUser";
        $http.get(url, config).then(function (response) {
            $scope.user = response.data;
            if ($scope.user!=="") counter = $scope.user.counter;

        });
    };

    $scope.getUsers = function () {
        var url = "getUsers";
        $http.get(url, config).then(function (response) {
            $scope.users = response.data;
        });
    };

    $scope.getCounter = function () {
        return counter;
    };

    $scope.getMessage = function () {
        return message;
    };

});

clicker.controller('postController', function ($scope, $http, $location, $window) {

    $scope.click = function () {
        var url = "click";
        $http.post(url, config).then(function (response) {
            counter++;
        });
    };

    $scope.submitRegistration = function () {
        var url = "submitRegistration";
        var data = {
            name: $scope.name,
            password: $scope.password
        };
        if ($scope.name == null || $scope.password == null || $scope.password === ""  || $scope.name === ""){
            message = "Enter nickname and password";
        }else {
            $http.post(url, data, config).then(function (response) {
                $scope.answer = response.data;
                if ($scope.answer === "true") {
                    $window.location.href = '/login';
                } else {
                    message = "This user already exists";
                }
            });
        }
    };

});