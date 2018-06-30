(function() {
    'use strict';

    angular
        .module('cloudApp')
        .controller('BookTypeMySuffixDeleteController',BookTypeMySuffixDeleteController);

    BookTypeMySuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'BookType'];

    function BookTypeMySuffixDeleteController($uibModalInstance, entity, BookType) {
        var vm = this;

        vm.bookType = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            BookType.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
