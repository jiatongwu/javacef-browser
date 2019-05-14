package cn.xvkang;

import java.awt.Component;
import java.awt.Frame;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.cef.browser.CefBrowser;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.panda_lang.pandomium.Pandomium;
import org.panda_lang.pandomium.settings.PandomiumSettings;
import org.panda_lang.pandomium.settings.categories.CommandLineSettings;
import org.panda_lang.pandomium.wrapper.PandomiumBrowser;
import org.panda_lang.pandomium.wrapper.PandomiumClient;
import org.slf4j.Logger;

public class BrowserSwtChromeTest {
	public static void main(String[] args) {
		  Display display = new Display (); 
		     final Shell shell = new Shell (display); 
		     FillLayout layout = new FillLayout(); 
		     shell.setLayout(layout); 
		     Composite composite = new Composite(shell, SWT.EMBEDDED);
		     
		     PandomiumSettings defaultSettings = PandomiumSettings.getDefaultSettings();
		     CommandLineSettings commandLine = defaultSettings.getCommandLine();
		     
		     Pandomium pandomium = new Pandomium(defaultSettings);
		     pandomium.initialize();
		     String chromiumVersion = pandomium.getChromiumVersion();
		     System.out.println(chromiumVersion);
		     String cefVersion = pandomium.getCefVersion();
		     System.out.println(cefVersion);
		     Logger logger = pandomium.getLogger();		     
		     
		     
		     PandomiumClient client = pandomium.createClient();		     
		     String 		    url= getUrlFromProperties();
		     
		     PandomiumBrowser browser=null;
		     if(StringUtils.isBlank(url)) {
		    	  browser = client.loadURL("http://www.qq.com");	 
		     }else {
		    	  browser = client.loadURL(url);	 
		     }		     

		     CefBrowser cefBrowser = browser.getCefBrowser();		     
		     cefBrowser.getDevTools();		     
		     
		     Component awtComponent = browser.toAWTComponent();
		     
		     Frame frame=SWT_AWT.new_Frame(composite);
		     frame.add(awtComponent);
		     shell.open (); 
		     while (!shell.isDisposed ()) { 
		        if (!display.readAndDispatch ()) display.sleep (); 
		     } 
		    display.dispose ();
	}

	private static String getUrlFromProperties() {
		String url=null;
		InputStream inputStream = BrowserSwtChromeTest.class
					.getClassLoader().getResourceAsStream("website.properties");
		 Properties p=new Properties();
		 try {
			p.load(inputStream);
			url=(String)p.get("url");
		} catch (IOException e) {
			e.printStackTrace();
		}
		 return url;
	}

}
