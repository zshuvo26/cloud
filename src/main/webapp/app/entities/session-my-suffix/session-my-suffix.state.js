(function() {
    'use strict';

    angular
        .module('cloudApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('session-my-suffix', {
            parent: 'entity',
            url: '/session-my-suffix?page&sort&search',
            data: {
                authorities: ['ROLE_INSTITUTE'],
                pageTitle: 'cloudApp.session.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/session-my-suffix/sessionsmySuffix.html',
                    controller: 'SessionMySuffixController',
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
                    $translatePartialLoader.addPart('session');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('session-my-suffix-detail', {
            parent: 'session-my-suffix',
            url: '/session-my-suffix/{id}',
            data: {
                authorities: ['ROLE_INSTITUTE'],
                pageTitle: 'cloudApp.session.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/session-my-suffix/session-my-suffix-detail.html',
                    controller: 'SessionMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('session');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Session', function($stateParams, Session) {
                    return Session.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'session-my-suffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('session-my-suffix-detail.edit', {
            parent: 'session-my-suffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_INSTITUTE']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/session-my-suffix/session-my-suffix-dialog.html',
                    controller: 'SessionMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Session', function(Session) {
                            return Session.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('session-my-suffix.new', {
            parent: 'session-my-suffix',
            url: '/new',
            data: {
                authorities: ['ROLE_INSTITUTE']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/session-my-suffix/session-my-suffix-dialog.html',
                    controller: 'SessionMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                estdDate: null,
                                status: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('session-my-suffix', null, { reload: 'session-my-suffix' });
                }, function() {
                    $state.go('session-my-suffix');
                });
            }]
        })
        .state('session-my-suffix.edit', {
            parent: 'session-my-suffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_INSTITUTE']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/session-my-suffix/session-my-suffix-dialog.html',
                    controller: 'SessionMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Session', function(Session) {
                            return Session.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('session-my-suffix', null, { reload: 'session-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('session-my-suffix.delete', {
            parent: 'session-my-suffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_INSTITUTE']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/session-my-suffix/session-my-suffix-delete-dialog.html',
                    controller: 'SessionMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Session', function(Session) {
                            return Session.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('session-my-suffix', null, { reload: 'session-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
