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
	   
	   // scheme, host
	   assertTrue("URL with scheme and host should pass",
			   urlVal.isValid("http://www.example.com"));
	   
	   // scheme, host, fragment
	   assertTrue("URL with scheme, host, and fragment should pass",
			   urlVal.isValid("https://subdomain.example.com/#fragment"));
	   
	   // scheme, host, port
	   assertTrue("URL with scheme, host, and port should pass",
			   urlVal.isValid("ftp://www.somesite.com:150"));
	   
	   // scheme, host, port, fragment
	   assertTrue("URL with scheme, host, port, and fragment should pass",
			   urlVal.isValid("http://www.example.com:55/#fragment/parts"));
	   
	   // scheme, host, port, path
	   assertTrue("URL with scheme, host, port, and path should pass",
			   urlVal.isValid("https://www.google.co.uk:80/path/to/something.php"));
	   
	   // scheme, host, port, path, fragment
	   assertTrue("URL with scheme, host, port, path, and fragment should pass",
			   urlVal.isValid("http://www.amazon.com:100/here/is/a/page.aspx#/this/is/a/fragment"));
	   
	   // scheme, host, port, path, query
	   assertTrue("URL with scheme, host, port, path, and query should pass",
			   urlVal.isValid("https://cars.example.com:55/search/for/stuff?type=awesome&wheels=3"));
	   
	   // scheme, host, port, path, query, fragment
	   assertTrue("URL with scheme, host, port, path, query, and fragment should pass",
			   urlVal.isValid("http://bread.plate.ca:6459/here/is/some/cheese/bread?would=you&like=some#yes/please"));
	   
	   // scheme, host, port, query
	   assertTrue("URL with scheme, host, port, and query should pass",
			   urlVal.isValid("about://tell.me.com:169?what=should&this=be"));
	   
	   // scheme, host, port, query, fragment
	   assertTrue("url with scheme, host, port, query, and fragment should pass",
			   urlVal.isValid("http://www.dogbone.com:555/?give=me&a=bone$PLEASE"));
	   
	   // scheme, host, path
	   assertTrue("URL with scheme, host, and path should pass", urlVal.isValid("ftp://sub.domain.com/path/to/file.php"));
	   
	   // scheme, host, path, fragment
	   assertTrue("URL with scheme, host, path, and fragment should pass",
			   urlVal.isValid("http://eat.chicken.com/blargh/pop.aspx#coolThing"));
	   
	   // scheme, host, query
	   assertTrue("URL with scheme, host, and query shuold pass",
			   urlVal.isValid("https://www.example.com?can=I&has=a&passing=test"));
	   
	   // scheme, host, query, fragment
	   assertTrue("URL with scheme, host, query, and fragment should pass",
			   urlVal.isValid("https://eat.chicken.com?is=this&a=query#woah/its/a/query"));
	   
	   // scheme, host, path, query
	   assertTrue("URL with scheme, host, path, and query should pass",
			   urlVal.isValid("http://www.google.co.uk/take/me/to/your/leader.aspx?foo=bar"));
	   
	   // scheme, host, path, query, fragment
	   assertTrue("URL with scheme, host, path, query, and fragment should pass",
			   urlVal.isValid("https://bear.hug.co.uk/lets/go/dance.php?can=i&has=pears#no/not/ever"));
   }
   
   public void testBadURLsPartition()
   {
   // INVALID PART FOR ANY OF THE FOLLOWING:
	   // create our URL validator
	   UrlValidator urlVal = new UrlValidator(null, null, UrlValidator.ALLOW_ALL_SCHEMES);
	   
	   // scheme, host
	   assertFalse("URL with invalid scheme but valid host should fail",
			   urlVal.isValid("htpa1234$$:)://www.example.com"));
	   assertFalse("URL with valid scheme and invalid host should fail",
			   urlVal.isValid("http://wwwl$%^&4897example.com"));

	   // scheme, host, fragment
	   assertFalse("URL with invalid scheme but valid host and fragment should fail",
			   urlVal.isValid("http897^s://subdomain.example.com/#fragment"));
	   assertFalse("URL with valid scheme, invalid host, and valid fragment should fail",
			   urlVal.isValid("https://subdomain.4%^. OWAHD .example.com/#fragment"));
	   assertFalse("URL with scheme, host, and invalid fragment should pass",
			   urlVal.isValid("https://subdomain.example.com/#fragment"));

	   // scheme, host, port
	   assertFalse("URL with scheme, host, and port should pass",
			   urlVal.isValid("ftp://www.somesite.com:150"));
	   assertFalse("URL with scheme, host, and port should pass",
			   urlVal.isValid("ftp://www.somesite.com:150"));
	   assertFalse("URL with scheme, host, and port should pass",
			   urlVal.isValid("ftp://www.somesite.com:150"));
	   
	   // scheme, host, port, fragment
	   assertFalse("URL with scheme, host, port, and fragment should pass",
			   urlVal.isValid("http://www.example.com:55/#fragment/parts"));
	   assertFalse("URL with scheme, host, port, and fragment should pass",
			   urlVal.isValid("http://www.example.com:55/#fragment/parts"));
	   assertFalse("URL with scheme, host, port, and fragment should pass",
			   urlVal.isValid("http://www.example.com:55/#fragment/parts"));
	   assertFalse("URL with scheme, host, port, and fragment should pass",
			   urlVal.isValid("http://www.example.com:55/#fragment/parts"));
	   
	   // scheme, host, port, path
	   assertFalse("URL with scheme, host, port, and path should pass",
			   urlVal.isValid("https://www.google.co.uk:80/path/to/something.php"));
	   assertFalse("URL with scheme, host, port, and path should pass",
			   urlVal.isValid("https://www.google.co.uk:80/path/to/something.php"));
	   assertFalse("URL with scheme, host, port, and path should pass",
			   urlVal.isValid("https://www.google.co.uk:80/path/to/something.php"));
	   assertFalse("URL with scheme, host, port, and path should pass",
			   urlVal.isValid("https://www.google.co.uk:80/path/to/something.php"));
	   
	   // scheme, host, port, path, fragment
	   assertFalse("URL with scheme, host, port, path, and fragment should pass",
			   urlVal.isValid("http://www.amazon.com:100/here/is/a/page.aspx#/this/is/a/fragment"));
	   assertFalse("URL with scheme, host, port, path, and fragment should pass",
			   urlVal.isValid("http://www.amazon.com:100/here/is/a/page.aspx#/this/is/a/fragment"));
	   assertFalse("URL with scheme, host, port, path, and fragment should pass",
			   urlVal.isValid("http://www.amazon.com:100/here/is/a/page.aspx#/this/is/a/fragment"));
	   assertFalse("URL with scheme, host, port, path, and fragment should pass",
			   urlVal.isValid("http://www.amazon.com:100/here/is/a/page.aspx#/this/is/a/fragment"));
	   assertFalse("URL with scheme, host, port, path, and fragment should pass",
			   urlVal.isValid("http://www.amazon.com:100/here/is/a/page.aspx#/this/is/a/fragment"));
	   
	   // scheme, host, port, path, query
	   assertFalse("URL with scheme, host, port, path, and query should pass",
			   urlVal.isValid("https://cars.example.com:55/search/for/stuff?type=awesome&wheels=3"));
	   assertFalse("URL with scheme, host, port, path, and query should pass",
			   urlVal.isValid("https://cars.example.com:55/search/for/stuff?type=awesome&wheels=3"));
	   assertFalse("URL with scheme, host, port, path, and query should pass",
			   urlVal.isValid("https://cars.example.com:55/search/for/stuff?type=awesome&wheels=3"));
	   assertFalse("URL with scheme, host, port, path, and query should pass",
			   urlVal.isValid("https://cars.example.com:55/search/for/stuff?type=awesome&wheels=3"));
	   assertFalse("URL with scheme, host, port, path, and query should pass",
			   urlVal.isValid("https://cars.example.com:55/search/for/stuff?type=awesome&wheels=3"));
	   
	   // scheme, host, port, path, query, fragment
	   assertFalse("URL with scheme, host, port, path, query, and fragment should pass",
			   urlVal.isValid("http://bread.plate.ca:6459/here/is/some/cheese/bread?would=you&like=some#yes/please"));
	   assertFalse("URL with scheme, host, port, path, query, and fragment should pass",
			   urlVal.isValid("http://bread.plate.ca:6459/here/is/some/cheese/bread?would=you&like=some#yes/please"));
	   assertFalse("URL with scheme, host, port, path, query, and fragment should pass",
			   urlVal.isValid("http://bread.plate.ca:6459/here/is/some/cheese/bread?would=you&like=some#yes/please"));
	   assertFalse("URL with scheme, host, port, path, query, and fragment should pass",
			   urlVal.isValid("http://bread.plate.ca:6459/here/is/some/cheese/bread?would=you&like=some#yes/please"));
	   assertFalse("URL with scheme, host, port, path, query, and fragment should pass",
			   urlVal.isValid("http://bread.plate.ca:6459/here/is/some/cheese/bread?would=you&like=some#yes/please"));
	   assertFalse("URL with scheme, host, port, path, query, and fragment should pass",
			   urlVal.isValid("http://bread.plate.ca:6459/here/is/some/cheese/bread?would=you&like=some#yes/please"));
	   
	   // scheme, host, port, query
	   assertFalse("URL with scheme, host, port, and query should pass",
			   urlVal.isValid("about://tell.me.com:169?what=should&this=be"));
	   assertFalse("URL with scheme, host, port, and query should pass",
			   urlVal.isValid("about://tell.me.com:169?what=should&this=be"));
	   assertFalse("URL with scheme, host, port, and query should pass",
			   urlVal.isValid("about://tell.me.com:169?what=should&this=be"));
	   assertFalse("URL with scheme, host, port, and query should pass",
			   urlVal.isValid("about://tell.me.com:169?what=should&this=be"));
	   
	   // scheme, host, port, query, fragment
	   assertFalse("url with scheme, host, port, query, and fragment should pass",
			   urlVal.isValid("http://www.dogbone.com:555/?give=me&a=bone$PLEASE"));
	   assertFalse("url with scheme, host, port, query, and fragment should pass",
			   urlVal.isValid("http://www.dogbone.com:555/?give=me&a=bone$PLEASE"));
	   assertFalse("url with scheme, host, port, query, and fragment should pass",
			   urlVal.isValid("http://www.dogbone.com:555/?give=me&a=bone$PLEASE"));
	   assertFalse("url with scheme, host, port, query, and fragment should pass",
			   urlVal.isValid("http://www.dogbone.com:555/?give=me&a=bone$PLEASE"));
	   assertFalse("url with scheme, host, port, query, and fragment should pass",
			   urlVal.isValid("http://www.dogbone.com:555/?give=me&a=bone$PLEASE"));
	   
	   // scheme, host, path
	   assertFalse("URL with scheme, host, and path should pass", 
			   urlVal.isValid("ftp://sub.domain.com/path/to/file.php"));
	   assertFalse("URL with scheme, host, and path should pass", 
			   urlVal.isValid("ftp://sub.domain.com/path/to/file.php"));
	   assertFalse("URL with scheme, host, and path should pass", 
			   urlVal.isValid("ftp://sub.domain.com/path/to/file.php"));
	   
	   // scheme, host, path, fragment
	   assertFalse("URL with scheme, host, path, and fragment should pass",
			   urlVal.isValid("http://eat.chicken.com/blargh/pop.aspx#coolThing"));
	   assertFalse("URL with scheme, host, path, and fragment should pass",
			   urlVal.isValid("http://eat.chicken.com/blargh/pop.aspx#coolThing"));
	   assertFalse("URL with scheme, host, path, and fragment should pass",
			   urlVal.isValid("http://eat.chicken.com/blargh/pop.aspx#coolThing"));
	   assertFalse("URL with scheme, host, path, and fragment should pass",
			   urlVal.isValid("http://eat.chicken.com/blargh/pop.aspx#coolThing"));
	   
	   // scheme, host, query
	   assertFalse("URL with scheme, host, and query shuold pass",
			   urlVal.isValid("https://www.example.com?can=I&has=a&passing=test"));
	   assertFalse("URL with scheme, host, and query shuold pass",
			   urlVal.isValid("https://www.example.com?can=I&has=a&passing=test"));
	   assertFalse("URL with scheme, host, and query shuold pass",
			   urlVal.isValid("https://www.example.com?can=I&has=a&passing=test"));
	   
	   // scheme, host, query, fragment
	   assertFalse("URL with scheme, host, query, and fragment should pass",
			   urlVal.isValid("https://eat.chicken.com?is=this&a=query#woah/its/a/query"));
	   assertFalse("URL with scheme, host, query, and fragment should pass",
			   urlVal.isValid("https://eat.chicken.com?is=this&a=query#woah/its/a/query"));
	   assertFalse("URL with scheme, host, query, and fragment should pass",
			   urlVal.isValid("https://eat.chicken.com?is=this&a=query#woah/its/a/query"));
	   assertFalse("URL with scheme, host, query, and fragment should pass",
			   urlVal.isValid("https://eat.chicken.com?is=this&a=query#woah/its/a/query"));
	   
	   // scheme, host, path, query
	   assertFalse("URL with scheme, host, path, and query should pass",
			   urlVal.isValid("http://www.google.co.uk/take/me/to/your/leader.aspx?foo=bar"));
	   assertFalse("URL with scheme, host, path, and query should pass",
			   urlVal.isValid("http://www.google.co.uk/take/me/to/your/leader.aspx?foo=bar"));
	   assertFalse("URL with scheme, host, path, and query should pass",
			   urlVal.isValid("http://www.google.co.uk/take/me/to/your/leader.aspx?foo=bar"));
	   assertFalse("URL with scheme, host, path, and query should pass",
			   urlVal.isValid("http://www.google.co.uk/take/me/to/your/leader.aspx?foo=bar"));
	   
	   // scheme, host, path, query, fragment
	   assertFalse("URL with scheme, host, path, query, and fragment should pass",
			   urlVal.isValid("https://bear.hug.co.uk/lets/go/dance.php?can=i&has=pears#no/not/ever"));
	   assertFalse("URL with scheme, host, path, query, and fragment should pass",
			   urlVal.isValid("https://bear.hug.co.uk/lets/go/dance.php?can=i&has=pears#no/not/ever"));
	   assertFalse("URL with scheme, host, path, query, and fragment should pass",
			   urlVal.isValid("https://bear.hug.co.uk/lets/go/dance.php?can=i&has=pears#no/not/ever"));
	   assertFalse("URL with scheme, host, path, query, and fragment should pass",
			   urlVal.isValid("https://bear.hug.co.uk/lets/go/dance.php?can=i&has=pears#no/not/ever"));
	   assertFalse("URL with scheme, host, path, query, and fragment should pass",
			   urlVal.isValid("https://bear.hug.co.uk/lets/go/dance.php?can=i&has=pears#no/not/ever"));
	   
   // URL MISSING SCHEME:
	   
	   
   // URL MISING HOST:
	   
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
