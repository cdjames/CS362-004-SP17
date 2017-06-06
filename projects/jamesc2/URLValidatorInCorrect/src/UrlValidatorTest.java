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
   private boolean printIndex = false;//print index that indicates current scheme,host,port,path, query test we're using.
   
   UrlFragment[] scheme = {
 		  new UrlFragment(true,"https://"),
 		  new UrlFragment(true,"http://"),
 		  new UrlFragment(true,"ftp://"),
 		  new UrlFragment(false,"4tp://"),
 		  new UrlFragment(false,"http//"),
 		  new UrlFragment(false,"ftp:/"),
 		  new UrlFragment(false,"https:")
   };
   UrlFragment[] host = {
		  new UrlFragment(true,"amazon.com"),
		  new UrlFragment(true,"www.amazon.com"),
		  new UrlFragment(true,"www.wbae.co"),
		  new UrlFragment(true,"maps.google.com"),
		  new UrlFragment(true,"bit.ly"),
		  new UrlFragment(true,"www-e.uni-magdeburg.de"),
		  new UrlFragment(true,"127.0.0.1"),
		  new UrlFragment(false,"amazon."),
		  new UrlFragment(false,"ww.amazon.jp"),
		  new UrlFragment(false,"wwe.wwf"),
		  new UrlFragment(false,"amazon*com")
   };
   UrlFragment[] port = {
		  new UrlFragment(true,":4"),
		  new UrlFragment(true,":799"),
		  new UrlFragment(true,":9090"),
		  new UrlFragment(true,":65535"),
		  new UrlFragment(true,""),
		  // new UrlFragment(false,":65536"), // seems like this should be invalid since only 65535 ports in IPv4, but in UrlValidator.java the regex is just looking for any combination of 1 to 5 digits
		  new UrlFragment(false,":655354"),
	   	  new UrlFragment(false,":NOT#"),
		  new UrlFragment(false,";800"),
		  new UrlFragment(false,":-7000")
		  
   };
   UrlFragment[] query = {
		  new UrlFragment(true,"?this=that"),
		  new UrlFragment(true,"?resetToken=8pfZWMh9tYFt9b8%2FcpmCizLxd5xbhXqSm67PWKoOpkdqUWTlro2ET%2BRHyS6WUfPWYMfrwi4kkkc%2FJFuxrdLVJQ%3D%3D&email=@&crAssisted=true"),
		  new UrlFragment(true,""),
		  // if you look at the regex pattern in UrlValidator.java, it's just any number of characters...
		  // so, it seems like the following all should pass. Not sure what to do so I'll comment them out for now
		  new UrlFragment(true,"?thisthat"),
		  new UrlFragment(true,"/this=that"),
		  new UrlFragment(true,"?this=#"),
		  new UrlFragment(false, " {}"),
		  new UrlFragment(false, " :::")
   };
   UrlFragment[] path = {
		   new UrlFragment(true,"/index"),
		   new UrlFragment(true,"/index/"),
		   new UrlFragment(true,"/index/resource"),
		   new UrlFragment(true,"/index/resource/"),
		   new UrlFragment(false,"/index//resource"),
		   new UrlFragment(true,"/index/resource;v=1.1"),
		   new UrlFragment(false,"/.."),
		   new UrlFragment(true,"/index/../"),
		   new UrlFragment(true,""),
		   new UrlFragment(false, "/../"),
   };
   UrlFragment[] fragment = {
		   new UrlFragment(true,"#"),
		   new UrlFragment(true,"#nav"),
		   new UrlFragment(true,"#nav?this=that"),
		   new UrlFragment(true,"#nav/index"),
		   new UrlFragment(true,"##nav"),
		   new UrlFragment(true,"#%23nav"),
		   new UrlFragment(true,"#nav:index"),
		   new UrlFragment(true,"#nav%3Aindex"),
		   new UrlFragment(true,"")
   };
   StringBuffer url = new StringBuffer();
   boolean expected = true;
   
   public UrlValidatorTest(String testName) {
      super(testName);
   }

  public class UrlFragment {
	  public boolean valid;
	  public String urlFragment;
	
	  public UrlFragment(boolean v,String u) {
		  valid = v;
		  urlFragment = u;
	  }
  }
   
   /************************************************************************************
    * TEST: testManualTest
    * DESCRIPTION: A set of 30 selected URLs divided into a section of URLs that should
    * pass and a section of URLs that should fail.
    ************************************************************************************/
   public void testManualTest()
   {
	   // create the new UrlValidator
	   UrlValidator urlVal = new UrlValidator(null, null, UrlValidator.ALLOW_ALL_SCHEMES);
	   
	   // SHOULD PASS
	   System.out.println("THE FOLLOWING URLS SHOULD PASS:");
	   System.out.println("http://www.amazon.com - " + urlVal.isValid("http://www.amazon.com"));
	   System.out.println("http://10.1.1.1 - " + urlVal.isValid("http://10.1.1.1"));
	   System.out.println("http://10.1.1.254 - " + urlVal.isValid("http://10.1.1.254"));
	   System.out.println("http//www.amazon.com#fragment - " + urlVal.isValid("http://www.amazon.com#fragment"));
	   System.out.println("https://www.amazon.com/ - " + urlVal.isValid("https://www.amazon.com/"));
	   System.out.println("http://www.google.com:80 - " + urlVal.isValid("http://www.google.com:80"));
	   System.out.println("https://birthday.example.com/ - " + urlVal.isValid("https://birthday.example.com/"));
	   System.out.println("http://example.com/ - " + urlVal.isValid("http://example.com/"));
	   // BUG!
	   System.out.println("http://www.example.net/afternoon/animal.php?border=account - " + urlVal.isValid("http://www.example.net/afternoon/animal.php?border=account"));
	   System.out.println("https://blow.example.net/birds/ants - " + urlVal.isValid("https://blow.example.net/birds/ants"));
	   System.out.println("http://www.example.com/attack.aspx - " + urlVal.isValid("http://www.example.com/attack.aspx"));
	   // BUG!
	   System.out.println("http://www.example.com/?bath=behavior&baseball=airport#bear - " + urlVal.isValid("http://www.example.com/?bath=behavior&baseball=airport#bear"));
	   // BUG!
	   System.out.println("http://www.example.com/?attraction=birth - " + urlVal.isValid("http://www.example.com/?attraction=birth"));
	   // BUG!
	   System.out.println("http://video.google.co.uk:80/videoplay?docid=-7246927612831078230&hl=en#00h02m30s - " + urlVal.isValid("http://video.google.co.uk:80/videoplay?docid=-7246927612831078230&hl=en#00h02m30s"));
	   // BUG!
	   System.out.println("http://video.google.co.uk - " + urlVal.isValid("http://video.google.co.uk"));
	   System.out.println("http://foo.com/blah_blah_(wikipedia) - " + urlVal.isValid("http://foo.com/blah_blah_(wikipedia)"));
	   System.out.println("http://223.255.255.254 - " + urlVal.isValid("http://223.255.255.254"));
	   System.out.println("ttp://foo.com/blah_(wikipedia)#cite-1 - " + urlVal.isValid("ttp://foo.com/blah_(wikipedia)#cite-1"));
	   System.out.println();
	   
	   // SHOULD FAIL
	   System.out.println("THE FOLLOWING URLS SHOULD FAIL:");
	   System.out.println("http://userid:password@example.com:8080 - " + urlVal.isValid("http://userid:password@example.com:8080"));
	   System.out.println("ftp://foo.bar/baz - " + urlVal.isValid("ftp://foo.bar/baz"));
	   System.out.println("http://foo.bar/?q=Test%20URL-encoded%20stuff - " + urlVal.isValid("http://foo.bar/?q=Test%20URL-encoded%20stuff"));
	   System.out.println("http://foo.com/unicode_(✪)_in_parens - " + urlVal.isValid("http://foo.com/unicode_(✪)_in_parens"));
	   System.out.println("http:// - " + urlVal.isValid("http://"));
	   System.out.println("http://foo.bar?q=Spaces should be encoded - " + urlVal.isValid("http://foo.bar?q=Spaces should be encoded"));
	   System.out.println("http:// shouldfail.com - " + urlVal.isValid("http:// shouldfail.com"));
	   System.out.println(":// should fail - " + urlVal.isValid(":// should fail"));
	   System.out.println("http://-error-.invalid/ - " + urlVal.isValid("http://-error-.invalid/"));
	   System.out.println("http://.www.foo.bar/ - " + urlVal.isValid("http://.www.foo.bar/"));
	   System.out.println("http://localhost - " + urlVal.isValid("http://localhost"));
	   System.out.println("www.amazon.com - " + urlVal.isValid("www.amazon.com"));
   }
   
   /************************************************************************************
   * Partition #1: GOOD URLs
   * Description:
   ************************************************************************************/
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
			   + urlVal.isValid("totally Bogus://www.example.com"));

	   System.out.println("Expect false for URL with scheme and *host - "
			   + urlVal.isValid("http@qw1://totallyBogus"));

	   // scheme, host, fragment

	   System.out.println("Expect false for URL with *scheme, host, and fragment - " +
			   urlVal.isValid("snorg_s://subdomain.example.com/#fragment"));

	   System.out.println("Expect false for URL with scheme, *host, and fragment - " +
			   urlVal.isValid("7/7https://snorg/#fragment"));

	   System.out.println("Expect false for URL with scheme, host, and *fragment - " +
			   urlVal.isValid("://subdomain.example.com/#s n o r g"));

	   // scheme, host, port

	   System.out.println("Expect false for URL with *scheme, host, and port - " +
			   urlVal.isValid(":-)://www.somesite.com:150"));

	   System.out.println("Expect false for URL with scheme, *host, and port - " +
			   urlVal.isValid("ftp://:-):150"));

	   System.out.println("Expect false for URL with scheme, host, and *port - " +
			   urlVal.isValid("ftp://www.somesite.com:thisIsntANumber!"));

	   // scheme, host, port, fragment

	   System.out.println("Expect false for URL with *scheme, host, port, and fragment - " +
			   urlVal.isValid("derp::://www.example.com:55/#fragment/parts"));

	   System.out.println("Expect false for URL with scheme, *host, port, and fragment - " +
			   urlVal.isValid("http://derpa:55/#fragment/parts"));

	   System.out.println("Expect false for URL with scheme, host, *port, and fragment - " +
			   urlVal.isValid("http://www.example.com:dorp/#fragment/parts"));

	   System.out.println("Expect false for URL with scheme, host, port, and *fragment - " +
			   urlVal.isValid("http://www.example.com:55/#dippy-/!@#///\""));

	   // scheme, host, port, path

	   System.out.println("Expect false for URL with *scheme, host, port, and path - " +
			   urlVal.isValid("this has spaces://///www.google.co.uk:80/path/to/something.php"));

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
			   urlVal.isValid("http://bread.plate.ca:6459/1234 56789/*-?would=you&like=some#yes/please"));

	   System.out.println("Expect false for URL with scheme, host, port, path, *query, and fragment - " +
			   urlVal.isValid("http://bread.plate.ca:6459/here/is/some/cheese/bread?1234567 89/*-#yes/please"));

	   System.out.println("Expect false for URL with scheme, host, port, path, query, and *fragment - " +
			   urlVal.isValid("http://bread.plate.ca:6459/here/is/some/cheese/bread?would=you&like=some#123456789/ *-"));

	   // scheme, host, port, query

	   System.out.println("Expect false for URL with *scheme, host, port, and query - " +
			   urlVal.isValid("vaticide://///tell.me.com:169?what=should&this=be"));

	   System.out.println("Expect false for URL with scheme, *host, port, and query - " +
			   urlVal.isValid("about:// W T F is that?:169?what=should&this=be"));

	   System.out.println("Expect false for URL with scheme, host, *port, and query - " +
			   urlVal.isValid("about://tell.me.com:one hundered sixty nine?what=should&this=be"));

	   System.out.println("Expect false for URL with scheme, host, port, and *query - " +
			   urlVal.isValid("about://tell.me.com:169?vaticide"));

	   // scheme, host, port, query, fragment

	   System.out.println("Expect false for url with *scheme, host, port, query, and fragment - " +
			   urlVal.isValid("SpIny:_//www.dogbone.com:555/?give=me&a=bone#PLEASE"));

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
			   urlVal.isValid("floobity098:?:?:://eat.chicken.com/blargh/pop.aspx#coolThing"));

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
			   urlVal.isValid("https://w<O_o>wwwwwwwwwwwwwwwwwwwwwwww.com?can=I&has=a&passing=test"));

	   System.out.println("Expect false for URL with scheme, host, and *query - https://www.example.com?can=yes=no=totally" +
			   urlVal.isValid("https://www.example.com?can\\&=yes\\&=n\\&=totally"));

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
			   urlVal.isValid("1o ne2two://www.google.co.uk/take/me/to/your/leader.aspx?foo=bar"));

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

