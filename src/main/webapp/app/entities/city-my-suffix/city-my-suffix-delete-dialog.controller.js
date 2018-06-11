(function() {
    'use strict';

    angular
        .module('cloudApp')
        .controller('CityMySuffixDeleteController',CityMySuffixDeleteController);

    CityMySuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'City'];

    function CityMySuffixDeleteController($uibModalInstance, entity, City) {
        var vm = this;

        vm.city = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            City.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
