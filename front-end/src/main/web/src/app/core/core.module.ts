import { NgModule } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';

import { GrowlModule } from 'primeng/components/growl/growl';
import { ConfirmationService, MessageService } from 'primeng/components/common/api';
import { ConfirmDialogModule } from 'primeng/components/confirmdialog/confirmdialog';

import { ErrorHandlerService } from './error-handler.service';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';

@NgModule({
  declarations: [PageNotFoundComponent],
  imports: [
    CommonModule,

    GrowlModule,
    ConfirmDialogModule
  ],
  exports: [
    GrowlModule,
    ConfirmDialogModule
  ],
  providers: [
    ConfirmationService,
    ErrorHandlerService,
    MessageService,
    DatePipe
  ]
})
export class CoreModule {
}
