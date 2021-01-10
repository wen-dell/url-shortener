import { User } from "./user";

export class UserURL {

    id: number;
    originalUrl: string;
    generatedUrl: string;
    date: Date;
    code: string;
    user: User;

    constructor(id?: number, originalUrl?: string, generatedUrl?: string,
        date?: Date, code?: string, user?: User) {
        this.id = id;
        this.originalUrl = originalUrl;
        this.generatedUrl = generatedUrl;
        this.date = date;
        this.code = code;
        this.user = user;
    }

}