(function() {
    'use strict';

    angular
        .module('cloudApp')
        .controller('BookInfoMySuffixDetailController', BookInfoMySuffixDetailController);

    BookInfoMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'BookInfo', 'Institute', 'Publisher', 'BookIssue', 'Edition', 'BookSubCategory'];

    function BookInfoMySuffixDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, BookInfo, Institute, Publisher, BookIssue, Edition, BookSubCategory) {
        var vm = this;

        vm.bookInfo = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('cloudApp:bookInfoUpdate', function(event, result) {
            vm.bookInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
