let admin = angular.module('admin', []);
let config = {headers: {'Content-Type': 'application/json;charset=utf-8;'}};
let urlPrefix = "/fias/";

admin.controller('adminController', function ($scope, $http, $timeout) {

    $scope.getCurrentUserInfo = function () {
        $http.get(urlPrefix + "user-info", config).then(function (response) {
            $scope.userName = response.data[0];
            $scope.userRole = response.data[1];
        });
    };

    $scope.getServerStatus = function () {
        $http.get(urlPrefix + "module-status", config).then(function (response) {
            $scope.serverStatus = response.data.toLowerCase();
            if ($scope.serverStatus === "working") $scope.disabled = false;
            if ($scope.serverStatus === "updating") $scope.disabled = true;
            if ($scope.serverStatus === "update_error") {
                $scope.serverStatus = serverStatus.replace("_", " ") + ", check logs";
                $scope.disabled = false;
            }
        });
    };

    $scope.refreshServerStatus = function () {
        $scope.getServerStatus();
        $timeout(function () {
            $scope.refreshServerStatus();
        }, 3000);
    };

    $scope.getLastVersion = function () {
        $http.get(urlPrefix + "last-version", config).then(function (response) {
            $scope.lastVersion = response.data;
            $scope.getNewVersions();
        });
    };

    $scope.getNewVersions = function () {
        $http.get(urlPrefix + "new-versions", config).then(function (response) {
            $scope.newVersions = response.data;
            $scope.getCurrentVersion();
        });
    };

    $scope.getCurrentVersion = function () {
        $http.get(urlPrefix + "current-version", config).then(function (response) {
            $scope.currentVersion = response.data;
            $scope.getInformation();
        });
    };

    $scope.information = [];
    $scope.getInformation = function () {
        if ($scope.currentVersion === null) {
            $scope.information.push("Complete database download is required");
            $scope.isComplete = true;
        } else {
            $scope.information.push("Current version: " + $scope.currentVersion);
            $scope.isComplete = false;
        }
        if ($scope.lastVersion !== null) {
            $scope.information.push("Last version: " + $scope.lastVersion);
            if ($scope.currentVersion === $scope.lastVersion) {
                $scope.information.push("All data are actual");
                $scope.isDelta = false;
            } else if ($scope.currentVersion !== null) {
                $scope.information.push("Next updates are required: " + $scope.newVersions.toString().replace(/,/g, ', '));
                $scope.isDelta = true;
            }
        } else {
            $scope.information.push("Check connection with fias.nalog.ru");
            $scope.isComplete = false;
            $scope.isDelta = false;
        }
    };

    $scope.updateDatabase = function (action) {
        $scope.disabled = true;
        $scope.serverStatus = "updating";
        $http.get(urlPrefix + action, config);
    };

    $scope.frame = "fias";

    $scope.signUp = function () {
        if ($scope.name == null || $scope.password == null || $scope.password === "" || $scope.name === "") {
            $scope.signUpMessage = "Enter name and password";
        } else {
            $http.get(urlPrefix + "sign-up?name=" + $scope.name + "&password=" + $scope.password, config)
                .then(function (response) {
                    if (!response.data) $scope.signUpMessage = "This user already exists";
                    else {
                        $scope.signUpMessage = "Registered successfully";
                        $scope.getAllUsers();
                    }
                });
        }
    };

    $scope.getAllUsers = function () {
        $http.get(urlPrefix + "users", config).then(function (response) {
            $scope.users = response.data;
        });
    };

    $scope.deleteUser = function (id) {
        $http.delete(urlPrefix + "user?id=" + id, config).then(function (response) {
            $scope.getAllUsers();
        });
    };

    $scope.blockUser = function (id) {
        $http.post(urlPrefix + "user?id=" + id, config).then(function (response) {
            $scope.getAllUsers();
        });
    };
});

admin.directive('navBar', function () {
    return {restrict: 'E', templateUrl: 'navbar.html'}
});