/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.components.view.diagram.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;
import org.eclipse.sirius.components.view.Conditional;
import org.eclipse.sirius.components.view.LabelStyle;
import org.eclipse.sirius.components.view.Operation;
import org.eclipse.sirius.components.view.RepresentationDescription;
import org.eclipse.sirius.components.view.diagram.BorderStyle;
import org.eclipse.sirius.components.view.diagram.ConditionalEdgeStyle;
import org.eclipse.sirius.components.view.diagram.ConditionalNodeStyle;
import org.eclipse.sirius.components.view.diagram.CreateView;
import org.eclipse.sirius.components.view.diagram.DeleteTool;
import org.eclipse.sirius.components.view.diagram.DeleteView;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.DiagramElementDescription;
import org.eclipse.sirius.components.view.diagram.DiagramPackage;
import org.eclipse.sirius.components.view.diagram.DiagramPalette;
import org.eclipse.sirius.components.view.diagram.DiagramToolSection;
import org.eclipse.sirius.components.view.diagram.DropTool;
import org.eclipse.sirius.components.view.diagram.EdgeDescription;
import org.eclipse.sirius.components.view.diagram.EdgePalette;
import org.eclipse.sirius.components.view.diagram.EdgeReconnectionTool;
import org.eclipse.sirius.components.view.diagram.EdgeStyle;
import org.eclipse.sirius.components.view.diagram.EdgeTool;
import org.eclipse.sirius.components.view.diagram.EdgeToolSection;
import org.eclipse.sirius.components.view.diagram.FreeFormLayoutStrategyDescription;
import org.eclipse.sirius.components.view.diagram.IconLabelNodeStyleDescription;
import org.eclipse.sirius.components.view.diagram.ImageNodeStyleDescription;
import org.eclipse.sirius.components.view.diagram.LabelEditTool;
import org.eclipse.sirius.components.view.diagram.LayoutStrategyDescription;
import org.eclipse.sirius.components.view.diagram.ListLayoutStrategyDescription;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.sirius.components.view.diagram.NodePalette;
import org.eclipse.sirius.components.view.diagram.NodeStyleDescription;
import org.eclipse.sirius.components.view.diagram.NodeTool;
import org.eclipse.sirius.components.view.diagram.NodeToolSection;
import org.eclipse.sirius.components.view.diagram.RectangularNodeStyleDescription;
import org.eclipse.sirius.components.view.diagram.SelectionDescription;
import org.eclipse.sirius.components.view.diagram.SourceEdgeEndReconnectionTool;
import org.eclipse.sirius.components.view.diagram.Style;
import org.eclipse.sirius.components.view.diagram.TargetEdgeEndReconnectionTool;
import org.eclipse.sirius.components.view.diagram.Tool;
import org.eclipse.sirius.components.view.diagram.ToolSection;

/**
 * <!-- begin-user-doc --> The <b>Switch</b> for the model's inheritance hierarchy. It supports the call
 * {@link #doSwitch(EObject) doSwitch(object)} to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object and proceeding up the inheritance hierarchy until a non-null result is
 * returned, which is the result of the switch. <!-- end-user-doc -->
 *
 * @see org.eclipse.sirius.components.view.diagram.DiagramPackage
 * @generated
 */
public class DiagramSwitch<T> extends Switch<T> {
    /**
     * The cached model package <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected static DiagramPackage modelPackage;

    /**
     * Creates an instance of the switch. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public DiagramSwitch() {
        if (modelPackage == null) {
            modelPackage = DiagramPackage.eINSTANCE;
        }
    }

    /**
     * Checks whether this is a switch for the given package. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param ePackage
     *            the package in question.
     * @return whether this is a switch for the given package.
     * @generated
     */
    @Override
    protected boolean isSwitchFor(EPackage ePackage) {
        return ePackage == modelPackage;
    }

