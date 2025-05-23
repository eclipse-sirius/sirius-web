/*******************************************************************************
 * Copyright (c) 2019, 2025 Obeo.
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
import { gql, useLazyQuery, useMutation } from '@apollo/client';
import { getCSSColor, useMultiToast } from '@eclipse-sirius/sirius-components-core';
import TextField from '@mui/material/TextField';
import { useMachine } from '@xstate/react';
import React, { FocusEvent, useEffect, useRef, useState } from 'react';
import { makeStyles } from 'tss-react/mui';
import { StateMachine } from 'xstate';
import { PropertySectionComponent, PropertySectionComponentProps } from '../form/Form.types';
import { GQLTextarea, GQLTextfield, GQLWidget } from '../form/FormEventFragments.types';
import { getTextDecorationLineValue } from './getTextDecorationLineValue';
import { GQLSuccessPayload } from './ListPropertySection.types';
import { LoadingIndicator } from './LoadingIndicator';
import { PropertySectionLabel } from './PropertySectionLabel';
import { ProposalsList } from './ProposalsList';
import {
  GQLCompletionProposal,
  GQLCompletionProposalsQueryData,
  GQLCompletionProposalsQueryVariables,
  GQLEditTextfieldInput,
  GQLEditTextfieldMutationData,
  GQLEditTextfieldMutationVariables,
  GQLEditTextfieldPayload,
  GQLErrorPayload,
  TextFieldState,
  TextfieldStyleProps,
} from './TextfieldPropertySection.types';
import {
  ChangeValueEvent,
  CompletionDismissedEvent,
  CompletionReceivedEvent,
  InitializeEvent,
  NewValueSentEvent,
  RequestCompletionEvent,
  SchemaValue,
  TextfieldPropertySectionContext,
  TextfieldPropertySectionEvent,
  textfieldPropertySectionMachine,
  TextfieldPropertySectionStateSchema,
} from './TextfieldPropertySectionMachine';

const useStyle = makeStyles<TextfieldStyleProps>()(
  (theme, { backgroundColor, foregroundColor, fontSize, italic, bold, underline, strikeThrough, gridLayout }) => {
    const {
      gridTemplateColumns,
      gridTemplateRows,
      labelGridColumn,
      labelGridRow,
      widgetGridColumn,
      widgetGridRow,
      gap,
    } = {
      ...gridLayout,
    };
    return {
      style: {
        backgroundColor: backgroundColor ? getCSSColor(backgroundColor, theme) : null,
        color: foregroundColor ? getCSSColor(foregroundColor, theme) : null,
        fontSize: fontSize ? fontSize : null,
        fontStyle: italic ? 'italic' : null,
        fontWeight: bold ? 'bold' : null,
        textDecorationLine: getTextDecorationLineValue(underline, strikeThrough),
      },
      input: {
        paddingTop: theme.spacing(0.5),
        paddingBottom: theme.spacing(0.5),
      },
      textfield: {
        marginTop: theme.spacing(0.5),
        marginBottom: theme.spacing(0.5),
      },
      formControl: {},
      propertySection: {
        display: 'grid',
        gridTemplateColumns,
        gridTemplateRows,
        alignItems: 'center',
        gap: gap ?? '',
      },
      propertySectionLabel: {
        gridColumn: labelGridColumn,
        gridRow: labelGridRow,
        display: 'flex',
        flexDirection: 'row',
        gap: theme.spacing(2),
        alignItems: 'center',
      },
      propertySectionWidget: {
        gridColumn: widgetGridColumn,
        gridRow: widgetGridRow,
      },
    };
  }
);

export const getCompletionProposalsQuery = gql`
  query completionProposals(
    $editingContextId: ID!
    $formId: ID!
    $widgetId: ID!
    $currentText: String!
    $cursorPosition: Int!
  ) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        representation(representationId: $formId) {
          description {
            ... on FormDescription {
              completionProposals(widgetId: $widgetId, currentText: $currentText, cursorPosition: $cursorPosition) {
                description
                textToInsert
                charsToReplace
              }
            }
          }
        }
      }
    }
  }
`;

export const editTextfieldMutation = gql`
  mutation editTextfield($input: EditTextfieldInput!) {
    editTextfield(input: $input) {
      __typename
      ... on ErrorPayload {
        messages {
          body
          level
        }
      }
      ... on SuccessPayload {
        messages {
          body
          level
        }
      }
    }
  }
`;

const isTextarea = (widget: GQLWidget): widget is GQLTextarea => widget.__typename === 'Textarea';
const isErrorPayload = (payload: GQLEditTextfieldPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';
const isSuccessPayload = (payload: GQLEditTextfieldPayload): payload is GQLSuccessPayload =>
  payload.__typename === 'SuccessPayload';

/**
 * Defines the content of a Textfield property section.
 * The content is submitted when the focus is lost and when pressing the "Enter" key.
 */
