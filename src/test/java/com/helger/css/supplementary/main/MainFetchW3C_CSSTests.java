/**
 * Copyright (C) 2014-2015 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.helger.css.supplementary.main;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import com.helger.commons.charset.CCharset;
import com.helger.commons.hierarchy.DefaultHierarchyWalkerCallback;
import com.helger.commons.io.file.SimpleFileIO;
import com.helger.commons.io.resource.URLResource;
import com.helger.commons.io.streams.StreamUtils;
import com.helger.commons.microdom.IMicroDocument;
import com.helger.commons.microdom.IMicroElement;
import com.helger.commons.microdom.IMicroNode;
import com.helger.commons.microdom.serialize.MicroReader;
import com.helger.commons.microdom.utils.MicroWalker;
import com.helger.commons.string.StringHelper;

public class MainFetchW3C_CSSTests
{
  public static void main (final String [] args) throws MalformedURLException
  {
    _fetch ("http://www.w3.org/Style/CSS/Test/CSS3/Selectors/current/xml/full/flat/",
            "src\\test\\resources\\testfiles\\css30\\w3c\\selectors");
  }

  private static void _fetch (final String sURL, final String sDestDir) throws MalformedURLException
  {
    final List <String> aCSSFilenames = new ArrayList <String> ();
    System.out.println ("Fetching from " + sURL);
    final List <String> aIndex = StreamUtils.readStreamLines (new URLResource (sURL + "index.html"),
                                                              CCharset.CHARSET_UTF_8_OBJ);
    {
      // Remove doctype
      aIndex.remove (0);

      // Fix HTML to be XML
      for (int i = 0; i < aIndex.size (); ++i)
      {
        final String sLine = aIndex.get (i);
        if (sLine.contains ("<link"))
          aIndex.set (i, sLine + "</link>");
      }
    }
    final IMicroDocument aDoc = MicroReader.readMicroXML (StringHelper.getImploded ('\n', aIndex));
    MicroWalker.walkNode (aDoc, new DefaultHierarchyWalkerCallback <IMicroNode> ()
    {
      @Override
      public void onItemBeforeChildren (final IMicroNode aItem)
      {
        if (aItem.isElement ())
        {
          final IMicroElement e = (IMicroElement) aItem;
          if (e.getTagName ().equals ("a"))
          {
            final String sHref = e.getAttributeValue ("href");
            if (sHref.endsWith (".xml"))
              aCSSFilenames.add (sHref.replace (".xml", ".css"));
          }
        }
      }
    });

    System.out.println ("Fetching a total of " + aCSSFilenames.size () + " files");
    int i = 0;
    for (final String sCSSFilename : aCSSFilenames)
    {
      System.out.println ("  " + (++i) + ".: " + sCSSFilename);
      final String sContent = StreamUtils.getAllBytesAsString (new URLResource (sURL + sCSSFilename),
                                                               CCharset.CHARSET_UTF_8_OBJ);
      SimpleFileIO.writeFile (new File (sDestDir, sCSSFilename), sContent, CCharset.CHARSET_UTF_8_OBJ);
    }
  }
}
