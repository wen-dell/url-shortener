import { Injectable } from "@angular/core";
import { ToastrService } from "ngx-toastr";

@Injectable({
    providedIn: 'root'
})
export class ToasterService {

    constructor(private toastrService: ToastrService) {

    }

    success(msg: string) {
        this.toastrService.success(msg, 'Sucesso');
    } 
    
    error(msg: string) {
        this.toastrService.error(msg, 'Erro');
    } 
    
    info(msg: string) {
        this.toastrService.info(msg, 'Informação');
    } 
    
    warning(msg: string) {
        this.toastrService.warning(msg, 'Atenção');
    } 

}