(function() {
    'use strict';

    angular
        .module('cloudApp')
        .controller('DigitalContentMySuffixDialogController', DigitalContentMySuffixDialogController);

    DigitalContentMySuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'DigitalContent', 'BookSubCategory', 'FileType'];

    function DigitalContentMySuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, DigitalContent, BookSubCategory, FileType) {
        var vm = this;

        vm.digitalContent = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.booksubcategories = BookSubCategory.query();
        vm.filetypes = FileType.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.digitalContent.id !== null) {
                DigitalContent.update(vm.digitalContent, onSaveSuccess, onSaveError);
            } else {
                DigitalContent.save(vm.digitalContent, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cloudApp:digitalContentUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        vm.setCoverPhoto = function ($file, digitalContent) {
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        digitalContent.coverPhoto = base64Data;
                        digitalContent.coverPhotoContentType = $file.type;
                    });
                });
            }
        };

        vm.setContent = function ($file, digitalContent) {
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        digitalContent.content = base64Data;
                        digitalContent.contentContentType = $file.type;
                    });
                });
            }
        };
        vm.datePickerOpenStatus.createDate = false;
        vm.datePickerOpenStatus.updateDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
