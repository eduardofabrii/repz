import { TestBed } from '@angular/core/testing';
import { CanActivateFn } from '@angular/router';

import { academiaGuard } from './academia-guard';

describe('academiaGuard', () => {
  const executeGuard: CanActivateFn = (...guardParameters) =>
    TestBed.runInInjectionContext(() => academiaGuard(...guardParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(executeGuard).toBeTruthy();
  });
});
