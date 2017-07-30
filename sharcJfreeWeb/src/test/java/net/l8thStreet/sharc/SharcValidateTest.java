package net.l8thStreet.sharc;

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
import net.l8thStreet.sharc.exceptions.*;
import net.l8thStreet.sharc.xmlObject.Instrument;
import net.l8thStreet.sharc.xmlObject.Note;
import net.street18.util.JdomUtils;


/**
 * Created by IntelliJ IDEA. User: gsandell Date: Apr 20, 2009 Time: 5:21:11 PM To change this
 * template use File | Settings | File Templates.
 */
public class SharcValidateTest
{

    @Before
    public void setup()

    {
    }

    @Test
    public void someTests()  {
        try
        {
            SharcValidate.notEmptyString("a string", "foo");
        }
        catch (SharcEmptyArgumentException e)
        {
            fail("Unexpected exception from correct call");
        }
        try
        {
            SharcValidate.notEmptyString(null, "foo");
            fail("Expected exception did not occur");
        }
        catch (SharcEmptyArgumentException e)
        {
        }
        try
        {
            SharcValidate.notNullArg("a string", "foo");
        }
        catch (SharcEmptyArgumentException e)
        {
            fail("Unexpected exception from correct call");
        }
        try
        {
            SharcValidate.notNullArg(null, "foo");
            fail("Expected exception did not occur");
        }
        catch (SharcEmptyArgumentException e)
        {
        }
        try
        {
            SharcValidate.notNullClassVar("a string", "foo");
        }
        catch (SharcEmptyArgumentException e)
        {
            fail("Unexpected exception from correct call");
        }
        try
        {
            SharcValidate.notNullClassVar(null, "foo");
            fail("Expected exception did not occur");
        }
        catch (SharcEmptyArgumentException e)
        {
        }
        try
        {
            SharcValidate.notNullLocalVar("a string", "foo");
        }
        catch (SharcEmptyArgumentException e)
        {
            fail("Unexpected exception from correct call");
        }
        try
        {
            SharcValidate.notNullLocalVar(null, "foo");
            fail("Expected exception did not occur");
        }
        catch (SharcEmptyArgumentException e)
        {
        }
    }


}