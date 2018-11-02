let registration = angular.module('registration', []);
let config = {headers: {'Content-Type': 'application/json; charset=utf-8;'}};

registration.controller('registrationController', function ($scope, $http, $window, $timeout) {

    $scope.submitRegistration = function () {
        let data = {
            name: $scope.name,
            password: $scope.password
        };
        if ($scope.name == null || $scope.password == null || $scope.password === "" || $scope.name === "") {
            $scope.message = "Enter name and password";
        } else {
            $http.post("submitRegistration", data, config).then(function (response) {
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
