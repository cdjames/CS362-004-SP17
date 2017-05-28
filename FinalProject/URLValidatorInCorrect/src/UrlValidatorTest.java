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
   
   UrlFragment[] scheme = {
 		  new UrlFragment(true,"https://"),
 		  new UrlFragment(false,"htp://")
   };
   UrlFragment[] host = {
		  new UrlFragment(true,"amazon.com"),
		  new UrlFragment(false,"amazon.")
   };
   UrlFragment[] port = {
		  new UrlFragment(true,":800"),
		  new UrlFragment(false,";800"),
		  new UrlFragment(true,"")
   };
   UrlFragment[] query = {
		  new UrlFragment(true,"?this=that"),
		  new UrlFragment(false,"?thisthat"),
		  new UrlFragment(true,"")
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
	   String[] validSchemes = {"https://","http://"};
	   
	   UrlValidator urlVal = new UrlValidator(validSchemes);

	   for(int i = 0; i < scheme.length; i++) {
		   
		   for(int j = 0; j < host.length; j++) {
			   
			   for(int k = 0; k < port.length; k++) {
				   
				   for(int l = 0; l < path.length; l++) {
					   
					   for(int m = 0; m < query.length; m++) {
						   
						   for(int n = 0; n < fragment.length; n++) {
							   url.delete(0, url.length()); // clear the buffer
							   url.append(scheme[i].urlFragment).append(host[j].urlFragment).append(port[k].urlFragment)
							   .append(path[l].urlFragment).append(query[m].urlFragment).append(fragment[n].urlFragment);
							   
							   expected = (scheme[i].valid && host[j].valid && port[k].valid && path[l].valid && query[m].valid && fragment[n].valid); 
							   
							   boolean actual = urlVal.isValid(url.toString());
							   
//							   System.out.println(urlVal.isValid(url.toString()) + " - " + url.toString());
//							   System.out.println(url.toString());
							   
							   if(actual != expected) {
								   System.out.println("Actual: " + actual + " - " + url.toString() + " - Expected: " + expected);
							   }
						   }
					   }
				   }
			   }
		   }
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
