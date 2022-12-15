/*******************************************************************************
 * Copyright (c) 2021, 2022 Obeo.
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
package org.eclipse.sirius.components.domain.design.services;

import org.eclipse.sirius.components.domain.Attribute;
import org.eclipse.sirius.components.domain.DataType;
import org.eclipse.sirius.components.domain.Feature;
import org.eclipse.sirius.components.domain.Relation;

/**
 * The services class used by the modeler.
 *
 * @author pcdavid
 */
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class DomainServices {

    public String renderAttribute(Attribute attr) {
        return attr.getName();
    }

    public Attribute editAttribute(Attribute attr, String newLabel) {
        if ("0".equals(newLabel.trim())) {
            attr.setOptional(true);
        } else if ("1".equals(newLabel.trim())) {
            attr.setOptional(false);
        } else if ("11".equals(newLabel.trim())) {
            attr.setOptional(false);
            attr.setMany(false);
        } else if ("*".equals(newLabel.trim())) {
            attr.setMany(true);
        } else if ("-1".equals(newLabel.trim())) {
            attr.setMany(true);
        } else {
            int typeStart = newLabel.indexOf(':');
            boolean setType = typeStart != -1;

            int nameEnd = newLabel.length();
            if (setType) {
                nameEnd = Math.min(nameEnd, typeStart);
            }

            String namePart = newLabel.substring(0, nameEnd).trim();
            if (namePart.length() > 0) {
                attr.setName(namePart);
            }

            if (setType) {
                String typePart = newLabel.substring(typeStart + ":".length(), newLabel.length()).trim();
                if (typePart != null) {
                    DataType newType = DataType.getByName(typePart.toUpperCase());
                    if (newType != null) {
                        attr.setType(newType);
                    }
                }
            }
        }
        return attr;
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

    public Relation editRelation(Relation rel, String newLabel) {
        rel.setName(newLabel);
        return rel;
    }

}
