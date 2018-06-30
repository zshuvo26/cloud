(function() {
    'use strict';

    angular
        .module('cloudApp')
        .controller('BookFineSettingMySuffixDetailController', BookFineSettingMySuffixDetailController);

    BookFineSettingMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'BookFineSetting', 'BookReturn', 'BookType'];

    function BookFineSettingMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, BookFineSetting, BookReturn, BookType) {
        var vm = this;

        vm.bookFineSetting = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cloudApp:bookFineSettingUpdate', function(event, result) {
            vm.bookFineSetting = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
