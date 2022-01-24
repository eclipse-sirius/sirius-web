/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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
package org.eclipse.sirius.web.spring.collaborative.diagrams.handlers;

import org.eclipse.sirius.web.spring.collaborative.diagrams.messages.ICollaborativeDiagramMessageService;

/**
 * Implementation of the collaborative diagram message service which does nothing.
 *
 * @author sbegaudeau
 */
public class NoOpCollaborativeDiagramMessageService implements ICollaborativeDiagramMessageService {

    @Override
    public String invalidInput(String expectedInputTypeName, String receivedInputTypeName) {
        return ""; //$NON-NLS-1$
    }

    @Override
    public String edgeNotFound(String id) {
        return ""; //$NON-NLS-1$
    }

    @Override
    public String nodeNotFound(String id) {
        return ""; //$NON-NLS-1$
    }

    @Override
    public String deleteEdgeFailed(String id) {
        return ""; //$NON-NLS-1$
    }

    @Override
    public String deleteNodeFailed(String id) {
        return ""; //$NON-NLS-1$
    }

    @Override
    public String deleteFailed() {
        return ""; //$NON-NLS-1$
    }

    @Override
    public String semanticObjectNotFound(String id) {
        return ""; //$NON-NLS-1$
    }

    @Override
    public String nodeDescriptionNotFound(String id) {
        return ""; //$NON-NLS-1$
    }

    @Override
    public String edgeDescriptionNotFound(String id) {
        return ""; //$NON-NLS-1$
    }

    @Override
    public String invalidDrop() {
        return null;
    }

}
