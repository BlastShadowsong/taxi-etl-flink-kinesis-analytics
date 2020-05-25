package com.amazonaws.samples.kaja.taxi.consumer.events.flink;

public class TripFee {
  public final double tripFee;
  public final String pickupGeoHash;

  public TripFee() {
    tripFee = 0;
    pickupGeoHash = "";
  }

  public TripFee(double tripFee, String pickupGeoHash) {
    this.tripFee = tripFee;
    this.pickupGeoHash = pickupGeoHash;
  }
}
