import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {MechanismsComponent} from './dashboard/mechanism/mechanisms.component';
import {AppRoutingModule} from './app-routing.module';
import {HeaderComponent} from './shared/components/header/header.component';
import {DashboardComponent} from './dashboard/dashboard.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {MechanismEditComponent} from './dashboard/mechanism/mechanism-edit/mechanism-edit.component';
import {MechanismListComponent} from './dashboard/mechanism/mechanism-list/mechanism-list.component';
import {MechanismDetailComponent} from './dashboard/mechanism/mechanism-detail/mechanism-detail.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {ConfigurationComponent} from './dashboard/configuration/configuration.component';
import {CreatorsComponent} from './dashboard/creators/creators.component';
import {CreatorListComponent} from './dashboard/creators/creator-list/creator-list.component';
import {CreatorEditComponent} from './dashboard/creators/creator-edit/creator-edit.component';
import {CreatorDetailComponent} from './dashboard/creators/creator-detail/creator-detail.component';
import {StringEnumPipe} from './shared/pipes/string-enum.pipe';
import {ConfirmModalComponent} from './shared/components/confirm-modal/confirm-modal.component';
import {EnumToValuePipe} from './shared/pipes/enum-to-value.pipe';
import {ContactFormComponent} from './shared/components/contact-form/contact-form.component';
import {SimpleFilterFormComponent} from './dashboard/shared/simple-filter-form/simple-filter-form.component';
import {PublishersComponent} from './dashboard/publishers/publishers.component';
import {PublisherListComponent} from './dashboard/publishers/publisher-list/publisher-list.component';
import {PublisherDetailComponent} from './dashboard/publishers/publisher-detail/publisher-detail.component';
import {PublisherEditComponent} from './dashboard/publishers/publisher-edit/publisher-edit.component';
import {CategoriesComponent} from './dashboard/categories/categories.component';
import {CategoryDetailComponent} from './dashboard/categories/category-detail/category-detail.component';
import {CategoryEditComponent} from './dashboard/categories/category-edit/category-edit.component';
import {CategoryListComponent} from './dashboard/categories/category-list/category-list.component';
import {NavWrapperComponent} from './wrapper/nav-wrapper/nav-wrapper.component';
import {ErrorPageComponent} from './error/error-page/error-page.component';
import {DashboardLoanComponent} from './dashboard-loan/dashboard-loan.component';
import {DashboardUserComponent} from './dashboard-user/dashboard-user.component';
import {GamesComponent} from './dashboard/games/games.component';
import {GameListComponent} from './dashboard/games/game-list/game-list.component';
import {GameSummaryComponent} from './dashboard/games/game-list/game-summary/game-summary.component';
import {GameDetailComponent} from './dashboard/games/game-detail/game-detail.component';
import {CommonModule} from '@angular/common';
import {GameEditWrapperComponent} from './wrapper/game-edit-wrapper/game-edit-wrapper.component';
import {GameEditComponent} from './dashboard/games/game-edit/game-edit.component';
import {CategoryHandlerComponent} from './dashboard/games/game-edit/category-handler/category-handler.component';
import {TitleHandlerComponent} from './dashboard/games/game-edit/title-handler/title-handler.component';
import {MechanismHandlerComponent} from './dashboard/games/game-edit/mechanism-handler/mechanism-handler.component';
import {CreatorHandlerComponent} from './dashboard/games/game-edit/creator-handler/creator-handler.component';
import {PublisherHandlerComponent} from './dashboard/games/game-edit/publisher-handler/publisher-handler.component';
import {InfoHandlerComponent} from './dashboard/games/game-edit/info-handler/info-handler.component';
import {SizeHandlerComponent} from './dashboard/games/game-edit/size-handler/size-handler.component';
import {MaterialHandlerComponent} from './dashboard/games/game-edit/material-handler/material-handler.component';
import {ImageHandlerComponent} from './dashboard/games/game-edit/image-handler/image-handler.component';
import {GameEditHelperComponent} from './dashboard/games/game-edit/game-edit-helper/game-edit-helper.component';
import {
  CategoryPickerComponent
} from './dashboard/games/game-edit/category-handler/category-picker/category-picker.component';
import {ObjectToStringPipe} from './shared/pipes/object-to-string.pipe';
import {
  MechanismPickerComponent
} from './dashboard/games/game-edit/mechanism-handler/mechanism-picker/mechanism-picker.component';
import {
  CreatorPickerComponent
} from './dashboard/games/game-edit/creator-handler/creator-picker/creator-picker.component';
import {
  DescriptionHandlerComponent
} from './dashboard/games/game-edit/description-handler/description-handler.component';
import {BannerComponent} from './shared/components/banner/banner.component';
import {LockedHeaderComponent} from './shared/components/locked-header/locked-header.component';
import {BackButtonComponent} from './shared/components/back-button/back-button.component';
import {NewGameBasicsComponent} from './dashboard/games/new-game/new-game-basics/new-game-basics.component';
import {NewGameComponent} from './dashboard/games/new-game/new-game.component';
import {
  NewGameParentChoiceComponent
} from './dashboard/games/new-game/new-game-parent-choice/new-game-parent-choice.component';
import {NewGameAddCoreComponent} from './dashboard/games/new-game/new-game-add-core/new-game-add-core.component';
import {NewGameAddExtComponent} from './dashboard/games/new-game/new-game-add-ext/new-game-add-ext.component';
import {
  NewGameCoreSummaryComponent
} from './dashboard/games/new-game/new-game-core-summary/new-game-core-summary.component';
import {NewGameInfosComponent} from './dashboard/games/new-game/new-game-infos/new-game-infos.component';
import {MemberListComponent} from './dashboard-user/members/member-list/member-list.component';
import {MemberNewComponent} from './dashboard-user/members/member-new/member-new.component';
import {MemberDetailComponent} from './dashboard-user/members/member-detail/member-detail.component';
import {SelectMemberComponent} from './dashboard-loan/loans/select-member/select-member.component';
import {SelectGameComponent} from './dashboard-loan/loans/select-game/select-game.component';
import {ConfirmLoanComponent} from './dashboard-loan/loans/confirm-loan/confirm-loan.component';
import {LoanListComponent} from './dashboard-loan/loans/loan-list/loan-list.component';
import {LoanDetailComponent} from './dashboard-loan/loans/loan-detail/loan-detail.component';
import {HomeComponent} from './home/home.component';
import {AuthInterceptor} from './auth.interceptor';
import {LoginComponent} from './auth/login/login.component';
import {XhrInterceptor} from './shared/interceptor/xhr.interceptor';


