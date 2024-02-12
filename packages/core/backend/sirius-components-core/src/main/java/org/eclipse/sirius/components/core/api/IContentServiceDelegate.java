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

/**
 * Interface  of the delegation service interacting with identity objects.
 *
 * @author mcharfadi
 */
public interface IContentServiceDelegate {

    boolean canHandle(Object object);
    List<Object> getContents(Object object);

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author mcharfadi
     */
    class NoOp implements IContentServiceDelegate {
        @Override
        public boolean canHandle(Object object) {
            return true;
        }
        @Override
        public List<Object> getContents(Object object) {
            return List.of();
        }

    }

}
