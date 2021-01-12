/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.web.services.api.projects;

import org.eclipse.sirius.web.core.api.IPayload;
import org.eclipse.sirius.web.services.api.Context;
import org.eclipse.sirius.web.spring.graphql.api.UploadFile;

/**
 * Service used to import a project.
 *
 * @author gcoutable
 */
public interface IProjectImportService {

    IPayload importProject(UploadFile file, Context context);

}
