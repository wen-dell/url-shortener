import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Router } from '@angular/router';
import { UserURL } from 'src/app/model/user-url';

@Injectable({
  providedIn: 'root'
})
export class AddFormService {

  constructor(
    private http: HttpClient
  ) { }

  public generateUrl(userURL: UserURL) {
    return this.http.post(`${environment.url}/url`, userURL);
  }

}
