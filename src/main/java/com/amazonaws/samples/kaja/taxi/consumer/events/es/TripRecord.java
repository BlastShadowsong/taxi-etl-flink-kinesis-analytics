/*
 * Copyright 2019 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"). You may
 * not use this file except in compliance with the License. A copy of the
 * License is located at
 *
 *    http://aws.amazon.com/apache2.0/
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package com.amazonaws.samples.kaja.taxi.consumer.events.es;

import java.time.Instant;


public class TripRecord extends Document {
  public final double pickupLatitude;
  public final double pickupLongitude;
  public final String pickupLocation;
  public final double dropoffLatitude;
  public final double dropoffLongitude;
  public final String dropoffLocation;
  public final double travelFee;
  public final Instant pickupDatetime;
  public final Instant dropoffDatetime;
  public final double travelDistance;
  public final long travelTime;
  public final double averageSpeed;
  

  public TripRecord(double pickupLatitude, double pickupLongitude, String pickupLocation, double dropoffLatitude, double dropoffLongitude, String dropoffLocation, double travelFee, Instant pickupDatetime, Instant dropoffDatetime, double travelDistance, long travelTime, double averageSpeed, long timestamp) {
    super(timestamp);

    this.pickupLatitude = pickupLatitude;
    this.pickupLongitude = pickupLongitude;
    this.pickupLocation = pickupLocation;
    this.dropoffLatitude = dropoffLatitude;
    this.dropoffLongitude = dropoffLongitude;
    this.dropoffLocation = dropoffLocation;
    this.travelFee = travelFee;
    this.pickupDatetime = pickupDatetime;
    this.dropoffDatetime = dropoffDatetime;
    this.travelDistance = travelDistance;
    this.travelTime = travelTime;
    this.averageSpeed = averageSpeed;
  }

}