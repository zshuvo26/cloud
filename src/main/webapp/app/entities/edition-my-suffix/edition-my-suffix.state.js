(function() {
    'use strict';

    angular
        .module('cloudApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('edition-my-suffix', {
            parent: 'entity',
            url: '/edition-my-suffix?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cloudApp.edition.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/edition-my-suffix/editionsmySuffix.html',
                    controller: 'EditionMySuffixController',
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
                    $translatePartialLoader.addPart('edition');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('edition-my-suffix-detail', {
            parent: 'edition-my-suffix',
            url: '/edition-my-suffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cloudApp.edition.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/edition-my-suffix/edition-my-suffix-detail.html',
                    controller: 'EditionMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('edition');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Edition', function($stateParams, Edition) {
                    return Edition.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'edition-my-suffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('edition-my-suffix-detail.edit', {
            parent: 'edition-my-suffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/edition-my-suffix/edition-my-suffix-dialog.html',
                    controller: 'EditionMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Edition', function(Edition) {
                            return Edition.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('edition-my-suffix.new', {
            parent: 'edition-my-suffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/edition-my-suffix/edition-my-suffix-dialog.html',
                    controller: 'EditionMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                editionName: null,
                                totalCopies: null,
                                compensation: null,
                                createDate: null,
                                updateDate: null,
                                createBy: null,
                                updateBy: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('edition-my-suffix', null, { reload: 'edition-my-suffix' });
                }, function() {
                    $state.go('edition-my-suffix');
                });
            }]
        })
        .state('edition-my-suffix.edit', {
            parent: 'edition-my-suffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/edition-my-suffix/edition-my-suffix-dialog.html',
                    controller: 'EditionMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Edition', function(Edition) {
                            return Edition.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('edition-my-suffix', null, { reload: 'edition-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('edition-my-suffix.delete', {
            parent: 'edition-my-suffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/edition-my-suffix/edition-my-suffix-delete-dialog.html',
                    controller: 'EditionMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Edition', function(Edition) {
                            return Edition.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('edition-my-suffix', null, { reload: 'edition-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
