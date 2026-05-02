import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class AiService {
  
private baseUrl='http://localhost:8080/api/v1/ai';
constructor(private http:HttpClient){}

chat(data:any){
return this.http.post(`${this.baseUrl}/chat`,data);
}

}
