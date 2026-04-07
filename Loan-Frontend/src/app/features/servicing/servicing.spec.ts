import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ServicingComponent } from './servicing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormsModule } from '@angular/forms';

describe('ServicingComponent', () => {
  let component: ServicingComponent;
  let fixture: ComponentFixture<ServicingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ServicingComponent, HttpClientTestingModule, FormsModule]
    }).compileComponents();
    fixture = TestBed.createComponent(ServicingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => expect(component).toBeTruthy());
  it('should return correct status class', () => {
    expect(component.getStatusClass('ACTIVE')).toBe('badge-success');
    expect(component.getStatusClass('NPA')).toBe('badge-danger');
  });
});
