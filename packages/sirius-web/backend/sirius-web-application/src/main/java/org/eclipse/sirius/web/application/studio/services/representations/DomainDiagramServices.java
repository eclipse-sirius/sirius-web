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
package org.eclipse.sirius.web.application.studio.services.representations;

import org.eclipse.sirius.components.domain.Attribute;
import org.eclipse.sirius.components.domain.Feature;
import org.eclipse.sirius.components.domain.Relation;

/**
 * The services class used by the modeler.
 *
 * @author pcdavid
 */
public class DomainDiagramServices {
    public String renderAttribute(Attribute attribute) {
        return attribute.getName();
    }

    public String renderRelation(Relation rel) {
        StringBuilder sb = new StringBuilder(rel.getName());
        this.appendCardinality(rel, sb);
        return sb.toString();
    }

    private void appendCardinality(Feature feature, StringBuilder sb) {
        if (feature.isOptional() || feature.isMany()) {
            sb.append(" [");
            if (feature.isOptional()) {
                sb.append("0");
            } else {
                sb.append("1");
            }
            sb.append("..");
            if (feature.isMany()) {
                sb.append("*");
            } else {
                sb.append("1");
            }
            sb.append("]");
        }
    }
}
