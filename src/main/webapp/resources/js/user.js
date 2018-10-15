let user = angular.module('user', []);
let config = {headers: {'Content-Type': 'application/json;charset=utf-8;'}};

let guid = " ";
let lastObject = null;
let lastRoom = null;

user.controller('userController', function ($scope, $http) {

    $scope.getLastObjectInformation = function () {
        let res = [];
        if (guid !== " ") res.push("GUID: " + guid);
        if (lastObject !== null) {
            if (lastObject.postalcode !== null) res.push("Почтовый индекс: " + lastObject.postalcode);
            if (lastObject.okato !== null) res.push("\nOKATO: " + lastObject.okato);
            if (lastObject.oktmo !== null) res.push("OKTMO: " + lastObject.oktmo);
            if (lastObject.ifnsfl !== null) res.push("ИФНС ФЛ: " + lastObject.ifnsfl);
            if (lastObject.ifnsul !== null) res.push("ИФНС ЮЛ: " + lastObject.ifnsul);
        }
        if (lastRoom !== null) {
            if (lastRoom.cadnum !== null) res.push("Кадастровый номер помещения: " + lastRoom.cadnum);
            if (lastRoom.roomcadnum !== null) res.push("Кадастровый номер комнаты в помещении: " + lastRoom.roomcadnum);
        }
        return res;
    };

    $scope.getFullAddress = function () {
        let res = "";
        try {
            res = lastObject.postalcode !== null ? lastObject.postalcode + ", " : "";
            for (let i = 0; i < directiveScopes.length - 1; i++) res += directiveScopes[i].search + ", "
            res += directiveScopes[directiveScopes.length - 1].search;
        } catch (error) {
        }
        return res;
    };

    $scope.getObjectsListByGuid = function () {
        $http.post("getObjectsListByGuid", guid, config).then(function (response) {
            $scope.objectsNextList = response.data;
            nextObjects = $scope.objectsNextList;
            if ($scope.objectsNextList.length === 0) {
                $scope.getSteadsListByGuid();
                $scope.getHousesListByGuid();
            }
        });
    };

    $scope.getSteadsListByGuid = function () {
        $http.post("getSteadsListByGuid", guid, config).then(function (response) {
            $scope.steadsList = response.data;
            nextObjects = $scope.steadsList;
        });
    };

    $scope.getHousesListByGuid = function () {
        $http.post("getHousesListByGuid", guid, config).then(function (response) {
            $scope.housesList = response.data;
            nextHouses = $scope.housesList;
            if ($scope.housesList.length === 0) {
                $scope.steadsList = [];
                $scope.getRoomsListByGuid();
            }
        });
    };

    $scope.getRoomsListByGuid = function () {
        $http.post("getRoomsListByGuid", guid, config).then(function (response) {
            $scope.roomsList = response.data;
            nextObjects = $scope.roomsList;
            if ($scope.roomsList.length > 0 && showDropdownList.length <= directiveScopes.length)
                showDropdownList.push({value: true});
            if ($scope.roomsList.length === 0 && showDropdownList.length > directiveScopes.length)
                showDropdownList.pop();
        });
    };

    $scope.getShowDropdownList = function () {
        return showDropdownList;
    };
});

let showDropdownList = [{value: true}];
let nextObjects = [];
let nextHouses = [];
let directiveScopes = [];

