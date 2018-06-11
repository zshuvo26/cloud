(function() {
    'use strict';

    angular
        .module('cloudApp')
        .controller('CountryMySuffixDetailController', CountryMySuffixDetailController);

    CountryMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Country', 'Region', 'City'];

    function CountryMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, Country, Region, City) {
        var vm = this;

        vm.country = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cloudApp:countryUpdate', function(event, result) {
            vm.country = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
