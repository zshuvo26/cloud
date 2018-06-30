(function() {
    'use strict';

    angular
        .module('cloudApp')
        .controller('BookCategoryMySuffixDeleteController',BookCategoryMySuffixDeleteController);

    BookCategoryMySuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'BookCategory'];

    function BookCategoryMySuffixDeleteController($uibModalInstance, entity, BookCategory) {
        var vm = this;

        vm.bookCategory = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            BookCategory.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
