let admin = angular.module('admin', []);
let config = {headers: {'Content-Type': 'application/json;charset=utf-8;'}};
let urlPrefix = "rest/";

admin.controller('adminController', function ($scope, $http) {

    $scope.getCurrentUserInfo = function () {
        $http.get(urlPrefix + "getCurrentUserInfo", config).then(function (response) {
            if (Array.isArray(response.data) && response.data.length > 0) {
                $scope.userName = response.data[0];
                $scope.userRole = response.data[1];
            }
        });
    };

    $scope.getLastVersion = function () {
        $http.get(urlPrefix+"getLastVersion", config).then(function (response) {
            if (Array.isArray(response.data))
                $scope.lastVersion = "Last version: " + response.data[0];
        });
    };

    $scope.getCurrentVersion = function () {
        $http.get(urlPrefix+"getCurrentVersion", config).then(function (response) {
            if (Array.isArray(response.data)) {
                if (response.data[0] === null) $scope.currentVersion = "Complete database download is required";
                else $scope.currentVersion = "Current version: " + response.data[0];
            }
        });
    };

    $scope.getNewVersions = function () {
        $http.get(urlPrefix+"getNewVersions", config).then(function (response) {
            if (Array.isArray(response.data)) {
                if (response.data === "") $scope.newVersions = "";
                else if (response.data.length > 0) $scope.newVersions = "Next updates required: " +
                    response.data.toString().replace(/,/g,', ');
                else $scope.newVersions = "All data are actual";
            }
        });
    };

    $scope.disabled = false;

    $scope.installComplete = function () {
        $scope.disabled = true;
        $http.get(urlPrefix+"installComplete", config).then(function (response) {
            $scope.disabled = !response.data;
        });
    };

    $scope.installOneUpdate = function () {
        $scope.disabled = true;
        $http.get(urlPrefix+"installOneUpdate", config).then(function (response) {
            $scope.disabled = !response.data;
        });
    };

    $scope.installUpdates = function () {
        $scope.disabled = true;
        $http.get(urlPrefix+"installUpdates", config).then(function (response) {
            $scope.disabled = !response.data;
        });
    };

    $scope.frame = "fias";

    $scope.signUp = function () {
        let data = {
            name: $scope.name,
            password: $scope.password
        };
        if ($scope.name == null || $scope.password == null || $scope.password === "" || $scope.name === "") {
            $scope.signUpMessage = "Enter name and password";
        } else {
            $http.post(urlPrefix + "signUp", data, config).then(function (response) {
                if (!response.data) $scope.signUpMessage = "This user already exists";
                else {
                    $scope.signUpMessage = "Registered successfully";
                    $scope.getAllUsers();
                }
            });
        }
    };

    $scope.getAllUsers = function () {
        $http.get(urlPrefix + "getAllUsers", config).then(function (response) {
            $scope.users = response.data;
        });
    };
});

admin.directive('navBar', function () {
    return {restrict: 'E', templateUrl: 'navbar.html'}
});