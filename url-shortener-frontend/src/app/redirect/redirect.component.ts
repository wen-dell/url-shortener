import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { RedirectService } from './redirect.service';

@Component({
  selector: 'app-redirect',
  templateUrl: './redirect.component.html',
  styleUrls: ['./redirect.component.sass']
})
export class RedirectComponent implements OnInit, OnDestroy {

  subscription: Subscription = new Subscription();

  constructor(
    private redirectService: RedirectService,
    private activatedRoute: ActivatedRoute,
    private router: Router) { }

  ngOnInit(): void {
    const code = this.activatedRoute.snapshot.url[0].path;
    this.subscription = this.redirectService.getByCode(code)
     .subscribe((response: any) => {
       if (response) {
         const url = response.originalUrl;
         location.href = url;
       } else {
        this.router.navigateByUrl('login');
       }
    });
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

}
