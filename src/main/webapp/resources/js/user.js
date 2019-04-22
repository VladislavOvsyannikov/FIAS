let user = angular.module('user', []);
let config = {headers: {'Content-Type': 'application/json; charset=utf-8;'}};
let urlPrefix = "/fias/";

user.controller('userController', function ($rootScope, $scope, $http) {

    $scope.getCurrentUserInfo = function () {
        $http.get(urlPrefix + "user-info", config).then(function (response) {
            $scope.userName = response.data[0];
            $scope.userRole = response.data[1];
        });
    };

    $rootScope.downloadMessage = "";
    $rootScope.downloadingMessage = "";
    $rootScope.actualAdvancedSearch = true;
    $scope.getObjectsByParentGuid = function () {
        $rootScope.downloadingMessage = "Загрузка...";
        $http.get(urlPrefix + "addr-objects-parent?guid=" + parentGuid + "&actual=" + $rootScope.actualAdvancedSearch, config)
            .then(function (response) {
                $rootScope.objectsList = response.data;
                $rootScope.downloadingMessage = "";
                $scope.formatShow();
                if ($rootScope.objectsList.length === 0) {
                    $scope.getSteadsByParentGuid();
                    $scope.getHousesByParentGuid();
                }
            });
    };

    $scope.getSteadsByParentGuid = function () {
        $rootScope.downloadingMessage = "Загрузка...";
        $http.get(urlPrefix + "steads-parent?guid=" + parentGuid + "&actual=" + $rootScope.actualAdvancedSearch, config)
            .then(function (response) {
                $rootScope.steadsList = response.data;
            });
    };

    $scope.getHousesByParentGuid = function () {
        $http.get(urlPrefix + "houses-parent?guid=" + parentGuid + "&actual=" + $rootScope.actualAdvancedSearch, config)
            .then(function (response) {
                $rootScope.housesList = response.data;
                $rootScope.downloadingMessage = "";
                $scope.formatShow();
            });
    };

    $scope.getRoomsListByParentGuid = function () {
        $rootScope.downloadingMessage = "Загрузка...";
        $http.get(urlPrefix + "rooms-parent?guid=" + parentGuid + "&actual=" + $rootScope.actualAdvancedSearch, config)
            .then(function (response) {
                $rootScope.roomsList = response.data;
                $rootScope.steadsList = [];
                $rootScope.housesList = [];
                $rootScope.downloadingMessage = "";
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

    $scope.searchingMessage = "";
    $scope.orderByField = 'fullAddress';
    $scope.reverseSort = false;
    $scope.searchObjects = function () {
        let queryPart = ($scope.guidSearch !== undefined && $scope.guidSearch !== "") ? "guid=" + $scope.guidSearch : "";
        queryPart += ($scope.postcodeSearch !== undefined && $scope.postcodeSearch !== "") ? "&postalcode=" + $scope.postcodeSearch : "";
        queryPart += ($scope.cadnumSearch !== undefined && $scope.cadnumSearch !== "") ? "&cadnum=" + $scope.cadnumSearch : "";
        queryPart += ($scope.okatoSearch !== undefined && $scope.okatoSearch !== "") ? "&okato=" + $scope.okatoSearch : "";
        queryPart += ($scope.oktmoSearch !== undefined && $scope.oktmoSearch !== "") ? "&oktmo=" + $scope.oktmoSearch : "";
        queryPart += ($scope.flSearch !== undefined && $scope.flSearch !== "") ? "&fl=" + $scope.flSearch : "";
        queryPart += ($scope.ulSearch !== undefined && $scope.ulSearch !== "") ? "&ul=" + $scope.ulSearch : "";
        if (queryPart.length > 0) {
            $scope.searchingMessage = "Поиск...";
            let types = [];
            if ($scope.objectCheck) types.push("ADDRESS_OBJECT");
            if ($scope.houseCheck) types.push("HOUSE");
            if ($scope.steadCheck) types.push("STEAD");
            if ($scope.roomCheck) types.push("ROOM");
            $http.get(urlPrefix + "objects-parameters?" + queryPart + "&actual=" + $scope.actualSearch
                + ((types.length > 0) ? "&types=" + types.join(",") : ""), config).then(function (response) {
                $scope.searchingMessage = "";
                if (response.data.length > 0) {
                    $scope.searchMessage = "";
                    $scope.resultObjects = response.data;
                    $scope.currentPage = 0;
                    $scope.paginate(0);
                } else {
                    $scope.searchMessage = "Не найдено";
                    $scope.resultObjects = [];
                    $scope.pagedObjects = [];
                }
            });
        } else $scope.searchMessage = "Все поля пусты";
    };

    $scope.getLastObjectInformation = function () {
        if (parentGuid !== "") {
            $rootScope.downloadMessage = "";
            $rootScope.downloadingMessage = "Загрузка...";
            $http.get(urlPrefix + "objects-parameters?guid=" + parentGuid + "&types="
                + $rootScope.typeOfLastObject + "&actual=" + $rootScope.actualAdvancedSearch, config).then(function (response) {
                $scope.resultObjects = response.data;
                $scope.pagedObjects = $scope.resultObjects;
                $rootScope.downloadingMessage = "";
            });
        } else $rootScope.downloadMessage = "Выберите объект";
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
        if (object.livestatus !== undefined) return object.livestatus === 1 ? "Актуальный" : "Неактуальный";
        else {
            let date = new Date().toJSON().slice(0, 10).replace(/-/g, '');
            return (object.enddate >= parseInt(date)) ? "Актуальный" : "Неактуальный";
        }
    };

    $scope.pageSize = 10;
    $scope.totalPages = function () {
        return Math.ceil($scope.resultObjects.length / $scope.pageSize);
    };

    $scope.pageButtonDisabled = function (dir) {
        if (dir === -1) return $scope.currentPage === 0;
        return $scope.currentPage >= $scope.resultObjects.length / $scope.pageSize - 1;
    };

    $scope.paginate = function (nextOrPrev) {
        $scope.currentPage += nextOrPrev;
        $scope.pagedObjects = $scope.resultObjects.slice($scope.currentPage * $scope.pageSize,
            ($scope.currentPage + 1) * $scope.pageSize);
    };
});

let directiveScopesIds = [];
let parentGuid = "";

user.directive('dropdownList', function ($rootScope, $timeout) {
    return {
        restrict: 'E',
        scope: true,
        templateUrl: 'template.html',
        link: function (scope, element) {
            let list = angular.element(element[0].querySelectorAll('.search-object-list')[0]);
            element.find('input').bind('focus', function () {
                list.addClass('show');
            });
            element.find('input').bind('blur', function () {
                $timeout(function () {
                    list.removeClass('show')
                }, 150);
            });

            scope.chooseObject = function (object) {
                $rootScope.downloadMessage = "";
                parentGuid = object.aoguid;
                scope.search = object.shortname + ' ' + object.formalname;
                $rootScope.typeOfLastObject = "ADDRESS_OBJECT";
                let level = scope.getCurrentLevel();

                if (level === 0) {
                    scope.objectsList = $rootScope.objectsList;
                    directiveScopesIds.push(scope);
                } else {
                    scope.formatDirectiveScopes(level);
                    scope.initLists();
                }
                scope.getObjectsByParentGuid();
                list.removeClass('show');
            };

            scope.chooseHouse = function (house) {
                $rootScope.downloadMessage = "";
                parentGuid = house.houseguid;
                scope.search = house.type + ' ' + house.name;
                $rootScope.typeOfLastObject = "HOUSE";
                let level = scope.getCurrentLevel();

                if (level === 0) {
                    scope.housesList = $rootScope.housesList;
                    scope.steadsList = $rootScope.steadsList;
                    directiveScopesIds.push(scope);
                } else {
                    scope.formatDirectiveScopes(level);
                    scope.initLists();
                }
                scope.getRoomsListByParentGuid();
                list.removeClass('show');
            };

            scope.chooseStead = function (stead) {
                $rootScope.downloadMessage = "";
                parentGuid = stead.steadguid;
                scope.search = 'участок ' + stead.number;
                $rootScope.typeOfLastObject = "STEAD";
                let level = scope.getCurrentLevel();

                if (level === 0) {
                    scope.steadsList = $rootScope.steadsList;
                    scope.housesList = $rootScope.housesList;
                    directiveScopesIds.push(scope);
                } else {
                    scope.formatDirectiveScopes(level);
                    scope.initLists();
                    scope.formatShow();
                }
                list.removeClass('show');
            };

            scope.chooseRoom = function (room) {
                $rootScope.downloadMessage = "";
                parentGuid = room.roomguid;
                scope.search = room.type + ' ' + room.flatnumber;
                $rootScope.typeOfLastObject = "ROOM";
                let level = scope.getCurrentLevel();

                if (level === 0) {
                    scope.roomsList = $rootScope.roomsList;
                    directiveScopesIds.push(scope);
                }
                list.removeClass('show');
            };

            scope.getCurrentLevel = function () {
                let level = 0;
                for (let i = 0; i < directiveScopesIds.length; i++) {
                    if (scope.$id === directiveScopesIds[i].$id) level = i + 1;
                }
                return level;
            };

            scope.formatDirectiveScopes = function (currentLevel) {
                while (directiveScopesIds.length > currentLevel) directiveScopesIds.pop();
            };

            $rootScope.clickImitation = function (object) {
                scope.chooseObject(object);
            };
        }
    }
});

user.directive('nameSearch', function ($rootScope, $http, $timeout) {
    return {
        restrict: 'E',
        scope: true,
        templateUrl: 'templateNameSearch.html',
        link: function (scope, element) {
            let list = angular.element(element[0].querySelectorAll('.search-object-list')[0]);
            element.find('input').bind('focus', function () {
                list.addClass('showAddresses');
            });
            element.find('input').bind('blur', function () {
                $timeout(function () {
                    list.removeClass('showAddresses')
                }, 150);
            });

            scope.chooseAddress = function (object) {
                scope.nameSearch = object.fullAddress;
                let names = object.fullAddress.split(' ');
                object.shortname = names[names.length - 2];

                if (directiveScopesIds.length > 0) {
                    parentGuid = object.aoguid;
                    $rootScope.typeOfLastObject = "ADDRESS_OBJECT";
                    while (directiveScopesIds.length > 1) directiveScopesIds.pop();
                    scope.initLists();
                    directiveScopesIds[0].search = object.shortname + ' ' + object.formalname;
                    scope.getObjectsByParentGuid();
                } else $rootScope.clickImitation(object);
                list.removeClass('showAddresses');
            };

            scope.getAddrObjectsByName = function () {
                if (scope.nameSearch !== undefined && scope.nameSearch.length > 2) {
                    $rootScope.downloadMessage = "";
                    $rootScope.downloadingMessage = "Загрузка...";
                    $http.get(urlPrefix + "addr-objects-name?name=" + scope.nameSearch + "&type="
                        + scope.getNameSearchType(scope.type) + "&actual=" + $rootScope.actualAdvancedSearch, config)
                        .then(function (response) {
                            $rootScope.downloadingMessage = "";
                            if (response.data.length > 0) {
                                scope.addrObjectsByName = response.data;
                                list.addClass('showAddresses');
                            } else {
                                $rootScope.downloadMessage = "Не найдено";
                                scope.addrObjectsByName = [];
                            }
                        });
                } else {
                    $rootScope.downloadMessage = "Введите минимум три символа";
                    scope.addrObjectsByName = [];
                }
            };

            scope.type = "all";
            scope.searchTypes = ["Город", "Область", "Район", "Край", "Республика", "Автономный округ",
                "Автономная область", "Улица", "Проспект", "Село", "Деревня", "Поселок", "Поселок городского типа"];

            scope.getNameSearchType = function (type) {
                switch (type) {
                    case "all":
                        return "ALL";
                    case "Город":
                        return "CITY";
                    case "Область":
                        return "REGION";
                    case "Район":
                        return "DISTRICT";
                    case "Край":
                        return "EDGE";
                    case "Республика":
                        return "REPUBLIC";
                    case "Автономный округ":
                        return "AUTONOMOUS_DISTRICT";
                    case "Автономная область":
                        return "AUTONOMOUS_REGION";
                    case "Улица":
                        return "STREET";
                    case "Проспект":
                        return "AVENUE";
                    case "Село":
                        return "VILLAGE";
                    case "Деревня":
                        return "HAMLET";
                    case "Поселок":
                        return "SETTLEMENT";
                    case "Поселок городского типа":
                        return "URBAN_TYPE_SETTLEMENT";
                }
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

user.filter('myNameSearchFilter', function () {
    return function (objects, search) {
        let res = [];
        if (search !== undefined && search !== "") {
            for (let i = 0; i < objects.length; i++) {
                if (objects[i].fullAddress.toLowerCase().indexOf(search.toLowerCase(), 0) !== -1)
                    res.push(objects[i]);
            }
        } else return objects;
        return res;
    };
});