'use strict';

describe('Controller Tests', function() {

    describe('BookReturn Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockBookReturn, MockBookIssue, MockBookFineSetting;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockBookReturn = jasmine.createSpy('MockBookReturn');
            MockBookIssue = jasmine.createSpy('MockBookIssue');
            MockBookFineSetting = jasmine.createSpy('MockBookFineSetting');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'BookReturn': MockBookReturn,
                'BookIssue': MockBookIssue,
                'BookFineSetting': MockBookFineSetting
            };
            createController = function() {
                $injector.get('$controller')("BookReturnMySuffixDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'cloudApp:bookReturnUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
