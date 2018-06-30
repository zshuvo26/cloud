'use strict';

describe('Controller Tests', function() {

    describe('BookSubCategory Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockBookSubCategory, MockBookCategory, MockBookInfo, MockBookRequisition, MockDigitalContent;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockBookSubCategory = jasmine.createSpy('MockBookSubCategory');
            MockBookCategory = jasmine.createSpy('MockBookCategory');
            MockBookInfo = jasmine.createSpy('MockBookInfo');
            MockBookRequisition = jasmine.createSpy('MockBookRequisition');
            MockDigitalContent = jasmine.createSpy('MockDigitalContent');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'BookSubCategory': MockBookSubCategory,
                'BookCategory': MockBookCategory,
                'BookInfo': MockBookInfo,
                'BookRequisition': MockBookRequisition,
                'DigitalContent': MockDigitalContent
            };
            createController = function() {
                $injector.get('$controller')("BookSubCategoryMySuffixDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'cloudApp:bookSubCategoryUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