export const TextfieldPropertySection: PropertySectionComponent<GQLTextfield | GQLTextarea> = ({
  editingContextId,
  formId,
  widget,
  readOnly,
}: PropertySectionComponentProps<GQLTextfield | GQLTextarea>) => {
  const inputElt = useRef<HTMLInputElement>();

  const props: TextfieldStyleProps = {
    backgroundColor: widget.style?.backgroundColor ?? null,
    foregroundColor: widget.style?.foregroundColor ?? null,
    fontSize: widget.style?.fontSize ?? null,
    italic: widget.style?.italic ?? null,
    bold: widget.style?.bold ?? null,
    underline: widget.style?.underline ?? null,
    strikeThrough: widget.style?.strikeThrough ?? null,
    gridLayout: widget.style?.widgetGridLayout ?? null,
  };
  const { classes } = useStyle(props);

  const [{ value: schemaValue, context }, dispatch] = useMachine<
    StateMachine<TextfieldPropertySectionContext, TextfieldPropertySectionStateSchema, TextfieldPropertySectionEvent>
  >(textfieldPropertySectionMachine);
  const { textfieldPropertySection } = schemaValue as SchemaValue;
  const { value, completionRequest, proposals } = context;

  useEffect(() => {
    const initializeEvent: InitializeEvent = { type: 'INITIALIZE', value: widget.stringValue };
    dispatch(initializeEvent);
  }, [dispatch, widget.stringValue]);

  const onChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const { value } = event.target;
    const changeValueEvent: ChangeValueEvent = { type: 'CHANGE_VALUE', value };
    dispatch(changeValueEvent);
  };

  const [editTextfield, { loading: updateTextfieldLoading, data: updateTextfieldData, error: updateTextfieldError }] =
    useMutation<GQLEditTextfieldMutationData, GQLEditTextfieldMutationVariables>(editTextfieldMutation);
  const sendEditedValue = () => {
    if (textfieldPropertySection === 'edited') {
      const input: GQLEditTextfieldInput = {
        id: crypto.randomUUID(),
        editingContextId,
        representationId: formId,
        textfieldId: widget.id,
        newValue: value,
      };
      const variables: GQLEditTextfieldMutationVariables = { input };
      editTextfield({ variables });
    }
  };

  const { addErrorMessage, addMessages } = useMultiToast();

  useEffect(() => {
    if (!updateTextfieldLoading) {
      let hasError = false;
      if (updateTextfieldError) {
        addErrorMessage('An unexpected error has occurred, please refresh the page');

        hasError = true;
      }
      if (updateTextfieldData) {
        const { editTextfield } = updateTextfieldData;
        if (isErrorPayload(editTextfield)) {
          addMessages(editTextfield.messages);
          hasError = true;
        }
        if (isSuccessPayload(editTextfield)) {
          addMessages(editTextfield.messages);
        }
      }

      if (hasError) {
        const initializeEvent: InitializeEvent = { type: 'INITIALIZE', value: widget.stringValue };
        dispatch(initializeEvent);
      } else {
        const event: NewValueSentEvent = { type: 'NEW_VALUE_SENT' };
        dispatch(event);
      }
    }
  }, [updateTextfieldLoading, updateTextfieldData, updateTextfieldError, dispatch]);

  useEffect(() => {
    if (textfieldPropertySection === 'sent') {
      const initializeEvent: InitializeEvent = { type: 'INITIALIZE', value: widget.stringValue };
      dispatch(initializeEvent);
    }
  }, [widget.stringValue, textfieldPropertySection]);

  const onBlur = () => {
    sendEditedValue();
  };

  const [getCompletionProposals, { loading: proposalsLoading, data: proposalsData, error: proposalsError }] =
    useLazyQuery<GQLCompletionProposalsQueryData, GQLCompletionProposalsQueryVariables>(getCompletionProposalsQuery);
  useEffect(() => {
    if (!proposalsLoading) {
      if (proposalsError) {
        addErrorMessage(proposalsError.message);
      }
      if (proposalsData) {
        const proposalsReceivedEvent: CompletionReceivedEvent = {
          type: 'COMPLETION_RECEIVED',
          proposals: proposalsData.viewer.editingContext.representation.description.completionProposals,
        };
        dispatch(proposalsReceivedEvent);
      }
    }
  }, [proposalsLoading, proposalsData, proposalsError, dispatch]);

  const onKeyPress: React.KeyboardEventHandler<HTMLInputElement> = (event) => {
    if ('Enter' === event.key && !event.shiftKey) {
      event.preventDefault();
      sendEditedValue();
    }
    const dismissCompletionEvent: CompletionDismissedEvent = { type: 'COMPLETION_DISMISSED' };
    dispatch(dismissCompletionEvent);
  };

  // Reacting to Ctrl-Space to trigger completion can not be done with onKeyPress.
  // We need a stateful combination of onKeyDown/onKeyUp for that.
  const [controlDown, setControlDown] = useState<boolean>(false);

  const onKeyDown: React.KeyboardEventHandler<HTMLInputElement> = (event) => {
    if (event.key === 'ArrowLeft' || event.key === 'ArrowRight') {
      const proposalsMenu = document.getElementById('completion-proposals');
      if (proposalsMenu && proposalsMenu.firstChild) {
        (proposalsMenu.firstChild as HTMLElement).focus();
      }
    } else if ('Control' === event.key) {
      setControlDown(true);
    } else if ('Escape' === event.key) {
      const dismissCompletionEvent: CompletionDismissedEvent = { type: 'COMPLETION_DISMISSED' };
      dispatch(dismissCompletionEvent);
    }
    if (widget.supportsCompletion && controlDown && event.key === ' ') {
      const cursorPosition = (event.target as HTMLInputElement).selectionStart;
      const variables: GQLCompletionProposalsQueryVariables = {
        editingContextId,
        formId,
        widgetId: widget.id,
        currentText: value,
        cursorPosition,
      };
      getCompletionProposals({ variables });
      const requestCompletionEvent: RequestCompletionEvent = {
        type: 'COMPLETION_REQUESTED',
        currentText: value,
        cursorPosition,
      };
      dispatch(requestCompletionEvent);
    }
  };
  const onKeyUp: React.KeyboardEventHandler<HTMLInputElement> = (event) => {
    if ('Control' === event.key) {
      setControlDown(false);
    }
  };

  const [caretPos, setCaretPos] = useState<number | null>(null);
  useEffect(() => {
    if (caretPos && inputElt.current) {
      inputElt.current.setSelectionRange(caretPos, caretPos);
      inputElt.current.focus();
      setCaretPos(null);
    }
  }, [caretPos, inputElt.current]);

  let proposalsList = null;
  if (proposals) {
    const dismissProposals = () => {
      const dismissCompletionEvent: CompletionDismissedEvent = { type: 'COMPLETION_DISMISSED' };
      dispatch(dismissCompletionEvent);
    };
    const applyProposal = (proposal: GQLCompletionProposal) => {
      const result = applyCompletionProposal(
        { textValue: value, cursorPosition: completionRequest.cursorPosition },
        proposal
      );
      const changeValueEvent: ChangeValueEvent = { type: 'CHANGE_VALUE', value: result.textValue };
      dispatch(changeValueEvent);
      setCaretPos(result.cursorPosition);
      dismissProposals();
    };
    proposalsList = (
      <ProposalsList
        anchorEl={inputElt.current}
        proposals={proposals}
        onProposalSelected={applyProposal}
        onClose={dismissProposals}
      />
    );
  }
  return (
    <div
      onBlur={(event: FocusEvent<HTMLDivElement, Element>) => {
        if (!event.currentTarget.contains(event.relatedTarget)) {
          onBlur();
        }
      }}
      className={classes.propertySection}>
      <div className={classes.propertySectionLabel}>
        <PropertySectionLabel editingContextId={editingContextId} formId={formId} widget={widget} />
        <LoadingIndicator loading={updateTextfieldLoading} />
      </div>
      <div className={classes.propertySectionWidget}>
        <TextField
          name={widget.label}
          placeholder={widget.label}
          variant="standard"
          value={value}
          spellCheck={false}
          margin="dense"
          multiline={isTextarea(widget)}
          maxRows={isTextarea(widget) ? 4 : 1}
          fullWidth
          onKeyDown={onKeyDown}
          onKeyUp={onKeyUp}
          onChange={onChange}
          onKeyPress={onKeyPress}
          data-testid={widget.label}
          disabled={readOnly || widget.readOnly}
          error={widget.diagnostics.length > 0}
          helperText={widget.diagnostics[0]?.message}
          inputRef={inputElt}
          className={classes.textfield}
          InputProps={
            widget.style
              ? {
                  className: classes.style,
                }
              : {}
          }
          inputProps={{
            'data-testid': `input-${widget.label}`,
            className: classes.input,
          }}
        />
        {proposalsList}
      </div>
    </div>
  );
};

// Proposal handling (exported for testing)
export const applyCompletionProposal = (
  initialState: TextFieldState,
  proposal: GQLCompletionProposal
): TextFieldState => {
  const prefix = initialState.textValue.substring(0, initialState.cursorPosition);
  const inserted = proposal.textToInsert.substring(proposal.charsToReplace);
  const suffix = initialState.textValue.substring(initialState.cursorPosition);
  const newValue = prefix + inserted + suffix;
  return { textValue: newValue, cursorPosition: (prefix + inserted).length };
};
