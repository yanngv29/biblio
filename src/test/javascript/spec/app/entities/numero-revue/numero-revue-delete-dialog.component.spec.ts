/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { BiblioTestModule } from '../../../test.module';
import { NumeroRevueDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/numero-revue/numero-revue-delete-dialog.component';
import { NumeroRevueService } from '../../../../../../main/webapp/app/entities/numero-revue/numero-revue.service';

describe('Component Tests', () => {

    describe('NumeroRevue Management Delete Component', () => {
        let comp: NumeroRevueDeleteDialogComponent;
        let fixture: ComponentFixture<NumeroRevueDeleteDialogComponent>;
        let service: NumeroRevueService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BiblioTestModule],
                declarations: [NumeroRevueDeleteDialogComponent],
                providers: [
                    NumeroRevueService
                ]
            })
            .overrideTemplate(NumeroRevueDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(NumeroRevueDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NumeroRevueService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
