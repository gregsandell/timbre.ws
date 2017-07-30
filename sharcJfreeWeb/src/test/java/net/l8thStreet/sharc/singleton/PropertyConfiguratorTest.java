package net.l8thStreet.sharc.singleton;

import java.util.List;
import java.util.Map;
import java.io.File;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.jdom.Document;
import org.jdom.Element;
import net.l8thStreet.sharc.singleton.SharcXmlSingleton;
import net.l8thStreet.sharc.singleton.PropertyConfigurator;
import net.l8thStreet.sharc.exceptions.SharcHttpQueryException;
import net.l8thStreet.sharc.exceptions.SharcException;
import net.street18.util.JdomUtils;


/**
 * Created by IntelliJ IDEA. User: gsandell Date: Apr 20, 2009 Time: 5:21:11 PM To change this
 * template use File | Settings | File Templates.
 */
public class PropertyConfiguratorTest
{
    PropertyConfigurator p;
    @Before
    public void setup()

    {
        p = PropertyConfigurator.getInstance();
    }

    @Test
    public void someTests()  {
        String path = p.getSharcRootPath();
        assertTrue("Sharc root path is empty", path != null && path.length() > 0);
    }
}