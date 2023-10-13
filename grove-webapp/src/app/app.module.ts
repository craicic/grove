import {BrowserModule} from '@angular/platform-browser';
import {APP_INITIALIZER, NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {MechanismsComponent} from './admin/dashboard/library/mechanism/mechanisms.component';
import {AppRoutingModule} from './app-routing.module';
import {HeaderComponent} from './shared/components/header/header.component';
import {DashboardComponent} from './admin/dashboard/library/dashboard.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {MechanismEditComponent} from './admin/dashboard/library/mechanism/mechanism-edit/mechanism-edit.component';
import {MechanismListComponent} from './admin/dashboard/library/mechanism/mechanism-list/mechanism-list.component';
import {MechanismDetailComponent} from './admin/dashboard/library/mechanism/mechanism-detail/mechanism-detail.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {ConfigurationComponent} from './admin/dashboard/library/configuration/configuration.component';
import {CreatorsComponent} from './admin/dashboard/library/creators/creators.component';
import {CreatorListComponent} from './admin/dashboard/library/creators/creator-list/creator-list.component';
import {CreatorEditComponent} from './admin/dashboard/library/creators/creator-edit/creator-edit.component';
import {CreatorDetailComponent} from './admin/dashboard/library/creators/creator-detail/creator-detail.component';
import {StringEnumPipe} from './shared/pipes/string-enum.pipe';
import {ConfirmModalComponent} from './shared/components/confirm-modal/confirm-modal.component';
import {EnumToValuePipe} from './shared/pipes/enum-to-value.pipe';
import {ContactFormComponent} from './shared/components/contact-form/contact-form.component';
import {SimpleFilterFormComponent} from './admin/dashboard/library/shared/simple-filter-form/simple-filter-form.component';
import {PublishersComponent} from './admin/dashboard/library/publishers/publishers.component';
import {PublisherListComponent} from './admin/dashboard/library/publishers/publisher-list/publisher-list.component';
import {PublisherDetailComponent} from './admin/dashboard/library/publishers/publisher-detail/publisher-detail.component';
import {PublisherEditComponent} from './admin/dashboard/library/publishers/publisher-edit/publisher-edit.component';
import {CategoriesComponent} from './admin/dashboard/library/categories/categories.component';
import {CategoryDetailComponent} from './admin/dashboard/library/categories/category-detail/category-detail.component';
import {CategoryEditComponent} from './admin/dashboard/library/categories/category-edit/category-edit.component';
import {CategoryListComponent} from './admin/dashboard/library/categories/category-list/category-list.component';
import {NavWrapperComponent} from './wrapper/nav-wrapper/nav-wrapper.component';
import {ErrorPageComponent} from './error/error-page/error-page.component';
import {DashboardLoanComponent} from './admin/dashboard/loans/dashboard-loan.component';
import {DashboardUserComponent} from './admin/dashboard/users/dashboard-user.component';
import {GamesComponent} from './admin/dashboard/library/games/games.component';
import {GameListComponent} from './admin/dashboard/library/games/game-list/game-list.component';
import {GameSummaryComponent} from './admin/dashboard/library/games/game-list/game-summary/game-summary.component';
import {GameDetailComponent} from './admin/dashboard/library/games/game-detail/game-detail.component';
import {CommonModule, NgOptimizedImage} from '@angular/common';
import {GameEditComponent} from './admin/dashboard/library/games/game-edit/game-edit.component';
import {CategoryHandlerComponent} from './admin/dashboard/library/games/game-edit/category-handler/category-handler.component';
import {TitleHandlerComponent} from './admin/dashboard/library/games/game-edit/title-handler/title-handler.component';
import {MechanismHandlerComponent} from './admin/dashboard/library/games/game-edit/mechanism-handler/mechanism-handler.component';
import {CreatorHandlerComponent} from './admin/dashboard/library/games/game-edit/creator-handler/creator-handler.component';
import {PublisherHandlerComponent} from './admin/dashboard/library/games/game-edit/publisher-handler/publisher-handler.component';
import {InfoHandlerComponent} from './admin/dashboard/library/games/game-edit/info-handler/info-handler.component';
import {SizeHandlerComponent} from './admin/dashboard/library/games/game-edit/size-handler/size-handler.component';
import {MaterialHandlerComponent} from './admin/dashboard/library/games/game-edit/material-handler/material-handler.component';
import {ImageHandlerComponent} from './admin/dashboard/library/games/game-edit/image-handler/image-handler.component';
import {GameEditHelperComponent} from './admin/dashboard/library/games/game-edit/game-edit-helper/game-edit-helper.component';
import {
  CategoryPickerComponent
} from './admin/dashboard/library/games/game-edit/category-handler/category-picker/category-picker.component';
import {ObjectToStringPipe} from './shared/pipes/object-to-string.pipe';
import {
  MechanismPickerComponent
} from './admin/dashboard/library/games/game-edit/mechanism-handler/mechanism-picker/mechanism-picker.component';
import {
  CreatorPickerComponent
} from './admin/dashboard/library/games/game-edit/creator-handler/creator-picker/creator-picker.component';
import {
  DescriptionHandlerComponent
} from './admin/dashboard/library/games/game-edit/description-handler/description-handler.component';
import {NewGameBasicsComponent} from './admin/dashboard/library/games/new-game/new-game-basics/new-game-basics.component';
import {NewGameComponent} from './admin/dashboard/library/games/new-game/new-game.component';
import {MemberListComponent} from './admin/dashboard/users/members/member-list/member-list.component';
import {MemberNewComponent} from './admin/dashboard/users/members/member-new/member-new.component';
import {MemberDetailComponent} from './admin/dashboard/users/members/member-detail/member-detail.component';
import {SelectMemberComponent} from './admin/dashboard/loans/loans/select-member/select-member.component';
import {SelectGameComponent} from './admin/dashboard/loans/loans/select-game/select-game.component';
import {ConfirmLoanComponent} from './admin/dashboard/loans/loans/confirm-loan/confirm-loan.component';
import {LoanListComponent} from './admin/dashboard/loans/loans/loan-list/loan-list.component';
import {LoanDetailComponent} from './admin/dashboard/loans/loans/loan-detail/loan-detail.component';
import {HomeComponent} from './home/home.component';
import {LoginComponent} from './auth/login/login.component';
import {AuthInterceptor} from './auth.interceptor';
import {CopiesDetailComponent} from './admin/dashboard/library/game-copies/copies-control/copies-detail/copies-detail.component';
import {CopyHandlerComponent} from './admin/dashboard/library/games/game-edit/copy-handler/copy-handler.component';
import {CopiesControlComponent} from './admin/dashboard/library/game-copies/copies-control/copies-control.component';
import {ImagesViewerComponent} from './admin/dashboard/library/games/game-detail/images-viewer/images-viewer.component';
import {RulesHandlerComponent} from './admin/dashboard/library/games/game-edit/rules-handler/rules-handler.component';
import {SearchHomeComponent} from './search/search-home/search-home.component';
import {sessionInitializer} from './session-initializer';
import {AuthenticationService} from './auth/authentication.service';


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
    NewGameBasicsComponent,
    NewGameComponent,
    MemberListComponent,
    MemberNewComponent,
    MemberDetailComponent,
    SelectMemberComponent,
    SelectGameComponent,
    ConfirmLoanComponent,
    LoanListComponent,
    LoanDetailComponent,
    HomeComponent,
    LoginComponent,
    CopiesControlComponent,
    CopiesDetailComponent,
    CopyHandlerComponent,
    ImagesViewerComponent,
    RulesHandlerComponent,
    SearchHomeComponent
  ],
  imports: [
    BrowserModule,
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    NgbModule,
    AppRoutingModule,
    NgOptimizedImage
  ],
  providers: [
    {
      provide: APP_INITIALIZER,
      useFactory: sessionInitializer,
      deps: [AuthenticationService],
      multi: true
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true,
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
