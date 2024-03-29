/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
package org.eclipse.sirius.components.view.deck.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;
import org.eclipse.sirius.components.view.Conditional;
import org.eclipse.sirius.components.view.LabelStyle;
import org.eclipse.sirius.components.view.RepresentationDescription;
import org.eclipse.sirius.components.view.deck.CardDescription;
import org.eclipse.sirius.components.view.deck.CardDropTool;
import org.eclipse.sirius.components.view.deck.ConditionalDeckDescriptionStyle;
import org.eclipse.sirius.components.view.deck.ConditionalDeckElementDescriptionStyle;
import org.eclipse.sirius.components.view.deck.CreateCardTool;
import org.eclipse.sirius.components.view.deck.DeckDescription;
import org.eclipse.sirius.components.view.deck.DeckDescriptionStyle;
import org.eclipse.sirius.components.view.deck.DeckElementDescription;
import org.eclipse.sirius.components.view.deck.DeckElementDescriptionStyle;
import org.eclipse.sirius.components.view.deck.DeckPackage;
import org.eclipse.sirius.components.view.deck.DeckTool;
import org.eclipse.sirius.components.view.deck.DeleteCardTool;
import org.eclipse.sirius.components.view.deck.EditCardTool;
import org.eclipse.sirius.components.view.deck.EditLaneTool;
import org.eclipse.sirius.components.view.deck.LaneDescription;
import org.eclipse.sirius.components.view.deck.LaneDropTool;

/**
 * <!-- begin-user-doc --> The <b>Switch</b> for the model's inheritance hierarchy. It supports the call
 * {@link #doSwitch(EObject) doSwitch(object)} to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object and proceeding up the inheritance hierarchy until a non-null result is
 * returned, which is the result of the switch. <!-- end-user-doc -->
 *
 * @see org.eclipse.sirius.components.view.deck.DeckPackage
 * @generated
 */
