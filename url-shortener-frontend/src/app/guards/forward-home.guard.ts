import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { SessionStorageService } from '../core/shared/session-storage.service';

@Injectable({
  providedIn: 'root'
})
export class ForwardHomeGuard implements CanActivate {

  constructor(
    private router: Router,
    private sessionStorageService: SessionStorageService) {

  }

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    let token = this.sessionStorageService.retrieve('token');

    if (next.routeConfig.path == 'login' && token) {
        this.router.navigateByUrl('home');
        return false;
      }

    return true;
  }

}