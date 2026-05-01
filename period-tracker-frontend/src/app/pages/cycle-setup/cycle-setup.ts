import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatNativeDateModule } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatFormField, MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { CycleService } from '../../services/cycle-service';
import { Router } from '@angular/router';
@Component({
  selector: 'app-cycle-setup',
  imports: [CommonModule,
    FormsModule,MatFormFieldModule,
    MatInputModule,MatDatepickerModule,
    MatNativeDateModule,MatButtonModule],
  templateUrl: './cycle-setup.html',
  styleUrl: './cycle-setup.css',
})
export class CycleSetup {



constructor(private cycleService:CycleService,private router:Router){}


cycleData={
  lastPeriodDate:null,
  cycleLength:null,
  periodDuration:null
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
    }
  })
}



formatDate(date: any): string {
  return date
    .toISOString()
    .split('T')[0];

}
}
