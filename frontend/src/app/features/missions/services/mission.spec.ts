import { TestBed } from '@angular/core/testing';
import { MissionService } from './mission';

describe('MissionService', () => {
  let service: MissionService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MissionService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
