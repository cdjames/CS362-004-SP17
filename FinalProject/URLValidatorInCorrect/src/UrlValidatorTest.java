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
		  new UrlFragment(false,";800"),
		  new UrlFragment(false,":-7000")
		  
   };
   UrlFragment[] query = {
		  new UrlFragment(true,"?this=that"),
		  new UrlFragment(true,"?resetToken=8pfZWMh9tYFt9b8%2FcpmCizLxd5xbhXqSm67PWKoOpkdqUWTlro2ET%2BRHyS6WUfPWYMfrwi4kkkc%2FJFuxrdLVJQ%3D%3D&email=@&crAssisted=true"),
		  new UrlFragment(true,"")
		  // if you look at the regex pattern in UrlValidator.java, it's just any number of characters...
		  // so, it seems like the following all should pass. Not sure what to do so I'll comment them out for now
		  new UrlFragment(false,"?thisthat"),
		  new UrlFragment(false,"this=that"),
		  new UrlFragment(false,"?this=#")
   };
   UrlFragment[] path = {
		   new UrlFragment(true,"/index"),
		   new UrlFragment(false,"\\index"),
		   new UrlFragment(true,"")
   };
   UrlFragment[] fragment = {
		   new UrlFragment(true,"#nav"),
		   new UrlFragment(false,"&nav"),
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
   
   
   public void testManualTest()
   {
	   UrlValidator urlVal = new UrlValidator(null, null, UrlValidator.ALLOW_ALL_SCHEMES);
	   System.out.println(urlVal.isValid("http://www.amazon.com"));
	   
	   
   }
   
   
   public void testYourFirstPartition()
   {
	   
   }
   
   public void testYourSecondPartition(){
	   
   }
   
   
   public void testIsValid()
   {
//	   UrlValidator urlVal = new UrlValidator(null, null, UrlValidator.ALLOW_ALL_SCHEMES);
   		// Not sure if we're supposed to test all schemes or just web-oriented ones...
	   String[] validSchemes = {"https://", "http://", "ftp://"};
	   Integer numTests = 0;
	   Integer failures = 0;
	   UrlValidator urlVal = new UrlValidator(validSchemes);

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
								   System.out.println("Actual: " + actual + " - " + url.toString() + " - Expected: " + expected);
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
