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
package org.eclipse.sirius.components.core.api;

import java.util.List;

import org.eclipse.sirius.components.core.api.labels.StyledString;

/**
 * Interface of the default service interacting with label objects.
 *
 * @author mcharfadi
 */
public interface IDefaultLabelService {

    StyledString getStyledLabel(Object object);

    List<String> getImagePaths(Object object);

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author mcharfadi
     */
    class NoOp implements IDefaultLabelService {
        @Override
        public StyledString getStyledLabel(Object object) {
            return StyledString.of("");
        }

        @Override
        public List<String> getImagePaths(Object object) {
            return List.of();
        }
    }
}
