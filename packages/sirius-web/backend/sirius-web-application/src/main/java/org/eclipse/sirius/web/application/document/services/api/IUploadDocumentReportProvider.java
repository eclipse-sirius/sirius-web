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
package org.eclipse.sirius.web.application.document.services.api;

import org.eclipse.emf.ecore.resource.Resource;

/**
 * Use to provide report when uploading a document into Sirius Web.
 *
 * @author arichard
 */
public interface IUploadDocumentReportProvider {

    boolean canHandle(Resource resource);

    String createReport(Resource resource);

}
