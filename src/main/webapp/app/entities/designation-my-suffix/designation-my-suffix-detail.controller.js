(function() {
    'use strict';

    angular
        .module('cloudApp')
        .controller('DesignationMySuffixDetailController', DesignationMySuffixDetailController);

    DesignationMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Designation', 'Employee'];

    function DesignationMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, Designation, Employee) {
        var vm = this;

        vm.designation = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cloudApp:designationUpdate', function(event, result) {
            vm.designation = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
