import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatNativeDateModule } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatFormField, MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { CycleService } from '../../services/cycle-service';
import { Router } from '@angular/router';
import { gsap } from 'gsap';
@Component({
  selector: 'app-cycle-setup',
  standalone:true,
  imports: [CommonModule,
    FormsModule,MatFormFieldModule,
    MatInputModule,MatDatepickerModule,
    MatNativeDateModule,MatButtonModule],
  templateUrl: './cycle-setup.html',
  styleUrl: './cycle-setup.css',
})
export class CycleSetup implements OnInit {



constructor(private cycleService:CycleService,private router:Router){}

today=new Date();

cycleData={
  lastPeriodDate:null,
  cycleLength:null,
  periodDuration:null
}

ngOnInit(): void {
  gsap.from(
    '.setup-card',
    {
      y:40,
      opacity:0,
      duration:1
    }
  );
  gsap.to(
    '.setup-blob-1',
    {
      x:70,
      y:40,
      repeat:-1,
      yoyo:true,
      duration:6
    });
  gsap.to(
    '.setup-blob-2',
    {
      x:-60,
      y:-30,
      repeat:-1,
      yoyo:true,
      duration:7
    });

}

submit() {
  console.log(this.cycleData);
  const payLoad={
    ...this.cycleData,
    lastPeriodDate:
    this.formatDate(this.cycleData.lastPeriodDate)
  };
  console.log("Payload:",payLoad);
  this.cycleService.createCycle(payLoad).subscribe({
    next:(res:any)=>{
      console.log("cycle created",res);
      this.router.navigate(['/dashboard'],{replaceUrl:true});
    },
    error:(err)=>{
      console.log(err);
      alert(err.error.message);
    }
  })
}



formatDate(date: any): string {
  return date
    .toISOString()
    .split('T')[0];

}
logout(){
   localStorage.removeItem('token');
  this.router.navigate(['/'], {
    replaceUrl: true
  });
}
}
