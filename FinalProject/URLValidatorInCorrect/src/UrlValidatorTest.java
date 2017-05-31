/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import junit.framework.TestCase;





/**
 * Performs Validation Test for url validations.
 *
 * @version $Revision: 1128446 $ $Date: 2011-05-27 13:29:27 -0700 (Fri, 27 May 2011) $
 */
public class UrlValidatorTest extends TestCase {

   private boolean printStatus = false;
   private boolean printIndex = false;//print index that indicates current scheme,host,port,path, query test were using.

   public UrlValidatorTest(String testName) {
      super(testName);
   }



   public void testManualTest()
   {
	   UrlValidator urlVal = new UrlValidator(null, null, UrlValidator.ALLOW_ALL_SCHEMES);
	   System.out.println(urlVal.isValid("http://www.amazon.com"));


   }


   public void testGoodURLsPartition()
   {
	   // create our URL validator
	   UrlValidator urlVal = new UrlValidator(null, null, UrlValidator.ALLOW_ALL_SCHEMES);

	   System.out.println("\nTesting known GOOD URLs:\n");

	   // scheme, host
	   System.out.println("Expect true for URL with scheme and host - "
			   + urlVal.isValid("http://www.example.com"));

	   // scheme, host, fragment
	   System.out.println("Expect true for URL with scheme, host, and fragment - " +
			   urlVal.isValid("https://subdomain.example.com/#fragment"));

	   // scheme, host, port
	   System.out.println("Expect true for URL with scheme, host, and port - " +
			   urlVal.isValid("ftp://www.somesite.com:150"));

	   // scheme, host, port, fragment
	   System.out.println("Expect true for URL with scheme, host, port, and fragment - " +
			   urlVal.isValid("http://www.example.com:55/#fragment/parts"));

	   // scheme, host, port, path
	   System.out.println("Expect true for URL with scheme, host, port, and path - " +
			   urlVal.isValid("https://www.google.co.uk:80/path/to/something.php"));

	   // scheme, host, port, path, fragment
	   System.out.println("Expect true for URL with scheme, host, port, path, and fragment - " +
			   urlVal.isValid("http://www.amazon.com:100/here/is/a/page.aspx#/this/is/a/fragment"));

	   // scheme, host, port, path, query
	   System.out.println("Expect true for URL with scheme, host, port, path, and query - " +
			   urlVal.isValid("https://cars.example.com:55/search/for/stuff?type=awesome&wheels=3"));

	   // scheme, host, port, path, query, fragment
	   System.out.println("Expect true for URL with scheme, host, port, path, query, and fragment - " +
			   urlVal.isValid("http://bread.plate.ca:6459/here/is/some/cheese/bread?would=you&like=some#yes/please"));

	   // scheme, host, port, query
	   System.out.println("Expect true for URL with scheme, host, port, and query - " +
			   urlVal.isValid("about://tell.me.com:169?what=should&this=be"));

	   // scheme, host, port, query, fragment
	   System.out.println("Expect true for url with scheme, host, port, query, and fragment - " +
			   urlVal.isValid("http://www.dogbone.com:555/?give=me&a=bone#PLEASE"));

	   // scheme, host, path
	   System.out.println("Expect true for URL with scheme, host, and path - " +
			   urlVal.isValid("ftp://sub.domain.com/path/to/file.php"));

	   // scheme, host, path, fragment
	   System.out.println("Expect true for URL with scheme, host, path, and fragment - " +
			   urlVal.isValid("http://eat.chicken.com/blargh/pop.aspx#coolThing"));

	   // scheme, host, query
	   System.out.println("Expect true for URL with scheme, host, and query - " +
			   urlVal.isValid("https://www.example.com?can=I&has=a&passing=test"));

	   // scheme, host, query, fragment
	   System.out.println("Expect true for URL with scheme, host, query, and fragment - " +
			   urlVal.isValid("https://eat.chicken.com?is=this&a=query#woah/its/a/query"));

	   // scheme, host, path, query
	   System.out.println("Expect true for URL with scheme, host, path, and query - " +
			   urlVal.isValid("http://www.google.co.uk/take/me/to/your/leader.aspx?foo=bar"));

	   // scheme, host, path, query, fragment
	   System.out.println("Expect true for URL with scheme, host, path, query, and fragment - " +
			   urlVal.isValid("https://bear.hug.co.uk/lets/go/dance.php?can=i&has=pears#no/not/ever"));
   }

