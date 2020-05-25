package com.amazonaws.samples.kaja.taxi.consumer.operators;

import ch.hsr.geohash.GeoHash;
import com.amazonaws.samples.kaja.taxi.consumer.events.flink.TripFee;
import com.amazonaws.samples.kaja.taxi.consumer.events.kinesis.TripEvent;
import com.amazonaws.samples.kaja.taxi.consumer.utils.GeoUtils;
import java.time.Duration;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.util.Collector;

public class TripToTripFee implements FlatMapFunction<TripEvent, TripFee> {
  @Override
  public void flatMap(TripEvent tripEvent, Collector<TripFee> collector) {
    String pickupLocation = GeoHash.geoHashStringWithCharacterPrecision(tripEvent.pickupLatitude, tripEvent.pickupLongitude, 6);
    double tripFee = tripEvent.totalAmount;

    collector.collect(new TripFee(tripFee, pickupLocation));
  }
}