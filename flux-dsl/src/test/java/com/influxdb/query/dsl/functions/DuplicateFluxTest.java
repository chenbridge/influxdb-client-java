/*
 * The MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.influxdb.query.dsl.functions;

import com.influxdb.query.dsl.AbstractFluxTest;
import com.influxdb.query.dsl.Flux;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

/**
 * @author Jakub Bednar (bednar@github) (09/10/2018 13:27)
 */
@RunWith(JUnitPlatform.class)
class DuplicateFluxTest extends AbstractFluxTest {

    @Test
    void duplicate() {

        Flux flux = Flux
                .from("telegraf")
                .duplicate("host", "server");

        Flux.Query query = flux.toQuery();
        Assertions.assertThat(query.flux).isEqualToIgnoringWhitespace("from(bucket:v0) |> duplicate(column: v1, as: v2)");
        assertVariables(query,
                "v0", "\"telegraf\"",
                "v1", "\"host\"",
                "v2", "\"server\"");
    }

    @Test
    void duplicateByParameters() {

        Flux flux = Flux
                .from("telegraf")
                .duplicate()
                    .withColumn("time")
                    .withAs("timestamp");

        Flux.Query query = flux.toQuery();
        Assertions.assertThat(query.flux).isEqualToIgnoringWhitespace("from(bucket:v0) |> duplicate(column: v1, as: v2)");
        assertVariables(query,
                "v0", "\"telegraf\"",
                "v1", "\"time\"",
                "v2", "\"timestamp\"");
    }
}