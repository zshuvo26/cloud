(function() {
    'use strict';

    angular
        .module('cloudApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('country-my-suffix', {
            parent: 'entity',
            url: '/country-my-suffix?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cloudApp.country.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/country-my-suffix/countriesmySuffix.html',
                    controller: 'CountryMySuffixController',
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
                    $translatePartialLoader.addPart('country');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('country-my-suffix-detail', {
            parent: 'country-my-suffix',
            url: '/country-my-suffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cloudApp.country.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/country-my-suffix/country-my-suffix-detail.html',
                    controller: 'CountryMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('country');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Country', function($stateParams, Country) {
                    return Country.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'country-my-suffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('country-my-suffix-detail.edit', {
            parent: 'country-my-suffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/country-my-suffix/country-my-suffix-dialog.html',
                    controller: 'CountryMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Country', function(Country) {
                            return Country.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('country-my-suffix.new', {
            parent: 'country-my-suffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/country-my-suffix/country-my-suffix-dialog.html',
                    controller: 'CountryMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                countryName: null,
                                capital: null,
                                countryCode: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('country-my-suffix', null, { reload: 'country-my-suffix' });
                }, function() {
                    $state.go('country-my-suffix');
                });
            }]
        })
        .state('country-my-suffix.edit', {
            parent: 'country-my-suffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/country-my-suffix/country-my-suffix-dialog.html',
                    controller: 'CountryMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Country', function(Country) {
                            return Country.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('country-my-suffix', null, { reload: 'country-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('country-my-suffix.delete', {
            parent: 'country-my-suffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/country-my-suffix/country-my-suffix-delete-dialog.html',
                    controller: 'CountryMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Country', function(Country) {
                            return Country.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('country-my-suffix', null, { reload: 'country-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
