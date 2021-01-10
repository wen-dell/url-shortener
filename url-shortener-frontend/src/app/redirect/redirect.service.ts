import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { environment } from "src/environments/environment";

@Injectable({
    providedIn: 'root'
})
export class RedirectService {

    constructor(private http: HttpClient) {

    }

    getByCode(code: string) {
        return this.http.get(`${environment.url}/url/code/${code}`);
    }

}