(function() {
    'use strict';

    angular
        .module('cloudApp')
        .controller('BookSubCategoryMySuffixDeleteController',BookSubCategoryMySuffixDeleteController);

    BookSubCategoryMySuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'BookSubCategory'];

    function BookSubCategoryMySuffixDeleteController($uibModalInstance, entity, BookSubCategory) {
        var vm = this;

        vm.bookSubCategory = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            BookSubCategory.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
