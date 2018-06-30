(function() {
    'use strict';

    angular
        .module('cloudApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('file-type-my-suffix', {
            parent: 'entity',
            url: '/file-type-my-suffix?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cloudApp.fileType.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/file-type-my-suffix/file-typesmySuffix.html',
                    controller: 'FileTypeMySuffixController',
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
                    $translatePartialLoader.addPart('fileType');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('file-type-my-suffix-detail', {
            parent: 'file-type-my-suffix',
            url: '/file-type-my-suffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cloudApp.fileType.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/file-type-my-suffix/file-type-my-suffix-detail.html',
                    controller: 'FileTypeMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('fileType');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'FileType', function($stateParams, FileType) {
                    return FileType.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'file-type-my-suffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('file-type-my-suffix-detail.edit', {
            parent: 'file-type-my-suffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/file-type-my-suffix/file-type-my-suffix-dialog.html',
                    controller: 'FileTypeMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['FileType', function(FileType) {
                            return FileType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('file-type-my-suffix.new', {
            parent: 'file-type-my-suffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/file-type-my-suffix/file-type-my-suffix-dialog.html',
                    controller: 'FileTypeMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                fileType: null,
                                sizeLimit: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('file-type-my-suffix', null, { reload: 'file-type-my-suffix' });
                }, function() {
                    $state.go('file-type-my-suffix');
                });
            }]
        })
        .state('file-type-my-suffix.edit', {
            parent: 'file-type-my-suffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/file-type-my-suffix/file-type-my-suffix-dialog.html',
                    controller: 'FileTypeMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['FileType', function(FileType) {
                            return FileType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('file-type-my-suffix', null, { reload: 'file-type-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('file-type-my-suffix.delete', {
            parent: 'file-type-my-suffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/file-type-my-suffix/file-type-my-suffix-delete-dialog.html',
                    controller: 'FileTypeMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['FileType', function(FileType) {
                            return FileType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('file-type-my-suffix', null, { reload: 'file-type-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
