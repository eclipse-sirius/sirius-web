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

import org.eclipse.sirius.web.diagrams.description.NodeDescription;
import org.eclipse.sirius.web.diagrams.tools.CreateNodeTool;
import org.eclipse.sirius.web.freediagram.behaviors.ICreateInstanceBehavior;
import org.eclipse.sirius.web.freediagram.services.CreateInstanceService;
import org.eclipse.sirius.web.freediagram.services.FreeDiagramService;
import org.eclipse.sirius.web.representations.Status;
import org.eclipse.sirius.web.representations.VariableManager;

/**
 * Node Tool supplier.
 *
 * @author hmarchadour
 */
public class NodeToolSupplier implements Supplier<CreateNodeTool> {

    private final CreateInstanceService createInstanceService;

    private final FreeDiagramService freeDiagramService;

    private final String imageURL;

    private final String label;

    private final NodeDescription nodeDescription;

    private final List<NodeDescription> targetDescriptions;

    private final boolean appliesToDiagramRoot;

    public NodeToolSupplier(String imageURL, String label, NodeDescription nodeDescription, List<NodeDescription> targetDescriptions, FreeDiagramService freeDiagramService,
            ICreateInstanceBehavior createInstanceBehavior, boolean appliesToDiagramRoot) {
        this.imageURL = Objects.requireNonNull(imageURL);
        this.label = Objects.requireNonNull(label);
        this.nodeDescription = Objects.requireNonNull(nodeDescription);
        this.targetDescriptions = Objects.requireNonNull(targetDescriptions);
        this.freeDiagramService = Objects.requireNonNull(freeDiagramService);
        this.createInstanceService = new CreateInstanceService(createInstanceBehavior);
        this.appliesToDiagramRoot = appliesToDiagramRoot;
    }

    @Override
    public CreateNodeTool get() {
        // @formatter:off
        return CreateNodeTool.newCreateNodeTool(this.label)
            .label(this.label)
            .imageURL(this.imageURL)
            .handler(this::handle)
            .targetDescriptions(this.targetDescriptions)
            .appliesToDiagramRoot(this.appliesToDiagramRoot)
            .build();
        // @formatter:on
    }

    private Status handle(VariableManager variableManager) {
        Status result = Status.ERROR;
        Optional<Object> optionalSelf = this.createInstanceService.createInstance(variableManager);
        if (optionalSelf.isPresent()) {
            Object self = optionalSelf.get();
            VariableManager childVariableManager = variableManager.createChild();
            childVariableManager.put(VariableManager.SELF, self);
            result = this.freeDiagramService.insertNode(childVariableManager, this.nodeDescription);
        }
        return result;
    }
}
