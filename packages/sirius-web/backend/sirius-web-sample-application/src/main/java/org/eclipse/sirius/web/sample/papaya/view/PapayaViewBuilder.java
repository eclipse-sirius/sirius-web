/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
package org.eclipse.sirius.web.sample.papaya.view;

import java.util.List;
import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.domain.Domain;
import org.eclipse.sirius.components.domain.Entity;
import org.eclipse.sirius.components.view.UserColor;
import org.eclipse.sirius.components.view.diagram.DiagramFactory;
import org.eclipse.sirius.components.view.diagram.InsideLabelDescription;
import org.eclipse.sirius.components.view.diagram.InsideLabelPosition;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.sirius.components.view.diagram.OutsideLabelDescription;
import org.eclipse.sirius.components.view.diagram.OutsideLabelPosition;
import org.eclipse.sirius.web.sample.papaya.domain.PapayaDomainProvider;

/**
 * Used to help creating the Papaya view.
 *
 * @author sbegaudeau
 */
public class PapayaViewBuilder {

    private final List<EObject> domains = new PapayaDomainProvider().getDomains();

    public Entity entity(String name) {
        // @formatter:off
        return this.domains.stream()
                .filter(Domain.class::isInstance)
                .map(Domain.class::cast)
                .map(Domain::getTypes)
                .flatMap(List::stream)
                .filter(entity -> name.equals(entity.getName()))
                .findFirst()
                .orElse(null);
        // @formatter:on
    }

    public String domainType(Entity entity) {
        // @formatter:off
        return Optional.ofNullable(entity)
                .map(Entity::eContainer)
                .filter(Domain.class::isInstance)
                .map(Domain.class::cast)
                .map(Domain::getName)
                .map(domainName -> domainName + "::" + entity.getName())
                .orElse("");
        // @formatter:on
    }

    public NodeDescription createNodeDescription(String entityName) {
        var domainType = this.domainType(this.entity(entityName));
        var nodeDescription = DiagramFactory.eINSTANCE.createNodeDescription();
        nodeDescription.setName("Node " + domainType);
        nodeDescription.setDomainType(domainType);
        return nodeDescription;
    }

    public InsideLabelDescription createInsideLabelDescription(String labelExpression, UserColor labelColor) {
        var insideLabel = DiagramFactory.eINSTANCE.createInsideLabelDescription();
        insideLabel.setLabelExpression(labelExpression);
        insideLabel.setPosition(InsideLabelPosition.TOP_CENTER);

        var insideLabelStyle = DiagramFactory.eINSTANCE.createInsideLabelStyle();
        insideLabelStyle.setLabelColor(labelColor);

        insideLabel.setStyle(insideLabelStyle);
        return insideLabel;
    }

    public InsideLabelDescription createInsideLabelDescriptionWithHeader(String labelExpression, UserColor labelColor, boolean withSeparator) {
        var insideLabel = DiagramFactory.eINSTANCE.createInsideLabelDescription();
        insideLabel.setLabelExpression(labelExpression);
        insideLabel.setPosition(InsideLabelPosition.TOP_CENTER);

        var insideLabelStyle = DiagramFactory.eINSTANCE.createInsideLabelStyle();
        insideLabelStyle.setLabelColor(labelColor);
        insideLabelStyle.setWithHeader(true);
        insideLabelStyle.setDisplayHeaderSeparator(withSeparator);

        insideLabel.setStyle(insideLabelStyle);
        return insideLabel;
    }

    public OutsideLabelDescription createOutsideLabelDescription(String labelExpression, UserColor labelColor) {
        var outsideLabel = DiagramFactory.eINSTANCE.createOutsideLabelDescription();
        outsideLabel.setLabelExpression(labelExpression);
        outsideLabel.setPosition(OutsideLabelPosition.BOTTOM_CENTER);

        var outsideLabelStyle = DiagramFactory.eINSTANCE.createOutsideLabelStyle();
        outsideLabelStyle.setLabelColor(labelColor);

        outsideLabel.setStyle(outsideLabelStyle);
        return outsideLabel;
    }
}
