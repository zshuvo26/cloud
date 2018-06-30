(function() {
    'use strict';

    angular
        .module('cloudApp')
        .controller('EditionMySuffixDeleteController',EditionMySuffixDeleteController);

    EditionMySuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'Edition'];

    function EditionMySuffixDeleteController($uibModalInstance, entity, Edition) {
        var vm = this;

        vm.edition = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Edition.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
