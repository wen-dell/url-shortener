import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { ToasterService } from '../core/shared/toaster.service';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.sass']
})
export class LoginComponent implements OnInit {

  form: FormGroup = new FormGroup({});

  constructor(
    private builder: FormBuilder,
    private router: Router,
    private toasterService: ToasterService) {

  }

  ngOnInit(): void {
    this.form = this.builder.group({
      username: [null, Validators.required],
      password: [null, Validators.required]
    });
  }

  login() {
    let user = this.username?.value;
    let pass = this.password?.value;

    if (user == 'user' && pass == '123') {
      this.router.navigate(['/home']);
    } else {
      this.toasterService.error('Usuário ou senha incorretos!');
    }
  }

  get username() {
    return this.form.get('username');
  }

  get password() {
    return this.form.get('password');
  }

}
