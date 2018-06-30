'use strict';

describe('Controller Tests', function() {

    describe('BookInfo Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockBookInfo, MockInstitute, MockPublisher, MockBookIssue, MockEdition, MockBookSubCategory;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockBookInfo = jasmine.createSpy('MockBookInfo');
            MockInstitute = jasmine.createSpy('MockInstitute');
            MockPublisher = jasmine.createSpy('MockPublisher');
            MockBookIssue = jasmine.createSpy('MockBookIssue');
            MockEdition = jasmine.createSpy('MockEdition');
            MockBookSubCategory = jasmine.createSpy('MockBookSubCategory');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'BookInfo': MockBookInfo,
                'Institute': MockInstitute,
                'Publisher': MockPublisher,
                'BookIssue': MockBookIssue,
                'Edition': MockEdition,
                'BookSubCategory': MockBookSubCategory
            };
            createController = function() {
                $injector.get('$controller')("BookInfoMySuffixDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'cloudApp:bookInfoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
