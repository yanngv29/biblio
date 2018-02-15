/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { BiblioTestModule } from '../../../test.module';
import { ChercheurDialogComponent } from '../../../../../../main/webapp/app/entities/chercheur/chercheur-dialog.component';
import { ChercheurService } from '../../../../../../main/webapp/app/entities/chercheur/chercheur.service';
import { Chercheur } from '../../../../../../main/webapp/app/entities/chercheur/chercheur.model';
import { ActesService } from '../../../../../../main/webapp/app/entities/actes';
import { ArticleService } from '../../../../../../main/webapp/app/entities/article';
import { ChapitreService } from '../../../../../../main/webapp/app/entities/chapitre';
import { CommunicationService } from '../../../../../../main/webapp/app/entities/communication';
import { OuvrageService } from '../../../../../../main/webapp/app/entities/ouvrage';
import { PublicationGouvernementaleService } from '../../../../../../main/webapp/app/entities/publication-gouvernementale';
import { NumeroRevueService } from '../../../../../../main/webapp/app/entities/numero-revue';
import { MemoireService } from '../../../../../../main/webapp/app/entities/memoire';
import { RapportService } from '../../../../../../main/webapp/app/entities/rapport';

describe('Component Tests', () => {

    describe('Chercheur Management Dialog Component', () => {
        let comp: ChercheurDialogComponent;
        let fixture: ComponentFixture<ChercheurDialogComponent>;
        let service: ChercheurService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BiblioTestModule],
                declarations: [ChercheurDialogComponent],
                providers: [
                    ActesService,
                    ArticleService,
                    ChapitreService,
                    CommunicationService,
                    OuvrageService,
                    PublicationGouvernementaleService,
                    NumeroRevueService,
                    MemoireService,
                    RapportService,
                    ChercheurService
                ]
            })
            .overrideTemplate(ChercheurDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ChercheurDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ChercheurService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Chercheur(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.chercheur = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'chercheurListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Chercheur();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.chercheur = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'chercheurListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
