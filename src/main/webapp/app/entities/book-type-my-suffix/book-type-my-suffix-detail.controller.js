(function() {
    'use strict';

    angular
        .module('cloudApp')
        .controller('BookTypeMySuffixDetailController', BookTypeMySuffixDetailController);

    BookTypeMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'BookType', 'BookCategory', 'BookFineSetting'];

    function BookTypeMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, BookType, BookCategory, BookFineSetting) {
        var vm = this;

        vm.bookType = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cloudApp:bookTypeUpdate', function(event, result) {
            vm.bookType = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
