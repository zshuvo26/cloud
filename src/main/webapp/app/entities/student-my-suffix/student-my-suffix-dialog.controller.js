(function() {
    'use strict';

    angular
        .module('cloudApp')
        .controller('StudentMySuffixDialogController', StudentMySuffixDialogController);

    StudentMySuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'DataUtils', 'entity', 'Student', 'Upazila', 'Department', 'Session', 'User'];

    function StudentMySuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, DataUtils, entity, Student, Upazila, Department, Session, User) {
        var vm = this;

        vm.student = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.upazilas = Upazila.query();
        vm.departments = Department.query();
        vm.sessions = Session.query();
        vm.users = User.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.student.id !== null) {
                Student.update(vm.student, onSaveSuccess, onSaveError);
            } else {
                Student.save(vm.student, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cloudApp:studentUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        vm.setPhoto = function ($file, student) {
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        student.photo = base64Data;
                        student.photoContentType = $file.type;
                    });
                });
            }
        };

        vm.setSignature = function ($file, student) {
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        student.signature = base64Data;
                        student.signatureContentType = $file.type;
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
