/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
package org.eclipse.sirius.components.collaborative.diagrams.services;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.eclipse.sirius.components.collaborative.diagrams.DiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.palette.IDiagramPaletteCustomizer;
import org.eclipse.sirius.components.collaborative.diagrams.dto.ITool;
import org.eclipse.sirius.components.collaborative.diagrams.dto.ToolSection;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.springframework.stereotype.Service;

/**
 * Used to merge contributed tool sections with the same id.
 *
 * @author mcharfadi
 */
@Service
public class DiagramPaletteCustomizerToolSectionMerger implements IDiagramPaletteCustomizer {

    @Override
    public List<ToolSection> customizeToolSections(IEditingContext editingContext, DiagramContext diagramContext, DiagramDescription diagramDescription, Object diagramElement, List<ToolSection> currentToolSections) {
        return this.mergeById(currentToolSections);
    }

    /**
     * Merges tool sections which share the same id into a single one will all the tools from all original matching
     * sections. When multiple sections are merged, the resulting section keeps the label and iconURL of the first
     * section in the list. The tools are also added in the merged section in the order of the original tool sections.
     *
     * @param toolSections
     *            a list of tool sections, where multiple sections may share the same id.
     * @return an equivalent list of tool section where tools from different sections with the same id are merged in a
     *         single section.
     */
    private List<ToolSection> mergeById(List<ToolSection> toolSections) {
        var result = new LinkedHashMap<String, ToolSection>();
        for (ToolSection toolSection : toolSections) {
            if (!result.containsKey(toolSection.id())) {
                result.put(toolSection.id(), toolSection);
            } else {
                var existingSection = result.get(toolSection.id());
                result.put(toolSection.id(), this.mergeToolSections(existingSection, toolSection));
            }
        }
        return result.values().stream().toList();
    }

    /**
     * Builds a copy of a tool section with additional tools from another section.
     *
     * @param referenceSection
     *            the reference tool section.
     * @param otherSection
     *            the other section with additional tools.
     * @return a new ToolSection identical to the reference except with additional tools.
     */
    private ToolSection mergeToolSections(ToolSection referenceSection, ToolSection otherSection) {
        var allTools = new ArrayList<ITool>();
        allTools.addAll(referenceSection.tools());
        allTools.addAll(otherSection.tools());
        return ToolSection.newToolSection(referenceSection.id())
                .label(referenceSection.label())
                .iconURL(referenceSection.iconURL())
                .tools(allTools)
                .build();
    }
}
