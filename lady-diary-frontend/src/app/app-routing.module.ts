import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {TopicComponent} from "./topic/topic.component";

const routes: Routes = [
  {path: 'topics', component: TopicComponent},
  {path: '', redirectTo: '/topics', pathMatch: 'full'},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
