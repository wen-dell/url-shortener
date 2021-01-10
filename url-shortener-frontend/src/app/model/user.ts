import { UserURL } from "./user-url";

export class User {

    id: number;
    login: string;
    email: string;
    password: string;
    urlsFromUser: Array<UserURL>;

    constructor(id?: number, login?: string, email?: string, password?: string, urlsFromUser?: Array<UserURL>) {
        this.id = id;
        this.login = login;
        this.email = email;
        this.password = password;
        this.urlsFromUser = urlsFromUser;
    }

}