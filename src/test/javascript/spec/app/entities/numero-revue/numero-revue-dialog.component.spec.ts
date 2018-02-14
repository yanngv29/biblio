/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { BiblioTestModule } from '../../../test.module';
import { NumeroRevueDialogComponent } from '../../../../../../main/webapp/app/entities/numero-revue/numero-revue-dialog.component';
import { NumeroRevueService } from '../../../../../../main/webapp/app/entities/numero-revue/numero-revue.service';
import { NumeroRevue } from '../../../../../../main/webapp/app/entities/numero-revue/numero-revue.model';
import { RevueService } from '../../../../../../main/webapp/app/entities/revue';

describe('Component Tests', () => {

    describe('NumeroRevue Management Dialog Component', () => {
        let comp: NumeroRevueDialogComponent;
        let fixture: ComponentFixture<NumeroRevueDialogComponent>;
        let service: NumeroRevueService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BiblioTestModule],
                declarations: [NumeroRevueDialogComponent],
                providers: [
                    RevueService,
                    NumeroRevueService
                ]
            })
            .overrideTemplate(NumeroRevueDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(NumeroRevueDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NumeroRevueService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new NumeroRevue(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.numeroRevue = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'numeroRevueListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new NumeroRevue();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.numeroRevue = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'numeroRevueListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
