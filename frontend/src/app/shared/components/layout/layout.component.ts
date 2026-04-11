import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { SidebarComponent } from '../sidebar/sidebar.component';

@Component({
  selector: 'app-layout',
  standalone: true,
  imports: [RouterOutlet, SidebarComponent],
  template: `
    <div class="d-flex">
      <app-sidebar></app-sidebar>
      <div class="main-content flex-grow-1">
        <router-outlet></router-outlet>
      </div>
    </div>
  `
})
export class LayoutComponent {}
