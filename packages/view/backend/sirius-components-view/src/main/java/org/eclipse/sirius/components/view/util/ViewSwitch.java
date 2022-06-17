/*******************************************************************************
 * Copyright (c) 2021, 2022 Obeo.
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
import org.eclipse.sirius.components.view.Conditional;
import org.eclipse.sirius.components.view.ConditionalBarChartDescriptionStyle;
import org.eclipse.sirius.components.view.ConditionalButtonDescriptionStyle;
import org.eclipse.sirius.components.view.ConditionalCheckboxDescriptionStyle;
import org.eclipse.sirius.components.view.ConditionalEdgeStyle;
import org.eclipse.sirius.components.view.ConditionalLabelDescriptionStyle;
import org.eclipse.sirius.components.view.ConditionalMultiSelectDescriptionStyle;
import org.eclipse.sirius.components.view.ConditionalNodeStyle;
import org.eclipse.sirius.components.view.ConditionalPieChartDescriptionStyle;
import org.eclipse.sirius.components.view.ConditionalRadioDescriptionStyle;
import org.eclipse.sirius.components.view.ConditionalSelectDescriptionStyle;
import org.eclipse.sirius.components.view.ConditionalTextareaDescriptionStyle;
import org.eclipse.sirius.components.view.ConditionalTextfieldDescriptionStyle;
import org.eclipse.sirius.components.view.CreateInstance;
import org.eclipse.sirius.components.view.CreateView;
import org.eclipse.sirius.components.view.DeleteElement;
import org.eclipse.sirius.components.view.DeleteTool;
import org.eclipse.sirius.components.view.DeleteView;
import org.eclipse.sirius.components.view.DiagramDescription;
import org.eclipse.sirius.components.view.DiagramElementDescription;
import org.eclipse.sirius.components.view.DropTool;
import org.eclipse.sirius.components.view.EdgeDescription;
import org.eclipse.sirius.components.view.EdgeStyle;
import org.eclipse.sirius.components.view.EdgeTool;
import org.eclipse.sirius.components.view.FlexboxContainerDescription;
import org.eclipse.sirius.components.view.FormDescription;
import org.eclipse.sirius.components.view.LabelDescription;
import org.eclipse.sirius.components.view.LabelDescriptionStyle;
import org.eclipse.sirius.components.view.LabelEditTool;
import org.eclipse.sirius.components.view.LabelStyle;
import org.eclipse.sirius.components.view.MultiSelectDescription;
import org.eclipse.sirius.components.view.MultiSelectDescriptionStyle;
import org.eclipse.sirius.components.view.NodeDescription;
import org.eclipse.sirius.components.view.NodeStyle;
import org.eclipse.sirius.components.view.NodeTool;
import org.eclipse.sirius.components.view.Operation;
import org.eclipse.sirius.components.view.PieChartDescription;
import org.eclipse.sirius.components.view.PieChartDescriptionStyle;
import org.eclipse.sirius.components.view.RadioDescription;
import org.eclipse.sirius.components.view.RadioDescriptionStyle;
import org.eclipse.sirius.components.view.RepresentationDescription;
import org.eclipse.sirius.components.view.SelectDescription;
import org.eclipse.sirius.components.view.SelectDescriptionStyle;
import org.eclipse.sirius.components.view.SetValue;
import org.eclipse.sirius.components.view.Style;
import org.eclipse.sirius.components.view.TextAreaDescription;
import org.eclipse.sirius.components.view.TextareaDescriptionStyle;
import org.eclipse.sirius.components.view.TextfieldDescription;
import org.eclipse.sirius.components.view.TextfieldDescriptionStyle;
import org.eclipse.sirius.components.view.Tool;
import org.eclipse.sirius.components.view.UnsetValue;
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
        case ViewPackage.NODE_STYLE: {
            NodeStyle nodeStyle = (NodeStyle) theEObject;
            T result = this.caseNodeStyle(nodeStyle);
            if (result == null)
                result = this.caseStyle(nodeStyle);
            if (result == null)
                result = this.caseLabelStyle(nodeStyle);
            if (result == null)
                result = this.caseBorderStyle(nodeStyle);
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
                result = this.caseNodeStyle(conditionalNodeStyle);
            if (result == null)
                result = this.caseStyle(conditionalNodeStyle);
            if (result == null)
                result = this.caseLabelStyle(conditionalNodeStyle);
            if (result == null)
                result = this.caseBorderStyle(conditionalNodeStyle);
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
     * Returns the result of interpreting the object as an instance of '<em>Node Style</em>'. <!-- begin-user-doc -->
     * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Node Style</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseNodeStyle(NodeStyle object) {
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
     * Returns the result of interpreting the object as an instance of '<em>Bar Chart Description</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Bar Chart Description</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
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
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
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
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
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
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseButtonDescription(ButtonDescription object) {
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
