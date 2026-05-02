import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class CycleHistoryService {
  

private baseUrl='http://localhost:8080/api/v1/cycle';

constructor(private http:HttpClient){};

getHistory(){
  return this.http.get(`${this.baseUrl}/history`)
}


}
