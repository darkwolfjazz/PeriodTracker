import { ChangeDetectorRef, Component } from '@angular/core';
import { AiService } from '../../services/ai-service';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-ai-chat',
  imports: [FormsModule],
  templateUrl: './ai-chat.html',
  styleUrl: './ai-chat.css',
})
export class AiChat {

constructor(private aiService:AiService,private cdr:ChangeDetectorRef,private router:Router){};

userMessage:string='';
isLoading=false;
messages:any[]=[];

sendMessage(){
  if(!this.userMessage.trim()){
    return;
  }
  const userText=this.userMessage;
  this.messages.push({
    sender:'user',
    text:userText
  });
  this.userMessage='';
  this.isLoading=true;
  this.aiService.chat({
    message:userText
  }).subscribe({
    next:(res:any)=>{
      this.messages.push({
        sender:'aura',
        text:res.response
      });
      this.isLoading=false;
      this.cdr.detectChanges();
    },
    error:(err)=>{
      console.log(err);
      this.isLoading=false;
    }
  })
}
logout() {
 localStorage.removeItem('token');
  this.router.navigate(['/'], {
    replaceUrl: true
  });
}

}
