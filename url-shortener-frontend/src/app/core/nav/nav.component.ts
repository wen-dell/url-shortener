import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { SessionStorageService } from '../shared/session-storage.service';

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.sass']
})
export class NavComponent implements OnInit {

  @Input() hide = false;
  user = {name: 'e'};

  constructor(private router: Router, private sessionStorageService: SessionStorageService) { }

  ngOnInit(): void {
  }

  logout() {
    this.sessionStorageService.clear();
    this.router.navigateByUrl('');
  }

}
