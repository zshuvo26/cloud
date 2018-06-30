'use strict';

describe('Controller Tests', function() {

    describe('Institute Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockInstitute, MockUpazila, MockCity, MockUser, MockDepartment, MockBookInfo;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockInstitute = jasmine.createSpy('MockInstitute');
            MockUpazila = jasmine.createSpy('MockUpazila');
            MockCity = jasmine.createSpy('MockCity');
            MockUser = jasmine.createSpy('MockUser');
            MockDepartment = jasmine.createSpy('MockDepartment');
            MockBookInfo = jasmine.createSpy('MockBookInfo');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Institute': MockInstitute,
                'Upazila': MockUpazila,
                'City': MockCity,
                'User': MockUser,
                'Department': MockDepartment,
                'BookInfo': MockBookInfo
            };
            createController = function() {
                $injector.get('$controller')("InstituteMySuffixDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'cloudApp:instituteUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
