(function() {
    'use strict';

    angular
        .module('cloudApp')
        .controller('BookRequisitionMySuffixDialogController', BookRequisitionMySuffixDialogController);

    BookRequisitionMySuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'BookRequisition', 'BookSubCategory'];

    function BookRequisitionMySuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, BookRequisition, BookSubCategory) {
        var vm = this;

        vm.bookRequisition = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.booksubcategories = BookSubCategory.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.bookRequisition.id !== null) {
                BookRequisition.update(vm.bookRequisition, onSaveSuccess, onSaveError);
            } else {
                BookRequisition.save(vm.bookRequisition, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cloudApp:bookRequisitionUpdate', result);
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
