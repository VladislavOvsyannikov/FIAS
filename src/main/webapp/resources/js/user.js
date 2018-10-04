var user = angular.module('user', []);
var config = {headers: {'Content-Type': 'application/json;charset=utf-8;'}};

var guid = " ";
var nextObjects;

user.controller('userController', function ($scope, $http) {

    $scope.getObjectsListByGuid = function () {
        $http.post("getObjectsListByGuid", guid, config).then(function (response) {
            $scope.objectsNextList = response.data;
            nextObjects = $scope.objectsNextList;
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
                    scope.searchNext = object.formalname+' '+object.shortname;
                    guid = object.aoguid;
                    $http.post("getObjectsListByGuid", guid, config).then(function (response) {
                        directiveScopes[level].objectsNextList=response.data;
                    });
                    for (i=directiveScopes.length-1; i>=level; i--){
                        directiveScopes[i].searchNext="";
                        directiveScopes[i].objectsNextList = [];
                        if (i>level) directiveScopes.pop();
                    }
                    while (showDropdownList.length !== directiveScopes.length) showDropdownList.pop();
                }

                if (level>0 && level===directiveScopes.length) {
                    guid = object.aoguid;
                    scope.searchNext = object.formalname+' '+object.shortname;
                    scope.getObjectsListByGuid();
                    if (showDropdownList.length<=directiveScopes.length) showDropdownList.push({value: true});
                }

                if (level===0){
                    guid = object.aoguid;
                    scope.objectsNextList = nextObjects;
                    scope.searchNext = object.formalname+' '+object.shortname;
                    showDropdownList.push({value: true});
                    if (level===0) directiveScopes.push(scope);
                    scope.getObjectsListByGuid();
                }
                $listContainer.removeClass('show');
            };
        }
    }
});