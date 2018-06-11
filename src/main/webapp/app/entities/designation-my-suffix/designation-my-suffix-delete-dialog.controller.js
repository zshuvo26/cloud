(function() {
    'use strict';

    angular
        .module('cloudApp')
        .controller('DesignationMySuffixDeleteController',DesignationMySuffixDeleteController);

    DesignationMySuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'Designation'];

    function DesignationMySuffixDeleteController($uibModalInstance, entity, Designation) {
        var vm = this;

        vm.designation = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Designation.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
