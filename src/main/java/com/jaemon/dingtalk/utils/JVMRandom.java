/*
 * Copyright 2015-2020 Jaemon(answer_ljm@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jaemon.dingtalk.utils;

import java.util.Random;

/**
 * <p><code>JVMRandom</code> is a wrapper that supports all possible
 * Random methods via the {@link java.lang.Math#random()} method
 * and its system-wide {@link Random} object.</p>
 *
 * @since 2.0
 * @version $Id: JVMRandom.java 471626 2006-11-06 04:02:09Z bayard $
 */
public final class JVMRandom extends Random {

    /**
     * Required for serialization support.
     *
     * @see java.io.Serializable
     */
    private static final long serialVersionUID = 1L;

    /**
     * Ensures that only the constructor can call reseed.
     */
    private boolean constructed = false;

    /**
     * Constructs a new instance.
     */
    public JVMRandom() {
        this.constructed = true;
    }

    /**
     * Unsupported in 2.0.
     *
     * @param seed ignored
     * @throws UnsupportedOperationException unsupportedOperationException
     */
    @Override
    public synchronized void setSeed(long seed) {
        if (this.constructed) {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Unsupported in 2.0.
     *
     * @return Nothing, this method always throws an UnsupportedOperationException.
     * @throws UnsupportedOperationException unsupportedOperationException
     */
    @Override
    public synchronized double nextGaussian() {
        throw new UnsupportedOperationException();
    }

    /**
     * Unsupported in 2.0.
     *
     * @param byteArray ignored
     * @throws UnsupportedOperationException unsupportedOperationException
     */
    @Override
    public void nextBytes(byte[] byteArray) {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>Returns the next pseudorandom, uniformly distributed int value
     * from the Math.random() sequence.</p>
     *
     * @return the random int
     */
    @Override
    public int nextInt() {
        return nextInt(Integer.MAX_VALUE);
    }
    /**
     * <p>Returns a pseudorandom, uniformly distributed int value between
     * <code>0</code> (inclusive) and the specified value (exclusive), from
     * the Math.random() sequence.</p>
     *
     * @param n  the specified exclusive max-value
     * @return the random int
     * @throws IllegalArgumentException when <code>n &lt;= 0</code>
     */
    @Override
    public int nextInt(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException(
                    "Upper bound for nextInt must be positive"
            );
        }
        return (int)(Math.random() * n);
    }
    /**
     * <p>Returns the next pseudorandom, uniformly distributed long value
     * from the Math.random() sequence.</p>
     * @return the random long
     */
    @Override
    public long nextLong() {
        // possible loss of precision?
        return nextLong(Long.MAX_VALUE);
    }


    /**
     * <p>Returns a pseudorandom, uniformly distributed long value between
     * <code>0</code> (inclusive) and the specified value (exclusive), from
     * the Math.random() sequence.</p>
     *
     * @param n  the specified exclusive max-value
     * @return the random long
     * @throws IllegalArgumentException when <code>n &lt;= 0</code>
     */
    public static long nextLong(long n) {
        if (n <= 0) {
            throw new IllegalArgumentException(
                    "Upper bound for nextInt must be positive"
            );
        }
        return (long)(Math.random() * n);
    }

    /**
     * <p>Returns the next pseudorandom, uniformly distributed boolean value
     * from the Math.random() sequence.</p>
     *
     * @return the random boolean
     */
    @Override
    public boolean nextBoolean() {
        return Math.random() > 0.5;
    }
    /**
     * <p>Returns the next pseudorandom, uniformly distributed float value
     * between <code>0.0</code> and <code>1.0</code> from the Math.random()
     * sequence.</p>
     *
     * @return the random float
     */
    @Override
    public float nextFloat() {
        return (float)Math.random();
    }
    /**
     * <p>Synonymous to the Math.random() call.</p>
     *
     * @return the random double
     */
    @Override
    public double nextDouble() {
        return Math.random();
    }

}