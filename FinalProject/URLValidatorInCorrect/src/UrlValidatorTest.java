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
   public void testYourFirstPartition()
   {
	   
   }
   
   /************************************************************************************
    * Partition #1: BAD URLs
    * Description:
    ************************************************************************************/
   public void testYourSecondPartition()
   {
	   
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
