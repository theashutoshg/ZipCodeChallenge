package com.ash.wsi.zipcode.domain;

import com.ash.wsi.zipcode.exception.ParamException;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import java.util.Comparator;

public class ZipRange implements Comparable<ZipRange> {

  @Getter
  @Range(min = 1, max = 99999, message = "Zip should be between 1-99999 and can't more than 99999")
  private int lowerBound;

  @Getter
  @Setter
  @Range(min = 1, max = 99999, message = Constants.CONTRAINTS_RANGE_MESSAGE)
  private int upperBound;

  /**
   * @param lowerBound LowerBound
   * @param upperBound UpperBound of the zip range
   */
  public ZipRange(int lowerBound, int upperBound) {
    if (lowerBound > upperBound) {
      throw new ParamException("Param not valid. Lower bound has to be lower then upper bound.");
    }
    this.lowerBound = lowerBound;
    this.upperBound = upperBound;
  }

  /**
   * @param o Input the object
   * @return Boolean for is equal
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;

    if (o == null || getClass() != o.getClass()) return false;

    ZipRange zipRange = (ZipRange) o;

    return this.lowerBound == zipRange.lowerBound && this.upperBound == zipRange.upperBound;
  }

  /**
   * @return Stringify object
   */
  @Override
  public String toString() {
    return "[" + lowerBound + "," + upperBound + "]";
  }

  private Comparator<ZipRange> zipComparator() {
    return Comparator.comparingInt(ZipRange::getLowerBound)
            .thenComparingInt(ZipRange::getUpperBound);
  }
  //  Comparator<ZipRange> COMPARE_ZIP =
  //
  // Comparator.comparingInt(ZipRange::getLowerBound).thenComparingInt(ZipRange::getUpperBound);

  /**
   * Compares this object with the specified object for order. Returns a negative integer, zero, or
   * a positive integer as this object is less than, equal to, or greater than the specified object.
   *
   * <p>The implementor must ensure <tt>sgn(x.compareTo(y)) == -sgn(y.compareTo(x))</tt> for all
   * <tt>x</tt> and <tt>y</tt>. (This implies that <tt>x.compareTo(y)</tt> must throw an exception
   * iff <tt>y.compareTo(x)</tt> throws an exception.)
   *
   * <p>The implementor must also ensure that the relation is transitive: <tt>(x.compareTo(y)&gt;0
   * &amp;&amp; y.compareTo(z)&gt;0)</tt> implies <tt>x.compareTo(z)&gt;0</tt>.
   *
   * <p>Finally, the implementor must ensure that <tt>x.compareTo(y)==0</tt> implies that
   * <tt>sgn(x.compareTo(z)) == sgn(y.compareTo(z))</tt>, for all <tt>z</tt>.
   *
   * <p>It is strongly recommended, but <i>not</i> strictly required that <tt>(x.compareTo(y)==0) ==
   * (x.equals(y))</tt>. Generally speaking, any class that implements the <tt>Comparable</tt>
   * interface and violates this condition should clearly indicate this fact. The recommended
   * language is "Note: this class has a natural ordering that is inconsistent with equals."
   *
   * <p>In the foregoing description, the notation <tt>sgn(</tt><i>expression</i><tt>)</tt>
   * designates the mathematical <i>signum</i> function, which is defined to return one of
   * <tt>-1</tt>, <tt>0</tt>, or <tt>1</tt> according to whether the value of <i>expression</i> is
   * negative, zero or positive.
   *
   * @param other the object to be compared.
   * @return a negative integer, zero, or a positive integer as this object is less than, equal to,
   * or greater than the specified object.
   * @throws NullPointerException if the specified object is null
   * @throws ClassCastException   if the specified object's type prevents it from being compared to
   *                              this object.
   */
  @Override
  public int compareTo(ZipRange other) {

    return zipComparator().compare(this, other);
  }
}
