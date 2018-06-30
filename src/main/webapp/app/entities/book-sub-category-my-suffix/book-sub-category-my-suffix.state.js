(function() {
    'use strict';

    angular
        .module('cloudApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('book-sub-category-my-suffix', {
            parent: 'entity',
            url: '/book-sub-category-my-suffix?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cloudApp.bookSubCategory.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/book-sub-category-my-suffix/book-sub-categoriesmySuffix.html',
                    controller: 'BookSubCategoryMySuffixController',
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
                    $translatePartialLoader.addPart('bookSubCategory');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('book-sub-category-my-suffix-detail', {
            parent: 'book-sub-category-my-suffix',
            url: '/book-sub-category-my-suffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cloudApp.bookSubCategory.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/book-sub-category-my-suffix/book-sub-category-my-suffix-detail.html',
                    controller: 'BookSubCategoryMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('bookSubCategory');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'BookSubCategory', function($stateParams, BookSubCategory) {
                    return BookSubCategory.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'book-sub-category-my-suffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('book-sub-category-my-suffix-detail.edit', {
            parent: 'book-sub-category-my-suffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/book-sub-category-my-suffix/book-sub-category-my-suffix-dialog.html',
                    controller: 'BookSubCategoryMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['BookSubCategory', function(BookSubCategory) {
                            return BookSubCategory.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('book-sub-category-my-suffix.new', {
            parent: 'book-sub-category-my-suffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/book-sub-category-my-suffix/book-sub-category-my-suffix-dialog.html',
                    controller: 'BookSubCategoryMySuffixDialogController',
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
                    $state.go('book-sub-category-my-suffix', null, { reload: 'book-sub-category-my-suffix' });
                }, function() {
                    $state.go('book-sub-category-my-suffix');
                });
            }]
        })
        .state('book-sub-category-my-suffix.edit', {
            parent: 'book-sub-category-my-suffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/book-sub-category-my-suffix/book-sub-category-my-suffix-dialog.html',
                    controller: 'BookSubCategoryMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['BookSubCategory', function(BookSubCategory) {
                            return BookSubCategory.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('book-sub-category-my-suffix', null, { reload: 'book-sub-category-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('book-sub-category-my-suffix.delete', {
            parent: 'book-sub-category-my-suffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/book-sub-category-my-suffix/book-sub-category-my-suffix-delete-dialog.html',
                    controller: 'BookSubCategoryMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['BookSubCategory', function(BookSubCategory) {
                            return BookSubCategory.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('book-sub-category-my-suffix', null, { reload: 'book-sub-category-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
