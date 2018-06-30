(function() {
    'use strict';

    angular
        .module('cloudApp')
        .controller('BookIssueMySuffixDialogController', BookIssueMySuffixDialogController);

    BookIssueMySuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'BookIssue', 'BookInfo', 'BookReturn'];

    function BookIssueMySuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, BookIssue, BookInfo, BookReturn) {
        var vm = this;

        vm.bookIssue = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.bookinfos = BookInfo.query();
        vm.bookreturns = BookReturn.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.bookIssue.id !== null) {
                BookIssue.update(vm.bookIssue, onSaveSuccess, onSaveError);
            } else {
                BookIssue.save(vm.bookIssue, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cloudApp:bookIssueUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.returnDate = false;
        vm.datePickerOpenStatus.createDate = false;
        vm.datePickerOpenStatus.updateDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
