(function() {
    'use strict';

    angular
        .module('cloudApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('city-my-suffix', {
            parent: 'entity',
            url: '/city-my-suffix?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cloudApp.city.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/city-my-suffix/citiesmySuffix.html',
                    controller: 'CityMySuffixController',
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
                    $translatePartialLoader.addPart('city');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('city-my-suffix-detail', {
            parent: 'city-my-suffix',
            url: '/city-my-suffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cloudApp.city.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/city-my-suffix/city-my-suffix-detail.html',
                    controller: 'CityMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('city');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'City', function($stateParams, City) {
                    return City.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'city-my-suffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('city-my-suffix-detail.edit', {
            parent: 'city-my-suffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/city-my-suffix/city-my-suffix-dialog.html',
                    controller: 'CityMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['City', function(City) {
                            return City.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('city-my-suffix.new', {
            parent: 'city-my-suffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/city-my-suffix/city-my-suffix-dialog.html',
                    controller: 'CityMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                cityName: null,
                                area: null,
                                stateProvince: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('city-my-suffix', null, { reload: 'city-my-suffix' });
                }, function() {
                    $state.go('city-my-suffix');
                });
            }]
        })
        .state('city-my-suffix.edit', {
            parent: 'city-my-suffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/city-my-suffix/city-my-suffix-dialog.html',
                    controller: 'CityMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['City', function(City) {
                            return City.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('city-my-suffix', null, { reload: 'city-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('city-my-suffix.delete', {
            parent: 'city-my-suffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/city-my-suffix/city-my-suffix-delete-dialog.html',
                    controller: 'CityMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['City', function(City) {
                            return City.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('city-my-suffix', null, { reload: 'city-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
