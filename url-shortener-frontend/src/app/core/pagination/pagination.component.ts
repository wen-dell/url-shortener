import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';
import { Pagination } from 'src/app/model/pagination';
import { PageChange } from './page-change';
import { PaginationService } from './pagination.service';

@Component({
  selector: 'app-pagination',
  templateUrl: './pagination.component.html',
  styleUrls: ['./pagination.component.sass']
})
export class PaginationComponent implements OnInit {

  @Input('pagination') pagination: Pagination;
  @Output('changePage') eventEmitter: EventEmitter<PageChange> = new EventEmitter<PageChange>();

  pageInit: number = 1;
  pageLimit: number = 3;
  pages = [1, 2, 3];

  constructor(private paginationService: PaginationService) { }

  ngOnInit(): void {

  }

  createPages() {
    this.pages = this.paginationService.createPages(this.pagination.page);
  }

  previous() {
    if (!this.pagination.firstPage) {
      this.eventEmitter.emit(new PageChange(--this.pagination.page));
  
      if (this.pagination.page < this.pageInit) {
        this.pages = this.paginationService.createPages(this.pagination.page, true);
        this.pageInit = this.pagination.page - 2;
        this.pageLimit = this.pagination.page;
      }
    }
  }

  choose(page: number) {
    this.pagination.page = page;
    this.eventEmitter.emit(new PageChange(this.pagination.page));
  }

  next() {
    if (this.pagination.hasNextPage) {
      this.eventEmitter.emit(new PageChange(++this.pagination.page));
  
      if (this.pagination.page > this.pageLimit) {
        this.pages = this.paginationService.createPages(this.pagination.page);
        this.pageInit = this.pagination.page;
        this.pageLimit = this.pagination.page + 2;
      }
    }
  }

}
