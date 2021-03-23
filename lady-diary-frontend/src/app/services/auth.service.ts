import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs/internal/Observable";
import {tap} from "rxjs/operators";
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient, private router: Router) {
  }

  login(email?: string, password?: string): Observable<string> {
    if (email && password) {
      return this.http.post<string>('localhost:8080/v1/users/login', {
        name: email,
        password: password
      }).pipe(tap(response => {
        localStorage.setItem("t", response);
      }));
    }
  }

  ping(): Observable<any> {
    let headers = new HttpHeaders();

    return this.http.get<any>('localhost:8080/ping');
  }

  logout(): void {
    localStorage.clear();
    this.router.navigate(['/login']);
  }

  getToken(): string {
    if (localStorage.getItem("t") !== undefined) {
      return localStorage.getItem("t");
    }
  }

}
