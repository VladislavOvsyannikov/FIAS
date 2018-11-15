let user = angular.module('user', []);
let config = {headers: {'Content-Type': 'application/json; charset=utf-8;'}};
let urlPrefix = "rest/";

user.controller('userController', function ($rootScope, $scope, $http) {

    $scope.getCurrentUserInfo = function () {
        $http.get(urlPrefix+"getCurrentUserInfo", config).then(function(response) {
            if (Array.isArray(response.data) && response.data.length > 0) {
                $scope.userName = response.data[0];
                $scope.userRole = response.data[1];
            }
        });
    };

    $scope.getObjectsByParentGuid = function () {
        let data = {guid: parentGuid, actual: $scope.actualAdvancedSearch};
        $scope.downloadingMessage = "Downloading...";
        $http.post(urlPrefix + "getAddrObjectsByParentGuid", data, config).then(function (response) {
            $rootScope.objectsList = response.data;
            $scope.downloadingMessage = "";
            $scope.formatShow();
            if ($rootScope.objectsList.length === 0) {
                $scope.getSteadsByParentGuid();
                $scope.getHousesByParentGuid();
            }
        });
    };

    $scope.getSteadsByParentGuid = function () {
        let data = {guid: parentGuid, actual: $scope.actualAdvancedSearch};
        $scope.downloadingMessage = "Downloading...";
        $http.post(urlPrefix + "getSteadsByParentGuid", data, config).then(function (response) {
            $rootScope.steadsList = response.data;
        });
    };

    $scope.getHousesByParentGuid = function () {
        let data = {guid: parentGuid, actual: $scope.actualAdvancedSearch};
        $http.post(urlPrefix + "getHousesByParentGuid", data, config).then(function (response) {
            $rootScope.housesList = response.data;
            $scope.downloadingMessage = "";
            $scope.formatShow();
        });
    };

    $scope.getRoomsListByParentGuid = function () {
        let data = {guid: parentGuid, actual: $scope.actualAdvancedSearch};
        $scope.downloadingMessage = "Downloading...";
        $http.post(urlPrefix + "getRoomsListByParentGuid", data, config).then(function (response) {
            $rootScope.roomsList = response.data;
            $rootScope.steadsList = [];
            $rootScope.housesList = [];
            $scope.downloadingMessage = "";
            $scope.formatShow();
        });
    };

    $scope.initLists = function () {
        $rootScope.objectsList = [];
        $rootScope.steadsList = [];
        $rootScope.housesList = [];
        $rootScope.roomsList = [];
    };

    $scope.showDropdownList = [];
    $scope.formatShow = function () {
        while ($scope.showDropdownList.length > directiveScopesIds.length) $scope.showDropdownList.pop();
        if ($rootScope.objectsList.length > 0 || $rootScope.steadsList.length > 0 ||
            $rootScope.housesList.length > 0 || $rootScope.roomsList.length > 0) {
            $scope.showDropdownList.push({show: true});
        }
    };


    $scope.searchObjects = function () {
        let data = Object();
        if ($scope.guidSearch !== undefined && $scope.guidSearch !== "") data.guid = $scope.guidSearch;
        if ($scope.postcodeSearch !== undefined && $scope.postcodeSearch !== "") data.postalcode = $scope.postcodeSearch;
        if (Object.values(data).length > 0) {
            data.searchType = $scope.objectCheck ? "addrObject" : "";
            data.searchType += $scope.houseCheck ? "house" : "";
            data.searchType += $scope.steadCheck ? "stead" : "";
            data.searchType += $scope.roomCheck ? "room" : "";
            data.onlyActual = $scope.actualSearch;
            $http.post(urlPrefix + "searchObjects", data, config).then(function (response) {
                if (response.data.length > 0) {
                    $scope.searchMessage = "";
                    $scope.resultObjects = response.data;
                } else {
                    $scope.searchMessage = "Не найдено";
                    $scope.resultObjects = [];
                }
            });
        } else {
            $scope.resultObjects = [];
            $scope.searchMessage = "Все поля для поиска по параметрам пусты";
        }
    };

    $scope.getLastObjectInformation = function () {
        if (parentGuid !== " ") {
            let data = Object();
            data.guid = parentGuid;
            data.searchType = $rootScope.typeOfLastObject;
            data.onlyActual = $scope.actualAdvancedSearch;
            $http.post(urlPrefix + "searchObjects", data, config).then(function (response) {
                $scope.searchMessage = "";
                $scope.resultObjects = response.data;
            });
        } else {
            $scope.resultObjects = [];
            $scope.searchMessage = "Выберите объект";
        }
    };

    $scope.getGuid = function (object) {
        let guid = " ";
        if (object.roomguid !== undefined && object.roomguid !== null) guid = object.roomguid;
        else if (object.houseguid !== undefined && object.houseguid !== null) guid = object.houseguid;
        else if (object.steadguid !== undefined && object.steadguid !== null) guid = object.steadguid;
        else if (object.aoguid !== undefined && object.aoguid !== null) guid = object.aoguid;
        guid = guid.substring(0, 8) + '-' + guid.substring(8, 12) + '-' + guid.substring(12, 16) + '-' + guid.substring(16, 20)
            + '-' + guid.substring(20, 32);
        return guid;
    };

    $scope.getStatus = function (object) {
        let date = new Date().toJSON().slice(0, 10).replace(/-/g, '');
        return (object.enddate >= parseInt(date)) ? "Актуальный" : "Неактуальный";
    }
});

