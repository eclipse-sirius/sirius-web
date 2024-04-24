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
package org.eclipse.sirius.web.domain.services.api;

/**
 * Used to compute internationalized messages.
 *
 * @author sbegaudeau
 */
public interface IMessageService {

    String revealSelectedFadedElements();

    String collapseSelectedElements();

    String expandSelectedElements();

    String fadeSelectedElements();

    String hideSelectedElements();

    String invalidName();

    String notFound();

    String pinSelectedElements();

    String showSelectedElements();

    String unexpectedError();

    String unpinSelectedElements();
}
