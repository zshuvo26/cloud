(function() {
    'use strict';

    angular
        .module('cloudApp')
        .controller('DesignationMySuffixDialogController', DesignationMySuffixDialogController);

    DesignationMySuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Designation', 'Employee'];

    function DesignationMySuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Designation, Employee) {
        var vm = this;

        vm.designation = entity;
        vm.clear = clear;
        vm.save = save;
        vm.employees = Employee.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.designation.id !== null) {
                Designation.update(vm.designation, onSaveSuccess, onSaveError);
            } else {
                Designation.save(vm.designation, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cloudApp:designationUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
