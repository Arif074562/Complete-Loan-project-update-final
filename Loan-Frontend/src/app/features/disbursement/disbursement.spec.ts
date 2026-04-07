import { ComponentFixture, TestBed } from '@angular/core/testing';
import { DisbursementComponent } from './disbursement';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormsModule } from '@angular/forms';

describe('DisbursementComponent', () => {
  let component: DisbursementComponent;
  let fixture: ComponentFixture<DisbursementComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DisbursementComponent, HttpClientTestingModule, FormsModule]
    }).compileComponents();
    fixture = TestBed.createComponent(DisbursementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => expect(component).toBeTruthy());
  it('should return correct status class', () => {
    expect(component.getStatusClass('COMPLETED')).toBe('badge-success');
    expect(component.getStatusClass('FAILED')).toBe('badge-danger');
  });
});
