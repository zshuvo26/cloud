(function() {
    'use strict';

    angular
        .module('cloudApp')
        .controller('DivisionMySuffixDeleteController',DivisionMySuffixDeleteController);

    DivisionMySuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'Division'];

    function DivisionMySuffixDeleteController($uibModalInstance, entity, Division) {
        var vm = this;

        vm.division = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Division.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
