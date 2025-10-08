/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.web.application.undo.services.recorder;

import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.InsideLabel;
import org.eclipse.sirius.components.diagrams.Label;
import org.eclipse.sirius.components.diagrams.LabelStyle;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.OutsideLabel;
import org.eclipse.sirius.components.diagrams.events.appearance.IAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.ResetNodeAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.label.ILabelAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.label.LabelBackgroundAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.label.LabelBoldAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.label.LabelBorderColorAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.label.LabelBorderRadiusAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.label.LabelBorderSizeAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.label.LabelBorderStyleAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.label.LabelColorAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.label.LabelFontSizeAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.label.LabelItalicAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.label.LabelStrikeThroughAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.label.LabelUnderlineAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.label.LabelVisibilityAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.label.ResetLabelAppearanceChange;
import org.eclipse.sirius.components.diagrams.renderer.LabelAppearanceHandler;
import org.eclipse.sirius.web.application.undo.services.api.ILabelAppearanceChangeUndoRecorder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Use to record data needed to perform the undo for the label appearance changes.
 *
 * @author mcharfadi
 */
@Service
public class LabelAppearanceChangeUndoRecorder implements ILabelAppearanceChangeUndoRecorder {

    @Override
    public List<IAppearanceChange> computeUndoNodeLabelAppearanceChanges(Node previousNode, String labelId, Optional<ILabelAppearanceChange> optionalChange) {
        List<IAppearanceChange> appearanceChanges = new ArrayList<>();
        if (optionalChange.isPresent()) {
            var change = optionalChange.get();
            this.getNodeOutsideLabel(previousNode, labelId)
                    .ifPresent(previousLabel -> appearanceChanges.addAll(this.getAppearanceChanges(change, previousLabel.customizedStyleProperties(), previousLabel.style())));
            this.getNodeInsideLabel(previousNode, labelId)
                    .ifPresent(previousLabel -> appearanceChanges.addAll(this.getAppearanceChanges(change, previousLabel.getCustomizedStyleProperties(), previousLabel.getStyle())));
        } else {
            this.getNodeOutsideLabel(previousNode, labelId)
                    .ifPresent(previousLabel -> appearanceChanges.addAll(this.getResetLabelApperanceChange(labelId, previousLabel.customizedStyleProperties(), previousLabel.style())));
            this.getNodeInsideLabel(previousNode, labelId)
                    .ifPresent(previousLabel -> appearanceChanges.addAll(this.getResetLabelApperanceChange(labelId, previousLabel.getCustomizedStyleProperties(), previousLabel.getStyle())));
        }
        return appearanceChanges;
    }

    @Override
    public List<IAppearanceChange> computeUndoEdgeLabelAppearanceChanges(Edge previousEdge, String labelId, Optional<ILabelAppearanceChange> optionalChange) {
        List<IAppearanceChange> appearanceChanges = new ArrayList<>();
        if (optionalChange.isPresent()) {
            var change = optionalChange.get();
            this.getEdgeLabel(previousEdge, labelId)
                    .ifPresent(previousLabel -> appearanceChanges.addAll(this.getAppearanceChanges(change, previousLabel.customizedStyleProperties(), previousLabel.style())));
        } else {
            this.getEdgeLabel(previousEdge, labelId)
                    .ifPresent(previousLabel -> appearanceChanges.addAll(this.getResetLabelApperanceChange(labelId, previousLabel.customizedStyleProperties(), previousLabel.style())));
        }
        return appearanceChanges;
    }

