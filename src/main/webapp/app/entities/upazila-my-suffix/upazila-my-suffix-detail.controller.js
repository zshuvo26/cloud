(function() {
    'use strict';

    angular
        .module('cloudApp')
        .controller('UpazilaMySuffixDetailController', UpazilaMySuffixDetailController);

    UpazilaMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Upazila', 'District', 'Student', 'Institute'];

    function UpazilaMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, Upazila, District, Student, Institute) {
        var vm = this;

        vm.upazila = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cloudApp:upazilaUpdate', function(event, result) {
            vm.upazila = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
