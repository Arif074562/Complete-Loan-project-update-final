import { ComponentFixture, TestBed } from '@angular/core/testing';
import { AuthComponent } from './auth';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormsModule } from '@angular/forms';

describe('AuthComponent', () => {
  let component: AuthComponent;
  let fixture: ComponentFixture<AuthComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AuthComponent, RouterTestingModule, HttpClientTestingModule, FormsModule]
    }).compileComponents();
    fixture = TestBed.createComponent(AuthComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => expect(component).toBeTruthy());
  it('should default to login tab', () => expect(component.activeTab).toBe('login'));
  it('should switch to signup tab', () => { component.switchTab('signup'); expect(component.activeTab).toBe('signup'); });
  it('should show error when login fields empty', () => { component.login(); expect(component.error).toBeTruthy(); });
  it('should show error when register fields empty', () => { component.register(); expect(component.error).toBeTruthy(); });
  it('should show error when passwords do not match', () => {
    component.regName = 'John Doe';
    component.regEmail = 'john@test.com';
    component.regPassword = 'pass123';
    component.regConfirmPassword = 'different';
    component.register();
    expect(component.error).toBe('Passwords do not match.');
  });
});