//	   UrlValidator urlVal = new UrlValidator(null, null, UrlValidator.ALLOW_ALL_SCHEMES);
   		// Not sure if we're supposed to test all schemes or just web-oriented ones...
	   String[] validSchemes = {"https://", "http://", "ftp://"};
	   Integer numTests = 0;
	   Integer failures = 0;
<<<<<<< HEAD
//	   UrlValidator urlVal = new UrlValidator(validSchemes);
	   UrlValidator urlVal = new UrlValidator(null, null, UrlValidator.ALLOW_ALL_SCHEMES);
	   System.out.println("***************** Begin testIsValid");
=======
	   UrlValidator urlVal = new UrlValidator(null, null, UrlValidator.ALLOW_ALL_SCHEMES);

>>>>>>> 31cb82ec9bb636332526244ff1af20113189e3a2
	   for(int i = 0; i < scheme.length; i++) {
		   
		   for(int j = 0; j < host.length; j++) {
			   
			   for(int k = 0; k < port.length; k++) {
				   
				   for(int l = 0; l < path.length; l++) {
					   
					   for(int m = 0; m < query.length; m++) {
						   
						   for(int n = 0; n < fragment.length; n++) {
							   url.delete(0, url.length()); // clear the buffer
							   url.append(scheme[i].urlFragment)
							   		.append(host[j].urlFragment)
							   		.append(port[k].urlFragment)
							   		.append(path[l].urlFragment)
							   		.append(query[m].urlFragment)
							   		.append(fragment[n].urlFragment);
							   
							   expected = (scheme[i].valid && host[j].valid && port[k].valid && path[l].valid && query[m].valid && fragment[n].valid); 
							   
							   boolean actual = urlVal.isValid(url.toString());
							   
//							   System.out.println(urlVal.isValid(url.toString()) + " - " + url.toString());
//							   System.out.println(url.toString());
							   
							   if(actual != expected) {
							   		failures++;
								   System.out.println("Actual: " + actual + " - Expected: " + expected + " - " + url.toString() );
							   }
							   numTests++;
						   }
					   }
				   }
			   }
		   }
	   }
	   if(failures > 0){
		   System.out.println("----------------- There were failures.");
		   System.out.println("----------------- " + (numTests-failures) + "/" + numTests + " passed.");
		   System.out.println("Number tests expected: " + (scheme.length*host.length*port.length*path.length*query.length*fragment.length));
		}
		else {
		   System.out.println("+++++++++++++++++ All tests passed.");

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
