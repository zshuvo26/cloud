(function() {
    'use strict';

    angular
        .module('cloudApp')
        .controller('BookFineSettingMySuffixDeleteController',BookFineSettingMySuffixDeleteController);

    BookFineSettingMySuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'BookFineSetting'];

    function BookFineSettingMySuffixDeleteController($uibModalInstance, entity, BookFineSetting) {
        var vm = this;

        vm.bookFineSetting = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            BookFineSetting.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
