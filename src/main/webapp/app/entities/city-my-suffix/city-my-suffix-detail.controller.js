(function() {
    'use strict';

    angular
        .module('cloudApp')
        .controller('CityMySuffixDetailController', CityMySuffixDetailController);

    CityMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'City', 'Country'];

    function CityMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, City, Country) {
        var vm = this;

        vm.city = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cloudApp:cityUpdate', function(event, result) {
            vm.city = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
