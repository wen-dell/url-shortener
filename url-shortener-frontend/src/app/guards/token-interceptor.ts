import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpEvent, HttpHandler, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { Router } from '@angular/router';
import { SessionStorageService } from '../core/shared/session-storage.service';


@Injectable()
export class TokenInterceptor implements HttpInterceptor {

    constructor(
        private router: Router,
        private sessionStorageService: SessionStorageService) { }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

        const jwt = this.getJWT();

        if (jwt) {
            request = request.clone({
                setHeaders: {
                    Authorization: `Bearer ${jwt}`
                }
            });
        }

        return next.handle(request).pipe(tap(() => { }, (error: any) => {

            if (error instanceof HttpErrorResponse) {

                if (error.status === 401) {
                    sessionStorage.removeItem('token');
                    this.router.navigate(['/login']);
                }
            }

        }));

    }

    getJWT() {
        return this.sessionStorageService.retrieve('token');
    }

}
