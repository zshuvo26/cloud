(function() {
    'use strict';

    angular
        .module('cloudApp')
        .controller('BookSubCategoryMySuffixDetailController', BookSubCategoryMySuffixDetailController);

    BookSubCategoryMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'BookSubCategory', 'BookCategory', 'BookInfo', 'BookRequisition', 'DigitalContent'];

    function BookSubCategoryMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, BookSubCategory, BookCategory, BookInfo, BookRequisition, DigitalContent) {
        var vm = this;

        vm.bookSubCategory = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cloudApp:bookSubCategoryUpdate', function(event, result) {
            vm.bookSubCategory = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
