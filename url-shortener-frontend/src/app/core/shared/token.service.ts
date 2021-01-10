import { Injectable } from "@angular/core";
import { SessionStorageService } from "./session-storage.service";

@Injectable({
    providedIn: 'root'
})
export class TokenService {

    constructor(private sessionStorageService: SessionStorageService) {

    }

    getPropertyFromJWT(property: string) {
        let token = this.sessionStorageService.retrieve('token');
        let parts : any = token?.split('.');
        let object : any = atob(parts[1]);
        object = JSON.parse(object);

        return object[property];
    }

}