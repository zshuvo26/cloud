'use strict';

describe('Controller Tests', function() {

    describe('Department Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockDepartment, MockInstitute, MockEmployee, MockStudent, MockSession;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockDepartment = jasmine.createSpy('MockDepartment');
            MockInstitute = jasmine.createSpy('MockInstitute');
            MockEmployee = jasmine.createSpy('MockEmployee');
            MockStudent = jasmine.createSpy('MockStudent');
            MockSession = jasmine.createSpy('MockSession');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Department': MockDepartment,
                'Institute': MockInstitute,
                'Employee': MockEmployee,
                'Student': MockStudent,
                'Session': MockSession
            };
            createController = function() {
                $injector.get('$controller')("DepartmentMySuffixDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'cloudApp:departmentUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
