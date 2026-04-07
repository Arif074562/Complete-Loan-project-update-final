import { ComponentFixture, TestBed } from '@angular/core/testing';
import { LoanApplicationsComponent } from './loan-applications';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormsModule } from '@angular/forms';

describe('LoanApplicationsComponent', () => {
  let component: LoanApplicationsComponent;
  let fixture: ComponentFixture<LoanApplicationsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LoanApplicationsComponent, HttpClientTestingModule, FormsModule]
    }).compileComponents();
    fixture = TestBed.createComponent(LoanApplicationsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => expect(component).toBeTruthy());
  it('should return correct status class', () => {
    expect(component.getStatusClass('APPROVED')).toBe('badge-success');
    expect(component.getStatusClass('PENDING')).toBe('badge-warning');
  });
});
