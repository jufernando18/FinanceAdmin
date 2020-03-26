import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAuditRegistry } from 'app/shared/model/audit-registry.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { AuditRegistryService } from './audit-registry.service';
import { AuditRegistryDeleteDialogComponent } from './audit-registry-delete-dialog.component';

@Component({
  selector: 'jhi-audit-registry',
  templateUrl: './audit-registry.component.html'
})
export class AuditRegistryComponent implements OnInit, OnDestroy {
  auditRegistries?: IAuditRegistry[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected auditRegistryService: AuditRegistryService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.auditRegistryService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IAuditRegistry[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
        () => this.onError()
      );
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.ascending = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
      this.ngbPaginationPage = data.pagingParams.page;
      this.loadPage();
    });
    this.registerChangeInAuditRegistries();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAuditRegistry): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInAuditRegistries(): void {
    this.eventSubscriber = this.eventManager.subscribe('auditRegistryListModification', () => this.loadPage());
  }

  delete(auditRegistry: IAuditRegistry): void {
    const modalRef = this.modalService.open(AuditRegistryDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.auditRegistry = auditRegistry;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IAuditRegistry[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/audit-registry'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.auditRegistries = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
