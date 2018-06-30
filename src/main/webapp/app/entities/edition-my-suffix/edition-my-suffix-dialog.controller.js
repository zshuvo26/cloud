(function() {
    'use strict';

    angular
        .module('cloudApp')
        .controller('EditionMySuffixDialogController', EditionMySuffixDialogController);

    EditionMySuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Edition', 'BookInfo'];

    function EditionMySuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Edition, BookInfo) {
        var vm = this;

        vm.edition = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.bookinfos = BookInfo.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.edition.id !== null) {
                Edition.update(vm.edition, onSaveSuccess, onSaveError);
            } else {
                Edition.save(vm.edition, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cloudApp:editionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.createDate = false;
        vm.datePickerOpenStatus.updateDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
