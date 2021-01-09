import { Component, OnInit } from '@angular/core';
import { ClipboardService } from 'ngx-clipboard';
import { ToasterService } from '../shared/toaster.service';

@Component({
  selector: 'app-add-form',
  templateUrl: './add-form.component.html',
  styleUrls: ['./add-form.component.sass']
})
export class AddFormComponent implements OnInit {

  constructor(private clipboardService: ClipboardService, private toasterService: ToasterService) { }

  ngOnInit(): void {
  }

  copy(url: string) {
    this.clipboardService.copy(url);
    this.toasterService.info('Conteúdo copiado!');
  }

}
