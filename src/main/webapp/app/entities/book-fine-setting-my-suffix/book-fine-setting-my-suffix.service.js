(function() {
    'use strict';
    angular
        .module('cloudApp')
        .factory('BookFineSetting', BookFineSetting);

    BookFineSetting.$inject = ['$resource'];

    function BookFineSetting ($resource) {
        var resourceUrl =  'api/book-fine-settings/:id';

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
