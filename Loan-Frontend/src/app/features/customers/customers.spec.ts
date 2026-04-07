import { ComponentFixture, TestBed } from '@angular/core/testing';
import { CustomersComponent } from './customers';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormsModule } from '@angular/forms';

describe('CustomersComponent', () => {
  let component: CustomersComponent;
  let fixture: ComponentFixture<CustomersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CustomersComponent, HttpClientTestingModule, FormsModule]
    }).compileComponents();
    fixture = TestBed.createComponent(CustomersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => expect(component).toBeTruthy());
  it('should return correct kyc class', () => {
    expect(component.getKycClass('VERIFIED')).toBe('badge-success');
    expect(component.getKycClass('REJECTED')).toBe('badge-danger');
    expect(component.getKycClass('PENDING')).toBe('badge-warning');
  });
});
