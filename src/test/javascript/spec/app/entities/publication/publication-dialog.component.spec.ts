/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { BiblioTestModule } from '../../../test.module';
import { PublicationDialogComponent } from '../../../../../../main/webapp/app/entities/publication/publication-dialog.component';
import { PublicationService } from '../../../../../../main/webapp/app/entities/publication/publication.service';
import { Publication } from '../../../../../../main/webapp/app/entities/publication/publication.model';
import { ArticleService } from '../../../../../../main/webapp/app/entities/article';
import { CommunicationService } from '../../../../../../main/webapp/app/entities/communication';
import { MemoireService } from '../../../../../../main/webapp/app/entities/memoire';
import { OuvrageService } from '../../../../../../main/webapp/app/entities/ouvrage';
import { PublicationGouvernementaleService } from '../../../../../../main/webapp/app/entities/publication-gouvernementale';
import { RapportService } from '../../../../../../main/webapp/app/entities/rapport';

describe('Component Tests', () => {

    describe('Publication Management Dialog Component', () => {
        let comp: PublicationDialogComponent;
        let fixture: ComponentFixture<PublicationDialogComponent>;
        let service: PublicationService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BiblioTestModule],
                declarations: [PublicationDialogComponent],
                providers: [
                    ArticleService,
                    CommunicationService,
                    MemoireService,
                    OuvrageService,
                    PublicationGouvernementaleService,
                    RapportService,
                    PublicationService
                ]
            })
            .overrideTemplate(PublicationDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PublicationDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PublicationService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Publication(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.publication = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'publicationListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Publication();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.publication = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'publicationListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
