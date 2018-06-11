(function() {
    'use strict';

    angular
        .module('cloudApp')
        .controller('SessionMySuffixDetailController', SessionMySuffixDetailController);

    SessionMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Session', 'Department', 'Student'];

    function SessionMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, Session, Department, Student) {
        var vm = this;

        vm.session = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cloudApp:sessionUpdate', function(event, result) {
            vm.session = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
