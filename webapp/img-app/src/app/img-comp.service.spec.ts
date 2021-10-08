import { TestBed } from '@angular/core/testing';

import { ImgCompService } from './img-comp.service';

describe('ImgCompService', () => {
  let service: ImgCompService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ImgCompService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
