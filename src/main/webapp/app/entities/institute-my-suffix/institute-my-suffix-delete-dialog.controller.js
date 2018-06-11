(function() {
    'use strict';

    angular
        .module('cloudApp')
        .controller('InstituteMySuffixDeleteController',InstituteMySuffixDeleteController);

    InstituteMySuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'Institute'];

    function InstituteMySuffixDeleteController($uibModalInstance, entity, Institute) {
        var vm = this;

        vm.institute = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Institute.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
