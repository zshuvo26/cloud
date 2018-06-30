(function() {
    'use strict';

    angular
        .module('cloudApp')
        .controller('PublisherMySuffixDetailController', PublisherMySuffixDetailController);

    PublisherMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Publisher', 'BookInfo'];

    function PublisherMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, Publisher, BookInfo) {
        var vm = this;

        vm.publisher = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cloudApp:publisherUpdate', function(event, result) {
            vm.publisher = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
