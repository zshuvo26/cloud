(function() {
    'use strict';

    angular
        .module('cloudApp')
        .controller('DepartmentMySuffixDetailController', DepartmentMySuffixDetailController);

    DepartmentMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Department', 'Institute', 'Employee', 'Student', 'Session'];

    function DepartmentMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, Department, Institute, Employee, Student, Session) {
        var vm = this;

        vm.department = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cloudApp:departmentUpdate', function(event, result) {
            vm.department = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
