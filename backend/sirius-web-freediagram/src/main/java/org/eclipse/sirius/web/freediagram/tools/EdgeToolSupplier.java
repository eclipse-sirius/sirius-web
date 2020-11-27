/***********************************************************************************************
 * Copyright (c) 2019, 2020 Obeo. All Rights Reserved.
 * This software and the attached documentation are the exclusive ownership
 * of its authors and was conceded to the profit of Obeo SARL.
 * This software and the attached documentation are protected under the rights
 * of intellectual ownership, including the section "Titre II  Droits des auteurs (Articles L121-1 L123-12)"
 * By installing this software, you acknowledge being aware of this rights and
 * accept them, and as a consequence you must:
 * - be in possession of a valid license of use conceded by Obeo only.
 * - agree that you have read, understood, and will comply with the license terms and conditions.
 * - agree not to do anything that could conflict with intellectual ownership owned by Obeo or its beneficiaries
 * or the authors of this software
 *
 * Should you not agree with these terms, you must stop to use this software and give it back to its legitimate owner.
 ***********************************************************************************************/
package org.eclipse.sirius.web.freediagram.tools;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.web.diagrams.tools.CreateEdgeTool;
import org.eclipse.sirius.web.diagrams.tools.EdgeCandidate;
import org.eclipse.sirius.web.freediagram.behaviors.ICreateInstanceBehavior;
import org.eclipse.sirius.web.freediagram.services.CreateInstanceService;
import org.eclipse.sirius.web.representations.Status;
import org.eclipse.sirius.web.representations.VariableManager;
import org.eclipse.sirius.web.services.api.objects.IObjectService;

/**
 * Edge Tool supplier.
 *
 * @author hmarchadour
 */
public class EdgeToolSupplier implements Supplier<CreateEdgeTool> {
    private final IObjectService objectService;

    private final CreateInstanceService createInstanceService;

    private final EClass domain;

    private final String label;

    private final List<EdgeCandidate> edgeCandidates;

    public EdgeToolSupplier(EClass domain, String label, IObjectService objectService, List<EdgeCandidate> edgeCandidates, ICreateInstanceBehavior createInstanceBehavior) {
        this.domain = Objects.requireNonNull(domain);
        this.label = Objects.requireNonNull(label);
        this.objectService = Objects.requireNonNull(objectService);
        this.edgeCandidates = Objects.requireNonNull(edgeCandidates);
        this.createInstanceService = new CreateInstanceService(Objects.requireNonNull(createInstanceBehavior));
    }

    @Override
    public CreateEdgeTool get() {
        String imagePath = this.objectService.getImagePath(EcoreUtil.create(this.domain));
        // @formatter:off
        return CreateEdgeTool.newCreateEdgeTool(this.label)
            .label(this.label)
            .imageURL(imagePath)
            .handler(this::handle)
            .edgeCandidates(this.edgeCandidates)
            .build();
        // @formatter:on
    }

    private Status handle(VariableManager variableManager) {
        Status result = Status.ERROR;
        Optional<Object> optionalInstance = this.createInstanceService.createInstance(variableManager);
        if (optionalInstance.isPresent()) {
            result = Status.OK;
        }
        return result;
    }
}
