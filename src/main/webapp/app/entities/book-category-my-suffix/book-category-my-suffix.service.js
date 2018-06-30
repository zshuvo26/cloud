(function() {
    'use strict';
    angular
        .module('cloudApp')
        .factory('BookCategory', BookCategory);

    BookCategory.$inject = ['$resource'];

    function BookCategory ($resource) {
        var resourceUrl =  'api/book-categories/:id';

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
