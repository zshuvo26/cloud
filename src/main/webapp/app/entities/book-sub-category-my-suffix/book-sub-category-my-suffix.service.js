(function() {
    'use strict';
    angular
        .module('cloudApp')
        .factory('BookSubCategory', BookSubCategory);

    BookSubCategory.$inject = ['$resource'];

    function BookSubCategory ($resource) {
        var resourceUrl =  'api/book-sub-categories/:id';

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
