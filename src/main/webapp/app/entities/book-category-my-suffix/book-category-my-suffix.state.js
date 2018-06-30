(function() {
    'use strict';

    angular
        .module('cloudApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('book-category-my-suffix', {
            parent: 'entity',
            url: '/book-category-my-suffix?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cloudApp.bookCategory.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/book-category-my-suffix/book-categoriesmySuffix.html',
                    controller: 'BookCategoryMySuffixController',
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
                    $translatePartialLoader.addPart('bookCategory');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('book-category-my-suffix-detail', {
            parent: 'book-category-my-suffix',
            url: '/book-category-my-suffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cloudApp.bookCategory.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/book-category-my-suffix/book-category-my-suffix-detail.html',
                    controller: 'BookCategoryMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('bookCategory');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'BookCategory', function($stateParams, BookCategory) {
                    return BookCategory.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'book-category-my-suffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('book-category-my-suffix-detail.edit', {
            parent: 'book-category-my-suffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/book-category-my-suffix/book-category-my-suffix-dialog.html',
                    controller: 'BookCategoryMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['BookCategory', function(BookCategory) {
                            return BookCategory.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('book-category-my-suffix.new', {
            parent: 'book-category-my-suffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/book-category-my-suffix/book-category-my-suffix-dialog.html',
                    controller: 'BookCategoryMySuffixDialogController',
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
                    $state.go('book-category-my-suffix', null, { reload: 'book-category-my-suffix' });
                }, function() {
                    $state.go('book-category-my-suffix');
                });
            }]
        })
        .state('book-category-my-suffix.edit', {
            parent: 'book-category-my-suffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/book-category-my-suffix/book-category-my-suffix-dialog.html',
                    controller: 'BookCategoryMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['BookCategory', function(BookCategory) {
                            return BookCategory.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('book-category-my-suffix', null, { reload: 'book-category-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('book-category-my-suffix.delete', {
            parent: 'book-category-my-suffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/book-category-my-suffix/book-category-my-suffix-delete-dialog.html',
                    controller: 'BookCategoryMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['BookCategory', function(BookCategory) {
                            return BookCategory.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('book-category-my-suffix', null, { reload: 'book-category-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
