package org.sample;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.I_Result;

import static org.openjdk.jcstress.annotations.Expect.ACCEPTABLE;
import static org.openjdk.jcstress.annotations.Expect.ACCEPTABLE_INTERESTING;

public class VolatileTest {

    @JCStressTest
    @Outcome(id = "1", expect = ACCEPTABLE_INTERESTING, desc = "One update lost.")
    @Outcome(id = "2", expect = ACCEPTABLE, desc = "Both updates.")
    @State
    public static class VolatileIncrement {
        volatile int v;

        @Actor
        public void actor1() {
            v++;
        }

        @Actor
        public void actor2() {
            v++;
        }

        @Arbiter
        public void arbiter(I_Result r) {
            r.r1 = v;
        }
    }
}
