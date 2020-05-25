package com.amazonaws.samples.kaja.taxi.consumer.operators;

import com.amazonaws.samples.kaja.taxi.consumer.events.es.AverageTripFee;
import com.amazonaws.samples.kaja.taxi.consumer.events.flink.TripFee;
import com.google.common.collect.Iterables;
import java.util.stream.StreamSupport;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.streaming.api.functions.windowing.WindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

public class TripFeeToAverageTripFee implements WindowFunction<TripFee, AverageTripFee, Tuple, TimeWindow> {
  @Override
  public void apply(Tuple tuple, TimeWindow timeWindow, Iterable<TripFee> iterable, Collector<AverageTripFee> collector) {
    if (Iterables.size(iterable) > 1) {
      String location = Iterables.get(iterable, 0).pickupGeoHash;

      double sumFee = StreamSupport
          .stream(iterable.spliterator(), false)
          .mapToDouble(trip -> trip.tripFee)
          .sum();

      double avgFee = (double) sumFee / Iterables.size(iterable);

      collector.collect(new AverageTripFee(location, sumFee, avgFee, timeWindow.getEnd()));
    }
  }
}
