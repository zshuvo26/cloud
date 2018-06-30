(function() {
    'use strict';

    angular
        .module('cloudApp')
        .controller('BookIssueMySuffixDetailController', BookIssueMySuffixDetailController);

    BookIssueMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'BookIssue', 'BookInfo', 'BookReturn'];

    function BookIssueMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, BookIssue, BookInfo, BookReturn) {
        var vm = this;

        vm.bookIssue = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cloudApp:bookIssueUpdate', function(event, result) {
            vm.bookIssue = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
