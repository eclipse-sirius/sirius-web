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
import org.eclipse.sirius.components.view.BorderStyle;
import org.eclipse.sirius.components.view.ChangeContext;
import org.eclipse.sirius.components.view.Conditional;
import org.eclipse.sirius.components.view.ConditionalEdgeStyle;
import org.eclipse.sirius.components.view.ConditionalNodeStyle;
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
import org.eclipse.sirius.components.view.FormDescription;
import org.eclipse.sirius.components.view.LabelEditTool;
import org.eclipse.sirius.components.view.LabelStyle;
import org.eclipse.sirius.components.view.NodeDescription;
import org.eclipse.sirius.components.view.NodeStyle;
import org.eclipse.sirius.components.view.NodeTool;
import org.eclipse.sirius.components.view.Operation;
import org.eclipse.sirius.components.view.RepresentationDescription;
import org.eclipse.sirius.components.view.SetValue;
import org.eclipse.sirius.components.view.Style;
import org.eclipse.sirius.components.view.TextfieldDescription;
import org.eclipse.sirius.components.view.Tool;
import org.eclipse.sirius.components.view.UnsetValue;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.ViewPackage;
import org.eclipse.sirius.components.view.WidgetDescription;

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
