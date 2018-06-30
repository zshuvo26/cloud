(function() {
    'use strict';

    angular
        .module('cloudApp')
        .controller('FileTypeMySuffixDeleteController',FileTypeMySuffixDeleteController);

    FileTypeMySuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'FileType'];

    function FileTypeMySuffixDeleteController($uibModalInstance, entity, FileType) {
        var vm = this;

        vm.fileType = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            FileType.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
