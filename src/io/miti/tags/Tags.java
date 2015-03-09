package io.miti.tags;

/**
 * This class demonstrates how to use the ParseFileTags class
 * to perform text substitution on an input text file.
 * 
 * @author mwallace
 * @version 1.0
 */
public final class Tags implements GetTagValue
{
  /**
   * Main entry point for this application.
   * 
   * @param args arguments supplied by the user
   */
  public static void main(final String[] args)
  {
    // Prepare the parser
    ParseFileTags tagger = new ParseFileTags("letter.txt",
                                             new Tags());
    
    // Parse and perform text substitution
    String data = tagger.parse();
    
    // Print out the contents of the string returned by
    // the parse() method.
    System.out.println(data);
  }
  
  
  /**
   * Perform text substitution for each tag found in the
   * input file.
   * 
   * @param tag the tag found in the input file
   * @return the value to replace the tag with
   */
  public String getTagValue(final String tag)
  {
    // Check the tag value, and return the appropriate value
    if (tag.equals("last.name"))
    {
      return "Smith";
    }
    else if (tag.equals("user.city"))
    {
      return "Panama";
    }
    else if (tag.equals("receiver.name"))
    {
      return "Bob";
    }
    
    return "(Tag " + tag + " not found)";
  }
}
