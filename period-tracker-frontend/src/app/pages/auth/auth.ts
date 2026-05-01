import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth-service';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { DashboardService } from '../../services/dashboard';

@Component({
  selector: 'app-auth',
  standalone:true,
  imports: [FormsModule],
  templateUrl: './auth.html',
  styleUrl: './auth.css',
})
export class Auth {

constructor(private authService:AuthService,private router:Router,private dashboardService:DashboardService){
}
isLoginMode=false;

toggleMode(){
  this.isLoginMode=!this.isLoginMode;
}

signUpData={
 username: '',
  password: '',
  age: null,
  height: null,
  weight: null,
  cycleLength: null,
  periodDuration: null
}

loginData={
   username: '',
   password: ''
}


onSubmit(){
  if(this.isLoginMode){
    this.authService.login(this.loginData).subscribe({
      next:(res:any)=>{
        console.log("Login successful!",res);
        localStorage.setItem('token',res.token);
        //this.resetLoginForm();
       setTimeout(() => {
     this.checkCycleData();
     }, 0);
      },
      error:(err)=>{
        console.log(err);
        alert("Login failed");
      }
    });
  }else{
    //signup flow
    this.authService.signUp(this.signUpData).subscribe({
      next:(res)=>{
        console.log("Signup success!")
        localStorage.removeItem('token');
        this.resetSignupForm();
        this.resetLoginForm();
        this.isLoginMode=true;
      },
      error:(err)=>{
        console.log(err);
        alert("Signup failed!");
      }
    })
  }
}


resetSignupForm() {
  this.signUpData = {
    username: '',
    password: '',
    age: null,
    height: null,
    weight: null,
    cycleLength: null,
    periodDuration: null
  };
}

resetLoginForm() {
  this.loginData = {
    username: '',
    password: ''
  };
}


checkCycleData(){
  this.dashboardService.getDashboard().subscribe({
     next:()=>{
      this.router.navigate(['/dashboard'],{replaceUrl:true});
     },
     error:(err)=>{
      if(err.status===404 || err.status===403){
        this.router.navigate(['/cycle-setup'],{replaceUrl:true})
      }
     }
  })
}




}
