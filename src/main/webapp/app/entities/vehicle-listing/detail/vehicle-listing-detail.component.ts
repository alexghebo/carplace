import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVehicleListing } from '../vehicle-listing.model';

@Component({
  selector: 'jhi-vehicle-listing-detail',
  templateUrl: './vehicle-listing-detail.component.html',
})
export class VehicleListingDetailComponent implements OnInit {
  vehicleListing: IVehicleListing | null = null;
  images: any = ['https://img.hey.car/unsafe/1024x/filters:quality(90)/https://cdn.hey.car/images/cas/2093d8052853db6d31e906eae69dc91b/original.jpg',
  'https://img.hey.car/unsafe/1024x/filters:quality(90)/https://cdn.hey.car/images/cas/3e8df8f2e2f7a2ab35b39c1451115bcb/original.jpg',
  'https://img.hey.car/unsafe/1024x/filters:quality(90)/https://cdn.hey.car/images/cas/783100e735d7ab7f8606be509c8ca1e7/original.jpg'
]

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ vehicleListing }) => {
      this.vehicleListing = vehicleListing;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
