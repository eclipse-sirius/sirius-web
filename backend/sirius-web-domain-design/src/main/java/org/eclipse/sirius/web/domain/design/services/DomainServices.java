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
package org.eclipse.sirius.web.domain.design.services;

import org.eclipse.sirius.web.domain.Attribute;
import org.eclipse.sirius.web.domain.DataType;
import org.eclipse.sirius.web.domain.Feature;
import org.eclipse.sirius.web.domain.Relation;

/**
 * The services class used by the modeler.
 */
public class DomainServices {

    public String renderAttribute(Attribute attr) {
        return attr.getName() + " : " + attr.getType().toString().toLowerCase(); //$NON-NLS-1$
    }

    public Attribute editAttribute(Attribute attr, String newLabel) {
        if ("0".equals(newLabel.trim())) { //$NON-NLS-1$
            attr.setOptional(true);
        } else if ("1".equals(newLabel.trim())) { //$NON-NLS-1$
            attr.setOptional(false);
        } else if ("11".equals(newLabel.trim())) { //$NON-NLS-1$
            attr.setOptional(false);
            attr.setMany(false);
        } else if ("*".equals(newLabel.trim())) { //$NON-NLS-1$
            attr.setMany(true);
        } else if ("-1".equals(newLabel.trim())) { //$NON-NLS-1$
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
                String typePart = newLabel.substring(typeStart + ":".length(), newLabel.length()).trim(); //$NON-NLS-1$
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
            sb.append(" ["); //$NON-NLS-1$
            if (feature.isOptional()) {
                sb.append("0"); //$NON-NLS-1$
            } else {
                sb.append("1"); //$NON-NLS-1$
            }
            sb.append(".."); //$NON-NLS-1$
            if (feature.isMany()) {
                sb.append("*"); //$NON-NLS-1$
            } else {
                sb.append("1"); //$NON-NLS-1$
            }
            sb.append("]"); //$NON-NLS-1$
        }
    }

    public Relation editRelation(Relation rel, String newLabel) {
        rel.setName(newLabel);
        return rel;
    }

}