@NgModule({
  declarations: [
    AppComponent,
    MechanismsComponent,
    MechanismHandlerComponent,
    HeaderComponent,
    DashboardComponent,
    MechanismEditComponent,
    MechanismListComponent,
    MechanismDetailComponent,
    ConfigurationComponent,
    CreatorsComponent,
    CreatorListComponent,
    CreatorEditComponent,
    CreatorDetailComponent,
    StringEnumPipe,
    ConfirmModalComponent,
    EnumToValuePipe,
    ObjectToStringPipe,
    ContactFormComponent,
    SimpleFilterFormComponent,
    PublishersComponent,
    PublisherListComponent,
    PublisherDetailComponent,
    PublisherEditComponent,
    CategoriesComponent,
    CategoryDetailComponent,
    CategoryEditComponent,
    CategoryListComponent,
    NavWrapperComponent,
    ErrorPageComponent,
    DashboardLoanComponent,
    DashboardUserComponent,
    GamesComponent,
    GameListComponent,
    GameSummaryComponent,
    GameDetailComponent,
    GameEditWrapperComponent,
    GameEditComponent,
    CategoryHandlerComponent,
    TitleHandlerComponent,
    CreatorHandlerComponent,
    PublisherHandlerComponent,
    InfoHandlerComponent,
    SizeHandlerComponent,
    MaterialHandlerComponent,
    ImageHandlerComponent,
    GameEditHelperComponent,
    CategoryPickerComponent,
    MechanismPickerComponent,
    CreatorPickerComponent,
    DescriptionHandlerComponent,
    BannerComponent,
    LockedHeaderComponent,
    BackButtonComponent,
    NewGameBasicsComponent,
    NewGameComponent,
    NewGameParentChoiceComponent,
    NewGameAddCoreComponent,
    NewGameAddExtComponent,
    NewGameCoreSummaryComponent,
    NewGameInfosComponent,
    MemberListComponent,
    MemberNewComponent,
    MemberDetailComponent,
    SelectMemberComponent,
    SelectGameComponent,
    ConfirmLoanComponent,
    LoanListComponent,
    LoanDetailComponent,
    HomeComponent,
    LoginComponent
  ],
  imports: [
    BrowserModule,
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    NgbModule,
    AppRoutingModule
  ],
  // providers: [
  //   {
  //     provide: HTTP_INTERCEPTORS,
  //     useClass: XhrInterceptor,
  //     multi: true,
  //   }
  // ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
