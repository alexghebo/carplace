import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVehicleListing } from '../vehicle-listing.model';

@Component({
  selector: 'jhi-vehicle-listing-detail',
  templateUrl: './vehicle-listing-detail.component.html',
})
export class VehicleListingDetailComponent implements OnInit {
  vehicleListing: IVehicleListing | null = null;
  images = [
    'https://img.hey.car/unsafe/1024x/filters:quality(90)/https://cdn.hey.car/images/cas/df3e0e8b4da51b531ce3097f5bfe1d4f/original.jpg',
    'https://img.hey.car/unsafe/1024x/filters:quality(90)/https://cdn.hey.car/images/cas/ba92fc0f5da16d3591afc5d310935b52/original.jpg',
    'https://img.hey.car/unsafe/1024x/filters:quality(90)/https://cdn.hey.car/images/cas/db7cb5aa9cb2d2c71ea396b45170a93a/original.jpg',
  ];

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
