import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MissionCreate } from './mission-create';

describe('MissionCreate', () => {
  let component: MissionCreate;
  let fixture: ComponentFixture<MissionCreate>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MissionCreate]
    })
      .compileComponents();

    fixture = TestBed.createComponent(MissionCreate);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
