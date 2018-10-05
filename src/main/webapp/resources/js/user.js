var user = angular.module('user', []);
var config = {headers: {'Content-Type': 'application/json;charset=utf-8;'}};

var guid = " ";
var nextObjects;

user.controller('userController', function ($scope, $http) {

    $scope.getObjectsListByGuid = function () {
        $http.post("getObjectsListByGuid", guid, config).then(function (response) {
            $scope.objectsNextList = response.data;
            nextObjects = $scope.objectsNextList;
            if ($scope.objectsNextList.length === 0) $scope.getSteadsListByGuid();
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
            nextObjects = $scope.housesList;
        });
    };

    $scope.getRoomsListByGuid = function () {
        $http.post("getRoomsListByGuid", guid, config).then(function (response) {
            $scope.roomsList = response.data;
            nextObjects = $scope.roomsList;
        });
    };

    $scope.getShowDropdownList = function () {
        return showDropdownList;
    };

    $scope.getGuid = function () {
        return guid;
    };
});

var showDropdownList = [{value: true}];
var directiveScopes = [];
var level = 0;

user.directive('dropdownListNext',function($http, $timeout){
    return {
        restrict: 'E',
        scope: true,
        templateUrl: 'template.html',
        link: function(scope, elelement, attrs){
            var $listContainer = angular.element( elelement[0].querySelectorAll('.search-object-list')[0] );
            elelement.find('input').bind('focus',function(){
                $listContainer.addClass('show');
            });
            elelement.find('input').bind('blur',function(){
                $timeout(function(){ $listContainer.removeClass('show') }, 200);
            });

            scope.chooseObject = function(object){
                level = 0;
                for (var i=0; i<directiveScopes.length; i++){
                    if (scope.$id === directiveScopes[i].$id) level = i+1;
                }

                if (level>0 && level<directiveScopes.length){
                    scope.search = object.formalname+' '+object.shortname;
                    guid = object.aoguid;
                    $http.post("getObjectsListByGuid", guid, config).then(function (response) {
                        directiveScopes[level].objectsNextList=response.data;
                        if (directiveScopes[level].objectsNextList.length === 0){
                            $http.post("getSteadsListByGuid", guid, config).then(function (response) {
                                directiveScopes[level].steadsList=response.data;
                            });
                        }
                    });
                    for (i=directiveScopes.length-1; i>=level; i--){
                        directiveScopes[i].search="";
                        directiveScopes[i].objectsNextList = [];
                        directiveScopes[i].steadsList = [];
                        if (i>level) directiveScopes.pop();
                    }
                    while (showDropdownList.length !== directiveScopes.length) showDropdownList.pop();
                }

                if (level>0 && level===directiveScopes.length) {
                    guid = object.aoguid;
                    scope.search = object.formalname+' '+object.shortname;
                    scope.getObjectsListByGuid();
                    if (showDropdownList.length<=directiveScopes.length) showDropdownList.push({value: true});
                }

                if (level===0){
                    guid = object.aoguid;
                    scope.objectsNextList = nextObjects;
                    scope.search = object.formalname+' '+object.shortname;
                    showDropdownList.push({value: true});
                    if (level===0) directiveScopes.push(scope);
                    scope.getObjectsListByGuid();
                }
                $listContainer.removeClass('show');
            };

            scope.chooseStead = function(stead) {
                level = 0;
                for (var i = 0; i < directiveScopes.length; i++) {
                    if (scope.$id === directiveScopes[i].$id) level = i + 1;
                }
                guid = stead.steadguid;
                scope.search = 'участок № ' + stead.number;

                if (level === 0) {
                    scope.steadsList = nextObjects;
                    directiveScopes.push(scope);
                }
                $listContainer.removeClass('show');
            };
        }
    }
});