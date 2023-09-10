package vip.zhaoxing.plogview.base;

public class Tuple{

    public static <T1, T2> Tuple2<T1, T2> of(T1 t1, T2 t2) {
        return new Tuple2<>(t1, t2);
    }


    static StringBuilder tupleStringRepresentation(Object... values) {
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < values.length; ++i) {
            Object t = values[i];
            if (i != 0) {
                sb.append(',');
            }

            if (t != null) {
                sb.append(t);
            }
        }

        return sb;
    }
}
