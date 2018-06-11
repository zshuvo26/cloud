(function() {
    'use strict';

    angular
        .module('cloudApp')
        .controller('UpazilaMySuffixDialogController', UpazilaMySuffixDialogController);

    UpazilaMySuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Upazila', 'District', 'Student', 'Institute'];

    function UpazilaMySuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Upazila, District, Student, Institute) {
        var vm = this;

        vm.upazila = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.districts = District.query();
        vm.students = Student.query();
        vm.institutes = Institute.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.upazila.id !== null) {
                Upazila.update(vm.upazila, onSaveSuccess, onSaveError);
            } else {
                Upazila.save(vm.upazila, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cloudApp:upazilaUpdate', result);
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
