(function() {
    'use strict';

    angular
        .module('cloudApp')
        .controller('BookReturnMySuffixDialogController', BookReturnMySuffixDialogController);

    BookReturnMySuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'BookReturn', 'BookIssue', 'BookFineSetting'];

    function BookReturnMySuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, BookReturn, BookIssue, BookFineSetting) {
        var vm = this;

        vm.bookReturn = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.bookissues = BookIssue.query();
        vm.bookfinesettings = BookFineSetting.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.bookReturn.id !== null) {
                BookReturn.update(vm.bookReturn, onSaveSuccess, onSaveError);
            } else {
                BookReturn.save(vm.bookReturn, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cloudApp:bookReturnUpdate', result);
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
