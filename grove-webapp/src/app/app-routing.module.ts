import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {MechanismsComponent} from './dashboard/mechanism/mechanisms.component';
import {AuthGuard, PermissionService} from './permission.service';
import {DashboardComponent} from './dashboard/dashboard.component';
import {MechanismEditComponent} from './dashboard/mechanism/mechanism-edit/mechanism-edit.component';
import {MechanismDetailComponent} from './dashboard/mechanism/mechanism-detail/mechanism-detail.component';
import {MechanismResolver} from './dashboard/mechanism/mechanism-resolver.service';
import {ConfigurationComponent} from './dashboard/configuration/configuration.component';
import {CreatorsComponent} from './dashboard/creators/creators.component';
import {CreatorEditComponent} from './dashboard/creators/creator-edit/creator-edit.component';
import {CreatorDetailComponent} from './dashboard/creators/creator-detail/creator-detail.component';
import {CreatorResolver} from './dashboard/creators/creator-resolver.service';
import {PublishersComponent} from './dashboard/publishers/publishers.component';
import {PublisherEditComponent} from './dashboard/publishers/publisher-edit/publisher-edit.component';
import {PublisherDetailComponent} from './dashboard/publishers/publisher-detail/publisher-detail.component';
import {ExistingMechanismsResolver} from './dashboard/mechanism/existing-mecanisms-resolver.service';
import {PublishersNamesResolver} from './dashboard/publishers/publishers-names-resolver.service';
import {PublishersResolver} from './dashboard/publishers/publishers-resolver.service';
import {CreatorNameResolver} from './dashboard/creators/creator-name-resolver.service';
import {CategoriesComponent} from './dashboard/categories/categories.component';
import {CategoryEditComponent} from './dashboard/categories/category-edit/category-edit.component';
import {CategoryDetailComponent} from './dashboard/categories/category-detail/category-detail.component';
import {CategoryResolver} from './dashboard/categories/category-resolver.service';
import {DashboardLoanComponent} from './dashboard-loan/dashboard-loan.component';
import {DashboardUserComponent} from './dashboard-user/dashboard-user.component';
import {GamesComponent} from './dashboard/games/games.component';
import {GameOverviewResolver} from './dashboard/games/game-overview-resolver.service';
import {GameSummaryComponent} from './dashboard/games/game-list/game-summary/game-summary.component';
import {GameDetailComponent} from './dashboard/games/game-detail/game-detail.component';
import {GameResolver} from './dashboard/games/game-resolver.service';
import {GameEditComponent} from './dashboard/games/game-edit/game-edit.component';
import {CategoryHandlerComponent} from './dashboard/games/game-edit/category-handler/category-handler.component';
import {TitleHandlerComponent} from './dashboard/games/game-edit/title-handler/title-handler.component';
import {InfoHandlerComponent} from './dashboard/games/game-edit/info-handler/info-handler.component';
import {CreatorHandlerComponent} from './dashboard/games/game-edit/creator-handler/creator-handler.component';
import {PublisherHandlerComponent} from './dashboard/games/game-edit/publisher-handler/publisher-handler.component';
import {SizeHandlerComponent} from './dashboard/games/game-edit/size-handler/size-handler.component';
import {MaterialHandlerComponent} from './dashboard/games/game-edit/material-handler/material-handler.component';
import {ImageHandlerComponent} from './dashboard/games/game-edit/image-handler/image-handler.component';
import {GameEditHelperComponent} from './dashboard/games/game-edit/game-edit-helper/game-edit-helper.component';
import {
  DescriptionHandlerComponent
} from './dashboard/games/game-edit/description-handler/description-handler.component';
import {WrapperEditResolver} from './shared/resolvers/wrapper-edit-resolver.service';
import {NavResolverService} from './shared/resolvers/nav-resolver.service';
import {NewGameBasicsComponent} from './dashboard/games/new-game/new-game-basics/new-game-basics.component';
import {WrapperNewResolver} from './shared/resolvers/wrapper-new-resolver.service';
import {NewGameComponent} from './dashboard/games/new-game/new-game.component';
import {
  NewGameParentChoiceComponent
} from './dashboard/games/new-game/new-game-parent-choice/new-game-parent-choice.component';
import {NewGameAddExtComponent} from './dashboard/games/new-game/new-game-add-ext/new-game-add-ext.component';
import {NewGameAddCoreComponent} from './dashboard/games/new-game/new-game-add-core/new-game-add-core.component';
import {NewGameInfosComponent} from './dashboard/games/new-game/new-game-infos/new-game-infos.component';
import {MemberNewComponent} from './dashboard-user/members/member-new/member-new.component';
import {MemberListComponent} from './dashboard-user/members/member-list/member-list.component';
import {MemberDetailComponent} from './dashboard-user/members/member-detail/member-detail.component';
import {SelectMemberComponent} from './dashboard-loan/loans/select-member/select-member.component';
import {SelectGameComponent} from './dashboard-loan/loans/select-game/select-game.component';
import {ConfirmLoanComponent} from './dashboard-loan/loans/confirm-loan/confirm-loan.component';
import {LoanListComponent} from './dashboard-loan/loans/loan-list/loan-list.component';
import {LoanDetailComponent} from './dashboard-loan/loans/loan-detail/loan-detail.component';
import {LoanResolver} from './dashboard-loan/loans/loan-resolver.service';
import {HomeComponent} from './home/home.component';
import {MechanismHandlerComponent} from './dashboard/games/game-edit/mechanism-handler/mechanism-handler.component';
import {LoginComponent} from './auth/login/login.component';
import {ErrorPageComponent} from './error/error-page/error-page.component';

