'use strict';

describe('Controller Tests', function() {

    describe('BookFineSetting Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockBookFineSetting, MockBookReturn, MockBookType;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockBookFineSetting = jasmine.createSpy('MockBookFineSetting');
            MockBookReturn = jasmine.createSpy('MockBookReturn');
            MockBookType = jasmine.createSpy('MockBookType');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'BookFineSetting': MockBookFineSetting,
                'BookReturn': MockBookReturn,
                'BookType': MockBookType
            };
            createController = function() {
                $injector.get('$controller')("BookFineSettingMySuffixDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'cloudApp:bookFineSettingUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
