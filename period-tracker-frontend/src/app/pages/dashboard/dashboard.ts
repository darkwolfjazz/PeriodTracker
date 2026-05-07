import { AfterViewInit, ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { DashboardService } from '../../services/dashboard';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { gsap } from 'gsap';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css',
})
export class Dashboard implements OnInit, AfterViewInit {
  constructor(
    private dashboardService: DashboardService,
    private router: Router,
    private cdr: ChangeDetectorRef,
  ) {}
  dashboardData: any = null;
  isLoading = true;

  ngOnInit(): void {
    this.dashboardService.getDashboard().subscribe({
      next: (res: any) => {
        console.log('dashboard data', res);
        this.dashboardData = res;
        console.log('setting loading false');
        this.isLoading = false;
        this.cdr.detectChanges();
        console.log('loading false set done !');
      },
      error: (err) => {
        console.log(err);
        console.log('inside err block loading is about to set false');
        this.isLoading = false;
        this.cdr.detectChanges();
        console.log('loading set to false inside err block !');
      },
    });
  }
  logout() {
    localStorage.removeItem('token');
    this.router.navigate(['/'], {
      replaceUrl: true,
    });
  }

  gotoHistory() {
    this.router.navigate(['/history']);
  }

  logNewPeriod() {
    this.router.navigate(['/cycle-setup']);
  }

  openAura() {
    this.router.navigate(['/ai-chat']);
  }

  ngAfterViewInit(): void {}

  animateDashboard() {
    gsap.from(
      '.dashboard-header',

      {
        y: -40,

        opacity: 0,

        duration: 1,
      },
    );

    gsap.from(
      '.phase-card',

      {
        scale: 0.8,

        opacity: 0,

        duration: 1,

        delay: 0.3,
      },
    );

    gsap.from(
      '.info-card, .workout-card, .diet-card',

      {
        y: 40,

        opacity: 0,

        stagger: 0.2,

        duration: 1,
      },
    );

    gsap.from(
      '.aura-card',

      {
        y: 50,

        opacity: 0,

        duration: 1,

        delay: 0.8,
      },
    );
  }
}
