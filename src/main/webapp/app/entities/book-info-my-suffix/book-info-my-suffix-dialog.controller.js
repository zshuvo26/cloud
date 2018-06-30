(function() {
    'use strict';

    angular
        .module('cloudApp')
        .controller('BookInfoMySuffixDialogController', BookInfoMySuffixDialogController);

    BookInfoMySuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'BookInfo', 'Institute', 'Publisher', 'BookIssue', 'Edition', 'BookSubCategory'];

    function BookInfoMySuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, BookInfo, Institute, Publisher, BookIssue, Edition, BookSubCategory) {
        var vm = this;

        vm.bookInfo = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.institutes = Institute.query();
        vm.publishers = Publisher.query();
        vm.bookissues = BookIssue.query();
        vm.editions = Edition.query();
        vm.booksubcategories = BookSubCategory.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.bookInfo.id !== null) {
                BookInfo.update(vm.bookInfo, onSaveSuccess, onSaveError);
            } else {
                BookInfo.save(vm.bookInfo, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cloudApp:bookInfoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.billDate = false;

        vm.setCoverPhoto = function ($file, bookInfo) {
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        bookInfo.coverPhoto = base64Data;
                        bookInfo.coverPhotoContentType = $file.type;
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
