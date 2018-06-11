(function() {
    'use strict';

    angular
        .module('cloudApp')
        .controller('UpazilaMySuffixDeleteController',UpazilaMySuffixDeleteController);

    UpazilaMySuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'Upazila'];

    function UpazilaMySuffixDeleteController($uibModalInstance, entity, Upazila) {
        var vm = this;

        vm.upazila = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Upazila.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
