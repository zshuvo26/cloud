(function() {
    'use strict';

    angular
        .module('cloudApp')
        .controller('BookReturnMySuffixDeleteController',BookReturnMySuffixDeleteController);

    BookReturnMySuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'BookReturn'];

    function BookReturnMySuffixDeleteController($uibModalInstance, entity, BookReturn) {
        var vm = this;

        vm.bookReturn = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            BookReturn.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
