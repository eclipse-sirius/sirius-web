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
package org.eclipse.sirius.web.emf.services.messages;

/**
 * Interface of the EMF message service.
 *
 * @author sbegaudeau
 */
public interface IEMFMessageService {

    String unexpectedError();

    String invalidDocumentName(String name);

    String stereotypeDescriptionNotFound(String stereotypeDescriptionId);

    String invalidInput(String expectedInputTypeName, String receivedInputTypeName);

}
