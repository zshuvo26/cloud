(function() {
    'use strict';

    angular
        .module('cloudApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('employee-my-suffix', {
            parent: 'entity',
            url: '/employee-my-suffix?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cloudApp.employee.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/employee-my-suffix/employeesmySuffix.html',
                    controller: 'EmployeeMySuffixController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('employee');
                    $translatePartialLoader.addPart('gender');
                    $translatePartialLoader.addPart('employeeType');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('employee-my-suffix-detail', {
            parent: 'employee-my-suffix',
            url: '/employee-my-suffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cloudApp.employee.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/employee-my-suffix/employee-my-suffix-detail.html',
                    controller: 'EmployeeMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('employee');
                    $translatePartialLoader.addPart('gender');
                    $translatePartialLoader.addPart('employeeType');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Employee', function($stateParams, Employee) {
                    return Employee.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'employee-my-suffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('employee-my-suffix-detail.edit', {
            parent: 'employee-my-suffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/employee-my-suffix/employee-my-suffix-dialog.html',
                    controller: 'EmployeeMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Employee', function(Employee) {
                            return Employee.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('employee-my-suffix.new', {
            parent: 'employee-my-suffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/employee-my-suffix/employee-my-suffix-dialog.html',
                    controller: 'EmployeeMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                firstName: null,
                                lastName: null,
                                employeeId: null,
                                email: null,
                                phoneNumber: null,
                                photo: null,
                                photoContentType: null,
                                photoType: null,
                                photoName: null,
                                signature: null,
                                signatureContentType: null,
                                signatureType: null,
                                signatureName: null,
                                dob: null,
                                nid: null,
                                gender: null,
                                employeeType: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('employee-my-suffix', null, { reload: 'employee-my-suffix' });
                }, function() {
                    $state.go('employee-my-suffix');
                });
            }]
        })
        .state('employee-my-suffix.edit', {
            parent: 'employee-my-suffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/employee-my-suffix/employee-my-suffix-dialog.html',
                    controller: 'EmployeeMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Employee', function(Employee) {
                            return Employee.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('employee-my-suffix', null, { reload: 'employee-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('employee-my-suffix.delete', {
            parent: 'employee-my-suffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/employee-my-suffix/employee-my-suffix-delete-dialog.html',
                    controller: 'EmployeeMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Employee', function(Employee) {
                            return Employee.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('employee-my-suffix', null, { reload: 'employee-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
