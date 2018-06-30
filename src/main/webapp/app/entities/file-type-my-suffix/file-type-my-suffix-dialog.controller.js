(function() {
    'use strict';

    angular
        .module('cloudApp')
        .controller('FileTypeMySuffixDialogController', FileTypeMySuffixDialogController);

    FileTypeMySuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'FileType', 'DigitalContent'];

    function FileTypeMySuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, FileType, DigitalContent) {
        var vm = this;

        vm.fileType = entity;
        vm.clear = clear;
        vm.save = save;
        vm.digitalcontents = DigitalContent.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.fileType.id !== null) {
                FileType.update(vm.fileType, onSaveSuccess, onSaveError);
            } else {
                FileType.save(vm.fileType, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cloudApp:fileTypeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
