(function() {
    'use strict';

    angular
        .module('cloudApp')
        .controller('DistrictMySuffixDeleteController',DistrictMySuffixDeleteController);

    DistrictMySuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'District'];

    function DistrictMySuffixDeleteController($uibModalInstance, entity, District) {
        var vm = this;

        vm.district = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            District.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
