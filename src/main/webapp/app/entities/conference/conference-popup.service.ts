import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { Conference } from './conference.model';
import { ConferenceService } from './conference.service';

@Injectable()
export class ConferencePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private conferenceService: ConferenceService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.conferenceService.find(id)
                    .subscribe((conferenceResponse: HttpResponse<Conference>) => {
                        const conference: Conference = conferenceResponse.body;
                        conference.dateDebutConference = this.datePipe
                            .transform(conference.dateDebutConference, 'yyyy-MM-ddTHH:mm:ss');
                        conference.dateFinConference = this.datePipe
                            .transform(conference.dateFinConference, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.conferenceModalRef(component, conference);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.conferenceModalRef(component, new Conference());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    conferenceModalRef(component: Component, conference: Conference): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.conference = conference;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
