let registration = angular.module('registration', []);
let config = {headers: {'Content-Type': 'application/json; charset=utf-8;'}};

registration.controller('registrationController', function ($scope, $http, $window, $timeout) {

    $scope.getCurrentUserInfo = function () {
        $scope.userName = "";
        $scope.userRole = "";
    };

    $scope.signUp = function () {
        let data = {
            name: $scope.name,
            password: $scope.password
        };
        if ($scope.name == null || $scope.password == null || $scope.password === "" || $scope.name === "") {
            $scope.message = "Enter name and password";
        } else {
            $http.post("signUp", data, config).then(function (response) {
                if (!response.data) $scope.message = "This user already exists";
                else {
                    $scope.message = "Registered successfully";
                    $timeout(function () {
                        $window.location.href = '/user';
                    }, 1000);
                }
            });
        }
    };
});

registration.directive('navBar', function () {
    return {restrict: 'E', templateUrl: 'navbar.html'}
});
