import { Component, OnInit } from '@angular/core';
import { DashboardService } from '../../services/dashboard';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  standalone:true,
  imports: [CommonModule],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css',
})
export class Dashboard implements OnInit {


constructor(private dashboardService:DashboardService,private router:Router){

}
dashboardData: any = {
  workouts: [],
  diet: []
};

ngOnInit():void{
 
this.dashboardService.getDashboard().subscribe({
  next:(res:any)=>{
    console.log("dashboard data" ,res);
    this.dashboardData=res;
  },
  error:(err)=>{
    console.log(err);
  }
});


}
logout() {
 localStorage.removeItem('token');
  this.router.navigate(['/'], {
    replaceUrl: true
  });
}

}
