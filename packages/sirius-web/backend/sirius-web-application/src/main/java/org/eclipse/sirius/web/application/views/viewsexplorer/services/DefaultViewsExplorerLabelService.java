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

package org.eclipse.sirius.web.application.views.viewsexplorer.services;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.collaborative.trees.api.IRenameTreeItemHandler;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.ILabelService;
import org.eclipse.sirius.components.core.api.IReadOnlyObjectPredicate;
import org.eclipse.sirius.components.core.api.labels.StyledString;
import org.eclipse.sirius.components.core.api.labels.StyledStringFragment;
import org.eclipse.sirius.components.core.api.labels.StyledStringFragmentStyle;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.trees.Tree;
import org.eclipse.sirius.components.trees.TreeItem;
import org.eclipse.sirius.web.application.views.viewsexplorer.services.api.IDefaultViewsExplorerLabelService;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.RepresentationMetadata;
import org.eclipse.sirius.web.domain.services.api.IMessageService;
import org.springframework.stereotype.Service;

/**
 * Used to provide the default behavior of the views explorer.
 *
 * @author tgiraudet
 */
@Service
public class DefaultViewsExplorerLabelService implements IDefaultViewsExplorerLabelService {

    private final IReadOnlyObjectPredicate readOnlyObjectPredicate;

    private final List<IRenameTreeItemHandler> renameTreeItemHandlers;

    private final ILabelService labelService;

    private final IMessageService messageService;

    public DefaultViewsExplorerLabelService(IReadOnlyObjectPredicate readOnlyObjectPredicate, List<IRenameTreeItemHandler> renameTreeItemHandlers, ILabelService labelService, IMessageService messageService) {
        this.readOnlyObjectPredicate = Objects.requireNonNull(readOnlyObjectPredicate);
        this.renameTreeItemHandlers = Objects.requireNonNull(renameTreeItemHandlers);
        this.labelService = Objects.requireNonNull(labelService);
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    public boolean isEditable(Object self) {
        return !this.readOnlyObjectPredicate.test(self) && self instanceof RepresentationMetadata;
    }

    @Override
    public StyledString getLabel(Object self) {
        var result = StyledString.of("");
        if (self instanceof RepresentationKind kind) {
            String name = kind.name();
            String size = String.valueOf(kind.representationDescriptionTypes().stream().mapToLong(descType -> descType.representationsMetadata().size()).sum());
            result = this.getColoredLabel(name, size);
        } else if (self instanceof RepresentationDescriptionType descriptionType) {
            String name = descriptionType.description().getLabel();
            String size = String.valueOf(descriptionType.representationsMetadata().size());
            result = this.getColoredLabel(name, size);
        } else {
            result = this.labelService.getStyledLabel(self);
        }
        return result;
    }

    @Override
    public IStatus editLabel(IEditingContext editingContext, Tree tree, TreeItem treeItem, String newValue) {
        var optionalHandler = this.renameTreeItemHandlers.stream()
            .filter(handler -> handler.canHandle(editingContext, treeItem, newValue))
            .findFirst();

        if (optionalHandler.isPresent()) {
            IRenameTreeItemHandler renameTreeItemHandler = optionalHandler.get();
            return renameTreeItemHandler.handle(editingContext, treeItem, newValue, tree);
        }

        return new Failure(this.messageService.failedToRename());
    }

    private StyledString getColoredLabel(String label, String size) {
        return new StyledString(List.of(
            new StyledStringFragment("%s (%s)".formatted(label.toUpperCase(), size), StyledStringFragmentStyle.newDefaultStyledStringFragmentStyle()
                .foregroundColor("#261E588A")
                .build())
        ));
    }
}
