import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class CycleService {
  

private baseUrl='http://localhost:8080/api/v1/cycle';
constructor(private http:HttpClient){

}
createCycle(data:any){
  return this.http.post(`${this.baseUrl}/create`,data);
}


}
