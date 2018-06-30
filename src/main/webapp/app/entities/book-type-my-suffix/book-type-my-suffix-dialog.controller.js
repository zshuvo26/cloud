(function() {
    'use strict';

    angular
        .module('cloudApp')
        .controller('BookTypeMySuffixDialogController', BookTypeMySuffixDialogController);

    BookTypeMySuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'BookType', 'BookCategory', 'BookFineSetting'];

    function BookTypeMySuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, BookType, BookCategory, BookFineSetting) {
        var vm = this;

        vm.bookType = entity;
        vm.clear = clear;
        vm.save = save;
        vm.bookcategories = BookCategory.query();
        vm.bookfinesettings = BookFineSetting.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.bookType.id !== null) {
                BookType.update(vm.bookType, onSaveSuccess, onSaveError);
            } else {
                BookType.save(vm.bookType, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cloudApp:bookTypeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
