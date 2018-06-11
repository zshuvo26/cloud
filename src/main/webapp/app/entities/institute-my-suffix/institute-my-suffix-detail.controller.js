(function() {
    'use strict';

    angular
        .module('cloudApp')
        .controller('InstituteMySuffixDetailController', InstituteMySuffixDetailController);

    InstituteMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Institute', 'Upazila', 'City', 'User', 'Department'];

    function InstituteMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, Institute, Upazila, City, User, Department) {
        var vm = this;

        vm.institute = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cloudApp:instituteUpdate', function(event, result) {
            vm.institute = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
