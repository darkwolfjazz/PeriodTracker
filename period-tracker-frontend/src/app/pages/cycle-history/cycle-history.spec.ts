import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CycleHistory } from './cycle-history';

describe('CycleHistory', () => {
  let component: CycleHistory;
  let fixture: ComponentFixture<CycleHistory>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CycleHistory]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CycleHistory);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
