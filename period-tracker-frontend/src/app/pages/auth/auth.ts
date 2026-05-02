import { ChangeDetectorRef, Component } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { Router } from '@angular/router';

import { AuthService } from '../../services/auth-service';
import { DashboardService } from '../../services/dashboard';

@Component({
  selector: 'app-auth',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './auth.html',
  styleUrl: './auth.css',
})
export class Auth {
  constructor(
    private authService: AuthService,
    private router: Router,
    private dashboardService:DashboardService,
    private cdr:ChangeDetectorRef
  ) {}

  isLoginMode = false;

  signUpData = {
    username: '',

    password: '',

    age: null,

    height: null,

    weight: null,
  };

  loginData = {
    username: '',

    password: '',
  };

  toggleMode() {
    this.isLoginMode = !this.isLoginMode;
  }

  onSubmit() {
    if (this.isLoginMode) {
      this.login();
    } else {
      this.signup();
    }
  }

  signup() {
    this.authService.signUp(this.signUpData).subscribe({
      next: () => {
        console.log('Signup success!');
        localStorage.removeItem('token');
        this.resetSignupForm();
        this.resetLoginForm();
        // Switch to login UI
        this.isLoginMode = true;
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.log(err);
        alert('Signup failed!');
        this.cdr.detectChanges();
      },
    });
  }

  login() {
    this.authService.login(this.loginData).subscribe({
      next: (res: any) => {
        console.log('Login successful!', res);

        localStorage.setItem('token', res.token);

        this.resetLoginForm();
        this.cdr.detectChanges();
        this.checkCycleData();
      },

      error: (err) => {
        console.log(err);

        alert('Login failed');
        this.cdr.detectChanges();
      },
    });
  }

  resetSignupForm() {
    this.signUpData = {
      username: '',

      password: '',

      age: null,

      height: null,

      weight: null,
    };
  }

  resetLoginForm() {
    this.loginData = {
      username: '',

      password: '',
    };
  }

  checkCycleData(){
    this.dashboardService.getDashboard().subscribe({
      next:()=>{
        this.router.navigate(['/dashboard'],{replaceUrl:true});
      },
      error:(err)=>{
        if(err.status===404 || err.status===403){
          this.router.navigate(['/cycle-setup'],{replaceUrl:true});
        }
      }
    });
  }
}
