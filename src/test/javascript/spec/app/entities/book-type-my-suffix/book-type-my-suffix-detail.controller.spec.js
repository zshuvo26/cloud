'use strict';

describe('Controller Tests', function() {

    describe('BookType Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockBookType, MockBookCategory, MockBookFineSetting;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockBookType = jasmine.createSpy('MockBookType');
            MockBookCategory = jasmine.createSpy('MockBookCategory');
            MockBookFineSetting = jasmine.createSpy('MockBookFineSetting');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'BookType': MockBookType,
                'BookCategory': MockBookCategory,
                'BookFineSetting': MockBookFineSetting
            };
            createController = function() {
                $injector.get('$controller')("BookTypeMySuffixDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'cloudApp:bookTypeUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
