import { TestBed } from '@angular/core/testing';

import { AuthGardGuard } from './auth-gard.guard';

describe('AuthGardGuard', () => {
  let guard: AuthGardGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(AuthGardGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
