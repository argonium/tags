# Tags
Tags is a Java module that makes it very easy to perform tag substitution on an input text file. Tags are of the form ${value}. I consider this project to be a very simple version of the Apache Velocity project. Tags doesn't have the functionality of Velocity, but it's simpler to use and fits my needs when Velocity is overkill. For instance, I've used Tags in the past to generate HTML files from data in a database.

Here's an example. Suppose you want to generate a form letter of the form:

```
  Dear Mr. ${last.name},
  
  Thank you for your letter from ${user.city}.
  
  Sincerely,
  ${receiver.name}
```

The first thing to do is implement the interface GetTagValue. Its only method is:

```
  String getTagValue(final String tag)
```

This method's tag parameter is the tag found in the input file (inside the ${ and }), and returns the string to use instead.

Here's one example implementation of this interface:

```
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
```

All that's left to do is construct a ParseFileTags object using this constructor:

```
  public ParseFileTags(final String strFilename,
                       final GetTagValue tagger)
```

The first argument is the input file name, and the second argument is the name of the class implementing the GetTagValue interface. That's it. Here's the complete example:

```
  public final class Tags implements GetTagValue
  {
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
```
      
Running the above example code on the input file given at the top of this page causes the following to be printed out:

```
  Dear Mr. Smith,
  
  Thank you for your letter from Panama.
  
  Sincerely,
  Bob
```

The source code is released under the MIT license.
