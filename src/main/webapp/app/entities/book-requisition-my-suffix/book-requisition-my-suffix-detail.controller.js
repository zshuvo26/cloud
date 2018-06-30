(function() {
    'use strict';

    angular
        .module('cloudApp')
        .controller('BookRequisitionMySuffixDetailController', BookRequisitionMySuffixDetailController);

    BookRequisitionMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'BookRequisition', 'BookSubCategory'];

    function BookRequisitionMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, BookRequisition, BookSubCategory) {
        var vm = this;

        vm.bookRequisition = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cloudApp:bookRequisitionUpdate', function(event, result) {
            vm.bookRequisition = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
