'use strict';

describe('Controller Tests', function() {

    describe('Employee Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockEmployee, MockDepartment, MockDesignation, MockUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockEmployee = jasmine.createSpy('MockEmployee');
            MockDepartment = jasmine.createSpy('MockDepartment');
            MockDesignation = jasmine.createSpy('MockDesignation');
            MockUser = jasmine.createSpy('MockUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Employee': MockEmployee,
                'Department': MockDepartment,
                'Designation': MockDesignation,
                'User': MockUser
            };
            createController = function() {
                $injector.get('$controller')("EmployeeMySuffixDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'cloudApp:employeeUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
