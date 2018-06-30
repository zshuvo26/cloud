(function() {
    'use strict';

    angular
        .module('cloudApp')
        .controller('BookSubCategoryMySuffixDialogController', BookSubCategoryMySuffixDialogController);

    BookSubCategoryMySuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'BookSubCategory', 'BookCategory', 'BookInfo', 'BookRequisition', 'DigitalContent'];

    function BookSubCategoryMySuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, BookSubCategory, BookCategory, BookInfo, BookRequisition, DigitalContent) {
        var vm = this;

        vm.bookSubCategory = entity;
        vm.clear = clear;
        vm.save = save;
        vm.bookcategories = BookCategory.query();
        vm.bookinfos = BookInfo.query();
        vm.bookrequisitions = BookRequisition.query();
        vm.digitalcontents = DigitalContent.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.bookSubCategory.id !== null) {
                BookSubCategory.update(vm.bookSubCategory, onSaveSuccess, onSaveError);
            } else {
                BookSubCategory.save(vm.bookSubCategory, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cloudApp:bookSubCategoryUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