    private List<IAppearanceChange> getAppearanceChanges(ILabelAppearanceChange change, Set<String> previousCustomizedStyleProperties, LabelStyle previousLabelStyle) {
        List<IAppearanceChange> appearanceChanges = new ArrayList<>();
        if (change instanceof LabelBoldAppearanceChange) {
            if (previousCustomizedStyleProperties.contains(LabelAppearanceHandler.BOLD)) {
                appearanceChanges.add(new LabelBoldAppearanceChange(change.labelId(), previousLabelStyle.isBold()));
            } else {
                appearanceChanges.add(new ResetNodeAppearanceChange(change.labelId(), LabelAppearanceHandler.BOLD));
            }
        }
        if (change instanceof LabelItalicAppearanceChange) {
            if (previousCustomizedStyleProperties.contains(LabelAppearanceHandler.ITALIC)) {
                appearanceChanges.add(new LabelItalicAppearanceChange(change.labelId(), previousLabelStyle.isItalic()));
            } else {
                appearanceChanges.add(new ResetNodeAppearanceChange(change.labelId(), LabelAppearanceHandler.ITALIC));
            }
        }
        if (change instanceof LabelUnderlineAppearanceChange) {
            if (previousCustomizedStyleProperties.contains(LabelAppearanceHandler.UNDERLINE)) {
                appearanceChanges.add(new LabelUnderlineAppearanceChange(change.labelId(), previousLabelStyle.isUnderline()));
            } else {
                appearanceChanges.add(new ResetNodeAppearanceChange(change.labelId(), LabelAppearanceHandler.UNDERLINE));
            }
        }
        if (change instanceof LabelStrikeThroughAppearanceChange) {
            if (previousCustomizedStyleProperties.contains(LabelAppearanceHandler.STRIKE_THROUGH)) {
                appearanceChanges.add(new LabelStrikeThroughAppearanceChange(change.labelId(), previousLabelStyle.isStrikeThrough()));
            } else {
                appearanceChanges.add(new ResetNodeAppearanceChange(change.labelId(), LabelAppearanceHandler.STRIKE_THROUGH));
            }
        }
        if (change instanceof LabelColorAppearanceChange) {
            if (previousCustomizedStyleProperties.contains(LabelAppearanceHandler.COLOR)) {
                appearanceChanges.add(new LabelColorAppearanceChange(change.labelId(), previousLabelStyle.getColor()));
            } else {
                appearanceChanges.add(new ResetNodeAppearanceChange(change.labelId(), LabelAppearanceHandler.COLOR));
            }
        }
        if (change instanceof LabelFontSizeAppearanceChange) {
            if (previousCustomizedStyleProperties.contains(LabelAppearanceHandler.FONT_SIZE)) {
                appearanceChanges.add(new LabelFontSizeAppearanceChange(change.labelId(), previousLabelStyle.getFontSize()));
            } else {
                appearanceChanges.add(new ResetNodeAppearanceChange(change.labelId(), LabelAppearanceHandler.FONT_SIZE));
            }
        }
        if (change instanceof LabelBackgroundAppearanceChange) {
            if (previousCustomizedStyleProperties.contains(LabelAppearanceHandler.BACKGROUND)) {
                appearanceChanges.add(new LabelBackgroundAppearanceChange(change.labelId(), previousLabelStyle.getBackground()));
            } else {
                appearanceChanges.add(new ResetNodeAppearanceChange(change.labelId(), LabelAppearanceHandler.BACKGROUND));
            }
        }
        if (change instanceof LabelBorderColorAppearanceChange) {
            if (previousCustomizedStyleProperties.contains(LabelAppearanceHandler.BORDER_COLOR)) {
                appearanceChanges.add(new LabelBorderColorAppearanceChange(change.labelId(), previousLabelStyle.getBorderColor()));
            } else {
                appearanceChanges.add(new ResetNodeAppearanceChange(change.labelId(), LabelAppearanceHandler.BORDER_COLOR));
            }
        }
        if (change instanceof LabelBorderSizeAppearanceChange) {
            if (previousCustomizedStyleProperties.contains(LabelAppearanceHandler.BORDER_SIZE)) {
                appearanceChanges.add(new LabelBorderSizeAppearanceChange(change.labelId(), previousLabelStyle.getBorderSize()));
            } else {
                appearanceChanges.add(new ResetNodeAppearanceChange(change.labelId(), LabelAppearanceHandler.BORDER_SIZE));
            }
        }
        if (change instanceof LabelBorderRadiusAppearanceChange) {
            if (previousCustomizedStyleProperties.contains(LabelAppearanceHandler.BORDER_RADIUS)) {
                appearanceChanges.add(new LabelBorderRadiusAppearanceChange(change.labelId(), previousLabelStyle.getBorderRadius()));
            } else {
                appearanceChanges.add(new ResetNodeAppearanceChange(change.labelId(), LabelAppearanceHandler.BORDER_RADIUS));
            }
        }
        if (change instanceof LabelBorderStyleAppearanceChange) {
            if (previousCustomizedStyleProperties.contains(LabelAppearanceHandler.BORDER_STYLE)) {
                appearanceChanges.add(new LabelBorderStyleAppearanceChange(change.labelId(), previousLabelStyle.getBorderStyle()));
            } else {
                appearanceChanges.add(new ResetNodeAppearanceChange(change.labelId(), LabelAppearanceHandler.BORDER_STYLE));
            }
        }
        if (change instanceof LabelVisibilityAppearanceChange) {
            if (previousCustomizedStyleProperties.contains(LabelAppearanceHandler.VISIBILITY)) {
                appearanceChanges.add(new LabelVisibilityAppearanceChange(change.labelId(), previousLabelStyle.getVisibility()));
            } else {
                appearanceChanges.add(new ResetNodeAppearanceChange(change.labelId(), LabelAppearanceHandler.VISIBILITY));
            }
        }
        if (change instanceof ResetLabelAppearanceChange) {
            appearanceChanges.addAll(this.getResetLabelApperanceChange(change.labelId(), previousCustomizedStyleProperties, previousLabelStyle));
        }
        return appearanceChanges;
    }

