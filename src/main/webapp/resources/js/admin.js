let admin = angular.module('admin', []);
let config = {headers: {'Content-Type': 'application/json;charset=utf-8;'}};
let urlPrefix = "rest/";

admin.controller('adminController', function ($scope, $http) {

    $scope.getLastVersion = function () {
        $http.get(urlPrefix+"getLastVersion", config).then(function (response) {
            $scope.lastVersion = "Last version: " + response.data[0];
        });
    };

    $scope.getCurrentVersion = function () {
        $http.get(urlPrefix+"getCurrentVersion", config).then(function (response) {
            if (response.data[0] === null) $scope.currentVersion = "Complete database download is required";
            else $scope.currentVersion = "Current version: " + response.data[0];
        });
    };

    $scope.getNewVersions = function () {
        $http.get(urlPrefix+"getNewVersions", config).then(function (response) {
            if (response.data === "") $scope.newVersions = "";
            else if (response.data.length > 0) $scope.newVersions = "Next updates required: " + response.data;
            else $scope.newVersions = "All data are actual";
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
});