'use strict';

describe('Controller Tests', function() {

    describe('BookIssue Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockBookIssue, MockBookInfo, MockBookReturn;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockBookIssue = jasmine.createSpy('MockBookIssue');
            MockBookInfo = jasmine.createSpy('MockBookInfo');
            MockBookReturn = jasmine.createSpy('MockBookReturn');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'BookIssue': MockBookIssue,
                'BookInfo': MockBookInfo,
                'BookReturn': MockBookReturn
            };
            createController = function() {
                $injector.get('$controller')("BookIssueMySuffixDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'cloudApp:bookIssueUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