    private Optional<Label> getEdgeLabel(Edge edge, String labelId) {
        Optional<Label> optionalLabel = Optional.empty();
        if (edge.getBeginLabel() != null && edge.getBeginLabel().id().equals(labelId)) {
            optionalLabel = Optional.of(edge.getBeginLabel());
        }
        if (edge.getCenterLabel() != null && edge.getCenterLabel().id().equals(labelId)) {
            optionalLabel = Optional.of(edge.getCenterLabel());
        }
        if (edge.getEndLabel() != null && edge.getEndLabel().id().equals(labelId)) {
            optionalLabel = Optional.of(edge.getEndLabel());
        }
        return optionalLabel;
    }

    private Optional<InsideLabel> getNodeInsideLabel(Node node, String labelId) {
        Optional<InsideLabel> optionalLabel = Optional.empty();
        if (node.getInsideLabel() != null && node.getInsideLabel().getId().equals(labelId)) {
            optionalLabel = Optional.of(node.getInsideLabel());
        }
        return optionalLabel;
    }

    private Optional<OutsideLabel> getNodeOutsideLabel(Node node, String labelId) {
        return  node.getOutsideLabels().stream().filter(label -> label.id().equals(labelId)).findFirst();
    }

    private List<IAppearanceChange> getResetLabelApperanceChange(String labelId, Set<String> previousCustomizedStyleProperties, LabelStyle previousLabelStyle) {
        List<IAppearanceChange> appearanceChanges = new ArrayList<>();
        previousCustomizedStyleProperties.forEach(customizedStyleProperty -> {
            switch (customizedStyleProperty) {
                case LabelAppearanceHandler.BOLD -> appearanceChanges.add(new LabelBoldAppearanceChange(labelId, previousLabelStyle.isBold()));
                case LabelAppearanceHandler.ITALIC -> appearanceChanges.add(new LabelItalicAppearanceChange(labelId, previousLabelStyle.isItalic()));
                case LabelAppearanceHandler.UNDERLINE -> appearanceChanges.add(new LabelUnderlineAppearanceChange(labelId, previousLabelStyle.isUnderline()));
                case LabelAppearanceHandler.STRIKE_THROUGH -> appearanceChanges.add(new LabelStrikeThroughAppearanceChange(labelId, previousLabelStyle.isStrikeThrough()));
                case LabelAppearanceHandler.COLOR -> appearanceChanges.add(new LabelColorAppearanceChange(labelId, previousLabelStyle.getColor()));
                case LabelAppearanceHandler.FONT_SIZE -> appearanceChanges.add(new LabelFontSizeAppearanceChange(labelId, previousLabelStyle.getFontSize()));
                case LabelAppearanceHandler.BACKGROUND -> appearanceChanges.add(new LabelBackgroundAppearanceChange(labelId, previousLabelStyle.getBackground()));
                case LabelAppearanceHandler.BORDER_COLOR -> appearanceChanges.add(new LabelBorderColorAppearanceChange(labelId, previousLabelStyle.getBorderColor()));
                case LabelAppearanceHandler.BORDER_SIZE -> appearanceChanges.add(new LabelBorderSizeAppearanceChange(labelId, previousLabelStyle.getBorderSize()));
                case LabelAppearanceHandler.BORDER_RADIUS -> appearanceChanges.add(new LabelBorderRadiusAppearanceChange(labelId, previousLabelStyle.getBorderRadius()));
                case LabelAppearanceHandler.BORDER_STYLE -> appearanceChanges.add(new LabelBorderStyleAppearanceChange(labelId, previousLabelStyle.getBorderStyle()));
                case LabelAppearanceHandler.VISIBILITY -> appearanceChanges.add(new LabelVisibilityAppearanceChange(labelId, previousLabelStyle.getVisibility()));
                default -> {
                    //We do nothing, the style property is not supported
                }
            }
        });
        return appearanceChanges;
    }

}
