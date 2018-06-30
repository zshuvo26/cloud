(function() {
    'use strict';

    angular
        .module('cloudApp')
        .controller('BookCategoryMySuffixDialogController', BookCategoryMySuffixDialogController);

    BookCategoryMySuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'BookCategory', 'BookSubCategory', 'BookType'];

    function BookCategoryMySuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, BookCategory, BookSubCategory, BookType) {
        var vm = this;

        vm.bookCategory = entity;
        vm.clear = clear;
        vm.save = save;
        vm.booksubcategories = BookSubCategory.query();
        vm.booktypes = BookType.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.bookCategory.id !== null) {
                BookCategory.update(vm.bookCategory, onSaveSuccess, onSaveError);
            } else {
                BookCategory.save(vm.bookCategory, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cloudApp:bookCategoryUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
