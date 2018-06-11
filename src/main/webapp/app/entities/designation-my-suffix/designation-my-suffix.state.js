(function() {
    'use strict';

    angular
        .module('cloudApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('designation-my-suffix', {
            parent: 'entity',
            url: '/designation-my-suffix?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cloudApp.designation.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/designation-my-suffix/designationsmySuffix.html',
                    controller: 'DesignationMySuffixController',
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
                    $translatePartialLoader.addPart('designation');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('designation-my-suffix-detail', {
            parent: 'designation-my-suffix',
            url: '/designation-my-suffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cloudApp.designation.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/designation-my-suffix/designation-my-suffix-detail.html',
                    controller: 'DesignationMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('designation');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Designation', function($stateParams, Designation) {
                    return Designation.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'designation-my-suffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('designation-my-suffix-detail.edit', {
            parent: 'designation-my-suffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/designation-my-suffix/designation-my-suffix-dialog.html',
                    controller: 'DesignationMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Designation', function(Designation) {
                            return Designation.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('designation-my-suffix.new', {
            parent: 'designation-my-suffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/designation-my-suffix/designation-my-suffix-dialog.html',
                    controller: 'DesignationMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('designation-my-suffix', null, { reload: 'designation-my-suffix' });
                }, function() {
                    $state.go('designation-my-suffix');
                });
            }]
        })
        .state('designation-my-suffix.edit', {
            parent: 'designation-my-suffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/designation-my-suffix/designation-my-suffix-dialog.html',
                    controller: 'DesignationMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Designation', function(Designation) {
                            return Designation.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('designation-my-suffix', null, { reload: 'designation-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('designation-my-suffix.delete', {
            parent: 'designation-my-suffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/designation-my-suffix/designation-my-suffix-delete-dialog.html',
                    controller: 'DesignationMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Designation', function(Designation) {
                            return Designation.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('designation-my-suffix', null, { reload: 'designation-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
