package com.github.tornaia.sorty.image;

import org.junit.Test;

import static org.junit.Assert.assertThat;

public class LocationTest {

    @Test
    public void zeroZero() {
        Location zero = new Location(0.0D, 0.0D);
        Distance distance = zero.distanceFrom(zero);
        assertThat(distance, DistanceMatcher.ofMeter(0.0D));
    }

    @Test
    public void zeroOne() {
        Location zero = new Location(0.0D, 0.0D);
        Location one = new Location(1.0D, 1.0D);
        Distance distance = zero.distanceFrom(one);
        assertThat(distance, DistanceMatcher.ofKilometer(157.24D));
    }

    @Test
    public void oneZero() {
        Location one = new Location(1.0D, 1.0D);
        Location zero = new Location(0.0D, 0.0D);
        Distance distance = one.distanceFrom(zero);
        assertThat(distance, DistanceMatcher.ofKilometer(157.24D));
    }

    @Test
    public void oneMinusOne() {
        Location one = new Location(1.0D, 1.0D);
        Location zero = new Location(-1.0D, -1.0D);
        Distance distance = one.distanceFrom(zero);
        assertThat(distance, DistanceMatcher.ofKilometer(314.48D));
    }

    @Test
    public void sameLocation() {
        Location someWhere = new Location(5.5D, 7.7D);
        Distance distance = someWhere.distanceFrom(someWhere);
        assertThat(distance, DistanceMatcher.ofMeter(0.0D));
    }

    @Test
    public void keszthelySiofok() {
        Location keszthely = new Location(46.76812D, 17.24317D);
        Location siofok = new Location(46.90413D, 18.058D);
        Distance distance = keszthely.distanceFrom(siofok);
        assertThat(distance, DistanceMatcher.ofKilometer(63.799D));
    }

    @Test
    public void buenosAiresBudapest() {
        Location buenosAires = new Location(-34.57224388D, -58.43931932D);
        Location budapest = new Location(47.4979D, 19.0402D);
        Distance distance = buenosAires.distanceFrom(budapest);
        assertThat(distance, DistanceMatcher.ofKilometer(11933.708D));
    }


}
