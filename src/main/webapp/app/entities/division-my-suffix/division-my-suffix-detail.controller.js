(function() {
    'use strict';

    angular
        .module('cloudApp')
        .controller('DivisionMySuffixDetailController', DivisionMySuffixDetailController);

    DivisionMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Division', 'District'];

    function DivisionMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, Division, District) {
        var vm = this;

        vm.division = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cloudApp:divisionUpdate', function(event, result) {
            vm.division = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
