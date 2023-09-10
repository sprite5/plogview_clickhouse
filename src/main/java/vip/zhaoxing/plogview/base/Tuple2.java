package vip.zhaoxing.plogview.base;


import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Tuple2<T1,T2>  implements Iterable<Object>,Serializable {
    private static final long serialVersionUID = -351818201888280684L;

        final T1 t1;
        final T2 t2;

        Tuple2(T1 t1, T2 t2) {
            this.t1 = t1;
            this.t2 = t2;
        }

        public T1 getT1() {
            return this.t1;
        }

        public T2 getT2() {
            return this.t2;
        }

        public static <T1, T2> Tuple2<T1, T2> of(T1 t1, T2 t2) {
            return new Tuple2<>(t1, t2);
        }

        @Nullable
        public Object get(int index) {
            switch(index) {
                case 0:
                    return this.t1;
                case 1:
                    return this.t2;
                default:
                    return null;
            }
        }

        public List<Object> toList() {
            return Arrays.asList(this.toArray());
        }

        public Object[] toArray() {
            return new Object[]{this.t1, this.t2};
        }


        public Iterator<Object> iterator() {
            return Collections.unmodifiableList(this.toList()).iterator();
        }

        public boolean equals(@Nullable Object o) {
            if (this == o) {
                return true;
            } else if (o != null && this.getClass() == o.getClass()) {
                Tuple2<?, ?> tuple2 = (Tuple2)o;
                return this.t1.equals(tuple2.t1) && this.t2.equals(tuple2.t2);
            } else {
                return false;
            }
        }

        public int hashCode() {
            int result = this.size();
            result = 31 * result + this.t1.hashCode();
            result = 31 * result + this.t2.hashCode();
            return result;
        }

        public int size() {
            return 2;
        }

        public final String toString() {
            return Tuple.tupleStringRepresentation(this.toArray()).insert(0, '[').append(']').toString();
        }
}
