let admin = angular.module('admin', []);
let config = {headers: {'Content-Type': 'application/json;charset=utf-8;'}};

admin.controller('adminController', function ($scope, $http, $window) {

    $scope.getLastVersion = function () {
        $http.post("getLastVersion", config).then(function (response) {
            $scope.lastVersion = "Last version: " + response.data;
        });
    };

    $scope.getCurrentVersion = function () {
        $http.post("getCurrentVersion", config).then(function (response) {
            if (response.data === "") $scope.currentVersion = "Complete database download is required";
            else $scope.currentVersion = "Current version: " + response.data;
        });
    };

    $scope.getNewVersions = function () {
        $http.post("getNewVersions", config).then(function (response) {
            if (response.data === "") $scope.newVersions = "";
            else if (response.data.length > 0) $scope.newVersions = "Next updates required: " + response.data;
            else $scope.newVersions = "All data are actual";
        });
    };

    $scope.disabled = false;

    $scope.installComplete = function () {
        $scope.disabled = true;
        $http.post("installComplete", config).then(function (response) {
            $scope.disabled = !response.data;
        });
    };

    $scope.installOneUpdate = function () {
        $scope.disabled = true;
        $http.post("installOneUpdate", config).then(function (response) {
            $scope.disabled = !response.data;
        });
    };

    $scope.installUpdates = function () {
        $scope.disabled = true;
        $http.post("installUpdates", config).then(function (response) {
            $scope.disabled = !response.data;
        });
    };
});