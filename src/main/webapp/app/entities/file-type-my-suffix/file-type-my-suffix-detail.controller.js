(function() {
    'use strict';

    angular
        .module('cloudApp')
        .controller('FileTypeMySuffixDetailController', FileTypeMySuffixDetailController);

    FileTypeMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'FileType', 'DigitalContent'];

    function FileTypeMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, FileType, DigitalContent) {
        var vm = this;

        vm.fileType = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cloudApp:fileTypeUpdate', function(event, result) {
            vm.fileType = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