let directiveScopesIds = [];
let parentGuid = " ";

user.directive('dropdownList', function ($rootScope, $http, $timeout) {
    return {
        restrict: 'E',
        scope: true,
        templateUrl: 'template.html',
        link: function (scope, elelement) {
            let $listContainer = angular.element(elelement[0].querySelectorAll('.search-object-list')[0]);
            elelement.find('input').bind('focus', function () {
                $listContainer.addClass('show');
            });
            elelement.find('input').bind('blur', function () {
                $timeout(function () {$listContainer.removeClass('show')}, 200);
            });

            scope.chooseObject = function (object) {
                parentGuid = object.aoguid;
                scope.search = object.shortname + ' ' + object.formalname;
                $rootScope.typeOfLastObject = "addrObject";
                let level = scope.getCurrentLevel();

                if (level === 0) {
                    scope.objectsList = $rootScope.objectsList;
                    directiveScopesIds.push(scope.$id);
                    scope.getObjectsByParentGuid();
                }else{
                    scope.formatDirectiveScopes(level);
                    scope.initLists();
                    scope.getObjectsByParentGuid();
                }
                $listContainer.removeClass('show');
            };

            scope.chooseHouse = function (house) {
                parentGuid = house.houseguid;
                scope.search = house.type + ' ' + house.name;
                $rootScope.typeOfLastObject = "house";
                let level = scope.getCurrentLevel();

                if (level === 0) {
                    scope.housesList = $rootScope.housesList;
                    scope.steadsList = $rootScope.steadsList;
                    directiveScopesIds.push(scope.$id);
                    scope.getRoomsListByParentGuid();
                }else{
                    scope.formatDirectiveScopes(level);
                    scope.initLists();
                    scope.getRoomsListByParentGuid();
                }
                $listContainer.removeClass('show');
            };

            scope.chooseStead = function (stead) {
                parentGuid = stead.steadguid;
                scope.search = 'участок ' + stead.number;
                $rootScope.typeOfLastObject = "stead";
                let level = scope.getCurrentLevel();

                if (level === 0) {
                    scope.steadsList = $rootScope.steadsList;
                    scope.housesList = $rootScope.housesList;
                    directiveScopesIds.push(scope.$id);
                }else{
                    scope.formatDirectiveScopes(level);
                    scope.initLists();
                    scope.formatShow();
                }
                $listContainer.removeClass('show');
            };

            scope.chooseRoom = function (room) {
                parentGuid = room.roomguid;
                scope.search = room.type + ' ' + room.flatnumber;
                $rootScope.typeOfLastObject = "room";
                let level = scope.getCurrentLevel();

                if (level === 0) {
                    scope.roomsList = $rootScope.roomsList;
                    directiveScopesIds.push(scope.$id);
                }
                $listContainer.removeClass('show');
            };

            scope.getCurrentLevel = function () {
                let level = 0;
                for (let i = 0; i < directiveScopesIds.length; i++) {
                    if (scope.$id === directiveScopesIds[i]) level = i + 1;
                }
                return level;
            };

            scope.formatDirectiveScopes = function (currentLevel) {
                while (directiveScopesIds.length > currentLevel) directiveScopesIds.pop();
            };
        }
    }
});

user.directive('navBar', function () {
    return {restrict: 'E', templateUrl: 'navbar.html'}
});

