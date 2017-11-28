/*
 * #%L
 * Alfresco Repository
 * %%
 * Copyright (C) 2005 - 2017 Alfresco Software Limited
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

import java.io.File;

import org.artofsolving.jodconverter.document.DocumentFormat;

import org.alfresco.repo.content.JodConverter;
import org.alfresco.repo.content.transform.ContentTransformerWorker;
import org.alfresco.repo.content.transform.OOoContentTransformerHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.artofsolving.jodconverter.OfficeDocumentConverter;
import org.springframework.beans.factory.InitializingBean;

/**
 * Makes use of the {@link http://code.google.com/p/jodconverter/} library and an installed
 * OpenOffice application to perform OpenOffice-driven conversions.
 * 
 * @author Neil McErlean
 */
public class JodContentTransformer extends OOoContentTransformerHelper implements ContentTransformerWorker, InitializingBean
{
    private static Log logger = LogFactory.getLog(JodContentTransformer.class);

    private JodConverter jodconverter;

    public void setJodConverter(JodConverter jodc)
    {
        this.jodconverter = jodc;
    }

    @Override
    protected Log getLogger()
    {
        return logger;
    }
    
    @Override
    protected String getTempFilePrefix()
    {
        return "JodContentTransformer";
    }
    
    @Override
    public boolean isAvailable()
    {
    	return jodconverter.isAvailable();
    }

    @Override
    protected void convert(File tempFromFile, DocumentFormat sourceFormat, File tempToFile,
            DocumentFormat targetFormat)
    {
        OfficeDocumentConverter converter = new OfficeDocumentConverter(jodconverter.getOfficeManager());
        converter.convert(tempFromFile, tempToFile);
    }
}
