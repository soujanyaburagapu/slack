/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SlackJhipTestModule } from '../../../test.module';
import { DirectMessageDetailComponent } from 'app/entities/direct-message/direct-message-detail.component';
import { DirectMessage } from 'app/shared/model/direct-message.model';

describe('Component Tests', () => {
    describe('DirectMessage Management Detail Component', () => {
        let comp: DirectMessageDetailComponent;
        let fixture: ComponentFixture<DirectMessageDetailComponent>;
        const route = ({ data: of({ directMessage: new DirectMessage(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SlackJhipTestModule],
                declarations: [DirectMessageDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(DirectMessageDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DirectMessageDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.directMessage).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
