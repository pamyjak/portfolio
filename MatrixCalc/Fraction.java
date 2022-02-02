import java.util.regex.*;

public class Fraction implements Comparable<Fraction>
{
  private int numerator;
  private int denominator;
  private int[] primes = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47};
  public static final Pattern pattern = Pattern.compile("\\d+/\\d+");
  private static final Pattern intPat = Pattern.compile("\\d+");

  /** Constructors  **/
  Fraction()
  {
    // Default fractions to 0/1 = 0
    this.numerator = 0;
    this.denominator = 1;
  }
  Fraction(int n, int d)
  {
    this.numerator = (d < 0) ? (-1)*n : n;
    this.denominator = (d < 0) ? (-1)*d : d;
  }

  /** Convert Fraction to String **/
  @Override
  public String toString()
  {
    // Print only the numerator if the denominator is 1
    if (this.denominator == 1) return "" + this.numerator;
    else return "" + this.numerator + "/" + this.denominator;
  }

  /** CompareTo Method **/
  public int compareTo(Fraction f) throws FractionUndefinedException
  {
    if (this.isUndefined())
      throw new FractionUndefinedException("'" + this + "'");
    if (f.isUndefined())
      throw new FractionUndefinedException("'" + f + "'");

    return ((f.getNumer() * this.denominator) - (this.numerator * f.getDenom()));
  }

  /** Determine if a string matches the Fraction pattern  **/
  public static boolean isFraction(String str)
  {
    return (Fraction.pattern.matcher(str).matches() || Fraction.intPat.matcher(str).matches());
  }

  /** Detemine if fraction is Undefined **/
  public boolean isUndefined()
  {
    return (this.denominator == 0);
  }

  /** Detemine if fraction is Defined **/
  public boolean isDefined()
  {
    return (this.denominator != 0);
  }

  /** Parse fraction **/
  public static Fraction parseFraction(String str) throws FractionFormatException
  {
    // Check formatting conditions
    if (!Fraction.isFraction(str))
      throw new FractionFormatException("'" + str + "'");

    // Return Fraction
    Fraction f = new Fraction(0, 0);

    // Check if Fraction is Integer
    if (Fraction.intPat.matcher(str).matches())
    {
      f.set(Integer.parseInt(str), 1);
    }

    // Check if numerator is 0 and set fraction to 0/1 = 0
    else if (str.charAt(0) == '0')
    {
      f.set(0, 1);
      return f;
    }

    // Parse Fraction
    else
    {
      boolean isNumerator = true;
      char c;
      for (int i = 0; i < str.length(); i++)
      {
        c = str.charAt(i);
        if (Character.isDigit(c) && c != '/')
        {
          if (isNumerator)
          {
            f.numerator *= 10;
            f.numerator += Character.getNumericValue(c);
          }
          else
          {
            f.denominator *= 10;
            f.denominator += Character.getNumericValue(c);
          }
        }
        else
          isNumerator = false;
      }
    }

    // Return final Fraction
    return f;
  }

  /** Reduce fraction (attempt to divide by prime numbers) **/
  public void reduce()
  {
    for (int p : primes) {
      if (this.numerator >= p || this.denominator >= p)
      {
        if (this.numerator % p == 0 && this.denominator % p == 0)
        {
          this.numerator /= p;
          this.denominator /= p;
          this.reduce();
          return;
        }
      }
    }
    return;
  }

  /** Reciprocal **/
  public void recprocate()
  {
    int temp = this.numerator;
    this.numerator = this.denominator;
    this.denominator = temp;
  }

  /** Addition **/
  public void add(int num)
  {
    this.numerator += num;
    this.reduce();
  }
  public void add(Fraction numF)
  {
    this.numerator *= numF.getDenom();
    this.numerator += (numF.getNumer() * this.denominator);
    this.denominator *= numF.getDenom();
    this.reduce();
  }

  /** Multiplication **/
  public void mult(int num)
  {
    this.numerator *= num;
    this.reduce();
  }
  public void mult(Fraction numF)
  {
    this.numerator *= numF.getNumer();
    this.denominator *= numF.getDenom();
    this.reduce();
  }

  /** Division **/
  public void divide(int num)
  {
    this.denominator *= num;
    this.reduce();
  }
  public void divide(Fraction numF)
  {
    this.numerator *= numF.getDenom();
    this.denominator *= numF.getNumer();
    this.reduce();
  }

  /** Getters **/
  public int getNumer()
  {
    return this.numerator;
  }
  public int getDenom()
  {
    return this.denominator;
  }
  public int[] getFraction()
  {
    int[] retVal = {this.numerator, this.denominator};
    return retVal;
  }
  public Fraction getCopy()
  {
    return new Fraction(this.numerator, this.denominator);
  }

  /** Setters **/
  public void setNumer(int n)
  {
    this.numerator = n;
    this.reduce();
  }
  public void setDenom(int d)
  {
    this.denominator = d;
    this.reduce();
  }
  public void set(int n, int d)
  {
    this.numerator = (d < 0) ? (-1)*n : n;
    this.denominator = (d < 0) ? (-1)*d : d;
    this.reduce();
  }
  public void set(Fraction numF)
  {
    this.numerator = numF.getNumer();
    this.denominator = numF.getDenom();
    this.reduce();
  }
}
