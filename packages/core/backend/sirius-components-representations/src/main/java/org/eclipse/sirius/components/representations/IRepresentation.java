/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo.
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

/**
 * Common interface for all the representations.
 *
 * @author sbegaudeau
 */
public interface IRepresentation {
    String KIND_PREFIX = "siriusComponents://representation";

    String getId();

    String getDescriptionId();

    String getLabel();

    String getKind();

    String getTargetObjectId();

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author gcoutable
     */
    class NoOp implements IRepresentation {
        @Override
        public String getId() {
            return "";
        }

        @Override
        public String getDescriptionId() {
            return "";
        }

        @Override
        public String getLabel() {
            return "";
        }

        @Override
        public String getKind() {
            return "";
        }

        @Override
        public String getTargetObjectId() {
            return "";
        }
    }
}
