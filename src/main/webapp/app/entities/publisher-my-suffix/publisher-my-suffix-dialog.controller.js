(function() {
    'use strict';

    angular
        .module('cloudApp')
        .controller('PublisherMySuffixDialogController', PublisherMySuffixDialogController);

    PublisherMySuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Publisher', 'BookInfo'];

    function PublisherMySuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Publisher, BookInfo) {
        var vm = this;

        vm.publisher = entity;
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
            if (vm.publisher.id !== null) {
                Publisher.update(vm.publisher, onSaveSuccess, onSaveError);
            } else {
                Publisher.save(vm.publisher, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cloudApp:publisherUpdate', result);
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
