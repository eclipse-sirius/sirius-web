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
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.Supplier;

import org.eclipse.sirius.web.diagrams.description.NodeDescription;
import org.eclipse.sirius.web.diagrams.tools.DropTool;
import org.eclipse.sirius.web.freediagram.services.FreeDiagramService;
import org.eclipse.sirius.web.representations.Status;
import org.eclipse.sirius.web.representations.VariableManager;

/**
 * Drop Tool supplier.
 *
 * @author hmarchadour
 */
public class DropToolSupplier implements Supplier<DropTool> {

    private final String toolId;

    private final FreeDiagramService freeDiagramService;

    private final List<NodeDescription> targetDescriptions;

    private final Map<Class<?>, NodeDescription> nodeDescriptionMap;

    private final boolean appliesToDiagramRoot;

    public DropToolSupplier(String toolId, FreeDiagramService freeDiagramService, List<NodeDescription> targetDescriptions, boolean appliesToDiagramRoot,
            Map<Class<?>, NodeDescription> nodeDescriptionMap) {
        this.toolId = Objects.requireNonNull(toolId);
        this.freeDiagramService = Objects.requireNonNull(freeDiagramService);
        this.targetDescriptions = Objects.requireNonNull(targetDescriptions);
        this.appliesToDiagramRoot = appliesToDiagramRoot;
        this.nodeDescriptionMap = Objects.requireNonNull(nodeDescriptionMap);
    }

    @Override
    public DropTool get() {
        // @formatter:off
        return DropTool.newDropTool(this.toolId)
                .label(this.toolId)
                .imageURL("") //$NON-NLS-1$
                .handler(this::handle)
                .targetDescriptions(this.targetDescriptions)
                .appliesToDiagramRoot(this.appliesToDiagramRoot)
                .build();
        // @formatter:on
    }

    private Status handle(VariableManager variableManager) {
        Status result = Status.ERROR;
        for (Entry<Class<?>, NodeDescription> entry : this.nodeDescriptionMap.entrySet()) {
            Class<?> clazz = entry.getKey();
            if (variableManager.get(VariableManager.SELF, clazz).isPresent()) {
                result = this.freeDiagramService.insertNode(variableManager, this.nodeDescriptionMap.get(clazz));
                break;
            }
        }
        return result;
    }
}
