(function() {
    'use strict';
    angular
        .module('cloudApp')
        .factory('Institute', Institute);

    Institute.$inject = ['$resource', 'DateUtils'];

    function Institute ($resource, DateUtils) {
        var resourceUrl =  'api/institutes/:id';

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
})();
