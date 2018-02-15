import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { PublicationGouvernementale } from './publication-gouvernementale.model';
import { PublicationGouvernementaleService } from './publication-gouvernementale.service';

@Injectable()
export class PublicationGouvernementalePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private publicationGouvernementaleService: PublicationGouvernementaleService

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
                this.publicationGouvernementaleService.find(id)
                    .subscribe((publicationGouvernementaleResponse: HttpResponse<PublicationGouvernementale>) => {
                        const publicationGouvernementale: PublicationGouvernementale = publicationGouvernementaleResponse.body;
                        publicationGouvernementale.datePG = this.datePipe
                            .transform(publicationGouvernementale.datePG, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.publicationGouvernementaleModalRef(component, publicationGouvernementale);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.publicationGouvernementaleModalRef(component, new PublicationGouvernementale());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    publicationGouvernementaleModalRef(component: Component, publicationGouvernementale: PublicationGouvernementale): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.publicationGouvernementale = publicationGouvernementale;
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
