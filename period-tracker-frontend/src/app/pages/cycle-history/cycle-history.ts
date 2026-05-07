import { ChangeDetectorRef, Component, OnInit } from '@angular/core';

import { Router } from '@angular/router';

import { gsap } from 'gsap';

import Chart from 'chart.js/auto';

import { CycleHistoryService } from '../../services/cycle-history-service';

@Component({
  selector: 'app-cycle-history',
  standalone: true,
  imports: [],
  templateUrl: './cycle-history.html',
  styleUrl: './cycle-history.css',
})
export class CycleHistory implements OnInit {
  constructor(
    private cyclehistoryService: CycleHistoryService,private cdr: ChangeDetectorRef,private router: Router,) {}
  history: any[] = [];
  isLoading = true;
  averageCycle = 0;
  averagePeriod = 0;
  ngOnInit(): void {
    gsap.to(
      '.history-blob-1',
      {
        x: 60,
        y: 30,
        repeat: -1,
        yoyo: true,
        duration: 6,
      },
    );
    gsap.to(
      '.history-blob-2',
      {
        x: -50,
        y: -40,
        repeat: -1,
        yoyo: true,
        duration: 7,
      },
    );
    this.loadHistory();
  }

  loadHistory() {
    this.cyclehistoryService
      .getHistory()
      .subscribe({
        next: (res: any) => {
          this.history = res;
          this.calculateStats();
          this.isLoading = false;
          this.cdr.detectChanges();
          setTimeout(() => {
            this.animateCards();
            this.createChart();
          }, 100);
        },
        error: (err) => {
          console.log(err);
          this.isLoading = false;
          this.cdr.detectChanges();
        },
      });
  }

  calculateStats() {
    if (!this.history.length) {
      return;
    }
    const cycleTotal = this.history.reduce(
      (sum, item) => sum + item.cycleLength,
      0,
    );
    const periodTotal = this.history.reduce(
      (sum, item) => sum + item.periodDuration,
      0,
    );
    this.averageCycle = Math.round(cycleTotal / this.history.length);
    this.averagePeriod = Math.round(periodTotal / this.history.length);
  }
  createChart() {
    new Chart(
      'cycleChart',
      {
        type: 'line',

        data: {
          labels: this.history

            .map((x) => x.periodStart)

            .reverse(),

          datasets: [
            {
              label: 'Cycle Length',

              data: this.history

                .map((x) => x.cycleLength)

                .reverse(),
            },
          ],
        },

        options: {
          responsive: true,
        },
      },
    );
  }

  animateCards() {
    gsap.from(
      '.history-card',

      {
        y: 40,

        opacity: 0,

        stagger: 0.15,

        duration: 1,
      },
    );
  }

  logout() {
    localStorage.removeItem('token');

    this.router.navigate(
      ['/'],

      {
        replaceUrl: true,
      },
    );
  }
}
