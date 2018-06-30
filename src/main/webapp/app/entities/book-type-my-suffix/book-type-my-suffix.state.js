(function() {
    'use strict';

    angular
        .module('cloudApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('book-type-my-suffix', {
            parent: 'entity',
            url: '/book-type-my-suffix?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cloudApp.bookType.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/book-type-my-suffix/book-typesmySuffix.html',
                    controller: 'BookTypeMySuffixController',
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
                    $translatePartialLoader.addPart('bookType');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('book-type-my-suffix-detail', {
            parent: 'book-type-my-suffix',
            url: '/book-type-my-suffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cloudApp.bookType.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/book-type-my-suffix/book-type-my-suffix-detail.html',
                    controller: 'BookTypeMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('bookType');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'BookType', function($stateParams, BookType) {
                    return BookType.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'book-type-my-suffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('book-type-my-suffix-detail.edit', {
            parent: 'book-type-my-suffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/book-type-my-suffix/book-type-my-suffix-dialog.html',
                    controller: 'BookTypeMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['BookType', function(BookType) {
                            return BookType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('book-type-my-suffix.new', {
            parent: 'book-type-my-suffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/book-type-my-suffix/book-type-my-suffix-dialog.html',
                    controller: 'BookTypeMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                pStatus: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('book-type-my-suffix', null, { reload: 'book-type-my-suffix' });
                }, function() {
                    $state.go('book-type-my-suffix');
                });
            }]
        })
        .state('book-type-my-suffix.edit', {
            parent: 'book-type-my-suffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/book-type-my-suffix/book-type-my-suffix-dialog.html',
                    controller: 'BookTypeMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['BookType', function(BookType) {
                            return BookType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('book-type-my-suffix', null, { reload: 'book-type-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('book-type-my-suffix.delete', {
            parent: 'book-type-my-suffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/book-type-my-suffix/book-type-my-suffix-delete-dialog.html',
                    controller: 'BookTypeMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['BookType', function(BookType) {
                            return BookType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('book-type-my-suffix', null, { reload: 'book-type-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
