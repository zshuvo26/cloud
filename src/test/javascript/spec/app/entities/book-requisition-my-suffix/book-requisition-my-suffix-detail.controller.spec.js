'use strict';

describe('Controller Tests', function() {

    describe('BookRequisition Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockBookRequisition, MockBookSubCategory;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockBookRequisition = jasmine.createSpy('MockBookRequisition');
            MockBookSubCategory = jasmine.createSpy('MockBookSubCategory');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'BookRequisition': MockBookRequisition,
                'BookSubCategory': MockBookSubCategory
            };
            createController = function() {
                $injector.get('$controller')("BookRequisitionMySuffixDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'cloudApp:bookRequisitionUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
