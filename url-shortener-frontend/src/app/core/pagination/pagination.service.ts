import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class PaginationService {

  constructor() { }

  createPages(page: number, reverse: boolean = false) {
    if (reverse) {
      return Array(3).fill(0).map((e, i) => (page - 2) + i); 
    }
    
    return Array(3).fill(0).map((e, i) => page + i);
  }

}
