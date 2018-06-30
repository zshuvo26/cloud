(function() {
    'use strict';
    angular
        .module('cloudApp')
        .factory('BookRequisition', BookRequisition);

    BookRequisition.$inject = ['$resource', 'DateUtils'];

    function BookRequisition ($resource, DateUtils) {
        var resourceUrl =  'api/book-requisitions/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.createDate = DateUtils.convertLocalDateFromServer(data.createDate);
                        data.updateDate = DateUtils.convertLocalDateFromServer(data.updateDate);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.createDate = DateUtils.convertLocalDateToServer(copy.createDate);
                    copy.updateDate = DateUtils.convertLocalDateToServer(copy.updateDate);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.createDate = DateUtils.convertLocalDateToServer(copy.createDate);
                    copy.updateDate = DateUtils.convertLocalDateToServer(copy.updateDate);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
