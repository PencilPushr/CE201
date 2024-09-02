package util;

import java.util.Objects;

public final class Pair<K, V> {
    private final K first;

    private final V second;

    public Pair ( final K first , final V second ) {
        this.first = first;
        this.second = second;
    }
    
    public static <K, V> Pair<K, V> of(K first, V second) {
        return new Pair<>(first, second);
    }

    public K first() { return first; }

    public V second () { return second; }

    @Override
    public boolean equals ( Object obj ) {
        if ( obj == this ) return true;
        if ( obj == null || obj.getClass () != this.getClass () ) return false;
        var other = ( Pair ) obj;
        return Objects.equals ( this.first , other.first ) &&
                Objects.equals ( this.first , other.second );
    }

    @Override
    public int hashCode () {
        return Objects.hash ( first , second );
    }

    @Override
    public String toString () {
        return "Pair[" +
                "Key: " + first + ", " +
                "Value: " + second + "]";
    }

}