    /**
     * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that
     * result. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the first non-null result returned by a <code>caseXXX</code> call.
     * @generated
     */
    @Override
    protected T doSwitch(int classifierID, EObject theEObject) {
        switch (classifierID) {
            case DiagramPackage.DIAGRAM_DESCRIPTION: {
                DiagramDescription diagramDescription = (DiagramDescription) theEObject;
                T result = this.caseDiagramDescription(diagramDescription);
                if (result == null)
                    result = this.caseRepresentationDescription(diagramDescription);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case DiagramPackage.DIAGRAM_ELEMENT_DESCRIPTION: {
                DiagramElementDescription diagramElementDescription = (DiagramElementDescription) theEObject;
                T result = this.caseDiagramElementDescription(diagramElementDescription);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case DiagramPackage.NODE_DESCRIPTION: {
                NodeDescription nodeDescription = (NodeDescription) theEObject;
                T result = this.caseNodeDescription(nodeDescription);
                if (result == null)
                    result = this.caseDiagramElementDescription(nodeDescription);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case DiagramPackage.EDGE_DESCRIPTION: {
                EdgeDescription edgeDescription = (EdgeDescription) theEObject;
                T result = this.caseEdgeDescription(edgeDescription);
                if (result == null)
                    result = this.caseDiagramElementDescription(edgeDescription);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case DiagramPackage.LAYOUT_STRATEGY_DESCRIPTION: {
                LayoutStrategyDescription layoutStrategyDescription = (LayoutStrategyDescription) theEObject;
                T result = this.caseLayoutStrategyDescription(layoutStrategyDescription);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case DiagramPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION: {
                ListLayoutStrategyDescription listLayoutStrategyDescription = (ListLayoutStrategyDescription) theEObject;
                T result = this.caseListLayoutStrategyDescription(listLayoutStrategyDescription);
                if (result == null)
                    result = this.caseLayoutStrategyDescription(listLayoutStrategyDescription);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case DiagramPackage.FREE_FORM_LAYOUT_STRATEGY_DESCRIPTION: {
                FreeFormLayoutStrategyDescription freeFormLayoutStrategyDescription = (FreeFormLayoutStrategyDescription) theEObject;
                T result = this.caseFreeFormLayoutStrategyDescription(freeFormLayoutStrategyDescription);
                if (result == null)
                    result = this.caseLayoutStrategyDescription(freeFormLayoutStrategyDescription);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case DiagramPackage.STYLE: {
                Style style = (Style) theEObject;
                T result = this.caseStyle(style);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case DiagramPackage.BORDER_STYLE: {
                BorderStyle borderStyle = (BorderStyle) theEObject;
                T result = this.caseBorderStyle(borderStyle);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case DiagramPackage.NODE_STYLE_DESCRIPTION: {
                NodeStyleDescription nodeStyleDescription = (NodeStyleDescription) theEObject;
                T result = this.caseNodeStyleDescription(nodeStyleDescription);
                if (result == null)
                    result = this.caseStyle(nodeStyleDescription);
                if (result == null)
                    result = this.caseLabelStyle(nodeStyleDescription);
                if (result == null)
                    result = this.caseBorderStyle(nodeStyleDescription);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case DiagramPackage.CONDITIONAL_NODE_STYLE: {
                ConditionalNodeStyle conditionalNodeStyle = (ConditionalNodeStyle) theEObject;
                T result = this.caseConditionalNodeStyle(conditionalNodeStyle);
                if (result == null)
                    result = this.caseConditional(conditionalNodeStyle);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case DiagramPackage.RECTANGULAR_NODE_STYLE_DESCRIPTION: {
                RectangularNodeStyleDescription rectangularNodeStyleDescription = (RectangularNodeStyleDescription) theEObject;
                T result = this.caseRectangularNodeStyleDescription(rectangularNodeStyleDescription);
                if (result == null)
                    result = this.caseNodeStyleDescription(rectangularNodeStyleDescription);
                if (result == null)
                    result = this.caseStyle(rectangularNodeStyleDescription);
                if (result == null)
                    result = this.caseLabelStyle(rectangularNodeStyleDescription);
                if (result == null)
                    result = this.caseBorderStyle(rectangularNodeStyleDescription);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case DiagramPackage.IMAGE_NODE_STYLE_DESCRIPTION: {
                ImageNodeStyleDescription imageNodeStyleDescription = (ImageNodeStyleDescription) theEObject;
                T result = this.caseImageNodeStyleDescription(imageNodeStyleDescription);
                if (result == null)
                    result = this.caseNodeStyleDescription(imageNodeStyleDescription);
                if (result == null)
                    result = this.caseStyle(imageNodeStyleDescription);
                if (result == null)
                    result = this.caseLabelStyle(imageNodeStyleDescription);
                if (result == null)
                    result = this.caseBorderStyle(imageNodeStyleDescription);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case DiagramPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION: {
                IconLabelNodeStyleDescription iconLabelNodeStyleDescription = (IconLabelNodeStyleDescription) theEObject;
                T result = this.caseIconLabelNodeStyleDescription(iconLabelNodeStyleDescription);
                if (result == null)
                    result = this.caseNodeStyleDescription(iconLabelNodeStyleDescription);
                if (result == null)
                    result = this.caseStyle(iconLabelNodeStyleDescription);
                if (result == null)
                    result = this.caseLabelStyle(iconLabelNodeStyleDescription);
                if (result == null)
                    result = this.caseBorderStyle(iconLabelNodeStyleDescription);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case DiagramPackage.EDGE_STYLE: {
                EdgeStyle edgeStyle = (EdgeStyle) theEObject;
                T result = this.caseEdgeStyle(edgeStyle);
                if (result == null)
                    result = this.caseStyle(edgeStyle);
                if (result == null)
                    result = this.caseLabelStyle(edgeStyle);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case DiagramPackage.CONDITIONAL_EDGE_STYLE: {
                ConditionalEdgeStyle conditionalEdgeStyle = (ConditionalEdgeStyle) theEObject;
                T result = this.caseConditionalEdgeStyle(conditionalEdgeStyle);
                if (result == null)
                    result = this.caseConditional(conditionalEdgeStyle);
                if (result == null)
                    result = this.caseEdgeStyle(conditionalEdgeStyle);
                if (result == null)
                    result = this.caseStyle(conditionalEdgeStyle);
                if (result == null)
                    result = this.caseLabelStyle(conditionalEdgeStyle);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case DiagramPackage.DIAGRAM_PALETTE: {
                DiagramPalette diagramPalette = (DiagramPalette) theEObject;
                T result = this.caseDiagramPalette(diagramPalette);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case DiagramPackage.NODE_PALETTE: {
                NodePalette nodePalette = (NodePalette) theEObject;
                T result = this.caseNodePalette(nodePalette);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case DiagramPackage.EDGE_PALETTE: {
                EdgePalette edgePalette = (EdgePalette) theEObject;
                T result = this.caseEdgePalette(edgePalette);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case DiagramPackage.TOOL: {
                Tool tool = (Tool) theEObject;
                T result = this.caseTool(tool);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case DiagramPackage.DELETE_TOOL: {
                DeleteTool deleteTool = (DeleteTool) theEObject;
                T result = this.caseDeleteTool(deleteTool);
                if (result == null)
                    result = this.caseTool(deleteTool);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case DiagramPackage.DROP_TOOL: {
                DropTool dropTool = (DropTool) theEObject;
                T result = this.caseDropTool(dropTool);
                if (result == null)
                    result = this.caseTool(dropTool);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case DiagramPackage.EDGE_TOOL: {
                EdgeTool edgeTool = (EdgeTool) theEObject;
                T result = this.caseEdgeTool(edgeTool);
                if (result == null)
                    result = this.caseTool(edgeTool);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case DiagramPackage.EDGE_RECONNECTION_TOOL: {
                EdgeReconnectionTool edgeReconnectionTool = (EdgeReconnectionTool) theEObject;
                T result = this.caseEdgeReconnectionTool(edgeReconnectionTool);
                if (result == null)
                    result = this.caseTool(edgeReconnectionTool);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case DiagramPackage.LABEL_EDIT_TOOL: {
                LabelEditTool labelEditTool = (LabelEditTool) theEObject;
                T result = this.caseLabelEditTool(labelEditTool);
                if (result == null)
                    result = this.caseTool(labelEditTool);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case DiagramPackage.NODE_TOOL: {
                NodeTool nodeTool = (NodeTool) theEObject;
                T result = this.caseNodeTool(nodeTool);
                if (result == null)
                    result = this.caseTool(nodeTool);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case DiagramPackage.SOURCE_EDGE_END_RECONNECTION_TOOL: {
                SourceEdgeEndReconnectionTool sourceEdgeEndReconnectionTool = (SourceEdgeEndReconnectionTool) theEObject;
                T result = this.caseSourceEdgeEndReconnectionTool(sourceEdgeEndReconnectionTool);
                if (result == null)
                    result = this.caseEdgeReconnectionTool(sourceEdgeEndReconnectionTool);
                if (result == null)
                    result = this.caseTool(sourceEdgeEndReconnectionTool);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case DiagramPackage.TARGET_EDGE_END_RECONNECTION_TOOL: {
                TargetEdgeEndReconnectionTool targetEdgeEndReconnectionTool = (TargetEdgeEndReconnectionTool) theEObject;
                T result = this.caseTargetEdgeEndReconnectionTool(targetEdgeEndReconnectionTool);
                if (result == null)
                    result = this.caseEdgeReconnectionTool(targetEdgeEndReconnectionTool);
                if (result == null)
                    result = this.caseTool(targetEdgeEndReconnectionTool);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case DiagramPackage.CREATE_VIEW: {
                CreateView createView = (CreateView) theEObject;
                T result = this.caseCreateView(createView);
                if (result == null)
                    result = this.caseOperation(createView);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case DiagramPackage.DELETE_VIEW: {
                DeleteView deleteView = (DeleteView) theEObject;
                T result = this.caseDeleteView(deleteView);
                if (result == null)
                    result = this.caseOperation(deleteView);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case DiagramPackage.SELECTION_DESCRIPTION: {
                SelectionDescription selectionDescription = (SelectionDescription) theEObject;
                T result = this.caseSelectionDescription(selectionDescription);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case DiagramPackage.TOOL_SECTION: {
                ToolSection toolSection = (ToolSection) theEObject;
                T result = this.caseToolSection(toolSection);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case DiagramPackage.DIAGRAM_TOOL_SECTION: {
                DiagramToolSection diagramToolSection = (DiagramToolSection) theEObject;
                T result = this.caseDiagramToolSection(diagramToolSection);
                if (result == null)
                    result = this.caseToolSection(diagramToolSection);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case DiagramPackage.NODE_TOOL_SECTION: {
                NodeToolSection nodeToolSection = (NodeToolSection) theEObject;
                T result = this.caseNodeToolSection(nodeToolSection);
                if (result == null)
                    result = this.caseToolSection(nodeToolSection);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case DiagramPackage.EDGE_TOOL_SECTION: {
                EdgeToolSection edgeToolSection = (EdgeToolSection) theEObject;
                T result = this.caseEdgeToolSection(edgeToolSection);
                if (result == null)
                    result = this.caseToolSection(edgeToolSection);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            default:
                return this.defaultCase(theEObject);
        }
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Description</em>'. <!-- begin-user-doc -->
     * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Description</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseDiagramDescription(DiagramDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Element Description</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Element Description</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseDiagramElementDescription(DiagramElementDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Node Description</em>'. <!-- begin-user-doc
     * --> This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc
     * -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Node Description</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseNodeDescription(NodeDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Edge Description</em>'. <!-- begin-user-doc
     * --> This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc
     * -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Edge Description</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseEdgeDescription(EdgeDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Layout Strategy Description</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Layout Strategy Description</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseLayoutStrategyDescription(LayoutStrategyDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>List Layout Strategy Description</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>List Layout Strategy Description</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseListLayoutStrategyDescription(ListLayoutStrategyDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Free Form Layout Strategy Description</em>'.
     * <!-- begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Free Form Layout Strategy Description</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseFreeFormLayoutStrategyDescription(FreeFormLayoutStrategyDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Style</em>'. <!-- begin-user-doc --> This
     * implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Style</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseStyle(Style object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Border Style</em>'. <!-- begin-user-doc -->
     * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Border Style</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseBorderStyle(BorderStyle object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Node Style Description</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Node Style Description</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseNodeStyleDescription(NodeStyleDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Conditional Node Style</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Conditional Node Style</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseConditionalNodeStyle(ConditionalNodeStyle object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Rectangular Node Style Description</em>'.
     * <!-- begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Rectangular Node Style Description</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseRectangularNodeStyleDescription(RectangularNodeStyleDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Image Node Style Description</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Image Node Style Description</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseImageNodeStyleDescription(ImageNodeStyleDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Icon Label Node Style Description</em>'.
     * <!-- begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Icon Label Node Style Description</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseIconLabelNodeStyleDescription(IconLabelNodeStyleDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Edge Style</em>'. <!-- begin-user-doc -->
     * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Edge Style</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseEdgeStyle(EdgeStyle object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Conditional Edge Style</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Conditional Edge Style</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseConditionalEdgeStyle(ConditionalEdgeStyle object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Palette</em>'. <!-- begin-user-doc --> This
     * implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Palette</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseDiagramPalette(DiagramPalette object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Node Palette</em>'. <!-- begin-user-doc -->
     * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Node Palette</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseNodePalette(NodePalette object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Edge Palette</em>'. <!-- begin-user-doc -->
     * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Edge Palette</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseEdgePalette(EdgePalette object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Tool</em>'. <!-- begin-user-doc --> This
     * implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Tool</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseTool(Tool object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Delete Tool</em>'. <!-- begin-user-doc -->
     * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Delete Tool</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseDeleteTool(DeleteTool object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Drop Tool</em>'. <!-- begin-user-doc -->
     * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Drop Tool</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseDropTool(DropTool object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Edge Tool</em>'. <!-- begin-user-doc -->
     * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Edge Tool</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseEdgeTool(EdgeTool object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Edge Reconnection Tool</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Edge Reconnection Tool</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseEdgeReconnectionTool(EdgeReconnectionTool object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Label Edit Tool</em>'. <!-- begin-user-doc
     * --> This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc
     * -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Label Edit Tool</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseLabelEditTool(LabelEditTool object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Node Tool</em>'. <!-- begin-user-doc -->
     * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Node Tool</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseNodeTool(NodeTool object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Source Edge End Reconnection Tool</em>'.
     * <!-- begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Source Edge End Reconnection Tool</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseSourceEdgeEndReconnectionTool(SourceEdgeEndReconnectionTool object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Target Edge End Reconnection Tool</em>'.
     * <!-- begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Target Edge End Reconnection Tool</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseTargetEdgeEndReconnectionTool(TargetEdgeEndReconnectionTool object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Create View</em>'. <!-- begin-user-doc -->
     * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Create View</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseCreateView(CreateView object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Delete View</em>'. <!-- begin-user-doc -->
     * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Delete View</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseDeleteView(DeleteView object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Selection Description</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Selection Description</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseSelectionDescription(SelectionDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Tool Section</em>'. <!-- begin-user-doc -->
     * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Tool Section</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseToolSection(ToolSection object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Tool Section</em>'. <!-- begin-user-doc -->
     * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Tool Section</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseDiagramToolSection(DiagramToolSection object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Node Tool Section</em>'. <!-- begin-user-doc
     * --> This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc
     * -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Node Tool Section</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseNodeToolSection(NodeToolSection object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Edge Tool Section</em>'. <!-- begin-user-doc
     * --> This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc
     * -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Edge Tool Section</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseEdgeToolSection(EdgeToolSection object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Representation Description</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Representation Description</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseRepresentationDescription(RepresentationDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Label Style</em>'. <!-- begin-user-doc -->
     * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Label Style</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseLabelStyle(LabelStyle object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Conditional</em>'. <!-- begin-user-doc -->
     * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Conditional</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseConditional(Conditional object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Operation</em>'. <!-- begin-user-doc -->
     * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Operation</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseOperation(Operation object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>EObject</em>'. <!-- begin-user-doc --> This
     * implementation returns null; returning a non-null result will terminate the switch, but this is the last case
     * anyway. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject)
     * @generated
     */
    @Override
    public T defaultCase(EObject object) {
        return null;
    }

} // DiagramSwitch
