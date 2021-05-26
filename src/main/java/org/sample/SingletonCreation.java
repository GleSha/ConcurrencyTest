package org.sample;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.I_Result;

import java.util.concurrent.atomic.AtomicInteger;

import static org.openjdk.jcstress.annotations.Expect.ACCEPTABLE;
import static org.openjdk.jcstress.annotations.Expect.ACCEPTABLE_INTERESTING;

public class SingletonCreation {

    @JCStressTest
    @State
    @Outcome(id = "1", expect = ACCEPTABLE, desc = "Correct work")
    @Outcome(id = "2", expect = ACCEPTABLE_INTERESTING, desc = "Singleton was created 2 times")
    public static class SingletonTest {

        public static class UnsafeLazyInitialization {
            public static AtomicInteger count = new AtomicInteger(0);
            private static Resource resource;

            public static Resource getInstance() {
                if (resource == null)
                    resource = new Resource(count);
                return resource;
            }
        }

        public static class Resource {

            public Resource(AtomicInteger count) {
                count.incrementAndGet();
            }
        }



        @Actor
        public void actor1() {
            Resource instance = UnsafeLazyInitialization.getInstance();
        }

        @Actor
        public void actor2() {
            Resource instance = UnsafeLazyInitialization.getInstance();
        }

        @Arbiter
        public void arbiter(I_Result r) {
            r.r1 = UnsafeLazyInitialization.count.get();
        }
    }

}
