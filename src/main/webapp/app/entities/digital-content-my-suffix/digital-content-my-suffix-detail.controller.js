(function() {
    'use strict';

    angular
        .module('cloudApp')
        .controller('DigitalContentMySuffixDetailController', DigitalContentMySuffixDetailController);

    DigitalContentMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'DigitalContent', 'BookSubCategory', 'FileType'];

    function DigitalContentMySuffixDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, DigitalContent, BookSubCategory, FileType) {
        var vm = this;

        vm.digitalContent = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('cloudApp:digitalContentUpdate', function(event, result) {
            vm.digitalContent = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
