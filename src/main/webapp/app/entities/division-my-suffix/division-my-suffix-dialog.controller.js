(function() {
    'use strict';

    angular
        .module('cloudApp')
        .controller('DivisionMySuffixDialogController', DivisionMySuffixDialogController);

    DivisionMySuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Division', 'District'];

    function DivisionMySuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Division, District) {
        var vm = this;

        vm.division = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.districts = District.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.division.id !== null) {
                Division.update(vm.division, onSaveSuccess, onSaveError);
            } else {
                Division.save(vm.division, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cloudApp:divisionUpdate', result);
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
