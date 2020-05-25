package com.amazonaws.samples.kaja.taxi.consumer.operators;

import com.amazonaws.samples.kaja.taxi.consumer.events.es.PickupCount;
import com.amazonaws.samples.kaja.taxi.consumer.events.flink.TripGeoHash;
import com.google.common.collect.Iterables;
import java.util.stream.StreamSupport;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.streaming.api.functions.windowing.WindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

public class CountByPickupHash implements WindowFunction<TripGeoHash, PickupCount, Tuple, TimeWindow> {
  @Override
  public void apply(Tuple tuple, TimeWindow timeWindow, Iterable<TripGeoHash> iterable, Collector<PickupCount> collector) throws Exception {
    long count = Iterables.size(iterable);
    String position = Iterables.get(iterable, 0).pickupHash;
    double sumFee = StreamSupport
          .stream(iterable.spliterator(), false)
          .mapToDouble(trip -> trip.tripFee)
          .sum();
          
    collector.collect(new PickupCount(position, count, sumFee, timeWindow.getEnd()));
  }
}