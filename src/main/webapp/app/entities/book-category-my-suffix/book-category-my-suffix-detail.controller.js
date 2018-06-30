(function() {
    'use strict';

    angular
        .module('cloudApp')
        .controller('BookCategoryMySuffixDetailController', BookCategoryMySuffixDetailController);

    BookCategoryMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'BookCategory', 'BookSubCategory', 'BookType'];

    function BookCategoryMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, BookCategory, BookSubCategory, BookType) {
        var vm = this;

        vm.bookCategory = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cloudApp:bookCategoryUpdate', function(event, result) {
            vm.bookCategory = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
