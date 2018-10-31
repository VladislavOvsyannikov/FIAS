let user = angular.module('user', []);
let config = {headers: {'Content-Type': 'application/json;charset=utf-8;'}};
let guid = " ";

user.controller('userController', function ($rootScope, $scope, $http) {

    $scope.getObjectsByParentGuid = function () {
        let data = {guid: guid,
                    actual: $rootScope.actualAdvancedSearch};
        $rootScope.downloadingMessage = "Downloading...";
        $http.post("getObjectsByParentGuid", data, config).then(function (response) {
            $scope.objectsList = response.data;
            $rootScope.downloadingMessage = "";
            nextObjects = $scope.objectsList;
            if ($scope.objectsList.length === 0) {
                $scope.getSteadsByParentGuid();
                $scope.getHousesByParentGuid();
            }
        });
    };

    $scope.getHousesByParentGuid = function () {
        let data = {guid: guid,
                    actual: $rootScope.actualAdvancedSearch};
        $rootScope.downloadingMessage = "Downloading...";
        $http.post("getHousesByParentGuid", data, config).then(function (response) {
            $scope.housesList = response.data;
            $rootScope.downloadingMessage = "";
            nextHouses = $scope.housesList;
            if ($scope.housesList.length === 0) {
                $scope.steadsList = [];
                $scope.getRoomsListByParentGuid();
            }
        });
    };

    $scope.getSteadsByParentGuid = function () {
        let data = {guid: guid,
                    actual: $rootScope.actualAdvancedSearch};
        $rootScope.downloadingMessage = "Downloading...";
        $http.post("getSteadsByParentGuid", data, config).then(function (response) {
            $scope.steadsList = response.data;
            $rootScope.downloadingMessage = "";
            nextObjects = $scope.steadsList;
        });
    };

    $scope.getRoomsListByParentGuid = function () {
        let data = {guid: guid,
                    actual: $rootScope.actualAdvancedSearch};
        $rootScope.downloadingMessage = "Downloading...";
        $http.post("getRoomsListByParentGuid", data, config).then(function (response) {
            $scope.roomsList = response.data;
            $rootScope.downloadingMessage = "";
            nextObjects = $scope.roomsList;
            if ($scope.roomsList.length > 0 && showDropdownList.length <= directiveScopes.length)
                showDropdownList.push({value: showDropdownList.length+1});
            if ($scope.roomsList.length === 0 && showDropdownList.length > directiveScopes.length)
                showDropdownList.pop();
        });
    };

    $scope.getShowDropdownList = function () {
        return showDropdownList;
    };


    $scope.searchObjects = function () {
        let data = Object();
        data.guid = ($scope.guidSearch !== undefined && $scope.guidSearch !== "") ?
            $scope.guidSearch.replace(new RegExp('-', 'g'), '').toLowerCase() : "";
        data.postalcode = ($scope.postcodeSearch !== undefined && $scope.postcodeSearch !== "") ?
            $scope.postcodeSearch : "";
        data.searchType = $scope.objectCheck ? "object":"";
        data.searchType += $scope.houseCheck ? "house":"";
        data.searchType += $scope.steadCheck ? "stead":"";
        data.searchType += $scope.roomCheck ? "room":"";
        data.actual = $scope.actualSearch;
        $http.post("/rest/searchObjects", data, config).then(function (response) {
            if (response.data.length > 0) {
                $scope.searchMessage = "";
                $scope.resultObjects = response.data;
            }
            else {
                $scope.searchMessage = "Не найдено";
                $scope.resultObjects = [];
            }
        });
    };

    $scope.getLastObjectInformation = function () {
        if (guid !== " ") {
            let data = Object();
            data.guid = guid;
            data.searchType = "objecthousesteadroom";
            data.actual = $scope.actualAdvancedSearch;
            $http.post("/rest/searchObjects", data, config).then(function (response) {
                $scope.searchMessage = "";
                $scope.resultObjects = response.data;
            });
        }
    };

    $scope.getFullAddress = function (object) {
        return object.postalcode !== undefined && object.postalcode !== null ?
            object.postalcode + ", " + object.fullAddress : object.fullAddress;
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
        let date = new Date().toJSON().slice(0,10).replace(/-/g,'');
        return (object.enddate >= parseInt(date)) ? "Актуальный" : "Неактуальный";
    }
});

let showDropdownList = [{value: 1}];
let nextObjects = [];
let nextHouses = [];
let directiveScopes = [];

