/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SlackJhipTestModule } from '../../../test.module';
import { DirectMessageComponent } from 'app/entities/direct-message/direct-message.component';
import { DirectMessageService } from 'app/entities/direct-message/direct-message.service';
import { DirectMessage } from 'app/shared/model/direct-message.model';

describe('Component Tests', () => {
    describe('DirectMessage Management Component', () => {
        let comp: DirectMessageComponent;
        let fixture: ComponentFixture<DirectMessageComponent>;
        let service: DirectMessageService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SlackJhipTestModule],
                declarations: [DirectMessageComponent],
                providers: []
            })
                .overrideTemplate(DirectMessageComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DirectMessageComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DirectMessageService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new DirectMessage(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.directMessages[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
