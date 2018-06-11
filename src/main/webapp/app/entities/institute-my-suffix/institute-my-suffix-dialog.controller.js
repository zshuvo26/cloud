(function() {
    'use strict';

    angular
        .module('cloudApp')
        .controller('InstituteMySuffixDialogController', InstituteMySuffixDialogController);

    InstituteMySuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Institute', 'Upazila', 'City', 'User', 'Department'];

    function InstituteMySuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Institute, Upazila, City, User, Department) {
        var vm = this;

        vm.institute = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.upazilas = Upazila.query();
        vm.cities = City.query({filter: 'institute-is-null'});
        $q.all([vm.institute.$promise, vm.cities.$promise]).then(function() {
            if (!vm.institute.cityId) {
                return $q.reject();
            }
            return City.get({id : vm.institute.cityId}).$promise;
        }).then(function(city) {
            vm.cities.push(city);
        });
        vm.users = User.query();
        vm.departments = Department.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.institute.id !== null) {
                Institute.update(vm.institute, onSaveSuccess, onSaveError);
            } else {
                Institute.save(vm.institute, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cloudApp:instituteUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.estdDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
