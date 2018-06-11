(function() {
    'use strict';

    angular
        .module('cloudApp')
        .controller('DistrictMySuffixDetailController', DistrictMySuffixDetailController);

    DistrictMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'District', 'Division', 'Upazila'];

    function DistrictMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, District, Division, Upazila) {
        var vm = this;

        vm.district = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cloudApp:districtUpdate', function(event, result) {
            vm.district = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
