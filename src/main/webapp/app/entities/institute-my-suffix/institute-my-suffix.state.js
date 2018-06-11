(function() {
    'use strict';

    angular
        .module('cloudApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('institute-my-suffix', {
            parent: 'entity',
            url: '/institute-my-suffix?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cloudApp.institute.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/institute-my-suffix/institutesmySuffix.html',
                    controller: 'InstituteMySuffixController',
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
                    $translatePartialLoader.addPart('institute');
                    $translatePartialLoader.addPart('instituteType');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('institute-my-suffix-detail', {
            parent: 'institute-my-suffix',
            url: '/institute-my-suffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cloudApp.institute.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/institute-my-suffix/institute-my-suffix-detail.html',
                    controller: 'InstituteMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('institute');
                    $translatePartialLoader.addPart('instituteType');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Institute', function($stateParams, Institute) {
                    return Institute.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'institute-my-suffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('institute-my-suffix-detail.edit', {
            parent: 'institute-my-suffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/institute-my-suffix/institute-my-suffix-dialog.html',
                    controller: 'InstituteMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Institute', function(Institute) {
                            return Institute.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('institute-my-suffix.new', {
            parent: 'institute-my-suffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/institute-my-suffix/institute-my-suffix-dialog.html',
                    controller: 'InstituteMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                estdDate: null,
                                email: null,
                                website: null,
                                contactNo: null,
                                instituteType: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('institute-my-suffix', null, { reload: 'institute-my-suffix' });
                }, function() {
                    $state.go('institute-my-suffix');
                });
            }]
        })
        .state('institute-my-suffix.edit', {
            parent: 'institute-my-suffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/institute-my-suffix/institute-my-suffix-dialog.html',
                    controller: 'InstituteMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Institute', function(Institute) {
                            return Institute.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('institute-my-suffix', null, { reload: 'institute-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('institute-my-suffix.delete', {
            parent: 'institute-my-suffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/institute-my-suffix/institute-my-suffix-delete-dialog.html',
                    controller: 'InstituteMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Institute', function(Institute) {
                            return Institute.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('institute-my-suffix', null, { reload: 'institute-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
