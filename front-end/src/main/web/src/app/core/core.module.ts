import { NgModule } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { RouterModule } from '@angular/router';

import { GrowlModule } from 'primeng/components/growl/growl';
import { ConfirmationService, MessageService } from 'primeng/components/common/api';
import { ConfirmDialogModule } from 'primeng/components/confirmdialog/confirmdialog';

import { ErrorHandlerService } from './error-handler.service';
import { PageNotFoundComponent } from './page-not-found.component';
import { NavbarComponent } from './navbar/navbar.component';
import { UtilsService } from './utils.service';

@NgModule({
  declarations: [
    PageNotFoundComponent,
    NavbarComponent
  ],
  imports: [
    CommonModule,
    RouterModule,

    GrowlModule,
    ConfirmDialogModule
  ],
  exports: [
    GrowlModule,
    ConfirmDialogModule,
    NavbarComponent
  ],
  providers: [
    ConfirmationService,
    ErrorHandlerService,
    MessageService,
    DatePipe,
    UtilsService
  ]
})
export class CoreModule {
}
