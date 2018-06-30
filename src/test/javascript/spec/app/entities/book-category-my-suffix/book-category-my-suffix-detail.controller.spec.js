'use strict';

describe('Controller Tests', function() {

    describe('BookCategory Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockBookCategory, MockBookSubCategory, MockBookType;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockBookCategory = jasmine.createSpy('MockBookCategory');
            MockBookSubCategory = jasmine.createSpy('MockBookSubCategory');
            MockBookType = jasmine.createSpy('MockBookType');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'BookCategory': MockBookCategory,
                'BookSubCategory': MockBookSubCategory,
                'BookType': MockBookType
            };
            createController = function() {
                $injector.get('$controller')("BookCategoryMySuffixDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'cloudApp:bookCategoryUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
