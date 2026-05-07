import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { AiService } from '../../services/ai-service';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { gsap } from 'gsap';

@Component({
  selector: 'app-ai-chat',
  imports: [FormsModule],
  templateUrl: './ai-chat.html',
  styleUrl: './ai-chat.css',
})
export class AiChat implements OnInit {
  constructor(
    private aiService: AiService,
    private cdr: ChangeDetectorRef,
    private router: Router,
  ) {}

  userMessage: string = '';
  isLoading = false;
  messages: any[] = [];

  ngOnInit() {
    gsap.from(
      '.aura-card',

      {
        y: 30,

        opacity: 0,

        duration: 1,
      },
    );

    gsap.to(
      '.aura-blob-1',

      {
        x: 60,

        y: 40,

        repeat: -1,

        yoyo: true,

        duration: 6,
      },
    );

    gsap.to(
      '.aura-blob-2',

      {
        x: -60,

        y: -40,

        repeat: -1,

        yoyo: true,

        duration: 7,
      },
    );
  }

  sendMessage() {
    if (!this.userMessage.trim()) {
      return;
    }
    const userText = this.userMessage;
    this.messages.push({
      sender: 'user',
      text: userText,
    });
    this.userMessage = '';
    this.isLoading = true;
    this.aiService
      .chat({
        message: userText,
      })
      .subscribe({
        next: (res: any) => {
          this.messages.push({
            sender: 'aura',
            text: res.response,
          });
          this.isLoading = false;
          this.cdr.detectChanges();
        },
        error: (err) => {
          console.log(err);
          this.isLoading = false;
        },
      });
  }
  logout() {
    localStorage.removeItem('token');
    this.router.navigate(['/'], {
      replaceUrl: true,
    });
  }
}
