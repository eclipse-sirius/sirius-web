/*******************************************************************************
 * Copyright (c) 2022, 2023 Obeo.
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
package org.eclipse.sirius.components.collaborative.diagrams.export.api;

import java.util.UUID;

import org.eclipse.sirius.components.diagrams.Diagram;

/**
 * Registers the images referenced in a {@link Diagram} for later retrieval as \<symbol\>.
 *
 * @author sbegaudeau
 */
public interface IImageRegistry {
    UUID registerImage(String imageURL);

    StringBuilder getReferencedImageSymbols();

    StringBuilder getImage(UUID symbolId);

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author sbegaudeau
     */
    class NoOp implements IImageRegistry {

        @Override
        public UUID registerImage(String imageURL) {
            return null;
        }

        @Override
        public StringBuilder getReferencedImageSymbols() {
            return new StringBuilder();
        }

        @Override
        public StringBuilder getImage(UUID symbolId) {
            return new StringBuilder();
        }
    }
}
