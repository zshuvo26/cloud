(function() {
    'use strict';

    angular
        .module('cloudApp')
        .controller('BookFineSettingMySuffixDialogController', BookFineSettingMySuffixDialogController);

    BookFineSettingMySuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'BookFineSetting', 'BookReturn', 'BookType'];

    function BookFineSettingMySuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, BookFineSetting, BookReturn, BookType) {
        var vm = this;

        vm.bookFineSetting = entity;
        vm.clear = clear;
        vm.save = save;
        vm.bookreturns = BookReturn.query();
        vm.booktypes = BookType.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.bookFineSetting.id !== null) {
                BookFineSetting.update(vm.bookFineSetting, onSaveSuccess, onSaveError);
            } else {
                BookFineSetting.save(vm.bookFineSetting, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cloudApp:bookFineSettingUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
