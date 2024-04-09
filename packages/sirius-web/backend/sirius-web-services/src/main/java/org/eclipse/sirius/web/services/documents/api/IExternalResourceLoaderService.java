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
package org.eclipse.sirius.web.services.documents.api;

import java.io.InputStream;
import java.util.Optional;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;

/**
 * Use to to create and load resource when uploading a document into Sirius Web.
 *
 * @author arichard
 */
public interface IExternalResourceLoaderService {

    boolean canHandle(InputStream inputStream, URI resourceURI, ResourceSet resourceSet);

    Optional<Resource> getResource(InputStream inputStream, URI resourceURI, ResourceSet resourceSet);

}
