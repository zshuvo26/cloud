(function() {
    'use strict';

    angular
        .module('cloudApp')
        .controller('DistrictMySuffixDialogController', DistrictMySuffixDialogController);

    DistrictMySuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'District', 'Division', 'Upazila'];

    function DistrictMySuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, District, Division, Upazila) {
        var vm = this;

        vm.district = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.divisions = Division.query();
        vm.upazilas = Upazila.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.district.id !== null) {
                District.update(vm.district, onSaveSuccess, onSaveError);
            } else {
                District.save(vm.district, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cloudApp:districtUpdate', result);
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
