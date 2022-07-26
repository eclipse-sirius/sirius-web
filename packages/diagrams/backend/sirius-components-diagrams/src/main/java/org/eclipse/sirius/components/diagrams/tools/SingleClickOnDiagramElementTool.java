/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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
package org.eclipse.sirius.components.diagrams.tools;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.diagrams.description.NodeDescription;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * A tool triggered by a single click in the palette on a diagram element.
 *
 * @author pcdavid
 * @author hmarchadour
 */
@Immutable
public final class SingleClickOnDiagramElementTool implements ITool {

    public static final String SELECTED_OBJECT = "selectedObject"; //$NON-NLS-1$

    private String id;

    private String imageURL;

    private String label;

    private Function<VariableManager, IStatus> handler;

    private List<NodeDescription> targetDescriptions;

    private boolean appliesToDiagramRoot;

    private String selectionDescriptionId;

    private SingleClickOnDiagramElementTool() {
        // Prevent instantiation
    }

    public List<NodeDescription> getTargetDescriptions() {
        return this.targetDescriptions;
    }

    public boolean isAppliesToDiagramRoot() {
        return this.appliesToDiagramRoot;
    }

    @Override
    public String getId() {
        return this.id;
    }

    // This field is defined in DiagramTypesProvider to add the server base URL prefix.
    @Override
    public String getImageURL() {
        return this.imageURL;
    }

    @Override
    public String getLabel() {
        return this.label;
    }

    public String getSelectionDescriptionId() {
        return this.selectionDescriptionId;
    }

    @Override
    public Function<VariableManager, IStatus> getHandler() {
        return this.handler;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, label: {2}, imageURL: {3}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.label, this.imageURL);
    }

    public static Builder newSingleClickOnDiagramElementTool(String id) {
        return new Builder(id);
    }

    /**
     * The builder used to create a tool.
     *
     * @author pcdavid
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String id;

        private String imageURL;

        private String label;

        private Function<VariableManager, IStatus> handler;

        private List<NodeDescription> targetDescriptions;

        private boolean appliesToDiagramRoot;

        private String selectionDescriptionId;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder imageURL(String imageURL) {
            this.imageURL = Objects.requireNonNull(imageURL);
            return this;
        }

        public Builder label(String label) {
            this.label = Objects.requireNonNull(label);
            return this;
        }

        public Builder targetDescriptions(List<NodeDescription> targetDescriptions) {
            this.targetDescriptions = Objects.requireNonNull(targetDescriptions);
            return this;
        }

        public Builder appliesToDiagramRoot(boolean appliesToDiagramRoot) {
            this.appliesToDiagramRoot = appliesToDiagramRoot;
            return this;
        }

        public Builder handler(Function<VariableManager, IStatus> handler) {
            this.handler = Objects.requireNonNull(handler);
            return this;
        }

        public Builder selectionDescriptionId(String selectionDescriptionId) {
            this.selectionDescriptionId = selectionDescriptionId;
            return this;
        }

        public SingleClickOnDiagramElementTool build() {
            SingleClickOnDiagramElementTool tool = new SingleClickOnDiagramElementTool();
            tool.id = Objects.requireNonNull(this.id);
            tool.imageURL = Objects.requireNonNull(this.imageURL);
            tool.label = Objects.requireNonNull(this.label);
            tool.handler = Objects.requireNonNull(this.handler);
            tool.targetDescriptions = Objects.requireNonNull(this.targetDescriptions);
            tool.appliesToDiagramRoot = this.appliesToDiagramRoot;
            tool.selectionDescriptionId = this.selectionDescriptionId;
            return tool;
        }
    }
}
