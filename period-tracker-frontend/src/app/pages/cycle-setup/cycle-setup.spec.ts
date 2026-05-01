import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CycleSetup } from './cycle-setup';

describe('CycleSetup', () => {
  let component: CycleSetup;
  let fixture: ComponentFixture<CycleSetup>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CycleSetup]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CycleSetup);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
