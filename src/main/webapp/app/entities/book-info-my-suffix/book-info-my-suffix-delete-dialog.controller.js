(function() {
    'use strict';

    angular
        .module('cloudApp')
        .controller('BookInfoMySuffixDeleteController',BookInfoMySuffixDeleteController);

    BookInfoMySuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'BookInfo'];

    function BookInfoMySuffixDeleteController($uibModalInstance, entity, BookInfo) {
        var vm = this;

        vm.bookInfo = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            BookInfo.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
