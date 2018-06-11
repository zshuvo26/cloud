(function() {
    'use strict';

    angular
        .module('cloudApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('district-my-suffix', {
            parent: 'entity',
            url: '/district-my-suffix?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cloudApp.district.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/district-my-suffix/districtsmySuffix.html',
                    controller: 'DistrictMySuffixController',
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
                    $translatePartialLoader.addPart('district');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('district-my-suffix-detail', {
            parent: 'district-my-suffix',
            url: '/district-my-suffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cloudApp.district.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/district-my-suffix/district-my-suffix-detail.html',
                    controller: 'DistrictMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('district');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'District', function($stateParams, District) {
                    return District.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'district-my-suffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('district-my-suffix-detail.edit', {
            parent: 'district-my-suffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/district-my-suffix/district-my-suffix-dialog.html',
                    controller: 'DistrictMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['District', function(District) {
                            return District.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('district-my-suffix.new', {
            parent: 'district-my-suffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/district-my-suffix/district-my-suffix-dialog.html',
                    controller: 'DistrictMySuffixDialogController',
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
                    $state.go('district-my-suffix', null, { reload: 'district-my-suffix' });
                }, function() {
                    $state.go('district-my-suffix');
                });
            }]
        })
        .state('district-my-suffix.edit', {
            parent: 'district-my-suffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/district-my-suffix/district-my-suffix-dialog.html',
                    controller: 'DistrictMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['District', function(District) {
                            return District.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('district-my-suffix', null, { reload: 'district-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('district-my-suffix.delete', {
            parent: 'district-my-suffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/district-my-suffix/district-my-suffix-delete-dialog.html',
                    controller: 'DistrictMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['District', function(District) {
                            return District.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('district-my-suffix', null, { reload: 'district-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
