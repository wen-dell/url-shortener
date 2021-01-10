import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Subscription } from 'rxjs';
import { SessionStorageService } from '../core/shared/session-storage.service';
import { ToasterService } from '../core/shared/toaster.service';
import { LoginService } from './login.service';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.sass']
})
export class LoginComponent implements OnInit {

  loading = false;
  form: FormGroup = new FormGroup({});

  subscription: Subscription = new Subscription();

  constructor(
    private builder: FormBuilder,
    private loginService: LoginService,
    private toasterService: ToasterService,
    private sessionStorageService: SessionStorageService) {

  }

  ngOnInit(): void {
    this.form = this.builder.group({
      username: [null, Validators.required],
      password: [null, Validators.required]
    });
  }

  get username() {
    return this.form.get('username');
  }

  get password() {
    return this.form.get('password');
  }

  login() {
    this.disableButton(true);
    this.loading = true;
    let username = this.username?.value;
    let password = this.password?.value;
    let b64 = btoa(`${username}:${password}`);
    
    this.subscription = this.loginService.authenticate(b64)
    .subscribe((res: any) => {
      this.sessionStorageService.save('token', res.data.token);
      this.loading = false;
      this.loginService.redirect();
    }, 
    (error: any) => {
      this.toasterService.error(error.error.message);
      this.loading = false;
      this.disableButton(false);
    });
  }

  disableButton(flag: boolean) {
    (document.getElementById('login') as HTMLInputElement).disabled = flag;
  }

}
