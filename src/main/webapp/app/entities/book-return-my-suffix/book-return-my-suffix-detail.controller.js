(function() {
    'use strict';

    angular
        .module('cloudApp')
        .controller('BookReturnMySuffixDetailController', BookReturnMySuffixDetailController);

    BookReturnMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'BookReturn', 'BookIssue', 'BookFineSetting'];

    function BookReturnMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, BookReturn, BookIssue, BookFineSetting) {
        var vm = this;

        vm.bookReturn = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cloudApp:bookReturnUpdate', function(event, result) {
            vm.bookReturn = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