user.filter('myObjectFilter', function () {
    return function (objects, search) {
        let res = [];
        if (search !== undefined && search !== "") {
            if (search.split(' ').length > 1) {
                let shortnameSearch = search.split(' ', 2)[0];
                let formalnameSearch = "";
                for (let i = 1; i < search.split(' ').length; i++) {
                    formalnameSearch += search.split(' ')[i];
                    if (i !== search.split(' ').length - 1) formalnameSearch += " ";
                }
                for (let i = 0; i < objects.length; i++) {
                    if (objects[i].shortname.indexOf(shortnameSearch.toLowerCase()) !== -1)
                        res.push(objects[i]);
                }
                for (let i = 0; i < res.length; i++) {
                    if (res[i].formalname.toLowerCase().indexOf(formalnameSearch.toLowerCase()) === -1) {
                        res.splice(i, 1);
                        i--;
                    }
                }
            } else {
                for (let i = 0; i < objects.length; i++) {
                    if (objects[i].shortname.indexOf(search.toLowerCase()) !== -1 ||
                        objects[i].formalname.toLowerCase().indexOf(search.toLowerCase()) !== -1)
                        res.push(objects[i]);
                }
            }
        } else return objects;
        return res;
    };
});

user.filter('myHouseFilter', function () {
    return function (objects, search) {
        let res = [];
        if (search !== undefined && search !== "") {
            if (search.split(' ').length > 1) {
                let typeSearch = search.split(' ')[0];
                let nameSearch = "";
                for (let i = 1; i < search.split(' ').length; i++) {
                    nameSearch += search.split(' ')[i];
                    if (i !== search.split(' ').length - 1) nameSearch += " ";
                }
                for (let i = 0; i < objects.length; i++) {
                    if (objects[i].type.indexOf(typeSearch.toLowerCase()) !== -1) res.push(objects[i]);
                }
                for (let i = 0; i < res.length; i++) {
                    if (res[i].name.toLowerCase().lastIndexOf(nameSearch.toLowerCase(), 0) === -1) {
                        res.splice(i, 1);
                        i--;
                    }
                }
            } else {
                for (let i = 0; i < objects.length; i++) {
                    if (objects[i].name.toLowerCase().lastIndexOf(search.toLowerCase(), 0) !== -1 ||
                        objects[i].type.indexOf(search.toLowerCase()) !== -1)
                        res.push(objects[i]);
                }
            }
        } else return objects;
        return res;
    };
});

user.filter('mySteadFilter', function () {
    return function (objects, search) {
        let res = [];
        if (search !== undefined && search !== "") {
            if (search.split(' ').length > 1) {
                let stead = search.split(' ')[0];
                let numberSearch = search.split(' ')[1];
                for (let i = 0; i < objects.length; i++) {
                    if ("участок".indexOf(stead.toLowerCase()) !== -1) res.push(objects[i]);
                }
                for (let i = 0; i < res.length; i++) {
                    if (res[i].number.toLowerCase().lastIndexOf(numberSearch.toLowerCase(), 0) === -1) {
                        res.splice(i, 1);
                        i--;
                    }
                }
            } else {
                for (let i = 0; i < objects.length; i++) {
                    if (objects[i].number.toLowerCase().lastIndexOf(search.toLowerCase(), 0) !== -1 ||
                        "участок".indexOf(search.toLowerCase()) !== -1)
                        res.push(objects[i]);
                }
            }
        } else return objects;
        return res;
    };
});

user.filter('myRoomFilter', function () {
    return function (objects, search) {
        let res = [];
        if (search !== undefined && search !== "") {
            if (search.split(' ').length > 1) {
                let typeSearch = search.split(' ')[0];
                let numberSearch = search.split(' ')[1];
                for (let i = 0; i < objects.length; i++) {
                    if (objects[i].type.indexOf(typeSearch.toLowerCase()) !== -1)
                        res.push(objects[i]);
                }
                for (let i = 0; i < res.length; i++) {
                    if (res[i].flatnumber.toLowerCase().lastIndexOf(numberSearch.toLowerCase(), 0) === -1) {
                        res.splice(i, 1);
                        i--;
                    }
                }
            } else {
                for (let i = 0; i < objects.length; i++) {
                    if (objects[i].flatnumber.toLowerCase().lastIndexOf(search.toLowerCase(), 0) !== -1 ||
                        objects[i].type.indexOf(search.toLowerCase()) !== -1)
                        res.push(objects[i]);
                }
            }
        } else return objects;
        return res;
    };
});