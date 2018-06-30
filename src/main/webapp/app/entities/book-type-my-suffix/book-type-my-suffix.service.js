(function() {
    'use strict';
    angular
        .module('cloudApp')
        .factory('BookType', BookType);

    BookType.$inject = ['$resource'];

    function BookType ($resource) {
        var resourceUrl =  'api/book-types/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
