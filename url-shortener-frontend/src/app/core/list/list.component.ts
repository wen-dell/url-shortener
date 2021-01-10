import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { Pagination } from 'src/app/model/pagination';
import { UserURL } from 'src/app/model/user-url';
import { TokenService } from '../shared/token.service';
import { ListService } from './list.service';

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.sass']
})
export class ListComponent implements OnInit, OnDestroy {

  urls: UserURL[] = [];
  subscription: Subscription;

  pagination: Pagination;

  page: number = 0;
  size: number = 5;

  constructor(
    private listService: ListService,
    private tokenService: TokenService) 
  {

  }

  ngOnInit(): void {
    this.listUrls();
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  listUrls() {
    let userId = this.tokenService.getPropertyFromJWT('userId');
    this.subscription = 
    this.listService.listUrls(this.page, this.size, 'date,desc', userId)
    .subscribe((response: any) => {
      this.pagination = response.data.pagination;
      this.urls = response.data.content;
    });
  }

  pageChanged(event) {
    this.page = --event.page;
    this.listUrls();
  }

}
