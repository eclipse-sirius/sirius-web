/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
package org.eclipse.sirius.web.services.projects;

import org.eclipse.sirius.web.services.messages.IServicesMessageService;

/**
 * Implementation of the services message service which does nothing.
 *
 * @author sbegaudeau
 */
public class NoOpServicesMessageService implements IServicesMessageService {

    @Override
    public String invalidProjectName() {
        return ""; //$NON-NLS-1$
    }

}
