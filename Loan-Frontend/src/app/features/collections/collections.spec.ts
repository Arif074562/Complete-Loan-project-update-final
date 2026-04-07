import { ComponentFixture, TestBed } from '@angular/core/testing';
import { CollectionsComponent } from './collections';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormsModule } from '@angular/forms';

describe('CollectionsComponent', () => {
  let component: CollectionsComponent;
  let fixture: ComponentFixture<CollectionsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CollectionsComponent, HttpClientTestingModule, FormsModule]
    }).compileComponents();
    fixture = TestBed.createComponent(CollectionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => expect(component).toBeTruthy());
  it('should return correct bucket class', () => {
    expect(component.getBucketClass('BUCKET_0')).toBe('badge-success');
    expect(component.getBucketClass('BUCKET_5_PLUS')).toBe('badge-danger');
  });
});
