/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SlackJhipTestModule } from '../../../test.module';
import { SlackAppUpdateComponent } from 'app/entities/slack-app/slack-app-update.component';
import { SlackAppService } from 'app/entities/slack-app/slack-app.service';
import { SlackApp } from 'app/shared/model/slack-app.model';

describe('Component Tests', () => {
    describe('SlackApp Management Update Component', () => {
        let comp: SlackAppUpdateComponent;
        let fixture: ComponentFixture<SlackAppUpdateComponent>;
        let service: SlackAppService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SlackJhipTestModule],
                declarations: [SlackAppUpdateComponent]
            })
                .overrideTemplate(SlackAppUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SlackAppUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SlackAppService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new SlackApp(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.slackApp = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new SlackApp();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.slackApp = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
