'use strict';

describe('Controller Tests', function() {

    describe('DigitalContent Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockDigitalContent, MockBookSubCategory, MockFileType;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockDigitalContent = jasmine.createSpy('MockDigitalContent');
            MockBookSubCategory = jasmine.createSpy('MockBookSubCategory');
            MockFileType = jasmine.createSpy('MockFileType');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'DigitalContent': MockDigitalContent,
                'BookSubCategory': MockBookSubCategory,
                'FileType': MockFileType
            };
            createController = function() {
                $injector.get('$controller')("DigitalContentMySuffixDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'cloudApp:digitalContentUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
