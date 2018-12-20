/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SlackJhipTestModule } from '../../../test.module';
import { DirectMessageDeleteDialogComponent } from 'app/entities/direct-message/direct-message-delete-dialog.component';
import { DirectMessageService } from 'app/entities/direct-message/direct-message.service';

describe('Component Tests', () => {
    describe('DirectMessage Management Delete Component', () => {
        let comp: DirectMessageDeleteDialogComponent;
        let fixture: ComponentFixture<DirectMessageDeleteDialogComponent>;
        let service: DirectMessageService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SlackJhipTestModule],
                declarations: [DirectMessageDeleteDialogComponent]
            })
                .overrideTemplate(DirectMessageDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DirectMessageDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DirectMessageService);
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
