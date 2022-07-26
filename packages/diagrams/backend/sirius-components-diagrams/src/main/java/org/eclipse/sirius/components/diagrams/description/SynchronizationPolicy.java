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
package org.eclipse.sirius.components.diagrams.description;

/**
 * The policy used to synchronized the description.
 *
 * @author sbegaudeau
 */
public enum SynchronizationPolicy {
    /**
     * A synchronized description will always create a figure in the diagram if a compatible semantic element is found.
     */
    SYNCHRONIZED,

    /**
     * A figure from an unsynchronized description will only appear in a diagram if some handler explicitly create it.
     */
    UNSYNCHRONIZED,
}
