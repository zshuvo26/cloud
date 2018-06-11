(function() {
    'use strict';

    angular
        .module('cloudApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('division-my-suffix', {
            parent: 'entity',
            url: '/division-my-suffix?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cloudApp.division.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/division-my-suffix/divisionsmySuffix.html',
                    controller: 'DivisionMySuffixController',
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
                    $translatePartialLoader.addPart('division');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('division-my-suffix-detail', {
            parent: 'division-my-suffix',
            url: '/division-my-suffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cloudApp.division.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/division-my-suffix/division-my-suffix-detail.html',
                    controller: 'DivisionMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('division');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Division', function($stateParams, Division) {
                    return Division.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'division-my-suffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('division-my-suffix-detail.edit', {
            parent: 'division-my-suffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/division-my-suffix/division-my-suffix-dialog.html',
                    controller: 'DivisionMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Division', function(Division) {
                            return Division.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('division-my-suffix.new', {
            parent: 'division-my-suffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/division-my-suffix/division-my-suffix-dialog.html',
                    controller: 'DivisionMySuffixDialogController',
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
                    $state.go('division-my-suffix', null, { reload: 'division-my-suffix' });
                }, function() {
                    $state.go('division-my-suffix');
                });
            }]
        })
        .state('division-my-suffix.edit', {
            parent: 'division-my-suffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/division-my-suffix/division-my-suffix-dialog.html',
                    controller: 'DivisionMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Division', function(Division) {
                            return Division.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('division-my-suffix', null, { reload: 'division-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('division-my-suffix.delete', {
            parent: 'division-my-suffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/division-my-suffix/division-my-suffix-delete-dialog.html',
                    controller: 'DivisionMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Division', function(Division) {
                            return Division.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('division-my-suffix', null, { reload: 'division-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
