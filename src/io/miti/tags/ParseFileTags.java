package io.miti.tags;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * This class performs the brunt of the work of performing
 * text substitution for specially-designated tags in an
 * input file.
 * 
 * @author mwallace
 * @version 1.0
 */
public final class ParseFileTags
{
  /**
   * The name of the input file.
   */
  private String strInputFile;
  
  /**
   * The object implementing the GetTagValue interface.
   */
  private GetTagValue tagProcessor;
  
  /**
   * The string that starts a tag.
   */
  private String strStartTag = "${";
  
  /**
   * The string that ends a tag.
   */
  private String strEndTag = "}";
  
  
  /**
   * Make the default constructor private.
   */
  private ParseFileTags()
  {
    super();
  }
  
  
  /**
   * Construct the object, taking the input filename
   * and an object implementing the GetTagValue
   * interface so the file contents can be
   * replaced.
   * 
   * @param strFilename the input file name
   * @param tagger object implementing the GetTagValue interface
   */
  public ParseFileTags(final String strFilename,
                       final GetTagValue tagger)
  {
    strInputFile = strFilename;
    tagProcessor = tagger;
  }
  
  
  /**
   * Parse the input file and call the getTagValue()
   * method for any tags it finds in the file.
   * 
   * @return the contents of the input file with its tags replaced
   */
  public String parse()
  {
    // This will hold the text read from the input file
    StringBuilder sbInput = new StringBuilder(500);
    
    // Save the length of the input tag start
    final int nStartTagLen = strStartTag.length();
    
    try
    {
      // Open the input file
      BufferedReader in = new BufferedReader(new FileReader(strInputFile));
      
      // Declare our string variable
      String strTemp;
      
      // Read lines until we get to the end of the input file
      while ((strTemp = in.readLine()) != null)
      {
        // Append the line
        sbInput.append(strTemp);
        
        // Add the line separator string
        sbInput.append("\n");
      }
      
      // Close the input stream
      in.close();
    }
    catch (java.io.IOException e)
    {
      System.out.println("File Exception: " + e.getMessage());
    }
    
    // Trim the string
    String strInput = sbInput.toString().trim();
    
    // Check if the input string has zero length.
    if (strInput.length() < 1)
    {
      // It does, so return it as-is
      return strInput.toString();
    }
    
    // This will hold the processed contents of the input file
    // (string substitution has already been performed)
    StringBuilder strOut = new StringBuilder(500);
    
    // Search strInput for the start tag
    int nPrevIndex = 0;
    final int nSize = strInput.length();
    int nIndex = strInput.indexOf(strStartTag);
    while ((nIndex >= 0) && (nIndex < nSize))
    {
      // Append the input string contents before the tag
      strOut.append(strInput.substring(nPrevIndex, nIndex));
      
      // Look for the end tag
      nIndex += nStartTagLen;
      int nEndIndex = strInput.indexOf(strEndTag, nIndex);
      if (nEndIndex > 0)
      {
        // The end tag was found, so return the substitute for the tag
        strOut.append(tagProcessor.getTagValue(strInput.substring(nIndex,
                                                                  nEndIndex)));
      }
      else
      {
        break;
      }
      
      // Update our variables
      nIndex = nEndIndex + 1;
      nPrevIndex = nIndex;
      nIndex = strInput.indexOf(strStartTag, nIndex);
    }
    
    // Append the remainder of the input string
    strOut.append(strInput.substring(nPrevIndex));
    
    // Return the output string
    return strOut.toString();
  }
}
