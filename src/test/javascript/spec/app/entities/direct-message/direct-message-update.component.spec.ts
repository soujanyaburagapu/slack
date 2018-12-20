/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SlackJhipTestModule } from '../../../test.module';
import { DirectMessageUpdateComponent } from 'app/entities/direct-message/direct-message-update.component';
import { DirectMessageService } from 'app/entities/direct-message/direct-message.service';
import { DirectMessage } from 'app/shared/model/direct-message.model';

describe('Component Tests', () => {
    describe('DirectMessage Management Update Component', () => {
        let comp: DirectMessageUpdateComponent;
        let fixture: ComponentFixture<DirectMessageUpdateComponent>;
        let service: DirectMessageService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SlackJhipTestModule],
                declarations: [DirectMessageUpdateComponent]
            })
                .overrideTemplate(DirectMessageUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DirectMessageUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DirectMessageService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new DirectMessage(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.directMessage = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new DirectMessage();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.directMessage = entity;
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
