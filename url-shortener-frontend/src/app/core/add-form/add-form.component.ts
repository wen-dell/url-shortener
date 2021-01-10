import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ClipboardService } from 'ngx-clipboard';
import { User } from 'src/app/model/user';
import { UserURL } from 'src/app/model/user-url';
import { ToasterService } from '../shared/toaster.service';
import { TokenService } from '../shared/token.service';
import { AddFormService } from './add-form.service';

@Component({
  selector: 'app-add-form',
  templateUrl: './add-form.component.html',
  styleUrls: ['./add-form.component.sass']
})
export class AddFormComponent implements OnInit {

  form: FormGroup = new FormGroup({});
  @ViewChild('generatedUrl') generatedUrl: any;

  constructor(
    private clipboardService: ClipboardService,
    private toasterService: ToasterService,
    private addFormService: AddFormService,
    private builder: FormBuilder,
    private tokenService: TokenService,
  ) { }

  ngOnInit(): void {
    this.form = this.builder.group({
      url: [null, Validators.required]
    });
  }

  get url() {
    return this.form.get('url');
  }

  copy(url: string) {
    this.clipboardService.copy(url);
    this.toasterService.info('Conteúdo copiado!');
  }

  generateUrl() {
    let url = this.url?.value;
    let userId = this.tokenService.getPropertyFromJWT('userId');

    let userURL = new UserURL(null, url, null, null, null, new User(userId));

    this.addFormService.generateUrl(userURL)
    .subscribe((res: any) => {
      this.applyValue(res.generatedUrl);
      this.toasterService.success('URL encurtada com sucesso!');
    },
    (error: any) => {
      this.toasterService.error(error.error.message);  
    });
  }

  applyValue(value: string) {
    this.generatedUrl.nativeElement.click();
    this.generatedUrl.nativeElement.value = value;
  }

}
