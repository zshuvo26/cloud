(function() {
    'use strict';

    angular
        .module('cloudApp')
        .controller('DigitalContentMySuffixDeleteController',DigitalContentMySuffixDeleteController);

    DigitalContentMySuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'DigitalContent'];

    function DigitalContentMySuffixDeleteController($uibModalInstance, entity, DigitalContent) {
        var vm = this;

        vm.digitalContent = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            DigitalContent.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
