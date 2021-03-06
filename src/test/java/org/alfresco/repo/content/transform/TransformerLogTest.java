/*
 * #%L
 * Alfresco Repository
 * %%
 * Copyright (C) 2005 - 2016 Alfresco Software Limited
 * %%
 * This file is part of the Alfresco software. 
 * If the software was purchased under a paid Alfresco license, the terms of 
 * the paid license agreement will prevail.  Otherwise, the software is 
 * provided under the following open source license terms:
 * 
 * Alfresco is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Alfresco is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with Alfresco. If not, see <http://www.gnu.org/licenses/>.
 * #L%
 */
package org.alfresco.repo.content.transform;

import static org.junit.Assert.assertArrayEquals;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Test class for TransformerLog.
 * 
 * @author Alan Davis
 *
 * @deprecated The transformations code is being moved out of the codebase and replaced by the new async RenditionService2 or other external libraries.
 */
@Deprecated
public class TransformerLogTest
{
    @Mock
    private TransformerDebug transformerDebug;
    
    @Mock
    private TransformerConfig transformerConfig;
    
    private TransformerLog log;

    @Before
    public void setUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);
        
        log = new TransformerLog();
        log.setTransformerDebug(transformerDebug);
        log.setTransformerConfig(transformerConfig);
    }

    static String[] stripDateStamp(String[] actual)
    {
        int beginIndex = (TransformerLogger.DATE_FORMAT.format(new Date())+' ').length();
        for (int i = actual.length-1; i >= 0; i--)
        {
            actual[i] = actual[i].substring(beginIndex);
        }
        return actual;
    }

    static void assertLogEntriesEquals(String[] expected, String[] actual)
    {
        assertArrayEquals(expected, stripDateStamp(actual));
    }

    @Test
    public void noEntriesDisabledTest()
    {
        assertArrayEquals(new String[] {"No entries are available. transformer.log.entries must be set to a number between 1 and 1000"}, log.getEntries(10));
    }
    
    @Test
    public void oneEntryTest()
    {
        when(transformerConfig.getProperty("transformer.log.entries")).thenReturn("3");
        log.debug("one");
        
        assertLogEntriesEquals(new String[] {"one"}, log.getEntries(10));
    }

    @Test
    public void fiveEntryTest()
    {
        when(transformerConfig.getProperty("transformer.log.entries")).thenReturn("3");

        log.debug("one");
        log.debug("two");
        log.debug("three");
        log.debug("four");
        log.debug("five");
        
        assertLogEntriesEquals(new String[] {"five", "four", "three"}, log.getEntries(10));
    }
}
