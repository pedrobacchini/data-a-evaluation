import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { GrowlModule } from 'primeng/growl';
import { AppRoutingModule } from './app-routing.module';
import { CoreModule } from './core/core.module';
import { MessageService } from 'primeng/api';
import { ElectionModule } from './election/election.module';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    GrowlModule,

    ElectionModule,
    CoreModule
  ],
  exports: [GrowlModule],
  providers: [MessageService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
