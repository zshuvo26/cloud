(function() {
    'use strict';
    angular
        .module('cloudApp')
        .factory('BookIssue', BookIssue);

    BookIssue.$inject = ['$resource', 'DateUtils'];

    function BookIssue ($resource, DateUtils) {
        var resourceUrl =  'api/book-issues/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.returnDate = DateUtils.convertLocalDateFromServer(data.returnDate);
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
                    copy.returnDate = DateUtils.convertLocalDateToServer(copy.returnDate);
                    copy.createDate = DateUtils.convertLocalDateToServer(copy.createDate);
                    copy.updateDate = DateUtils.convertLocalDateToServer(copy.updateDate);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.returnDate = DateUtils.convertLocalDateToServer(copy.returnDate);
                    copy.createDate = DateUtils.convertLocalDateToServer(copy.createDate);
                    copy.updateDate = DateUtils.convertLocalDateToServer(copy.updateDate);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
