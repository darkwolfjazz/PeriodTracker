import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  

private baseUrl='http://localhost:8080/api/v1/api/user';

constructor(private http:HttpClient){

}

signUp(data:any){
  return this.http.post(`${this.baseUrl}/signup`,data);
}

login(data:any){
  return this.http.post(`${this.baseUrl}/login`,data);
}



}
