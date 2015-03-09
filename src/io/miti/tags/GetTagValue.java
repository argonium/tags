package io.miti.tags;

/**
 * This interface defines the method that must be
 * implemented by a class that wants to perform
 * text substitution on an input text file.
 * 
 * @author mwallace
 * @version 1.0
 */
interface GetTagValue
{
  /**
   * This method will receive the tag found in the input file
   * and return the substitute value.
   * 
   * @param tag the tag found in the input file
   * @return the substitute value
   */
  String getTagValue(final String tag);
}