const routes: Routes = [
  {
    path: ''
    // component:
  },
  {
    path: 'login',
    component: LoginComponent,
  },
  {
    path: 'admin/locked-mode',
    canActivate: [AuthGuard],
    resolve: [WrapperNewResolver],
    data: {roles: ['ADMIN']},
    children: [
      {
        path: 'games/new',
        component: NewGameComponent,
        children: [
          {
            path: 'basics',
            component: NewGameBasicsComponent
          },
          {
            path: 'parent-choice',
            component: NewGameParentChoiceComponent
          },
          {
            path: 'add-extension',
            component: NewGameAddExtComponent
          },
          {
            path: 'add-core',
            component: NewGameAddCoreComponent
          },
          {
            path: 'add-core-summary',
            component: GameSummaryComponent
          },
          {
            path: 'infos',
            component: NewGameInfosComponent
          },
        ]
      },
      {
        path: 'games/:id/edit',
        component: GameEditComponent,
        resolve: [GameResolver, WrapperEditResolver],
        children: [
          {
            path: '',
            component: GameEditHelperComponent,
          },
          {
            path: 'name',
            component: TitleHandlerComponent
          },
          {
            path: 'categories',
            component: CategoryHandlerComponent
          },
          {
            path: 'mechanisms',
            component: MechanismHandlerComponent
          },
          {
            path: 'infos',
            component: InfoHandlerComponent
          },
          {
            path: 'creators',
            component: CreatorHandlerComponent
          },
          {
            path: 'publisher',
            component: PublisherHandlerComponent
          },
          {
            path: 'description',
            component: DescriptionHandlerComponent
          },
          {
            path: 'size',
            component: SizeHandlerComponent
          },
          {
            path: 'material',
            component: MaterialHandlerComponent
          },
          {
            path: 'images',
            component: ImageHandlerComponent
          },
        ]
      }
    ]
  },
  {
    path: 'admin',
    resolve: [NavResolverService],
    children: [
      {
        path: 'home',
        component: HomeComponent,
        canActivate: [PermissionService],
      },
      {
        path: 'editor',
        component: DashboardComponent,
        canActivate: [AuthGuard],
        data: {roles: ['ADMIN']},
        children: [
          {
            path: 'games',
            component: GamesComponent,
            resolve: [GameOverviewResolver],
            children: [
              {
                path: ':id',
                component: GameSummaryComponent
              },
              {
                path: 'detail/:id',
                component: GameDetailComponent,
                resolve: [GameResolver]
              }
            ]
          },
          {
            path: 'mechanisms',
            component: MechanismsComponent,
            children: [
              {path: 'new', component: MechanismEditComponent, resolve: [ExistingMechanismsResolver]},
              {
                path: ':id/edit',
                component: MechanismEditComponent,
                resolve: [MechanismResolver, ExistingMechanismsResolver]
              },
              {path: ':id', component: MechanismDetailComponent, resolve: [MechanismResolver]}
            ]
          },
          {
            path: 'creators',
            component: CreatorsComponent,
            children: [
              {path: 'new', component: CreatorEditComponent, resolve: [CreatorNameResolver]},
              {
                path: ':id/edit',
                component: CreatorEditComponent,
                resolve: [CreatorResolver, CreatorNameResolver]
              },
              {path: ':id', component: CreatorDetailComponent, resolve: [CreatorResolver]}
            ]
          },
          {
            path: 'publishers',
            component: PublishersComponent,
            children: [
              {path: 'new', component: PublisherEditComponent, resolve: [PublishersNamesResolver]},
              {
                path: ':id/edit',
                component: PublisherEditComponent,
                resolve: [PublishersResolver, PublishersNamesResolver]
              },
              {path: ':id', component: PublisherDetailComponent, resolve: [PublishersResolver]}
            ]
          },
          {
            path: 'categories',
            component: CategoriesComponent,
            resolve: [CategoryResolver],
            children: [
              {path: 'new', component: CategoryEditComponent},
              {path: ':id/edit', component: CategoryEditComponent, resolve: [CategoryResolver]},
              {path: ':id', component: CategoryDetailComponent, resolve: [CategoryResolver]}
            ]
          },
          {
            path: 'configuration',
            component: ConfigurationComponent
          }]
      },
      {
        path: 'loans',
        component: DashboardLoanComponent,
        canActivate: [AuthGuard],
        data: {roles: ['ADMIN']},
        children: [
          {
            path: 'select-member',
            component: SelectMemberComponent
          },
          {
            path: ':accountId/select-game',
            component: SelectGameComponent
          },
          {
            path: 'confirm',
            component: ConfirmLoanComponent
          },
          {
            path: 'list',
            component: LoanListComponent
          },
          {
            path: ':id',
            component: LoanDetailComponent,
            resolve: [LoanResolver]
          }
        ]
      },
      {
        path: 'members',
        component: DashboardUserComponent,
        canActivate: [AuthGuard],
        data: {roles: ['ADMIN']},
        children: [
          {
            path: 'new',
            component: MemberNewComponent
          },
          {
            path: 'list',
            component: MemberListComponent
          },
          {
            path: ':id',
            component: MemberDetailComponent
          }
        ]
      }
    ]
  },
  {
    path: 'not-found',
    resolve: [NavResolverService],
    component: ErrorPageComponent,
    data: {message: 'page not found!'}
  },
  {
    path: '**',
    redirectTo: '/not-found'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes
// , {enableTracing: true}
    , {})],
  exports: [RouterModule]
})
export class AppRoutingModule {

}
