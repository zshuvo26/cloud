(function() {
    'use strict';

    angular
        .module('cloudApp')
        .controller('EmployeeMySuffixDetailController', EmployeeMySuffixDetailController);

    EmployeeMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Employee', 'Department', 'Designation', 'User'];

    function EmployeeMySuffixDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Employee, Department, Designation, User) {
        var vm = this;

        vm.employee = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('cloudApp:employeeUpdate', function(event, result) {
            vm.employee = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
