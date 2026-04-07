import { ComponentFixture, TestBed } from '@angular/core/testing';
import { UnderwritingComponent } from './underwriting';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormsModule } from '@angular/forms';

describe('UnderwritingComponent', () => {
  let component: UnderwritingComponent;
  let fixture: ComponentFixture<UnderwritingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UnderwritingComponent, HttpClientTestingModule, FormsModule]
    }).compileComponents();
    fixture = TestBed.createComponent(UnderwritingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => expect(component).toBeTruthy());
  it('should return correct decision class', () => {
    expect(component.getDecisionClass('APPROVED')).toBe('badge-success');
    expect(component.getDecisionClass('REJECTED')).toBe('badge-danger');
  });
});
