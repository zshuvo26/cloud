(function() {
    'use strict';

    angular
        .module('cloudApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('book-info-my-suffix', {
            parent: 'entity',
            url: '/book-info-my-suffix?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cloudApp.bookInfo.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/book-info-my-suffix/book-infosmySuffix.html',
                    controller: 'BookInfoMySuffixController',
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
                    $translatePartialLoader.addPart('bookInfo');
                    $translatePartialLoader.addPart('bookCondition');
                    $translatePartialLoader.addPart('contBookLang');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('book-info-my-suffix-detail', {
            parent: 'book-info-my-suffix',
            url: '/book-info-my-suffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cloudApp.bookInfo.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/book-info-my-suffix/book-info-my-suffix-detail.html',
                    controller: 'BookInfoMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('bookInfo');
                    $translatePartialLoader.addPart('bookCondition');
                    $translatePartialLoader.addPart('contBookLang');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'BookInfo', function($stateParams, BookInfo) {
                    return BookInfo.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'book-info-my-suffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('book-info-my-suffix-detail.edit', {
            parent: 'book-info-my-suffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/book-info-my-suffix/book-info-my-suffix-dialog.html',
                    controller: 'BookInfoMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['BookInfo', function(BookInfo) {
                            return BookInfo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('book-info-my-suffix.new', {
            parent: 'book-info-my-suffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/book-info-my-suffix/book-info-my-suffix-dialog.html',
                    controller: 'BookInfoMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                accessionNo: null,
                                title: null,
                                isbnNo: null,
                                authorName: null,
                                billNo: null,
                                billDate: null,
                                coverPhoto: null,
                                coverPhotoContentType: null,
                                coverPhotoType: null,
                                coverPhotoName: null,
                                createDate: null,
                                updateDate: null,
                                createBy: null,
                                bookCondition: null,
                                contBookLang: null,
                                updateBy: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('book-info-my-suffix', null, { reload: 'book-info-my-suffix' });
                }, function() {
                    $state.go('book-info-my-suffix');
                });
            }]
        })
        .state('book-info-my-suffix.edit', {
            parent: 'book-info-my-suffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/book-info-my-suffix/book-info-my-suffix-dialog.html',
                    controller: 'BookInfoMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['BookInfo', function(BookInfo) {
                            return BookInfo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('book-info-my-suffix', null, { reload: 'book-info-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('book-info-my-suffix.delete', {
            parent: 'book-info-my-suffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/book-info-my-suffix/book-info-my-suffix-delete-dialog.html',
                    controller: 'BookInfoMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['BookInfo', function(BookInfo) {
                            return BookInfo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('book-info-my-suffix', null, { reload: 'book-info-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
