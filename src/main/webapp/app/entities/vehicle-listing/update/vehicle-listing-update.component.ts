import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IVehicleListing, VehicleListing } from '../vehicle-listing.model';
import { VehicleListingService } from '../service/vehicle-listing.service';
import { ICarModel } from 'app/entities/car-model/car-model.model';
import { CarModelService } from 'app/entities/car-model/service/car-model.service';
import { FuelType } from 'app/entities/enumerations/fuel-type.model';
import { ListingStatus } from 'app/entities/enumerations/listing-status.model';
import { AC } from 'app/entities/enumerations/a-c.model';
import { EmissionClass } from 'app/entities/enumerations/emission-class.model';
import { GearBox } from 'app/entities/enumerations/gear-box.model';

@Component({
  selector: 'jhi-vehicle-listing-update',
  templateUrl: './vehicle-listing-update.component.html',
})
export class VehicleListingUpdateComponent implements OnInit {
  isSaving = false;
  fuelTypeValues = Object.keys(FuelType);
  listingStatusValues = Object.keys(ListingStatus);
  acTypeValues = Object.keys(AC);
  emissionClassValues = Object.keys(EmissionClass);
  gearBoxValues = Object.keys(GearBox);

  carModelsSharedCollection: ICarModel[] = [];

  editForm = this.fb.group({
    id: [],
    price: [],
    year: [],
    mileage: [],
    fuel: [],
    status: [],
    carModel: [],
    make: [],
    internalNumber: [],
    performance: [],
    mot: [],
    regDate: [],
    vin: [],
    colour: [],
    ac: [],
    esp: [],
    abs: [],
    doors: [],
    cubicCapacity: [],
    numberOfSeats: [],
    emissionClass: [],
    emission: [],
    gearbox: [],
    vat: [],
  });

  constructor(
    protected vehicleListingService: VehicleListingService,
    protected carModelService: CarModelService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ vehicleListing }) => {
      this.updateForm(vehicleListing);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const vehicleListing = this.createFromForm();
    if (vehicleListing.id !== undefined) {
      this.subscribeToSaveResponse(this.vehicleListingService.update(vehicleListing));
    } else {
      this.subscribeToSaveResponse(this.vehicleListingService.create(vehicleListing));
    }
  }

  trackCarModelById(_index: number, item: ICarModel): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVehicleListing>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(vehicleListing: IVehicleListing): void {
    this.editForm.patchValue({
      id: vehicleListing.id,
      price: vehicleListing.price,
      year: vehicleListing.year,
      mileage: vehicleListing.mileage,
      fuel: vehicleListing.fuel,
      status: vehicleListing.status,
      carModel: vehicleListing.carModel,
      make: vehicleListing.carModel?.make,
      internalNumber: vehicleListing.internalNumber,
      performance: vehicleListing.performance,
      mot: vehicleListing.mot,
      regDate: vehicleListing.regDate,
      vin: vehicleListing.vin,
      colour: vehicleListing.colour,
      ac: vehicleListing.ac,
      esp: vehicleListing.esp,
      abs: vehicleListing.abs,
      doors: vehicleListing.doors,
      cubicCapacity: vehicleListing.cubicCapacity,
      numberOfSeats: vehicleListing.numberOfSeats,
      emissionClass: vehicleListing.emissionClass,
      emission: vehicleListing.emission,
      gearbox: vehicleListing.gearbox,
      vat: vehicleListing.vat,
    });

    this.carModelsSharedCollection = this.carModelService.addCarModelToCollectionIfMissing(
      this.carModelsSharedCollection,
      vehicleListing.carModel
    );
  }

  protected loadRelationshipsOptions(): void {
    this.carModelService
      .query()
      .pipe(map((res: HttpResponse<ICarModel[]>) => res.body ?? []))
      .pipe(
        map((carModels: ICarModel[]) =>
          this.carModelService.addCarModelToCollectionIfMissing(carModels, this.editForm.get('carModel')!.value)
        )
      )
      .subscribe((carModels: ICarModel[]) => (this.carModelsSharedCollection = carModels));
  }

  protected createFromForm(): IVehicleListing {
    return {
      ...new VehicleListing(),
      id: this.editForm.get(['id'])!.value,
      price: this.editForm.get(['price'])!.value,
      year: this.editForm.get(['year'])!.value,
      mileage: this.editForm.get(['mileage'])!.value,
      fuel: this.editForm.get(['fuel'])!.value,
      status: this.editForm.get(['status'])!.value,
      carModel: this.editForm.get(['carModel'])!.value,
      make: this.editForm.get(['make'])!.value,
      internalNumber: this.editForm.get(['internalNumber'])!.value,
      performance: this.editForm.get(['performance'])!.value,
      mot: this.editForm.get(['mot'])!.value,
      regDate: this.editForm.get(['regDate'])!.value,
      vin: this.editForm.get(['vin'])!.value,
      colour: this.editForm.get(['colour'])!.value,
      ac: this.editForm.get(['ac'])!.value,
      esp: this.editForm.get(['esp'])!.value,
      abs: this.editForm.get(['abs'])!.value,
      doors: this.editForm.get(['doors'])!.value,
      cubicCapacity: this.editForm.get(['cubicCapacity'])!.value,
      numberOfSeats: this.editForm.get(['numberOfSeats'])!.value,
      emissionClass: this.editForm.get(['emissionClass'])!.value,
      emission: this.editForm.get(['emission'])!.value,
      gearbox: this.editForm.get(['gearbox'])!.value,
      vat: this.editForm.get(['vat'])!.value,
    };
  }
}
