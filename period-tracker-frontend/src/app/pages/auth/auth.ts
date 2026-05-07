import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { Router } from '@angular/router';

import { AuthService } from '../../services/auth-service';
import { DashboardService } from '../../services/dashboard';
import { gsap } from 'gsap';
@Component({
  selector: 'app-auth',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './auth.html',
  styleUrl: './auth.css',
})
export class Auth implements OnInit {
  constructor(
    private authService: AuthService,
    private router: Router,
    private dashboardService:DashboardService,
    private cdr:ChangeDetectorRef
  ) {}

  isLoginMode = false;
  validationErrors:any={};
  apiError:string='';

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
    this.validationErrors={};
    this.apiError='';
    this.isLoginMode = !this.isLoginMode;
  }

  onSubmit() {
    if (this.isLoginMode) {
      this.login();
    } else {
      this.signup();
    }
  }

ngOnInit(): void {


  gsap.from(

    '.auth-card',

    {

      y:40,

      opacity:0,

      duration:1.2

    }

  );



  gsap.from(

    'h1',

    {

      scale:0.8,

      opacity:0,

      duration:1.5

    }

  );



  gsap.to(

    '.blob-1',

    {

      x:80,

      y:50,

      duration:6,

      repeat:-1,

      yoyo:true,

      ease:'sine.inOut'

    }

  );



  gsap.to(

    '.blob-2',

    {

      x:-70,

      y:-40,

      duration:8,

      repeat:-1,

      yoyo:true,

      ease:'sine.inOut'

    }

  );



  gsap.to(

    '.blob-3',

    {

      x:50,

      y:-60,

      duration:7,

      repeat:-1,

      yoyo:true,

      ease:'sine.inOut'

    }

  );

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
        this.validationErrors={};
        this.apiError='';
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.log(err);
        this.validationErrors=err.error;
        this.apiError=err.error.message || ' ';
        console.log("Validation error -> ",this.validationErrors);
        this.cdr.detectChanges();
      },
    });
  }

  login() {
    this.authService.login(this.loginData).subscribe({
      next: (res: any) => {
        console.log('Login successful!', res);

        localStorage.setItem('token', res.token);
       this.apiError='';
        this.resetLoginForm();
        this.cdr.detectChanges();
        this.checkCycleData();
      },

      error: (err) => {
        console.log(err);

        alert('Login failed');
        this.validationErrors=err.error;
        this.apiError=err.error.message || ' ';
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
