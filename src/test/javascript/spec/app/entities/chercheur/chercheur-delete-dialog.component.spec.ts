/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { BiblioTestModule } from '../../../test.module';
import { ChercheurDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/chercheur/chercheur-delete-dialog.component';
import { ChercheurService } from '../../../../../../main/webapp/app/entities/chercheur/chercheur.service';

describe('Component Tests', () => {

    describe('Chercheur Management Delete Component', () => {
        let comp: ChercheurDeleteDialogComponent;
        let fixture: ComponentFixture<ChercheurDeleteDialogComponent>;
        let service: ChercheurService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BiblioTestModule],
                declarations: [ChercheurDeleteDialogComponent],
                providers: [
                    ChercheurService
                ]
            })
            .overrideTemplate(ChercheurDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ChercheurDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ChercheurService);
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
