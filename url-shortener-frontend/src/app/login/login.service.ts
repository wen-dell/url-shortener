import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(
    private http: HttpClient,
    private router: Router
  ) { }

  public authenticate(b64: string) {
    return this.http.get(`${environment.url}/token/generate`, { headers: { 'Authentication': b64 } });
  }

  public redirect() {
    this.router.navigateByUrl('/home');
  }

}
