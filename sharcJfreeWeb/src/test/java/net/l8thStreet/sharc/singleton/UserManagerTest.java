package net.l8thStreet.sharc.singleton;

import java.util.List;
import java.util.Map;
import java.io.File;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;
import org.jdom.Document;
import org.jdom.Element;
import net.l8thStreet.sharc.singleton.*;
import net.l8thStreet.sharc.exceptions.SharcHttpQueryException;
import net.l8thStreet.sharc.exceptions.SharcException;
import net.l8thStreet.sharc.audit.User;
import net.street18.util.JdomUtils;


/**
 * Created by IntelliJ IDEA. User: gsandell Date: Apr 20, 2009 Time: 5:21:11 PM To change this
 * template use File | Settings | File Templates.
 */
public class UserManagerTest
{
    UserManager u;
    @Before
    public void setup()

    {
        u = UserManager.getInstance();
    }

    @Test
    public void someTests()  {
        assertNotNull("Failed to create UserManager", u);
        User user = u.add("newSession");
        assertNotNull("Failed to create new user with new session", user);
        User userCopy = u.get("newSession");
        assertEquals("Same user should have been retrieved", user, userCopy);
    }
}