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
package org.eclipse.sirius.web.services.api.modelers;

/**
 * The possible publication status of a {@link Modeler}.
 *
 * @author pcdavid
 */
public enum PublicationStatus {
    /**
     * In this state, a modeler can be modified (by users with the appropriate rights), but can not be used on concrete
     * models. This is the initial state of a newly create modeler.
     */
    DRAFT,
    /**
     * In this state, a modeler's definition is freezed and can be used on concrete models (by users who have the
     * appropriate rights).
     */
    PUBLISHED
}
