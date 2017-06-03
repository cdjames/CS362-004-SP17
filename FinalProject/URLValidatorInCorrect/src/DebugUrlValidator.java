public class DebugUrlValidator 
{
	public static void main(String[] argv)
	{
		UrlValidator urlVal = new UrlValidator(null, null, UrlValidator.ALLOW_ALL_SCHEMES);
		
		System.out.println(urlVal.isValid("ftp://bit.ly#"));
		urlVal.isValid("ftp://bit.ly#");
	}
}
