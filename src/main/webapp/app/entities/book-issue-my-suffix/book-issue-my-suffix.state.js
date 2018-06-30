(function() {
    'use strict';

    angular
        .module('cloudApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('book-issue-my-suffix', {
            parent: 'entity',
            url: '/book-issue-my-suffix?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cloudApp.bookIssue.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/book-issue-my-suffix/book-issuesmySuffix.html',
                    controller: 'BookIssueMySuffixController',
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
                    $translatePartialLoader.addPart('bookIssue');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('book-issue-my-suffix-detail', {
            parent: 'book-issue-my-suffix',
            url: '/book-issue-my-suffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cloudApp.bookIssue.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/book-issue-my-suffix/book-issue-my-suffix-detail.html',
                    controller: 'BookIssueMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('bookIssue');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'BookIssue', function($stateParams, BookIssue) {
                    return BookIssue.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'book-issue-my-suffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('book-issue-my-suffix-detail.edit', {
            parent: 'book-issue-my-suffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/book-issue-my-suffix/book-issue-my-suffix-dialog.html',
                    controller: 'BookIssueMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['BookIssue', function(BookIssue) {
                            return BookIssue.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('book-issue-my-suffix.new', {
            parent: 'book-issue-my-suffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/book-issue-my-suffix/book-issue-my-suffix-dialog.html',
                    controller: 'BookIssueMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                noOfCopies: null,
                                returnDate: null,
                                createDate: null,
                                updateDate: null,
                                createBy: null,
                                updateBy: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('book-issue-my-suffix', null, { reload: 'book-issue-my-suffix' });
                }, function() {
                    $state.go('book-issue-my-suffix');
                });
            }]
        })
        .state('book-issue-my-suffix.edit', {
            parent: 'book-issue-my-suffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/book-issue-my-suffix/book-issue-my-suffix-dialog.html',
                    controller: 'BookIssueMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['BookIssue', function(BookIssue) {
                            return BookIssue.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('book-issue-my-suffix', null, { reload: 'book-issue-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('book-issue-my-suffix.delete', {
            parent: 'book-issue-my-suffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/book-issue-my-suffix/book-issue-my-suffix-delete-dialog.html',
                    controller: 'BookIssueMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['BookIssue', function(BookIssue) {
                            return BookIssue.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('book-issue-my-suffix', null, { reload: 'book-issue-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
