import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { environment } from "src/environments/environment";

@Injectable({
    providedIn: 'root'
})
export class ListService {

    constructor(private http: HttpClient) {

    }

    listUrls(page: number, size: number, sort: string, userId: number) {
        return this.http.get(`${environment.url}/url?page=${page}&size=${size}&sort=${sort}&userId=${userId}`); 
    }

}