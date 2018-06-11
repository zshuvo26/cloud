(function() {
    'use strict';

    angular
        .module('cloudApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('department-my-suffix', {
            parent: 'entity',
            url: '/department-my-suffix?page&sort&search',
            data: {
                authorities: ['ROLE_INSTITUTE'],
                pageTitle: 'cloudApp.department.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/department-my-suffix/departmentsmySuffix.html',
                    controller: 'DepartmentMySuffixController',
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
                    $translatePartialLoader.addPart('department');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('department-my-suffix-detail', {
            parent: 'department-my-suffix',
            url: '/department-my-suffix/{id}',
            data: {
                authorities: ['ROLE_INSTITUTE'],
                pageTitle: 'cloudApp.department.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/department-my-suffix/department-my-suffix-detail.html',
                    controller: 'DepartmentMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('department');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Department', function($stateParams, Department) {
                    return Department.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'department-my-suffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('department-my-suffix-detail.edit', {
            parent: 'department-my-suffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_INSTITUTE']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/department-my-suffix/department-my-suffix-dialog.html',
                    controller: 'DepartmentMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Department', function(Department) {
                            return Department.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('department-my-suffix.new', {
            parent: 'department-my-suffix',
            url: '/new',
            data: {
                authorities: ['ROLE_INSTITUTE']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/department-my-suffix/department-my-suffix-dialog.html',
                    controller: 'DepartmentMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                estdDate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('department-my-suffix', null, { reload: 'department-my-suffix' });
                }, function() {
                    $state.go('department-my-suffix');
                });
            }]
        })
        .state('department-my-suffix.edit', {
            parent: 'department-my-suffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_INSTITUTE']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/department-my-suffix/department-my-suffix-dialog.html',
                    controller: 'DepartmentMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Department', function(Department) {
                            return Department.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('department-my-suffix', null, { reload: 'department-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('department-my-suffix.delete', {
            parent: 'department-my-suffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_INSTITUTE']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/department-my-suffix/department-my-suffix-delete-dialog.html',
                    controller: 'DepartmentMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Department', function(Department) {
                            return Department.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('department-my-suffix', null, { reload: 'department-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
