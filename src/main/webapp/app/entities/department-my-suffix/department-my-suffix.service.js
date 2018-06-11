(function() {
    'use strict';
    angular
        .module('cloudApp')
        .factory('Department', Department)
        .factory('DepartmentListByInstitute', DepartmentListByInstitute);

    Department.$inject = ['$resource', 'DateUtils'];
    DepartmentListByInstitute.$inject = ['$resource'];

    function Department ($resource, DateUtils) {
        var resourceUrl =  'api/departments/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.estdDate = DateUtils.convertLocalDateFromServer(data.estdDate);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.estdDate = DateUtils.convertLocalDateToServer(copy.estdDate);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.estdDate = DateUtils.convertLocalDateToServer(copy.estdDate);
                    return angular.toJson(copy);
                }
            }
        });
    }

    function DepartmentListByInstitute ($resource) {
        var resourceUrl =  'api/departments/institutewisedepartments';

        return $resource(resourceUrl, {}, {
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            }

        });
    }
})();
