/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SlackJhipTestModule } from '../../../test.module';
import { SlackAppDeleteDialogComponent } from 'app/entities/slack-app/slack-app-delete-dialog.component';
import { SlackAppService } from 'app/entities/slack-app/slack-app.service';

describe('Component Tests', () => {
    describe('SlackApp Management Delete Component', () => {
        let comp: SlackAppDeleteDialogComponent;
        let fixture: ComponentFixture<SlackAppDeleteDialogComponent>;
        let service: SlackAppService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SlackJhipTestModule],
                declarations: [SlackAppDeleteDialogComponent]
            })
                .overrideTemplate(SlackAppDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SlackAppDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SlackAppService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
