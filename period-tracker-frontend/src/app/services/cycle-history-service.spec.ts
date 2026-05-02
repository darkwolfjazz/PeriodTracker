import { TestBed } from '@angular/core/testing';

import { CycleHistoryService } from './cycle-history-service';

describe('CycleHistoryService', () => {
  let service: CycleHistoryService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CycleHistoryService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
