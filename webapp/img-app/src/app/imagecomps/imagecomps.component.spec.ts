import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ImagecompsComponent } from './imagecomps.component';

describe('ImagecompsComponent', () => {
  let component: ImagecompsComponent;
  let fixture: ComponentFixture<ImagecompsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ImagecompsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ImagecompsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
