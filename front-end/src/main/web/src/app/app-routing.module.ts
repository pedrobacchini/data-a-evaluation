import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PageNotFoundComponent } from './core/page-not-found/page-not-found.component';
import { ElectionSearchComponent } from './election/election-search/election-search.component';

const routes: Routes = [
  {path: '', redirectTo: 'election', pathMatch: 'full'},
  {path: 'election', component: ElectionSearchComponent},
  {path: 'page-not-found', component: PageNotFoundComponent},
  {path: '**', redirectTo: 'page-not-found'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
