(function() {
    'use strict';

    angular
        .module('cloudApp')
        .controller('SessionMySuffixDeleteController',SessionMySuffixDeleteController);

    SessionMySuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'Session'];

    function SessionMySuffixDeleteController($uibModalInstance, entity, Session) {
        var vm = this;

        vm.session = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Session.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
