/*******************************************************************************
 * Copyright (c) 2021, 2023 Obeo.
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
package org.eclipse.sirius.components.view.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;
import org.eclipse.sirius.components.view.BarChartDescription;
import org.eclipse.sirius.components.view.BarChartDescriptionStyle;
import org.eclipse.sirius.components.view.BorderStyle;
import org.eclipse.sirius.components.view.ButtonDescription;
import org.eclipse.sirius.components.view.ButtonDescriptionStyle;
import org.eclipse.sirius.components.view.ChangeContext;
import org.eclipse.sirius.components.view.CheckboxDescription;
import org.eclipse.sirius.components.view.CheckboxDescriptionStyle;
import org.eclipse.sirius.components.view.ColorPalette;
import org.eclipse.sirius.components.view.Conditional;
import org.eclipse.sirius.components.view.ConditionalBarChartDescriptionStyle;
import org.eclipse.sirius.components.view.ConditionalButtonDescriptionStyle;
import org.eclipse.sirius.components.view.ConditionalCheckboxDescriptionStyle;
import org.eclipse.sirius.components.view.ConditionalEdgeStyle;
import org.eclipse.sirius.components.view.ConditionalLabelDescriptionStyle;
import org.eclipse.sirius.components.view.ConditionalLinkDescriptionStyle;
import org.eclipse.sirius.components.view.ConditionalListDescriptionStyle;
import org.eclipse.sirius.components.view.ConditionalMultiSelectDescriptionStyle;
import org.eclipse.sirius.components.view.ConditionalNodeStyle;
import org.eclipse.sirius.components.view.ConditionalPieChartDescriptionStyle;
import org.eclipse.sirius.components.view.ConditionalRadioDescriptionStyle;
import org.eclipse.sirius.components.view.ConditionalSelectDescriptionStyle;
import org.eclipse.sirius.components.view.ConditionalTextareaDescriptionStyle;
import org.eclipse.sirius.components.view.ConditionalTextfieldDescriptionStyle;
import org.eclipse.sirius.components.view.CreateInstance;
import org.eclipse.sirius.components.view.CreateView;
import org.eclipse.sirius.components.view.DSelectWidgetDescription;
import org.eclipse.sirius.components.view.DTextFieldWidgetDescription;
import org.eclipse.sirius.components.view.DValidationMessageDescription;
import org.eclipse.sirius.components.view.DWidgetDescription;
import org.eclipse.sirius.components.view.DWidgetObjectOutputDescription;
import org.eclipse.sirius.components.view.DWidgetOutputDescription;
import org.eclipse.sirius.components.view.DWidgetStringOutputDescription;
import org.eclipse.sirius.components.view.DeleteElement;
import org.eclipse.sirius.components.view.DeleteTool;
import org.eclipse.sirius.components.view.DeleteView;
import org.eclipse.sirius.components.view.DiagramDescription;
import org.eclipse.sirius.components.view.DiagramElementDescription;
import org.eclipse.sirius.components.view.DiagramPalette;
import org.eclipse.sirius.components.view.DropTool;
import org.eclipse.sirius.components.view.DynamicDialogDescription;
import org.eclipse.sirius.components.view.DynamicDialogFolder;
import org.eclipse.sirius.components.view.EdgeDescription;
import org.eclipse.sirius.components.view.EdgePalette;
import org.eclipse.sirius.components.view.EdgeReconnectionTool;
import org.eclipse.sirius.components.view.EdgeStyle;
import org.eclipse.sirius.components.view.EdgeTool;
import org.eclipse.sirius.components.view.FixedColor;
import org.eclipse.sirius.components.view.FlexboxContainerDescription;
import org.eclipse.sirius.components.view.FormDescription;
import org.eclipse.sirius.components.view.FreeFormLayoutStrategyDescription;
import org.eclipse.sirius.components.view.GroupDescription;
import org.eclipse.sirius.components.view.IconLabelNodeStyleDescription;
import org.eclipse.sirius.components.view.ImageDescription;
import org.eclipse.sirius.components.view.ImageNodeStyleDescription;
import org.eclipse.sirius.components.view.LabelDescription;
import org.eclipse.sirius.components.view.LabelDescriptionStyle;
import org.eclipse.sirius.components.view.LabelEditTool;
import org.eclipse.sirius.components.view.LabelStyle;
import org.eclipse.sirius.components.view.LayoutStrategyDescription;
import org.eclipse.sirius.components.view.LinkDescription;
import org.eclipse.sirius.components.view.LinkDescriptionStyle;
import org.eclipse.sirius.components.view.ListDescription;
import org.eclipse.sirius.components.view.ListDescriptionStyle;
import org.eclipse.sirius.components.view.ListLayoutStrategyDescription;
import org.eclipse.sirius.components.view.MultiSelectDescription;
import org.eclipse.sirius.components.view.MultiSelectDescriptionStyle;
import org.eclipse.sirius.components.view.NodeDescription;
import org.eclipse.sirius.components.view.NodePalette;
import org.eclipse.sirius.components.view.NodeStyleDescription;
import org.eclipse.sirius.components.view.NodeTool;
import org.eclipse.sirius.components.view.Operation;
import org.eclipse.sirius.components.view.PageDescription;
import org.eclipse.sirius.components.view.PieChartDescription;
import org.eclipse.sirius.components.view.PieChartDescriptionStyle;
import org.eclipse.sirius.components.view.RadioDescription;
import org.eclipse.sirius.components.view.RadioDescriptionStyle;
import org.eclipse.sirius.components.view.RectangularNodeStyleDescription;
import org.eclipse.sirius.components.view.RepresentationDescription;
import org.eclipse.sirius.components.view.RichTextDescription;
import org.eclipse.sirius.components.view.SelectDescription;
import org.eclipse.sirius.components.view.SelectDescriptionStyle;
import org.eclipse.sirius.components.view.SelectionDescription;
import org.eclipse.sirius.components.view.SetValue;
import org.eclipse.sirius.components.view.SourceEdgeEndReconnectionTool;
import org.eclipse.sirius.components.view.Style;
import org.eclipse.sirius.components.view.TargetEdgeEndReconnectionTool;
import org.eclipse.sirius.components.view.TextAreaDescription;
import org.eclipse.sirius.components.view.TextareaDescriptionStyle;
import org.eclipse.sirius.components.view.TextfieldDescription;
import org.eclipse.sirius.components.view.TextfieldDescriptionStyle;
import org.eclipse.sirius.components.view.Tool;
import org.eclipse.sirius.components.view.UnsetValue;
import org.eclipse.sirius.components.view.UserColor;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.ViewPackage;
import org.eclipse.sirius.components.view.WidgetDescription;
import org.eclipse.sirius.components.view.WidgetDescriptionStyle;

/**
 * <!-- begin-user-doc --> The <b>Switch</b> for the model's inheritance hierarchy. It supports the call
 * {@link #doSwitch(EObject) doSwitch(object)} to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object and proceeding up the inheritance hierarchy until a non-null result is
 * returned, which is the result of the switch. <!-- end-user-doc -->
 *
 * @see org.eclipse.sirius.components.view.ViewPackage
 * @generated
 */
