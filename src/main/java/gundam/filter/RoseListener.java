package gundam.filter;


import net.paoding.rose.scanning.LoadScope;
import net.paoding.rose.scanning.context.RoseWebAppContext;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Enumeration;



public class RoseListener implements ServletContextListener {

    protected static Logger logger = LoggerFactory.getLogger(RoseListener.class);

    private static final String ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE;

    private String contextConfigLocation;

    private LoadScope load = new LoadScope("", "controllers");

    @Override
    public void contextInitialized(ServletContextEvent event) {

        ApplicationContext oldRootContext = (ApplicationContext)event.getServletContext().getAttribute(ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        if (oldRootContext != null) {
            if (oldRootContext.getClass() != RoseWebAppContext.class) {
                throw new IllegalStateException("Cannot initialize context because there is already a root application context present - check whether you have multiple ContextLoader* definitions in your web.xml!");
            } else {
                event.getServletContext().setAttribute(ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE,(RoseWebAppContext)oldRootContext);
            }
        } else {
            RoseWebAppContext rootContext = new RoseWebAppContext(event.getServletContext(), this.load, false);
            String contextConfigLocation = this.contextConfigLocation;
            if (StringUtils.isBlank(contextConfigLocation)) {
                String webxmlContextConfigLocation = event.getServletContext().getInitParameter("contextConfigLocation");
                if (StringUtils.isBlank(webxmlContextConfigLocation)) {
                    contextConfigLocation = "/WEB-INF/applicationContext*.xml";
                } else {
                    contextConfigLocation = webxmlContextConfigLocation;
                }
            }

            rootContext.setConfigLocation(contextConfigLocation);
            rootContext.setId("rose.root");
            rootContext.refresh();

            event.getServletContext().setAttribute(ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, rootContext);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        event.getServletContext().removeAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        cleanupAttributes(event.getServletContext());
    }

    static {
        ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE = WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE;
    }

    static void cleanupAttributes(ServletContext sc) {
        Enumeration<String> attrNames = sc.getAttributeNames();
        while (attrNames.hasMoreElements()) {
            String attrName = attrNames.nextElement();
            if (attrName.startsWith("org.springframework.")) {
                Object attrValue = sc.getAttribute(attrName);
                if (attrValue instanceof DisposableBean) {
                    try {
                        ((DisposableBean) attrValue).destroy();
                    }
                    catch (Throwable ex) {
                        logger.error("Couldn't invoke destroy method of attribute with name '" + attrName + "'", ex);
                    }
                }
            }
        }
    }
}
