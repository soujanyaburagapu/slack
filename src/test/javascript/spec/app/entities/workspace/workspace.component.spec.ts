/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SlackJhipTestModule } from '../../../test.module';
import { WorkspaceComponent } from 'app/entities/workspace/workspace.component';
import { WorkspaceService } from 'app/entities/workspace/workspace.service';
import { Workspace } from 'app/shared/model/workspace.model';

describe('Component Tests', () => {
    describe('Workspace Management Component', () => {
        let comp: WorkspaceComponent;
        let fixture: ComponentFixture<WorkspaceComponent>;
        let service: WorkspaceService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SlackJhipTestModule],
                declarations: [WorkspaceComponent],
                providers: []
            })
                .overrideTemplate(WorkspaceComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(WorkspaceComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(WorkspaceService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Workspace(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.workspaces[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