public class DeckSwitch<T> extends Switch<T> {
    /**
     * The cached model package <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected static DeckPackage modelPackage;

    /**
     * Creates an instance of the switch. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public DeckSwitch() {
        if (modelPackage == null) {
            modelPackage = DeckPackage.eINSTANCE;
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
            case DeckPackage.DECK_DESCRIPTION: {
                DeckDescription deckDescription = (DeckDescription) theEObject;
                T result = this.caseDeckDescription(deckDescription);
                if (result == null)
                    result = this.caseRepresentationDescription(deckDescription);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case DeckPackage.LANE_DESCRIPTION: {
                LaneDescription laneDescription = (LaneDescription) theEObject;
                T result = this.caseLaneDescription(laneDescription);
                if (result == null)
                    result = this.caseDeckElementDescription(laneDescription);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case DeckPackage.CARD_DESCRIPTION: {
                CardDescription cardDescription = (CardDescription) theEObject;
                T result = this.caseCardDescription(cardDescription);
                if (result == null)
                    result = this.caseDeckElementDescription(cardDescription);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case DeckPackage.DECK_TOOL: {
                DeckTool deckTool = (DeckTool) theEObject;
                T result = this.caseDeckTool(deckTool);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case DeckPackage.CREATE_CARD_TOOL: {
                CreateCardTool createCardTool = (CreateCardTool) theEObject;
                T result = this.caseCreateCardTool(createCardTool);
                if (result == null)
                    result = this.caseDeckTool(createCardTool);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case DeckPackage.EDIT_CARD_TOOL: {
                EditCardTool editCardTool = (EditCardTool) theEObject;
                T result = this.caseEditCardTool(editCardTool);
                if (result == null)
                    result = this.caseDeckTool(editCardTool);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case DeckPackage.DELETE_CARD_TOOL: {
                DeleteCardTool deleteCardTool = (DeleteCardTool) theEObject;
                T result = this.caseDeleteCardTool(deleteCardTool);
                if (result == null)
                    result = this.caseDeckTool(deleteCardTool);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case DeckPackage.EDIT_LANE_TOOL: {
                EditLaneTool editLaneTool = (EditLaneTool) theEObject;
                T result = this.caseEditLaneTool(editLaneTool);
                if (result == null)
                    result = this.caseDeckTool(editLaneTool);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case DeckPackage.CARD_DROP_TOOL: {
                CardDropTool cardDropTool = (CardDropTool) theEObject;
                T result = this.caseCardDropTool(cardDropTool);
                if (result == null)
                    result = this.caseDeckTool(cardDropTool);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case DeckPackage.LANE_DROP_TOOL: {
                LaneDropTool laneDropTool = (LaneDropTool) theEObject;
                T result = this.caseLaneDropTool(laneDropTool);
                if (result == null)
                    result = this.caseDeckTool(laneDropTool);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case DeckPackage.DECK_ELEMENT_DESCRIPTION: {
                DeckElementDescription deckElementDescription = (DeckElementDescription) theEObject;
                T result = this.caseDeckElementDescription(deckElementDescription);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case DeckPackage.DECK_DESCRIPTION_STYLE: {
                DeckDescriptionStyle deckDescriptionStyle = (DeckDescriptionStyle) theEObject;
                T result = this.caseDeckDescriptionStyle(deckDescriptionStyle);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case DeckPackage.CONDITIONAL_DECK_DESCRIPTION_STYLE: {
                ConditionalDeckDescriptionStyle conditionalDeckDescriptionStyle = (ConditionalDeckDescriptionStyle) theEObject;
                T result = this.caseConditionalDeckDescriptionStyle(conditionalDeckDescriptionStyle);
                if (result == null)
                    result = this.caseConditional(conditionalDeckDescriptionStyle);
                if (result == null)
                    result = this.caseDeckDescriptionStyle(conditionalDeckDescriptionStyle);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case DeckPackage.DECK_ELEMENT_DESCRIPTION_STYLE: {
                DeckElementDescriptionStyle deckElementDescriptionStyle = (DeckElementDescriptionStyle) theEObject;
                T result = this.caseDeckElementDescriptionStyle(deckElementDescriptionStyle);
                if (result == null)
                    result = this.caseLabelStyle(deckElementDescriptionStyle);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case DeckPackage.CONDITIONAL_DECK_ELEMENT_DESCRIPTION_STYLE: {
                ConditionalDeckElementDescriptionStyle conditionalDeckElementDescriptionStyle = (ConditionalDeckElementDescriptionStyle) theEObject;
                T result = this.caseConditionalDeckElementDescriptionStyle(conditionalDeckElementDescriptionStyle);
                if (result == null)
                    result = this.caseConditional(conditionalDeckElementDescriptionStyle);
                if (result == null)
                    result = this.caseDeckElementDescriptionStyle(conditionalDeckElementDescriptionStyle);
                if (result == null)
                    result = this.caseLabelStyle(conditionalDeckElementDescriptionStyle);
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
    public T caseDeckDescription(DeckDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Lane Description</em>'. <!-- begin-user-doc
     * --> This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc
     * -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Lane Description</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseLaneDescription(LaneDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Card Description</em>'. <!-- begin-user-doc
     * --> This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc
     * -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Card Description</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseCardDescription(CardDescription object) {
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
    public T caseDeckTool(DeckTool object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Create Card Tool</em>'. <!-- begin-user-doc
     * --> This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc
     * -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Create Card Tool</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseCreateCardTool(CreateCardTool object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Edit Card Tool</em>'. <!-- begin-user-doc
     * --> This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc
     * -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Edit Card Tool</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseEditCardTool(EditCardTool object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Delete Card Tool</em>'. <!-- begin-user-doc
     * --> This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc
     * -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Delete Card Tool</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseDeleteCardTool(DeleteCardTool object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Edit Lane Tool</em>'. <!-- begin-user-doc
     * --> This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc
     * -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Edit Lane Tool</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseEditLaneTool(EditLaneTool object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Card Drop Tool</em>'. <!-- begin-user-doc
     * --> This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc
     * -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Card Drop Tool</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseCardDropTool(CardDropTool object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Lane Drop Tool</em>'. <!-- begin-user-doc
     * --> This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc
     * -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Lane Drop Tool</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseLaneDropTool(LaneDropTool object) {
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
    public T caseDeckElementDescription(DeckElementDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Description Style</em>'. <!-- begin-user-doc
     * --> This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc
     * -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Description Style</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseDeckDescriptionStyle(DeckDescriptionStyle object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Conditional Deck Description Style</em>'.
     * <!-- begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Conditional Deck Description Style</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseConditionalDeckDescriptionStyle(ConditionalDeckDescriptionStyle object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Element Description Style</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Element Description Style</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseDeckElementDescriptionStyle(DeckElementDescriptionStyle object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Conditional Deck Element Description
     * Style</em>'. <!-- begin-user-doc --> This implementation returns null; returning a non-null result will terminate
     * the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Conditional Deck Element Description
     *         Style</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseConditionalDeckElementDescriptionStyle(ConditionalDeckElementDescriptionStyle object) {
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

} // DeckSwitch
