import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class DashboardService {
  
private baseUrl='http://localhost:8080/api/v1/cycle';

constructor(private http:HttpClient){

}

getDashboard(){
  return this.http.get(`${this.baseUrl}/dashboard`);
}
  

}
