import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth-service';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-auth',
  imports: [FormsModule],
  templateUrl: './auth.html',
  styleUrl: './auth.css',
})
export class Auth {

constructor(private authService:AuthService,private router:Router){
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
        this.router.navigate(['/dashboard']);
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
        alert("Signup successful , Please login");
        this.isLoginMode=true;
      },
      error:(err)=>{
        console.log(err);
        alert("Signup failed!");
      }
    })
  }
}

}