user.directive('dropdownListNext', function ($http, $timeout) {
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
                    $listContainer.removeClass('show')}, 200);
            });

            scope.chooseObject = function (object) {
                let level = 0;
                for (let i = 0; i < directiveScopes.length; i++) {
                    if (scope.$id === directiveScopes[i].$id) level = i + 1;
                }
                lastRoom = null;
                lastObject = object;
                guid = object.aoguid;
                scope.search = object.shortname + ' ' + object.formalname;
                if (level === 0) {
                    scope.objectsNextList = nextObjects;
                    showDropdownList.push({value: true});
                    directiveScopes.push(scope);
                    scope.getObjectsListByGuid();
                }
                if (level > 0 && level === directiveScopes.length) {
                    scope.getObjectsListByGuid();
                    if (showDropdownList.length <= directiveScopes.length) showDropdownList.push({value: true});
                }
                if (level > 0 && level < directiveScopes.length) {
                    $http.post("getObjectsListByGuid", guid, config).then(function (response) {
                        directiveScopes[level].objectsNextList = response.data;
                        if (directiveScopes[level].objectsNextList.length === 0) {
                            $http.post("getSteadsListByGuid", guid, config).then(function (response) {
                                directiveScopes[level].steadsList = response.data;
                            });
                            $http.post("getHousesListByGuid", guid, config).then(function (response) {
                                directiveScopes[level].housesList = response.data;
                                let dirSco = directiveScopes[level];
                                if ((dirSco.housesList.length > 0 || dirSco.steadsList.length > 0) &&
                                    showDropdownList.length < directiveScopes.length)
                                    showDropdownList.push({value: true});
                                if (dirSco.housesList.length === 0 && dirSco.steadsList.length === 0)
                                    directiveScopes.pop();
                                if (showDropdownList.length > directiveScopes.length) showDropdownList.pop();
                            });
                        }
                    });
                    for (let i = directiveScopes.length - 1; i >= level; i--) {
                        directiveScopes[i].search = "";
                        directiveScopes[i].objectsNextList = [];
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
                lastRoom = null;
                lastObject = house;
                guid = house.houseguid;
                scope.search = 'дом ' + house.housenum;
                if (house.buildnum !== null) scope.search += ' к. ' + house.buildnum;
                if (house.strucnum !== null) scope.search += ' стр. ' + house.strucnum;
                if (level === 0) {
                    scope.housesList = nextHouses;
                    scope.steadsList = nextObjects;
                    directiveScopes.push(scope);
                    scope.getHousesListByGuid();
                }
                if (level > 0 && level === directiveScopes.length) scope.getHousesListByGuid();
                if (level > 0 && level < directiveScopes.length) {
                    $http.post("getRoomsListByGuid", guid, config).then(function (response) {
                        directiveScopes[level].roomsList = response.data;
                        directiveScopes[level].search = "";
                        if (response.data.length > 0 && showDropdownList.length < directiveScopes.length)
                            showDropdownList.push({value: true});
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
                lastRoom = room;
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
                lastRoom = null;
                lastObject = stead;
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
                let shortnameSearch = search.split(' ')[0];
                let formalnameSearch = search.split(' ')[1];
                for (let i = 0; i < objects.length; i++) {
                    if (objects[i].shortname.toLowerCase().indexOf(shortnameSearch.toLowerCase()) !== -1)
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
                    if (objects[i].shortname.toLowerCase().indexOf(search.toLowerCase()) !== -1 ||
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
                let house = search.split(' ')[0];
                let flatnumberSearch = search.split(' ')[1];
                for (let i = 0; i < objects.length; i++) {
                    if ("дом".indexOf(house.toLowerCase()) !== -1) res.push(objects[i]);
                }
                for (let i = 0; i < res.length; i++) {
                    if (res[i].housenum.toLowerCase().lastIndexOf(flatnumberSearch.toLowerCase(), 0) === -1) {
                        res.splice(i, 1);
                        i--;
                    }
                }
            } else {
                for (let i = 0; i < objects.length; i++) {
                    if (objects[i].housenum.toLowerCase().lastIndexOf(search.toLowerCase(), 0) !== -1 ||
                        "дом".indexOf(search.toLowerCase()) !== -1)
                        res.push(objects[i]);
                }
            }
        }else return objects;
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
        }else return objects;
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
                    if (objects[i].type.toLowerCase().indexOf(typeSearch.toLowerCase()) !== -1)
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
                        objects[i].type.toLowerCase().indexOf(search.toLowerCase()) !== -1)
                        res.push(objects[i]);
                }
            }
        } else return objects;
        return res;
    };
});