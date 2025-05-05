/*******************************************************************************
 * Copyright (c) 2019, 2025 Obeo.
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
package org.eclipse.sirius.components.representations;

import org.eclipse.sirius.components.annotations.PublicApi;

/**
 * A fragment is a specific kind of element used to act as the virtual container of a list of elements.
 *
 * @author sbegaudeau
 * @since v0.1.0
 */
@PublicApi
public class Fragment extends Element {

    public static final String TYPE = "Fragment";

    public Fragment(FragmentProps props) {
        super(TYPE, props);
    }

}
