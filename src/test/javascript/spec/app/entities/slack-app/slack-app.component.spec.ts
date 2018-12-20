/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SlackJhipTestModule } from '../../../test.module';
import { SlackAppComponent } from 'app/entities/slack-app/slack-app.component';
import { SlackAppService } from 'app/entities/slack-app/slack-app.service';
import { SlackApp } from 'app/shared/model/slack-app.model';

describe('Component Tests', () => {
    describe('SlackApp Management Component', () => {
        let comp: SlackAppComponent;
        let fixture: ComponentFixture<SlackAppComponent>;
        let service: SlackAppService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SlackJhipTestModule],
                declarations: [SlackAppComponent],
                providers: []
            })
                .overrideTemplate(SlackAppComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SlackAppComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SlackAppService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new SlackApp(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.slackApps[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
