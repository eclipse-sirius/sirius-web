/*******************************************************************************
 * Copyright (c) 2024 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.web.application.document.services;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.sirius.components.emf.utils.EMFResourceUtils;
import org.eclipse.sirius.web.application.document.services.api.IExternalResourceLoaderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Use to to create and load XMI resource when uploading a document into Sirius Web.
 *
 * @author arichard
 */
@Service
public class XMIExternalResourceLoaderService implements IExternalResourceLoaderService {

    private final Logger logger = LoggerFactory.getLogger(XMIExternalResourceLoaderService.class);

    @Override
    public boolean canHandle(InputStream inputStream, URI resourceURI, ResourceSet resourceSet) {
        boolean canHandle = false;
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        bufferedInputStream.mark(Integer.MAX_VALUE);
        try (var reader = new BufferedReader(new InputStreamReader(bufferedInputStream, StandardCharsets.UTF_8))) {
            String line = reader.readLine();
            if (line != null && line.startsWith("<?xml")) {
                canHandle = true;
            }
        } catch (IOException exception) {
            this.logger.warn(exception.getMessage(), exception);
        }
        return canHandle;
    }

    @Override
    public Optional<Resource> getResource(InputStream inputStream, URI resourceURI, ResourceSet resourceSet) {
        Resource resource = null;
        try {
            var xmiResource = new XMIResourceImpl(resourceURI);
            resourceSet.getResources().add(xmiResource);
            xmiResource.load(inputStream, new EMFResourceUtils().getXMILoadOptions());

            resource = xmiResource;
        } catch (IOException exception) {
            this.logger.warn(exception.getMessage(), exception);
        }
        return Optional.ofNullable(resource);
    }
}
