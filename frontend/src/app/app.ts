import { Component } from '@angular/core';
import { ResourceCatalogComponent } from './components/resource-catalog/resource-catalog.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [ResourceCatalogComponent],
  templateUrl: './app.html'
})
export class AppComponent {
  title = 'frontend';
}
