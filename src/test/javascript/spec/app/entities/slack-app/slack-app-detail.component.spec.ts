/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SlackJhipTestModule } from '../../../test.module';
import { SlackAppDetailComponent } from 'app/entities/slack-app/slack-app-detail.component';
import { SlackApp } from 'app/shared/model/slack-app.model';

describe('Component Tests', () => {
    describe('SlackApp Management Detail Component', () => {
        let comp: SlackAppDetailComponent;
        let fixture: ComponentFixture<SlackAppDetailComponent>;
        const route = ({ data: of({ slackApp: new SlackApp(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SlackJhipTestModule],
                declarations: [SlackAppDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SlackAppDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SlackAppDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.slackApp).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
