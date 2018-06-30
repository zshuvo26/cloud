(function() {
    'use strict';

    angular
        .module('cloudApp')
        .controller('PublisherMySuffixDeleteController',PublisherMySuffixDeleteController);

    PublisherMySuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'Publisher'];

    function PublisherMySuffixDeleteController($uibModalInstance, entity, Publisher) {
        var vm = this;

        vm.publisher = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Publisher.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
