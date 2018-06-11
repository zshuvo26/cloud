(function() {
    'use strict';

    angular
        .module('cloudApp')
        .controller('EmployeeMySuffixDialogController', EmployeeMySuffixDialogController);

    EmployeeMySuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'DataUtils', 'entity', 'Employee', 'Department', 'Designation', 'User'];

    function EmployeeMySuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, DataUtils, entity, Employee, Department, Designation, User) {
        var vm = this;

        vm.employee = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.departments = Department.query();
        vm.designations = Designation.query();
        vm.users = User.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.employee.id !== null) {
                Employee.update(vm.employee, onSaveSuccess, onSaveError);
            } else {
                Employee.save(vm.employee, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cloudApp:employeeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        vm.setPhoto = function ($file, employee) {
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        employee.photo = base64Data;
                        employee.photoContentType = $file.type;
                    });
                });
            }
        };

        vm.setSignature = function ($file, employee) {
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        employee.signature = base64Data;
                        employee.signatureContentType = $file.type;
                    });
                });
            }
        };
        vm.datePickerOpenStatus.dob = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
