(function() {
    'use strict';

    angular
        .module('cloudApp')
        .controller('EditionMySuffixDetailController', EditionMySuffixDetailController);

    EditionMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Edition', 'BookInfo'];

    function EditionMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, Edition, BookInfo) {
        var vm = this;

        vm.edition = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cloudApp:editionUpdate', function(event, result) {
            vm.edition = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
