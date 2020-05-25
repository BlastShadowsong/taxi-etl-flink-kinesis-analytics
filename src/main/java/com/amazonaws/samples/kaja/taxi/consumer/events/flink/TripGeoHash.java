package com.amazonaws.samples.kaja.taxi.consumer.events.flink;

public class TripGeoHash {
  public final String pickupHash;
  public final String dropoffHash;
  public final double tripFee;

  public TripGeoHash() {
    this.pickupHash = "";
    this.dropoffHash = "";
    this.tripFee = 0;
  }

  public TripGeoHash(String pickupHash, String dropoffHash, double tripFee) {
    this.pickupHash = pickupHash;
    this.dropoffHash = dropoffHash;
    this.tripFee = tripFee;
  }
}
