import { ICarModel } from 'app/entities/car-model/car-model.model';
import { FuelType } from 'app/entities/enumerations/fuel-type.model';
import { ListingStatus } from 'app/entities/enumerations/listing-status.model';
import { AC } from '../enumerations/a-c.model';
import { EmissionClass } from '../enumerations/emission-class.model';
import { GearBox } from '../enumerations/gear-box.model';

export interface IVehicleListing {
  id?: number;
  price?: number | null;
  year?: number | null;
  mileage?: number;
  fuel?: FuelType | null;
  status?: ListingStatus | null;
  carModel?: ICarModel;
  make?: ICarModel;
  internalNumber?: string;
  performance?: number | null;
  mot?: Date | null;
  regDate?: Date | null;
  vin?: string | null;
  colour?: string | null;
  ac?: AC | null;
  esp?: boolean | null;
  abs?: boolean | null;
  doors?: number | null;
  cubicCapacity?: number | null;
  numberOfSeats?: number | null;
  emissionClass?: EmissionClass | null;
  emission?: number | null;
  gearbox?: GearBox | null;
  vat?: boolean;
}

export class VehicleListing implements IVehicleListing {
  constructor(
    public id?: number,
    public price?: number | null,
    public year?: number | null,
    public mileage?: number,
    public fuel?: FuelType | null,
    public status?: ListingStatus | null,
    public carModel?: ICarModel,
    public make?: ICarModel,
    public internalNumber?: string,
    public mot?: Date | null,
    public regDate?: Date | null,
    public vin?: string | null,
    public colour?: string | null,
    public ac?: AC | null,
    public esp?: boolean | null,
    public abs?: boolean | null,
    public doors?: number | null,
    public cubicCapacity?: number | null,
    public numberOfSeats?: number | null,
    public emissionClass?: EmissionClass | null,
    public emission?: number | null,
    public gearbox?: GearBox | null,
    public vat?: boolean
  ) {}
}

export function getVehicleListingIdentifier(vehicleListing: IVehicleListing): number | undefined {
  return vehicleListing.id;
}