public class ViewSwitch<T> extends Switch<T> {
    /**
     * The cached model package <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected static ViewPackage modelPackage;

    /**
     * Creates an instance of the switch. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public ViewSwitch() {
        if (modelPackage == null) {
            modelPackage = ViewPackage.eINSTANCE;
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
            case ViewPackage.VIEW: {
                View view = (View) theEObject;
                T result = this.caseView(view);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.COLOR_PALETTE: {
                ColorPalette colorPalette = (ColorPalette) theEObject;
                T result = this.caseColorPalette(colorPalette);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.FIXED_COLOR: {
                FixedColor fixedColor = (FixedColor) theEObject;
                T result = this.caseFixedColor(fixedColor);
                if (result == null)
                    result = this.caseUserColor(fixedColor);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.USER_COLOR: {
                UserColor userColor = (UserColor) theEObject;
                T result = this.caseUserColor(userColor);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.REPRESENTATION_DESCRIPTION: {
                RepresentationDescription representationDescription = (RepresentationDescription) theEObject;
                T result = this.caseRepresentationDescription(representationDescription);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.DIAGRAM_DESCRIPTION: {
                DiagramDescription diagramDescription = (DiagramDescription) theEObject;
                T result = this.caseDiagramDescription(diagramDescription);
                if (result == null)
                    result = this.caseRepresentationDescription(diagramDescription);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.DIAGRAM_ELEMENT_DESCRIPTION: {
                DiagramElementDescription diagramElementDescription = (DiagramElementDescription) theEObject;
                T result = this.caseDiagramElementDescription(diagramElementDescription);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.NODE_DESCRIPTION: {
                NodeDescription nodeDescription = (NodeDescription) theEObject;
                T result = this.caseNodeDescription(nodeDescription);
                if (result == null)
                    result = this.caseDiagramElementDescription(nodeDescription);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.EDGE_DESCRIPTION: {
                EdgeDescription edgeDescription = (EdgeDescription) theEObject;
                T result = this.caseEdgeDescription(edgeDescription);
                if (result == null)
                    result = this.caseDiagramElementDescription(edgeDescription);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.LABEL_STYLE: {
                LabelStyle labelStyle = (LabelStyle) theEObject;
                T result = this.caseLabelStyle(labelStyle);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.BORDER_STYLE: {
                BorderStyle borderStyle = (BorderStyle) theEObject;
                T result = this.caseBorderStyle(borderStyle);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.STYLE: {
                Style style = (Style) theEObject;
                T result = this.caseStyle(style);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.NODE_STYLE_DESCRIPTION: {
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
            case ViewPackage.RECTANGULAR_NODE_STYLE_DESCRIPTION: {
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
            case ViewPackage.IMAGE_NODE_STYLE_DESCRIPTION: {
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
            case ViewPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION: {
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
            case ViewPackage.LAYOUT_STRATEGY_DESCRIPTION: {
                LayoutStrategyDescription layoutStrategyDescription = (LayoutStrategyDescription) theEObject;
                T result = this.caseLayoutStrategyDescription(layoutStrategyDescription);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.FREE_FORM_LAYOUT_STRATEGY_DESCRIPTION: {
                FreeFormLayoutStrategyDescription freeFormLayoutStrategyDescription = (FreeFormLayoutStrategyDescription) theEObject;
                T result = this.caseFreeFormLayoutStrategyDescription(freeFormLayoutStrategyDescription);
                if (result == null)
                    result = this.caseLayoutStrategyDescription(freeFormLayoutStrategyDescription);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION: {
                ListLayoutStrategyDescription listLayoutStrategyDescription = (ListLayoutStrategyDescription) theEObject;
                T result = this.caseListLayoutStrategyDescription(listLayoutStrategyDescription);
                if (result == null)
                    result = this.caseLayoutStrategyDescription(listLayoutStrategyDescription);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.EDGE_STYLE: {
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
            case ViewPackage.TOOL: {
                Tool tool = (Tool) theEObject;
                T result = this.caseTool(tool);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.LABEL_EDIT_TOOL: {
                LabelEditTool labelEditTool = (LabelEditTool) theEObject;
                T result = this.caseLabelEditTool(labelEditTool);
                if (result == null)
                    result = this.caseTool(labelEditTool);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.DELETE_TOOL: {
                DeleteTool deleteTool = (DeleteTool) theEObject;
                T result = this.caseDeleteTool(deleteTool);
                if (result == null)
                    result = this.caseTool(deleteTool);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.NODE_TOOL: {
                NodeTool nodeTool = (NodeTool) theEObject;
                T result = this.caseNodeTool(nodeTool);
                if (result == null)
                    result = this.caseTool(nodeTool);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.EDGE_TOOL: {
                EdgeTool edgeTool = (EdgeTool) theEObject;
                T result = this.caseEdgeTool(edgeTool);
                if (result == null)
                    result = this.caseTool(edgeTool);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.EDGE_RECONNECTION_TOOL: {
                EdgeReconnectionTool edgeReconnectionTool = (EdgeReconnectionTool) theEObject;
                T result = this.caseEdgeReconnectionTool(edgeReconnectionTool);
                if (result == null)
                    result = this.caseTool(edgeReconnectionTool);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.SOURCE_EDGE_END_RECONNECTION_TOOL: {
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
            case ViewPackage.TARGET_EDGE_END_RECONNECTION_TOOL: {
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
            case ViewPackage.DROP_TOOL: {
                DropTool dropTool = (DropTool) theEObject;
                T result = this.caseDropTool(dropTool);
                if (result == null)
                    result = this.caseTool(dropTool);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.OPERATION: {
                Operation operation = (Operation) theEObject;
                T result = this.caseOperation(operation);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.CHANGE_CONTEXT: {
                ChangeContext changeContext = (ChangeContext) theEObject;
                T result = this.caseChangeContext(changeContext);
                if (result == null)
                    result = this.caseOperation(changeContext);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.CREATE_INSTANCE: {
                CreateInstance createInstance = (CreateInstance) theEObject;
                T result = this.caseCreateInstance(createInstance);
                if (result == null)
                    result = this.caseOperation(createInstance);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.SET_VALUE: {
                SetValue setValue = (SetValue) theEObject;
                T result = this.caseSetValue(setValue);
                if (result == null)
                    result = this.caseOperation(setValue);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.UNSET_VALUE: {
                UnsetValue unsetValue = (UnsetValue) theEObject;
                T result = this.caseUnsetValue(unsetValue);
                if (result == null)
                    result = this.caseOperation(unsetValue);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.DELETE_ELEMENT: {
                DeleteElement deleteElement = (DeleteElement) theEObject;
                T result = this.caseDeleteElement(deleteElement);
                if (result == null)
                    result = this.caseOperation(deleteElement);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.CREATE_VIEW: {
                CreateView createView = (CreateView) theEObject;
                T result = this.caseCreateView(createView);
                if (result == null)
                    result = this.caseOperation(createView);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.DELETE_VIEW: {
                DeleteView deleteView = (DeleteView) theEObject;
                T result = this.caseDeleteView(deleteView);
                if (result == null)
                    result = this.caseOperation(deleteView);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.CONDITIONAL: {
                Conditional conditional = (Conditional) theEObject;
                T result = this.caseConditional(conditional);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.CONDITIONAL_NODE_STYLE: {
                ConditionalNodeStyle conditionalNodeStyle = (ConditionalNodeStyle) theEObject;
                T result = this.caseConditionalNodeStyle(conditionalNodeStyle);
                if (result == null)
                    result = this.caseConditional(conditionalNodeStyle);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.CONDITIONAL_EDGE_STYLE: {
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
            case ViewPackage.FORM_DESCRIPTION: {
                FormDescription formDescription = (FormDescription) theEObject;
                T result = this.caseFormDescription(formDescription);
                if (result == null)
                    result = this.caseRepresentationDescription(formDescription);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.PAGE_DESCRIPTION: {
                PageDescription pageDescription = (PageDescription) theEObject;
                T result = this.casePageDescription(pageDescription);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.GROUP_DESCRIPTION: {
                GroupDescription groupDescription = (GroupDescription) theEObject;
                T result = this.caseGroupDescription(groupDescription);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.WIDGET_DESCRIPTION: {
                WidgetDescription widgetDescription = (WidgetDescription) theEObject;
                T result = this.caseWidgetDescription(widgetDescription);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.TEXTFIELD_DESCRIPTION: {
                TextfieldDescription textfieldDescription = (TextfieldDescription) theEObject;
                T result = this.caseTextfieldDescription(textfieldDescription);
                if (result == null)
                    result = this.caseWidgetDescription(textfieldDescription);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.CHECKBOX_DESCRIPTION: {
                CheckboxDescription checkboxDescription = (CheckboxDescription) theEObject;
                T result = this.caseCheckboxDescription(checkboxDescription);
                if (result == null)
                    result = this.caseWidgetDescription(checkboxDescription);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.SELECT_DESCRIPTION: {
                SelectDescription selectDescription = (SelectDescription) theEObject;
                T result = this.caseSelectDescription(selectDescription);
                if (result == null)
                    result = this.caseWidgetDescription(selectDescription);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.MULTI_SELECT_DESCRIPTION: {
                MultiSelectDescription multiSelectDescription = (MultiSelectDescription) theEObject;
                T result = this.caseMultiSelectDescription(multiSelectDescription);
                if (result == null)
                    result = this.caseWidgetDescription(multiSelectDescription);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.TEXT_AREA_DESCRIPTION: {
                TextAreaDescription textAreaDescription = (TextAreaDescription) theEObject;
                T result = this.caseTextAreaDescription(textAreaDescription);
                if (result == null)
                    result = this.caseWidgetDescription(textAreaDescription);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.RICH_TEXT_DESCRIPTION: {
                RichTextDescription richTextDescription = (RichTextDescription) theEObject;
                T result = this.caseRichTextDescription(richTextDescription);
                if (result == null)
                    result = this.caseWidgetDescription(richTextDescription);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.RADIO_DESCRIPTION: {
                RadioDescription radioDescription = (RadioDescription) theEObject;
                T result = this.caseRadioDescription(radioDescription);
                if (result == null)
                    result = this.caseWidgetDescription(radioDescription);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.BAR_CHART_DESCRIPTION: {
                BarChartDescription barChartDescription = (BarChartDescription) theEObject;
                T result = this.caseBarChartDescription(barChartDescription);
                if (result == null)
                    result = this.caseWidgetDescription(barChartDescription);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.PIE_CHART_DESCRIPTION: {
                PieChartDescription pieChartDescription = (PieChartDescription) theEObject;
                T result = this.casePieChartDescription(pieChartDescription);
                if (result == null)
                    result = this.caseWidgetDescription(pieChartDescription);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.FLEXBOX_CONTAINER_DESCRIPTION: {
                FlexboxContainerDescription flexboxContainerDescription = (FlexboxContainerDescription) theEObject;
                T result = this.caseFlexboxContainerDescription(flexboxContainerDescription);
                if (result == null)
                    result = this.caseWidgetDescription(flexboxContainerDescription);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.BUTTON_DESCRIPTION: {
                ButtonDescription buttonDescription = (ButtonDescription) theEObject;
                T result = this.caseButtonDescription(buttonDescription);
                if (result == null)
                    result = this.caseWidgetDescription(buttonDescription);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.IMAGE_DESCRIPTION: {
                ImageDescription imageDescription = (ImageDescription) theEObject;
                T result = this.caseImageDescription(imageDescription);
                if (result == null)
                    result = this.caseWidgetDescription(imageDescription);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.WIDGET_DESCRIPTION_STYLE: {
                WidgetDescriptionStyle widgetDescriptionStyle = (WidgetDescriptionStyle) theEObject;
                T result = this.caseWidgetDescriptionStyle(widgetDescriptionStyle);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.TEXTFIELD_DESCRIPTION_STYLE: {
                TextfieldDescriptionStyle textfieldDescriptionStyle = (TextfieldDescriptionStyle) theEObject;
                T result = this.caseTextfieldDescriptionStyle(textfieldDescriptionStyle);
                if (result == null)
                    result = this.caseWidgetDescriptionStyle(textfieldDescriptionStyle);
                if (result == null)
                    result = this.caseLabelStyle(textfieldDescriptionStyle);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.CONDITIONAL_TEXTFIELD_DESCRIPTION_STYLE: {
                ConditionalTextfieldDescriptionStyle conditionalTextfieldDescriptionStyle = (ConditionalTextfieldDescriptionStyle) theEObject;
                T result = this.caseConditionalTextfieldDescriptionStyle(conditionalTextfieldDescriptionStyle);
                if (result == null)
                    result = this.caseConditional(conditionalTextfieldDescriptionStyle);
                if (result == null)
                    result = this.caseTextfieldDescriptionStyle(conditionalTextfieldDescriptionStyle);
                if (result == null)
                    result = this.caseWidgetDescriptionStyle(conditionalTextfieldDescriptionStyle);
                if (result == null)
                    result = this.caseLabelStyle(conditionalTextfieldDescriptionStyle);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.CHECKBOX_DESCRIPTION_STYLE: {
                CheckboxDescriptionStyle checkboxDescriptionStyle = (CheckboxDescriptionStyle) theEObject;
                T result = this.caseCheckboxDescriptionStyle(checkboxDescriptionStyle);
                if (result == null)
                    result = this.caseWidgetDescriptionStyle(checkboxDescriptionStyle);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.CONDITIONAL_CHECKBOX_DESCRIPTION_STYLE: {
                ConditionalCheckboxDescriptionStyle conditionalCheckboxDescriptionStyle = (ConditionalCheckboxDescriptionStyle) theEObject;
                T result = this.caseConditionalCheckboxDescriptionStyle(conditionalCheckboxDescriptionStyle);
                if (result == null)
                    result = this.caseConditional(conditionalCheckboxDescriptionStyle);
                if (result == null)
                    result = this.caseCheckboxDescriptionStyle(conditionalCheckboxDescriptionStyle);
                if (result == null)
                    result = this.caseWidgetDescriptionStyle(conditionalCheckboxDescriptionStyle);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.SELECT_DESCRIPTION_STYLE: {
                SelectDescriptionStyle selectDescriptionStyle = (SelectDescriptionStyle) theEObject;
                T result = this.caseSelectDescriptionStyle(selectDescriptionStyle);
                if (result == null)
                    result = this.caseWidgetDescriptionStyle(selectDescriptionStyle);
                if (result == null)
                    result = this.caseLabelStyle(selectDescriptionStyle);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.CONDITIONAL_SELECT_DESCRIPTION_STYLE: {
                ConditionalSelectDescriptionStyle conditionalSelectDescriptionStyle = (ConditionalSelectDescriptionStyle) theEObject;
                T result = this.caseConditionalSelectDescriptionStyle(conditionalSelectDescriptionStyle);
                if (result == null)
                    result = this.caseConditional(conditionalSelectDescriptionStyle);
                if (result == null)
                    result = this.caseSelectDescriptionStyle(conditionalSelectDescriptionStyle);
                if (result == null)
                    result = this.caseWidgetDescriptionStyle(conditionalSelectDescriptionStyle);
                if (result == null)
                    result = this.caseLabelStyle(conditionalSelectDescriptionStyle);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.MULTI_SELECT_DESCRIPTION_STYLE: {
                MultiSelectDescriptionStyle multiSelectDescriptionStyle = (MultiSelectDescriptionStyle) theEObject;
                T result = this.caseMultiSelectDescriptionStyle(multiSelectDescriptionStyle);
                if (result == null)
                    result = this.caseWidgetDescriptionStyle(multiSelectDescriptionStyle);
                if (result == null)
                    result = this.caseLabelStyle(multiSelectDescriptionStyle);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.CONDITIONAL_MULTI_SELECT_DESCRIPTION_STYLE: {
                ConditionalMultiSelectDescriptionStyle conditionalMultiSelectDescriptionStyle = (ConditionalMultiSelectDescriptionStyle) theEObject;
                T result = this.caseConditionalMultiSelectDescriptionStyle(conditionalMultiSelectDescriptionStyle);
                if (result == null)
                    result = this.caseConditional(conditionalMultiSelectDescriptionStyle);
                if (result == null)
                    result = this.caseMultiSelectDescriptionStyle(conditionalMultiSelectDescriptionStyle);
                if (result == null)
                    result = this.caseWidgetDescriptionStyle(conditionalMultiSelectDescriptionStyle);
                if (result == null)
                    result = this.caseLabelStyle(conditionalMultiSelectDescriptionStyle);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.TEXTAREA_DESCRIPTION_STYLE: {
                TextareaDescriptionStyle textareaDescriptionStyle = (TextareaDescriptionStyle) theEObject;
                T result = this.caseTextareaDescriptionStyle(textareaDescriptionStyle);
                if (result == null)
                    result = this.caseWidgetDescriptionStyle(textareaDescriptionStyle);
                if (result == null)
                    result = this.caseLabelStyle(textareaDescriptionStyle);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE: {
                ConditionalTextareaDescriptionStyle conditionalTextareaDescriptionStyle = (ConditionalTextareaDescriptionStyle) theEObject;
                T result = this.caseConditionalTextareaDescriptionStyle(conditionalTextareaDescriptionStyle);
                if (result == null)
                    result = this.caseConditional(conditionalTextareaDescriptionStyle);
                if (result == null)
                    result = this.caseTextareaDescriptionStyle(conditionalTextareaDescriptionStyle);
                if (result == null)
                    result = this.caseWidgetDescriptionStyle(conditionalTextareaDescriptionStyle);
                if (result == null)
                    result = this.caseLabelStyle(conditionalTextareaDescriptionStyle);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.RADIO_DESCRIPTION_STYLE: {
                RadioDescriptionStyle radioDescriptionStyle = (RadioDescriptionStyle) theEObject;
                T result = this.caseRadioDescriptionStyle(radioDescriptionStyle);
                if (result == null)
                    result = this.caseWidgetDescriptionStyle(radioDescriptionStyle);
                if (result == null)
                    result = this.caseLabelStyle(radioDescriptionStyle);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.CONDITIONAL_RADIO_DESCRIPTION_STYLE: {
                ConditionalRadioDescriptionStyle conditionalRadioDescriptionStyle = (ConditionalRadioDescriptionStyle) theEObject;
                T result = this.caseConditionalRadioDescriptionStyle(conditionalRadioDescriptionStyle);
                if (result == null)
                    result = this.caseConditional(conditionalRadioDescriptionStyle);
                if (result == null)
                    result = this.caseRadioDescriptionStyle(conditionalRadioDescriptionStyle);
                if (result == null)
                    result = this.caseWidgetDescriptionStyle(conditionalRadioDescriptionStyle);
                if (result == null)
                    result = this.caseLabelStyle(conditionalRadioDescriptionStyle);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.BUTTON_DESCRIPTION_STYLE: {
                ButtonDescriptionStyle buttonDescriptionStyle = (ButtonDescriptionStyle) theEObject;
                T result = this.caseButtonDescriptionStyle(buttonDescriptionStyle);
                if (result == null)
                    result = this.caseWidgetDescriptionStyle(buttonDescriptionStyle);
                if (result == null)
                    result = this.caseLabelStyle(buttonDescriptionStyle);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.CONDITIONAL_BUTTON_DESCRIPTION_STYLE: {
                ConditionalButtonDescriptionStyle conditionalButtonDescriptionStyle = (ConditionalButtonDescriptionStyle) theEObject;
                T result = this.caseConditionalButtonDescriptionStyle(conditionalButtonDescriptionStyle);
                if (result == null)
                    result = this.caseConditional(conditionalButtonDescriptionStyle);
                if (result == null)
                    result = this.caseButtonDescriptionStyle(conditionalButtonDescriptionStyle);
                if (result == null)
                    result = this.caseWidgetDescriptionStyle(conditionalButtonDescriptionStyle);
                if (result == null)
                    result = this.caseLabelStyle(conditionalButtonDescriptionStyle);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.BAR_CHART_DESCRIPTION_STYLE: {
                BarChartDescriptionStyle barChartDescriptionStyle = (BarChartDescriptionStyle) theEObject;
                T result = this.caseBarChartDescriptionStyle(barChartDescriptionStyle);
                if (result == null)
                    result = this.caseWidgetDescriptionStyle(barChartDescriptionStyle);
                if (result == null)
                    result = this.caseLabelStyle(barChartDescriptionStyle);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.CONDITIONAL_BAR_CHART_DESCRIPTION_STYLE: {
                ConditionalBarChartDescriptionStyle conditionalBarChartDescriptionStyle = (ConditionalBarChartDescriptionStyle) theEObject;
                T result = this.caseConditionalBarChartDescriptionStyle(conditionalBarChartDescriptionStyle);
                if (result == null)
                    result = this.caseConditional(conditionalBarChartDescriptionStyle);
                if (result == null)
                    result = this.caseBarChartDescriptionStyle(conditionalBarChartDescriptionStyle);
                if (result == null)
                    result = this.caseWidgetDescriptionStyle(conditionalBarChartDescriptionStyle);
                if (result == null)
                    result = this.caseLabelStyle(conditionalBarChartDescriptionStyle);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.PIE_CHART_DESCRIPTION_STYLE: {
                PieChartDescriptionStyle pieChartDescriptionStyle = (PieChartDescriptionStyle) theEObject;
                T result = this.casePieChartDescriptionStyle(pieChartDescriptionStyle);
                if (result == null)
                    result = this.caseWidgetDescriptionStyle(pieChartDescriptionStyle);
                if (result == null)
                    result = this.caseLabelStyle(pieChartDescriptionStyle);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.CONDITIONAL_PIE_CHART_DESCRIPTION_STYLE: {
                ConditionalPieChartDescriptionStyle conditionalPieChartDescriptionStyle = (ConditionalPieChartDescriptionStyle) theEObject;
                T result = this.caseConditionalPieChartDescriptionStyle(conditionalPieChartDescriptionStyle);
                if (result == null)
                    result = this.caseConditional(conditionalPieChartDescriptionStyle);
                if (result == null)
                    result = this.casePieChartDescriptionStyle(conditionalPieChartDescriptionStyle);
                if (result == null)
                    result = this.caseWidgetDescriptionStyle(conditionalPieChartDescriptionStyle);
                if (result == null)
                    result = this.caseLabelStyle(conditionalPieChartDescriptionStyle);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.LABEL_DESCRIPTION: {
                LabelDescription labelDescription = (LabelDescription) theEObject;
                T result = this.caseLabelDescription(labelDescription);
                if (result == null)
                    result = this.caseWidgetDescription(labelDescription);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.LABEL_DESCRIPTION_STYLE: {
                LabelDescriptionStyle labelDescriptionStyle = (LabelDescriptionStyle) theEObject;
                T result = this.caseLabelDescriptionStyle(labelDescriptionStyle);
                if (result == null)
                    result = this.caseWidgetDescriptionStyle(labelDescriptionStyle);
                if (result == null)
                    result = this.caseLabelStyle(labelDescriptionStyle);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.CONDITIONAL_LABEL_DESCRIPTION_STYLE: {
                ConditionalLabelDescriptionStyle conditionalLabelDescriptionStyle = (ConditionalLabelDescriptionStyle) theEObject;
                T result = this.caseConditionalLabelDescriptionStyle(conditionalLabelDescriptionStyle);
                if (result == null)
                    result = this.caseConditional(conditionalLabelDescriptionStyle);
                if (result == null)
                    result = this.caseLabelDescriptionStyle(conditionalLabelDescriptionStyle);
                if (result == null)
                    result = this.caseWidgetDescriptionStyle(conditionalLabelDescriptionStyle);
                if (result == null)
                    result = this.caseLabelStyle(conditionalLabelDescriptionStyle);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.LINK_DESCRIPTION: {
                LinkDescription linkDescription = (LinkDescription) theEObject;
                T result = this.caseLinkDescription(linkDescription);
                if (result == null)
                    result = this.caseWidgetDescription(linkDescription);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.LINK_DESCRIPTION_STYLE: {
                LinkDescriptionStyle linkDescriptionStyle = (LinkDescriptionStyle) theEObject;
                T result = this.caseLinkDescriptionStyle(linkDescriptionStyle);
                if (result == null)
                    result = this.caseWidgetDescriptionStyle(linkDescriptionStyle);
                if (result == null)
                    result = this.caseLabelStyle(linkDescriptionStyle);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.CONDITIONAL_LINK_DESCRIPTION_STYLE: {
                ConditionalLinkDescriptionStyle conditionalLinkDescriptionStyle = (ConditionalLinkDescriptionStyle) theEObject;
                T result = this.caseConditionalLinkDescriptionStyle(conditionalLinkDescriptionStyle);
                if (result == null)
                    result = this.caseConditional(conditionalLinkDescriptionStyle);
                if (result == null)
                    result = this.caseLinkDescriptionStyle(conditionalLinkDescriptionStyle);
                if (result == null)
                    result = this.caseWidgetDescriptionStyle(conditionalLinkDescriptionStyle);
                if (result == null)
                    result = this.caseLabelStyle(conditionalLinkDescriptionStyle);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.LIST_DESCRIPTION: {
                ListDescription listDescription = (ListDescription) theEObject;
                T result = this.caseListDescription(listDescription);
                if (result == null)
                    result = this.caseWidgetDescription(listDescription);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.LIST_DESCRIPTION_STYLE: {
                ListDescriptionStyle listDescriptionStyle = (ListDescriptionStyle) theEObject;
                T result = this.caseListDescriptionStyle(listDescriptionStyle);
                if (result == null)
                    result = this.caseWidgetDescriptionStyle(listDescriptionStyle);
                if (result == null)
                    result = this.caseLabelStyle(listDescriptionStyle);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.CONDITIONAL_LIST_DESCRIPTION_STYLE: {
                ConditionalListDescriptionStyle conditionalListDescriptionStyle = (ConditionalListDescriptionStyle) theEObject;
                T result = this.caseConditionalListDescriptionStyle(conditionalListDescriptionStyle);
                if (result == null)
                    result = this.caseConditional(conditionalListDescriptionStyle);
                if (result == null)
                    result = this.caseListDescriptionStyle(conditionalListDescriptionStyle);
                if (result == null)
                    result = this.caseWidgetDescriptionStyle(conditionalListDescriptionStyle);
                if (result == null)
                    result = this.caseLabelStyle(conditionalListDescriptionStyle);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.DIAGRAM_PALETTE: {
                DiagramPalette diagramPalette = (DiagramPalette) theEObject;
                T result = this.caseDiagramPalette(diagramPalette);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.NODE_PALETTE: {
                NodePalette nodePalette = (NodePalette) theEObject;
                T result = this.caseNodePalette(nodePalette);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.EDGE_PALETTE: {
                EdgePalette edgePalette = (EdgePalette) theEObject;
                T result = this.caseEdgePalette(edgePalette);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.SELECTION_DESCRIPTION: {
                SelectionDescription selectionDescription = (SelectionDescription) theEObject;
                T result = this.caseSelectionDescription(selectionDescription);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.DYNAMIC_DIALOG_FOLDER: {
                DynamicDialogFolder dynamicDialogFolder = (DynamicDialogFolder) theEObject;
                T result = this.caseDynamicDialogFolder(dynamicDialogFolder);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.DYNAMIC_DIALOG_DESCRIPTION: {
                DynamicDialogDescription dynamicDialogDescription = (DynamicDialogDescription) theEObject;
                T result = this.caseDynamicDialogDescription(dynamicDialogDescription);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.DWIDGET_DESCRIPTION: {
                DWidgetDescription dWidgetDescription = (DWidgetDescription) theEObject;
                T result = this.caseDWidgetDescription(dWidgetDescription);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.DWIDGET_OUTPUT_DESCRIPTION: {
                DWidgetOutputDescription dWidgetOutputDescription = (DWidgetOutputDescription) theEObject;
                T result = this.caseDWidgetOutputDescription(dWidgetOutputDescription);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.DWIDGET_OBJECT_OUTPUT_DESCRIPTION: {
                DWidgetObjectOutputDescription dWidgetObjectOutputDescription = (DWidgetObjectOutputDescription) theEObject;
                T result = this.caseDWidgetObjectOutputDescription(dWidgetObjectOutputDescription);
                if (result == null)
                    result = this.caseDWidgetOutputDescription(dWidgetObjectOutputDescription);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.DWIDGET_STRING_OUTPUT_DESCRIPTION: {
                DWidgetStringOutputDescription dWidgetStringOutputDescription = (DWidgetStringOutputDescription) theEObject;
                T result = this.caseDWidgetStringOutputDescription(dWidgetStringOutputDescription);
                if (result == null)
                    result = this.caseDWidgetOutputDescription(dWidgetStringOutputDescription);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.DSELECT_WIDGET_DESCRIPTION: {
                DSelectWidgetDescription dSelectWidgetDescription = (DSelectWidgetDescription) theEObject;
                T result = this.caseDSelectWidgetDescription(dSelectWidgetDescription);
                if (result == null)
                    result = this.caseDWidgetDescription(dSelectWidgetDescription);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.DTEXT_FIELD_WIDGET_DESCRIPTION: {
                DTextFieldWidgetDescription dTextFieldWidgetDescription = (DTextFieldWidgetDescription) theEObject;
                T result = this.caseDTextFieldWidgetDescription(dTextFieldWidgetDescription);
                if (result == null)
                    result = this.caseDWidgetDescription(dTextFieldWidgetDescription);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.DVALIDATION_MESSAGE_DESCRIPTION: {
                DValidationMessageDescription dValidationMessageDescription = (DValidationMessageDescription) theEObject;
                T result = this.caseDValidationMessageDescription(dValidationMessageDescription);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            default:
                return this.defaultCase(theEObject);
        }
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>View</em>'. <!-- begin-user-doc --> This
     * implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>View</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseView(View object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Color Palette</em>'. <!-- begin-user-doc -->
     * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Color Palette</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseColorPalette(ColorPalette object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Fixed Color</em>'. <!-- begin-user-doc -->
     * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Fixed Color</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseFixedColor(FixedColor object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>User Color</em>'. <!-- begin-user-doc -->
     * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>User Color</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseUserColor(UserColor object) {
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
     * Returns the result of interpreting the object as an instance of '<em>Diagram Description</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Diagram Description</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseDiagramDescription(DiagramDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Diagram Element Description</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Diagram Element Description</em>'.
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
     * Returns the result of interpreting the object as an instance of '<em>Change Context</em>'. <!-- begin-user-doc
     * --> This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc
     * -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Change Context</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseChangeContext(ChangeContext object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Create Instance</em>'. <!-- begin-user-doc
     * --> This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc
     * -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Create Instance</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseCreateInstance(CreateInstance object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Set Value</em>'. <!-- begin-user-doc -->
     * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Set Value</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseSetValue(SetValue object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Unset Value</em>'. <!-- begin-user-doc -->
     * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Unset Value</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseUnsetValue(UnsetValue object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Delete Element</em>'. <!-- begin-user-doc
     * --> This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc
     * -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Delete Element</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseDeleteElement(DeleteElement object) {
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
     * Returns the result of interpreting the object as an instance of '<em>Form Description</em>'. <!-- begin-user-doc
     * --> This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc
     * -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Form Description</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseFormDescription(FormDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Page Description</em>'. <!-- begin-user-doc
     * --> This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc
     * -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Page Description</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T casePageDescription(PageDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Group Description</em>'. <!-- begin-user-doc
     * --> This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc
     * -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Group Description</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseGroupDescription(GroupDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Widget Description</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Widget Description</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseWidgetDescription(WidgetDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Textfield Description</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Textfield Description</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseTextfieldDescription(TextfieldDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Checkbox Description</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Checkbox Description</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseCheckboxDescription(CheckboxDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Select Description</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Select Description</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseSelectDescription(SelectDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Multi Select Description</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Multi Select Description</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseMultiSelectDescription(MultiSelectDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Text Area Description</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Text Area Description</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseTextAreaDescription(TextAreaDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Rich Text Description</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Rich Text Description</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseRichTextDescription(RichTextDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Radio Description</em>'. <!-- begin-user-doc
     * --> This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc
     * -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Radio Description</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseRadioDescription(RadioDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Bar Chart Description</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Bar Chart Description</em>'.
     * @generated
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     */
    public T caseBarChartDescription(BarChartDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Pie Chart Description</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Pie Chart Description</em>'.
     * @generated
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     */
    public T casePieChartDescription(PieChartDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Flexbox Container Description</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Flexbox Container Description</em>'.
     * @generated
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     */
    public T caseFlexboxContainerDescription(FlexboxContainerDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Button Description</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Button Description</em>'.
     * @generated
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     */
    public T caseButtonDescription(ButtonDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Image Description</em>'. <!-- begin-user-doc
     * --> This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc
     * -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Image Description</em>'.
     * @generated
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     */
    public T caseImageDescription(ImageDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Widget Description Style</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Widget Description Style</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseWidgetDescriptionStyle(WidgetDescriptionStyle object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Textfield Description Style</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Textfield Description Style</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseTextfieldDescriptionStyle(TextfieldDescriptionStyle object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Conditional Textfield Description
     * Style</em>'. <!-- begin-user-doc --> This implementation returns null; returning a non-null result will terminate
     * the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Conditional Textfield Description
     *         Style</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseConditionalTextfieldDescriptionStyle(ConditionalTextfieldDescriptionStyle object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Checkbox Description Style</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Checkbox Description Style</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseCheckboxDescriptionStyle(CheckboxDescriptionStyle object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Conditional Checkbox Description
     * Style</em>'. <!-- begin-user-doc --> This implementation returns null; returning a non-null result will terminate
     * the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Conditional Checkbox Description
     *         Style</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseConditionalCheckboxDescriptionStyle(ConditionalCheckboxDescriptionStyle object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Select Description Style</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Select Description Style</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseSelectDescriptionStyle(SelectDescriptionStyle object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Conditional Select Description Style</em>'.
     * <!-- begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Conditional Select Description Style</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseConditionalSelectDescriptionStyle(ConditionalSelectDescriptionStyle object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Multi Select Description Style</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Multi Select Description Style</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseMultiSelectDescriptionStyle(MultiSelectDescriptionStyle object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Conditional Multi Select Description
     * Style</em>'. <!-- begin-user-doc --> This implementation returns null; returning a non-null result will terminate
     * the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Conditional Multi Select Description
     *         Style</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseConditionalMultiSelectDescriptionStyle(ConditionalMultiSelectDescriptionStyle object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Textarea Description Style</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Textarea Description Style</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseTextareaDescriptionStyle(TextareaDescriptionStyle object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Conditional Textarea Description
     * Style</em>'. <!-- begin-user-doc --> This implementation returns null; returning a non-null result will terminate
     * the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Conditional Textarea Description
     *         Style</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseConditionalTextareaDescriptionStyle(ConditionalTextareaDescriptionStyle object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Radio Description Style</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Radio Description Style</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseRadioDescriptionStyle(RadioDescriptionStyle object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Conditional Radio Description Style</em>'.
     * <!-- begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Conditional Radio Description Style</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseConditionalRadioDescriptionStyle(ConditionalRadioDescriptionStyle object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Button Description Style</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Button Description Style</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseButtonDescriptionStyle(ButtonDescriptionStyle object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Conditional Button Description Style</em>'.
     * <!-- begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Conditional Button Description Style</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseConditionalButtonDescriptionStyle(ConditionalButtonDescriptionStyle object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Bar Chart Description Style</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Bar Chart Description Style</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseBarChartDescriptionStyle(BarChartDescriptionStyle object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Conditional Bar Chart Description
     * Style</em>'. <!-- begin-user-doc --> This implementation returns null; returning a non-null result will terminate
     * the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Conditional Bar Chart Description
     *         Style</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseConditionalBarChartDescriptionStyle(ConditionalBarChartDescriptionStyle object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Pie Chart Description Style</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Pie Chart Description Style</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T casePieChartDescriptionStyle(PieChartDescriptionStyle object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Conditional Pie Chart Description
     * Style</em>'. <!-- begin-user-doc --> This implementation returns null; returning a non-null result will terminate
     * the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Conditional Pie Chart Description
     *         Style</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseConditionalPieChartDescriptionStyle(ConditionalPieChartDescriptionStyle object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Label Description</em>'. <!-- begin-user-doc
     * --> This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc
     * -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Label Description</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseLabelDescription(LabelDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Label Description Style</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Label Description Style</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseLabelDescriptionStyle(LabelDescriptionStyle object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Conditional Label Description Style</em>'.
     * <!-- begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Conditional Label Description Style</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseConditionalLabelDescriptionStyle(ConditionalLabelDescriptionStyle object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Link Description</em>'. <!-- begin-user-doc
     * --> This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc
     * -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Link Description</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseLinkDescription(LinkDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Link Description Style</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Link Description Style</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseLinkDescriptionStyle(LinkDescriptionStyle object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Conditional Link Description Style</em>'.
     * <!-- begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Conditional Link Description Style</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseConditionalLinkDescriptionStyle(ConditionalLinkDescriptionStyle object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>List Description</em>'. <!-- begin-user-doc
     * --> This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc
     * -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>List Description</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseListDescription(ListDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>List Description Style</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>List Description Style</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseListDescriptionStyle(ListDescriptionStyle object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Conditional List Description Style</em>'.
     * <!-- begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Conditional List Description Style</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseConditionalListDescriptionStyle(ConditionalListDescriptionStyle object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Diagram Palette</em>'. <!-- begin-user-doc
     * --> This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc
     * -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Diagram Palette</em>'.
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
     * Returns the result of interpreting the object as an instance of '<em>Dynamic Dialog Folder</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Dynamic Dialog Folder</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseDynamicDialogFolder(DynamicDialogFolder object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Dynamic Dialog Description</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Dynamic Dialog Description</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseDynamicDialogDescription(DynamicDialogDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>DWidget Description</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>DWidget Description</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseDWidgetDescription(DWidgetDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>DSelect Widget Description</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>DSelect Widget Description</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseDSelectWidgetDescription(DSelectWidgetDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>DText Field Widget Description</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>DText Field Widget Description</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseDTextFieldWidgetDescription(DTextFieldWidgetDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>DValidation Message Description</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>DValidation Message Description</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseDValidationMessageDescription(DValidationMessageDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>DWidget Output Description</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>DWidget Output Description</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseDWidgetOutputDescription(DWidgetOutputDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>DWidget Object Output Description</em>'.
     * <!-- begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>DWidget Object Output Description</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseDWidgetObjectOutputDescription(DWidgetObjectOutputDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>DWidget String Output Description</em>'.
     * <!-- begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>DWidget String Output Description</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseDWidgetStringOutputDescription(DWidgetStringOutputDescription object) {
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

} // ViewSwitch
