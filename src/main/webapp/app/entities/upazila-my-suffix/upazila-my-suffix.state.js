(function() {
    'use strict';

    angular
        .module('cloudApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('upazila-my-suffix', {
            parent: 'entity',
            url: '/upazila-my-suffix?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cloudApp.upazila.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/upazila-my-suffix/upazilasmySuffix.html',
                    controller: 'UpazilaMySuffixController',
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
                    $translatePartialLoader.addPart('upazila');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('upazila-my-suffix-detail', {
            parent: 'upazila-my-suffix',
            url: '/upazila-my-suffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cloudApp.upazila.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/upazila-my-suffix/upazila-my-suffix-detail.html',
                    controller: 'UpazilaMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('upazila');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Upazila', function($stateParams, Upazila) {
                    return Upazila.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'upazila-my-suffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('upazila-my-suffix-detail.edit', {
            parent: 'upazila-my-suffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/upazila-my-suffix/upazila-my-suffix-dialog.html',
                    controller: 'UpazilaMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Upazila', function(Upazila) {
                            return Upazila.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('upazila-my-suffix.new', {
            parent: 'upazila-my-suffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/upazila-my-suffix/upazila-my-suffix-dialog.html',
                    controller: 'UpazilaMySuffixDialogController',
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
                    $state.go('upazila-my-suffix', null, { reload: 'upazila-my-suffix' });
                }, function() {
                    $state.go('upazila-my-suffix');
                });
            }]
        })
        .state('upazila-my-suffix.edit', {
            parent: 'upazila-my-suffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/upazila-my-suffix/upazila-my-suffix-dialog.html',
                    controller: 'UpazilaMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Upazila', function(Upazila) {
                            return Upazila.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('upazila-my-suffix', null, { reload: 'upazila-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('upazila-my-suffix.delete', {
            parent: 'upazila-my-suffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/upazila-my-suffix/upazila-my-suffix-delete-dialog.html',
                    controller: 'UpazilaMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Upazila', function(Upazila) {
                            return Upazila.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('upazila-my-suffix', null, { reload: 'upazila-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
