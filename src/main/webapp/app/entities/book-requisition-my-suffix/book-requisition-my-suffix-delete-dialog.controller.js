(function() {
    'use strict';

    angular
        .module('cloudApp')
        .controller('BookRequisitionMySuffixDeleteController',BookRequisitionMySuffixDeleteController);

    BookRequisitionMySuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'BookRequisition'];

    function BookRequisitionMySuffixDeleteController($uibModalInstance, entity, BookRequisition) {
        var vm = this;

        vm.bookRequisition = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            BookRequisition.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
