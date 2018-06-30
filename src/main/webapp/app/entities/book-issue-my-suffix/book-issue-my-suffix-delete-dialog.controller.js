(function() {
    'use strict';

    angular
        .module('cloudApp')
        .controller('BookIssueMySuffixDeleteController',BookIssueMySuffixDeleteController);

    BookIssueMySuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'BookIssue'];

    function BookIssueMySuffixDeleteController($uibModalInstance, entity, BookIssue) {
        var vm = this;

        vm.bookIssue = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            BookIssue.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
