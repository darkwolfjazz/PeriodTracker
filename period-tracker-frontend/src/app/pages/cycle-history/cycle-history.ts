import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { CycleHistoryService } from '../../services/cycle-history-service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-cycle-history',
  standalone:true,
  imports: [],
  templateUrl: './cycle-history.html',
  styleUrl: './cycle-history.css',
})
export class CycleHistory implements OnInit {


constructor(private cyclehistoryService:CycleHistoryService,private cdr:ChangeDetectorRef,private router:Router){}

history:any[]=[];
isLoading=true;

ngOnInit(): void {
  this.loadHistory();
}

loadHistory(){
this.cyclehistoryService.getHistory().subscribe({
  next:(res:any)=>{
    console.log("data from history",res);
    this.history=res;
    this.isLoading=false;
     this.cdr.detectChanges();
  },
  error:(err)=>{
    console.log(err);
    this.isLoading=false;
    this.cdr.detectChanges();
  }
})
}

logout(){
   localStorage.removeItem('token');
  this.router.navigate(['/'], {
    replaceUrl: true
  });
}

}
