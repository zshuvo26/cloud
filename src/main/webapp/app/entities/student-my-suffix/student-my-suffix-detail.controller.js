(function() {
    'use strict';

    angular
        .module('cloudApp')
        .controller('StudentMySuffixDetailController', StudentMySuffixDetailController);

    StudentMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Student', 'Upazila', 'Department', 'Session', 'User'];

    function StudentMySuffixDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Student, Upazila, Department, Session, User) {
        var vm = this;

        vm.student = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('cloudApp:studentUpdate', function(event, result) {
            vm.student = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
