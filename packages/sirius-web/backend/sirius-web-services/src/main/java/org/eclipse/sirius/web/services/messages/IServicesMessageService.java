/*******************************************************************************
 * Copyright (c) 2021, 2024 Obeo.
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
package org.eclipse.sirius.web.services.messages;

import java.util.UUID;

/**
 * Interface of the services messages.
 *
 * @author sbegaudeau
 */
public interface IServicesMessageService {

    String revealSelectedFadedElements();

    String collapseSelectedElements();

    String expandSelectedElements();

    String fadeSelectedElements();

    String hideSelectedElements();

    String invalidInput(String expectedInputTypeName, String receivedInputTypeName);

    String invalidProjectName();

    String pinSelectedElements();

    String projectNotFound();

    String showSelectedElements();

    String unexpectedError();

    String invalidDocumentName(String name);

    String stereotypeNotFound(UUID stereotypeId);

    String unpinSelectedElements();
}
