'use strict';

describe('Controller Tests', function() {

    describe('Upazila Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockUpazila, MockDistrict, MockStudent, MockInstitute;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockUpazila = jasmine.createSpy('MockUpazila');
            MockDistrict = jasmine.createSpy('MockDistrict');
            MockStudent = jasmine.createSpy('MockStudent');
            MockInstitute = jasmine.createSpy('MockInstitute');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Upazila': MockUpazila,
                'District': MockDistrict,
                'Student': MockStudent,
                'Institute': MockInstitute
            };
            createController = function() {
                $injector.get('$controller')("UpazilaMySuffixDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'cloudApp:upazilaUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
