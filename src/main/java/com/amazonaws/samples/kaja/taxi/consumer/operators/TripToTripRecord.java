package com.amazonaws.samples.kaja.taxi.consumer.operators;

import ch.hsr.geohash.GeoHash;
import com.amazonaws.samples.kaja.taxi.consumer.events.es.TripRecord;
import com.amazonaws.samples.kaja.taxi.consumer.events.kinesis.TripEvent;
import com.amazonaws.samples.kaja.taxi.consumer.utils.GeoUtils;
import java.time.Duration;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.util.Collector;
import java.time.Instant;

import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GeodeticCurve;
import org.gavaghan.geodesy.GlobalCoordinates;

public class TripToTripRecord implements FlatMapFunction<TripEvent, TripRecord> {
  @Override
  public void flatMap(TripEvent tripEvent, Collector<TripRecord> collector) {
    double pickupLatitude = tripEvent.pickupLatitude;
    double pickupLongitude = tripEvent.pickupLongitude;
    String pickupLocation = GeoHash.geoHashStringWithCharacterPrecision(tripEvent.pickupLatitude, tripEvent.pickupLongitude, 8);
    double dropoffLatitude = tripEvent.dropoffLatitude;
    double dropoffLongitude = tripEvent.dropoffLongitude;
    String dropoffLocation = GeoHash.geoHashStringWithCharacterPrecision(tripEvent.dropoffLatitude, tripEvent.dropoffLongitude, 8);
    double travelFee = tripEvent.totalAmount;
    long pickupDatetime = tripEvent.pickupDatetime;
    long dropoffDatetime = tripEvent.dropoffDatetime;
    
    // Calculate distance between pickupLocation and dropoffLocation using Sphere
    GlobalCoordinates pickup = new GlobalCoordinates(pickupLatitude, pickupLongitude);
    GlobalCoordinates dropoff = new GlobalCoordinates(dropoffLatitude, dropoffLongitude);
    double travelDistance = getDistanceMeter(pickup, dropoff, Ellipsoid.Sphere) / 1000;
    
    // Calculate duration between pickup and dropoff in minutes
    long travelTime = Duration.between(tripEvent.pickupDatetime, tripEvent.dropoffDatetime).toMinutes();
    
    // Calculate average speed in Km/h
    double averageSpeed = travelDistance / travelTime * 60;

    collector.collect(new TripRecord(pickupLatitude, pickupLongitude, pickupLocation, dropoffLatitude, dropoffLongitude, dropoffLocation, travelFee, pickupDatetime, dropoffDatetime, travelDistance, travelTime, averageSpeed, tripEvent.getTimestamp()));
  }
  
  public static double getDistanceMeter(GlobalCoordinates gpsFrom, GlobalCoordinates gpsTo, Ellipsoid ellipsoid){
    GeodeticCurve geoCurve = new GeodeticCalculator().calculateGeodeticCurve(ellipsoid, gpsFrom, gpsTo);
    return geoCurve.getEllipsoidalDistance();
  }
}