user.directive('dropdownListNext', function ($rootScope, $http, $timeout) {
    return {
        restrict: 'E',
        scope: true,
        templateUrl: 'template.html',
        link: function (scope, elelement, attrs) {
            let $listContainer = angular.element(elelement[0].querySelectorAll('.search-object-list')[0]);
            elelement.find('input').bind('focus', function () {
                $listContainer.addClass('show');
            });
            elelement.find('input').bind('blur', function () {
                $timeout(function () {
                    $listContainer.removeClass('show')
                }, 200);
            });

            scope.chooseObject = function (object) {
                let level = 0;
                for (let i = 0; i < directiveScopes.length; i++) {
                    if (scope.$id === directiveScopes[i].$id) level = i + 1;
                }
                guid = object.aoguid;
                scope.search = object.shortname + ' ' + object.formalname;
                if (level === 0) {
                    scope.objectsList = nextObjects;
                    showDropdownList.push({value: showDropdownList.length+1});
                    directiveScopes.push(scope);
                    scope.getObjectsByParentGuid();
                }
                if (level > 0 && level === directiveScopes.length) {
                    scope.getObjectsByParentGuid();
                    if (showDropdownList.length <= directiveScopes.length)
                        showDropdownList.push({value: showDropdownList.length+1});
                }
                if (level > 0 && level < directiveScopes.length) {
                    $rootScope.downloadingMessage = "Downloading...";
                    let data = {guid: guid,
                                actual: $rootScope.actualAdvancedSearch};
                    $http.post("getObjectsByParentGuid", data, config).then(function (response) {
                        directiveScopes[level].objectsList = response.data;
                        $rootScope.downloadingMessage = "";
                        if (directiveScopes[level].objectsList.length === 0) {
                            $rootScope.downloadingMessage = "Downloading...";
                            $http.post("getSteadsByParentGuid", data, config).then(function (response) {
                                directiveScopes[level].steadsList = response.data;
                                $rootScope.downloadingMessage = "";
                            });
                            $rootScope.downloadingMessage = "Downloading...";
                            $http.post("getHousesByParentGuid", data, config).then(function (response) {
                                directiveScopes[level].housesList = response.data;
                                $rootScope.downloadingMessage = "";
                                let dirSco = directiveScopes[level];
                                if ((dirSco.housesList.length > 0 || dirSco.steadsList.length > 0) &&
                                    showDropdownList.length < directiveScopes.length)
                                    showDropdownList.push({value: showDropdownList.length+1});
                                if (dirSco.housesList.length === 0 && dirSco.steadsList.length === 0)
                                    directiveScopes.pop();
                                if (showDropdownList.length > directiveScopes.length) showDropdownList.pop();
                            });
                        }
                    });
                    for (let i = directiveScopes.length - 1; i >= level; i--) {
                        directiveScopes[i].search = "";
                        directiveScopes[i].objectsList = [];
                        directiveScopes[i].steadsList = [];
                        directiveScopes[i].housesList = [];
                        directiveScopes[i].roomsList = [];
                        if (i > level) directiveScopes.pop();
                    }
                    while (showDropdownList.length > directiveScopes.length) showDropdownList.pop();
                }
                $listContainer.removeClass('show');
            };

            scope.chooseHouse = function (house) {
                let level = 0;
                for (let i = 0; i < directiveScopes.length; i++) {
                    if (scope.$id === directiveScopes[i].$id) level = i + 1;
                }
                guid = house.houseguid;
                scope.search = house.type + ' ' +  house.name;
                if (level === 0) {
                    scope.housesList = nextHouses;
                    scope.steadsList = nextObjects;
                    directiveScopes.push(scope);
                    scope.getHousesByParentGuid();
                }
                if (level > 0 && level === directiveScopes.length) scope.getHousesByParentGuid();
                if (level > 0 && level < directiveScopes.length) {
                    $rootScope.downloadingMessage = "Downloading...";
                    let data = {guid: guid,
                                actual: $rootScope.actualAdvancedSearch};
                    $http.post("getRoomsListByParentGuid", data, config).then(function (response) {
                        directiveScopes[level].roomsList = response.data;
                        $rootScope.downloadingMessage = "";
                        directiveScopes[level].search = "";
                        if (response.data.length > 0 && showDropdownList.length < directiveScopes.length)
                            showDropdownList.push({value: showDropdownList.length+1});
                        if (response.data.length === 0) directiveScopes.pop();
                        if (showDropdownList.length > directiveScopes.length) showDropdownList.pop();
                    });
                }
                $listContainer.removeClass('show');
            };

            scope.chooseRoom = function (room) {
                let level = 0;
                for (let i = 0; i < directiveScopes.length; i++) {
                    if (scope.$id === directiveScopes[i].$id) level = i + 1;
                }
                guid = room.roomguid;
                scope.search = room.type + ' ' + room.flatnumber;
                if (level === 0) {
                    scope.roomsList = nextObjects;
                    directiveScopes.push(scope);
                }
                $listContainer.removeClass('show');
            };

            scope.chooseStead = function (stead) {
                let level = 0;
                for (let i = 0; i < directiveScopes.length; i++) {
                    if (scope.$id === directiveScopes[i].$id) level = i + 1;
                }
                guid = stead.steadguid;
                scope.search = 'участок ' + stead.number;

                if (level === 0) {
                    scope.steadsList = nextObjects;
                    scope.housesList = nextHouses;
                    directiveScopes.push(scope);
                }
                if (level > 0 && level < showDropdownList.length) {
                    showDropdownList.pop();
                    if (level < directiveScopes.length) directiveScopes.pop();
                }
                $listContainer.removeClass('show');
            };
        }
    }
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