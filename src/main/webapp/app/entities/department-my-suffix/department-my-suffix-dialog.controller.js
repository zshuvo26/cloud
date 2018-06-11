(function() {
    'use strict';

    angular
        .module('cloudApp')
        .controller('DepartmentMySuffixDialogController', DepartmentMySuffixDialogController);

    DepartmentMySuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Department', 'Institute', 'Employee', 'Student', 'Session'];

    function DepartmentMySuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Department, Institute, Employee, Student, Session) {
        var vm = this;

        vm.department = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.institutes = Institute.query();
        vm.employees = Employee.query();
        vm.students = Student.query();
        vm.sessions = Session.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.department.id !== null) {
                Department.update(vm.department, onSaveSuccess, onSaveError);
            } else {
                Department.save(vm.department, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cloudApp:departmentUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.estdDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