   public void testBadURLsPartition()
   {
	   // create our URL validator
	   UrlValidator urlVal = new UrlValidator(null, null, UrlValidator.ALLOW_ALL_SCHEMES);

	   System.out.println("\nTesting known BAD URLs\n");

	   // scheme, host

	   System.out.println("Expect false for URL with  *scheme and host - "
			   + urlVal.isValid("totallyBogus://www.example.com"));

	   System.out.println("Expect false for URL with scheme and *host - "
			   + urlVal.isValid("http://totallyBogus"));

	   // scheme, host, fragment

	   System.out.println("Expect false for URL with *scheme, host, and fragment - " +
			   urlVal.isValid("snorg://subdomain.example.com/#fragment"));

	   System.out.println("Expect false for URL with scheme, *host, and fragment - " +
			   urlVal.isValid("https://snorg/#fragment"));

	   System.out.println("Expect false for URL with scheme, host, and *fragment - " +
			   urlVal.isValid("https://subdomain.example.com/#s n o r g"));

	   // scheme, host, port

	   System.out.println("Expect false for URL with *scheme, host, and port - " +
			   urlVal.isValid(":-)://www.somesite.com:150"));

	   System.out.println("Expect false for URL with scheme, *host, and port - " +
			   urlVal.isValid("ftp://:-):150"));

	   System.out.println("Expect false for URL with scheme, host, and *port - " +
			   urlVal.isValid("ftp://www.somesite.com:thisIsntANumber!"));

	   // scheme, host, port, fragment

	   System.out.println("Expect false for URL with *scheme, host, port, and fragment - " +
			   urlVal.isValid("derp://www.example.com:55/#fragment/parts"));

	   System.out.println("Expect false for URL with scheme, *host, port, and fragment - " +
			   urlVal.isValid("http://derpa:55/#fragment/parts"));

	   System.out.println("Expect false for URL with scheme, host, *port, and fragment - " +
			   urlVal.isValid("http://www.example.com:dorp/#fragment/parts"));

	   System.out.println("Expect false for URL with scheme, host, port, and *fragment - " +
			   urlVal.isValid("http://www.example.com:55/#dippy-/!@#///\""));

	   // scheme, host, port, path

	   System.out.println("Expect false for URL with *scheme, host, port, and path - " +
			   urlVal.isValid("this has spaces://www.google.co.uk:80/path/to/something.php"));

	   System.out.println("Expect false for URL with scheme, *host, port, and path - " +
			   urlVal.isValid("https://so does this:80/path/to/something.php"));

	   System.out.println("Expect false for URL with scheme, host, *port, and path - " +
			   urlVal.isValid("https://www.google.co.uk:80A22@/path/to/something.php"));

	   System.out.println("Expect false for URL with scheme, host, port, and *path - " +
			   urlVal.isValid("https://www.google.co.uk:80/path\\to\\something\\//\\//"));

	   // scheme, host, port, path, fragment

	   System.out.println("Expect false for URL with *scheme, host, port, path, and fragment - " +
			   urlVal.isValid("O(log n)://www.amazon.com:100/here/is/a/page.aspx#/this/is/a/fragment"));

	   System.out.println("Expect false for URL with scheme, *host, port, path, and fragment - " +
			   urlVal.isValid("http://BASSHEAD:100/here/is/a/page.aspx#/this/is/a/fragment"));

	   System.out.println("Expect false for URL with scheme, host, *port, path, and fragment - " +
			   urlVal.isValid("http://www.amazon.com:11D12/here/is/a/page.aspx#/this/is/a/fragment"));

	   System.out.println("Expect false for URL with scheme, host, port, *path, and fragment - " +
			   urlVal.isValid("http://www.amazon.com:100smiggy smoggity schmoo#/this/is/a/fragment"));

	   System.out.println("Expect false for URL with scheme, host, port, path, and *fragment - " +
			   urlVal.isValid("http://www.amazon.com:100/here/is/a/page.aspx#¯\\_(ツ)_/¯"));

	   // scheme, host, port, path, query

	   System.out.println("Expect false for URL with *scheme, host, port, path, and query - " +
			   urlVal.isValid("¯\\_(ツ)_/¯://cars.example.com:55/search/for/stuff?type=awesome&wheels=3"));

	   System.out.println("Expect false for URL with scheme, *host, port, path, and query - " +
			   urlVal.isValid("https://¯\\_(ツ)_/¯:55/search/for/stuff?type=awesome&wheels=3"));

	   System.out.println("Expect false for URL with scheme, host, *port, path, and query - " +
			   urlVal.isValid("https://cars.example.com:¯\\_(ツ)_/¯/search/for/stuff?type=awesome&wheels=3"));

	   System.out.println("Expect false for URL with scheme, host, port, *path, and query - " +
			   urlVal.isValid("https://cars.example.com:55¯\\_(ツ)_/¯?type=awesome&wheels=3"));

	   System.out.println("Expect false for URL with scheme, host, port, path, and *query - " +
			   urlVal.isValid("https://cars.example.com:55/search/for/stuff?¯\\_(ツ)_/¯"));

	   // scheme, host, port, path, query, fragment

	   System.out.println("Expect false for URL with *scheme, host, port, path, query, and fragment - " +
			   urlVal.isValid("12344567890-=://bread.plate.ca:6459/here/is/some/cheese/bread?would=you&like=some#yes/please"));

	   System.out.println("Expect false for URL with scheme, *host, port, path, query, and fragment - " +
			   urlVal.isValid("http://123456789/*-:6459/here/is/some/cheese/bread?would=you&like=some#yes/please"));

	   System.out.println("Expect false for URL with scheme, host, *port, path, query, and fragment - " +
			   urlVal.isValid("http://bread.plate.ca:123456789/*-/here/is/some/cheese/bread?would=you&like=some#yes/please"));

	   System.out.println("Expect false for URL with scheme, host, port, *path, query, and fragment - " +
			   urlVal.isValid("http://bread.plate.ca:6459/123456789/*-?would=you&like=some#yes/please"));

	   System.out.println("Expect false for URL with scheme, host, port, path, *query, and fragment - " +
			   urlVal.isValid("http://bread.plate.ca:6459/here/is/some/cheese/bread?123456789/*-#yes/please"));

	   System.out.println("Expect false for URL with scheme, host, port, path, query, and *fragment - " +
			   urlVal.isValid("http://bread.plate.ca:6459/here/is/some/cheese/bread?would=you&like=some#123456789/*-"));

	   // scheme, host, port, query

	   System.out.println("Expect false for URL with *scheme, host, port, and query - " +
			   urlVal.isValid("vaticide://tell.me.com:169?what=should&this=be"));

	   System.out.println("Expect false for URL with scheme, *host, port, and query - " +
			   urlVal.isValid("about:// W T F is that?:169?what=should&this=be"));

	   System.out.println("Expect false for URL with scheme, host, *port, and query - " +
			   urlVal.isValid("about://tell.me.com:one hundered sixty nine?what=should&this=be"));

	   System.out.println("Expect false for URL with scheme, host, port, and *query - " +
			   urlVal.isValid("about://tell.me.com:169?vaticide"));

	   // scheme, host, port, query, fragment

	   System.out.println("Expect false for url with *scheme, host, port, query, and fragment - " +
			   urlVal.isValid("SpIny://www.dogbone.com:555/?give=me&a=bone#PLEASE"));

	   System.out.println("Expect false for url with scheme, *host, port, query, and fragment - " +
			   urlVal.isValid("http://!!()!!:555/?give=me&a=bone$PLEASE"));

	   System.out.println("Expect false for url with scheme, host, *port, query, and fragment - " +
			   urlVal.isValid("http://www.dogbone.com:    /?give=me&a=bone#PLEASE"));

	   System.out.println("Expect false for url with scheme, host, port, *query, and fragment - " +
			   urlVal.isValid("http://www.dogbone.com:555/??=?#PLEASE"));

	   System.out.println("Expect false for url with scheme, host, port, query, and *fragment - " +
			   urlVal.isValid("http://www.dogbone.com:555/?give=me&a=bone#__+_8008"));

	   // scheme, host, path

	   System.out.println("Expect false for URL with *scheme, host, and path - " +
			   urlVal.isValid("I'm running out of stuff://sub.domain.com/path/to/file.php"));

	   System.out.println("Expect false for URL with scheme, *host, and path - " +
			   urlVal.isValid("ftp://like for reals/path/to/file.php"));

	   System.out.println("Expect false for URL with scheme, host, and *path - " +
			   urlVal.isValid("ftp://sub.domain.com\\what\\to\\do.php"));

	   // scheme, host, path, fragment

	   System.out.println("Expect false for URL with *scheme, host, path, and fragment - " +
			   urlVal.isValid("floobity://eat.chicken.com/blargh/pop.aspx#coolThing"));

	   System.out.println("Expect false for URL with scheme, *host, path, and fragment - " +
			   urlVal.isValid("http://do.pa.de.d000!@#/blargh/pop.aspx#coolThing"));

	   System.out.println("Expect false for URL with scheme, host, *path, and fragment - " +
			   urlVal.isValid("http://eat.chicken.comAYE BEE CEE#coolThing"));

	   System.out.println("Expect false for URL with scheme, host, path, and *fragment - " +
			   urlVal.isValid("http://eat.chicken.com/blargh/pop.aspx#DUKKA DEEKA DEE"));


	   // scheme, host, query

	   System.out.println("Expect false for URL with *scheme, host, and query - " +
			   urlVal.isValid("get a hat://www.example.com?can=I&has=a&passing=test"));

	   System.out.println("Expect false for URL with scheme, *host, and query - " +
			   urlVal.isValid("https://wwwwwwwwwwwwwwwwwwwwwwwww.com?can=I&has=a&passing=test"));

	   System.out.println("Expect false for URL with scheme, host, and *query - " +
			   urlVal.isValid("https://www.example.com?can=yes=no=totally"));

	   // scheme, host, query, fragment

	   System.out.println("Expect false for URL with *scheme, host, query, and fragment - " +
			   urlVal.isValid("ht tps://eat . chicken . com?is=this&a=query#woah/its/a/query"));

	   System.out.println("Expect false for URL with scheme, *host, query, and fragment - " +
			   urlVal.isValid("https://eat.chicken.com ? is=this&a=query#woah/its/a/query"));

	   System.out.println("Expect false for URL with scheme, host, *query, and fragment - " +
			   urlVal.isValid("https://eat.chicken.com?is = this & a = query #woah/its/a/query"));

	   System.out.println("Expect false for URL with scheme, host, query, and *fragment - " +
			   urlVal.isValid("https://eat.chicken.com?is=this&a=query # woah / its / a / query"));

	   // scheme, host, path, query

	   System.out.println("Expect false for URL with *scheme, host, path, and query - " +
			   urlVal.isValid("1one2two://www.google.co.uk/take/me/to/your/leader.aspx?foo=bar"));

	   System.out.println("Expect false for URL with scheme, *host, path, and query - " +
			   urlVal.isValid("http://doubleyoudoubleyoudoubleyou.google.co.uk/take/me/to/your/leader.aspx?foo=bar"));

	   System.out.println("Expect false for URL with scheme, host, *path, and query - " +
			   urlVal.isValid("http://www.google.co.uk/please///////woah.aspx?foo=bar"));

	   System.out.println("Expect false for URL with scheme, host, path, and *query - " +
			   urlVal.isValid("http://www.google.co.uk/take/me/to/your/leader.aspx?foo="));

	   // scheme, host, path, query, fragment

	   System.out.println("Expect false for URL with scheme, host, path, query, and fragment - " +
			   urlVal.isValid("super bowl woah!://bear.hug.co.uk/lets/go/dance.php?can=i&has=pears#no/not/ever"));

	   System.out.println("Expect false for URL with scheme, host, path, query, and fragment - " +
			   urlVal.isValid("https://\"here's something\"/lets/go/dance.php?can=i&has=pears#no/not/ever"));

	   System.out.println("Expect false for URL with scheme, host, path, query, and fragment - " +
			   urlVal.isValid("https://bear.hug.co.uk/'this isn't valid'?can=i&has=pears#no/not/ever"));

	   System.out.println("Expect false for URL with scheme, host, path, query, and fragment - " +
			   urlVal.isValid("https://bear.hug.co.uk/lets/go/dance.php?&this&that&those#no/not/ever"));

	   System.out.println("Expect false for URL with scheme, host, path, query, and fragment - " +
			   urlVal.isValid("https://bear.hug.co.uk/lets/go/dance.php?can=i&has=pears#########"));

	   // URL MISSING SCHEME:


	   System.out.println("Expect false for URL missing scheme - " +
			   urlVal.isValid("www.google.com"));

	   // URL MISING HOST:

	   System.out.println("Expect false for URL missing host - " +
			   urlVal.isValid("http://"));

	   // MISSING ALL PARTS:

	   System.out.println("Expect false for URL missing all parts - " +
			   urlVal.isValid("123456789"));

	   System.out.println("Expect false for URL missing all parts - " +
			   urlVal.isValid("This is just a sentence."));
   }


   public void testIsValid()
   {
	   for(int i = 0;i<10000;i++)
	   {

	   }
   }

   public void testAnyOtherUnitTest()
   {

   }
   /**
    * Create set of tests by taking the testUrlXXX arrays and
    * running through all possible permutations of their combinations.
    *
    * @param testObjects Used to create a url.
    */


}
