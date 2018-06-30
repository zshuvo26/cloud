(function() {
    'use strict';

    angular
        .module('cloudApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('publisher-my-suffix', {
            parent: 'entity',
            url: '/publisher-my-suffix?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cloudApp.publisher.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/publisher-my-suffix/publishersmySuffix.html',
                    controller: 'PublisherMySuffixController',
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
                    $translatePartialLoader.addPart('publisher');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('publisher-my-suffix-detail', {
            parent: 'publisher-my-suffix',
            url: '/publisher-my-suffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cloudApp.publisher.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/publisher-my-suffix/publisher-my-suffix-detail.html',
                    controller: 'PublisherMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('publisher');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Publisher', function($stateParams, Publisher) {
                    return Publisher.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'publisher-my-suffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('publisher-my-suffix-detail.edit', {
            parent: 'publisher-my-suffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/publisher-my-suffix/publisher-my-suffix-dialog.html',
                    controller: 'PublisherMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Publisher', function(Publisher) {
                            return Publisher.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('publisher-my-suffix.new', {
            parent: 'publisher-my-suffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/publisher-my-suffix/publisher-my-suffix-dialog.html',
                    controller: 'PublisherMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                place: null,
                                createDate: null,
                                updateDate: null,
                                createBy: null,
                                updateBy: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('publisher-my-suffix', null, { reload: 'publisher-my-suffix' });
                }, function() {
                    $state.go('publisher-my-suffix');
                });
            }]
        })
        .state('publisher-my-suffix.edit', {
            parent: 'publisher-my-suffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/publisher-my-suffix/publisher-my-suffix-dialog.html',
                    controller: 'PublisherMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Publisher', function(Publisher) {
                            return Publisher.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('publisher-my-suffix', null, { reload: 'publisher-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('publisher-my-suffix.delete', {
            parent: 'publisher-my-suffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/publisher-my-suffix/publisher-my-suffix-delete-dialog.html',
                    controller: 'PublisherMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Publisher', function(Publisher) {
                            return Publisher.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('publisher-my-suffix', null, { reload: 'publisher-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
