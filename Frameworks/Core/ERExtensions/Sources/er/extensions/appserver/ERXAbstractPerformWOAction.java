package er.extensions.appserver;

import org.apache.log4j.Logger;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;

/**
 * An abstract class that provides convenience methods that are available in WOComponent and WODirectAction. This
 * can be sub-classed for component or direct action delegates that want the convenience of having these commonly used methods
 * available.
 * 
 * This class also provides nice logging to stop your head hurting when trying to figure out which delegate
 * is performing an action.
 *
 * @author kieran
 */
public abstract class ERXAbstractPerformWOAction implements IERXPerformWOAction {
    // Used for logging only
    private String pageNameThatCreated = "Unknown";

    private static final Logger log = Logger.getLogger(ERXAbstractPerformWOAction.class);

    public ERXAbstractPerformWOAction() {
        if (log.isDebugEnabled()) {
            WOContext context = ERXWOContext.currentContext();
            pageNameThatCreated = (context == null ? "Unknown" : context.page().name());
            log.info("Controller named '" + getClass().getName() + "' just instantiated in page named '" + pageNameThatCreated + "'");
        }
    }

    public <T extends WOComponent> T pageWithName(Class<T> componentClass) {
        if (log.isDebugEnabled())
            log.debug("Controller named '" + getClass().getName()
                            + "' which was originally created on " + pageNameThatCreated
                            + "' is creating pageWithName '" + componentClass.getName()
                            + "' while performing action in page '"
                            + ERXWOContext.currentContext() == null ? "Unknown" : ERXWOContext.currentContext().page().name()
                                            +"'");
        return ERXApplication.erxApplication().pageWithName(componentClass);
    }

    public WOContext context() {
        return ERXWOContext.currentContext();
    }

    public abstract WOActionResults performAction();
}
