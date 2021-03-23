import {Component, OnInit} from '@angular/core';
import {AuthService} from "../services/auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  email: string;
  password: string;

  constructor(
    private auth: AuthService,
    private router: Router) {
  }

  ngOnInit(): void {
    this.auth.ping().subscribe((r: any) => {
      console.log('aaaa');
    })
  }

  login(): void {
    this.auth.login(this.email, this.password).subscribe((response: string) => {
      if (localStorage.getItem('t') !== undefined) {
        this.router.navigate(['']);
      }
    });
  }


}
