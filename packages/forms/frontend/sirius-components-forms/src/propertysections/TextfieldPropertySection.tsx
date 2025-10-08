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
import React, { FocusEvent, useEffect, useRef, useState } from 'react';
import { makeStyles } from 'tss-react/mui';
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
  TextFieldPropertySectionState,
  TextFieldState,
  TextfieldStyleProps,
} from './TextfieldPropertySection.types';

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
        backgroundColor: backgroundColor ? getCSSColor(backgroundColor, theme) : undefined,
        color: foregroundColor ? getCSSColor(foregroundColor, theme) : undefined,
        fontSize: fontSize ? fontSize : undefined,
        fontStyle: italic ? 'italic' : undefined,
        fontWeight: bold ? 'bold' : undefined,
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

  const { addErrorMessage, addMessages } = useMultiToast();
  const [state, setState] = useState<TextFieldPropertySectionState>({
    value: widget.stringValue,
    completionRequest: null,
    proposals: null,
    caretPos: 0,
  });

  const [editTextfield, { loading: updateTextfieldLoading, data: updateTextfieldData, error: updateTextfieldError }] =
    useMutation<GQLEditTextfieldMutationData, GQLEditTextfieldMutationVariables>(editTextfieldMutation);

  useEffect(() => {
    if (widget.stringValue != state.value && document.activeElement !== inputElt.current) {
      setState((prevState) => ({
        ...prevState,
        value: widget.stringValue,
      }));
    }
  }, [widget]);

  const sendEditedValue = () => {
    if (widget.stringValue != state.value) {
      const input: GQLEditTextfieldInput = {
        id: crypto.randomUUID(),
        editingContextId,
        representationId: formId,
        textfieldId: widget.id,
        newValue: state.value,
      };
      const variables: GQLEditTextfieldMutationVariables = { input };
      editTextfield({ variables });
    }
  };

  const onChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const { value } = event.target;
    setState((prevState) => ({
      ...prevState,
      value: value,
    }));
  };

  const onBlur = () => {
    sendEditedValue();
  };

  const onKeyPress: React.KeyboardEventHandler<HTMLInputElement> = (event) => {
    if (event.code === 'Enter' && !event.shiftKey) {
      event.preventDefault();
      sendEditedValue();
    }
    setState((prevState) => ({
      ...prevState,
      completionRequest: null,
      proposals: null,
    }));
  };

  useEffect(() => {
    if (!updateTextfieldLoading) {
      if (updateTextfieldError) {
        addErrorMessage(updateTextfieldError.message);
      }
      if (updateTextfieldData) {
        const { editTextfield } = updateTextfieldData;
        if (isErrorPayload(editTextfield)) {
          addMessages(editTextfield.messages);
          setState((prevState) => ({
            ...prevState,
            completionRequest: null,
            proposals: null,
            value: widget.stringValue,
          }));
        }
        if (isSuccessPayload(editTextfield)) {
          addMessages(editTextfield.messages);
        }
      }
    }
  }, [updateTextfieldLoading, updateTextfieldData, updateTextfieldError]);

  const [getCompletionProposals, { loading: proposalsLoading, data: proposalsData, error: proposalsError }] =
    useLazyQuery<GQLCompletionProposalsQueryData, GQLCompletionProposalsQueryVariables>(getCompletionProposalsQuery);

  useEffect(() => {
    if (!proposalsLoading) {
      if (proposalsError) {
        addErrorMessage(proposalsError.message);
      }
      if (proposalsData) {
        const proposals = proposalsData.viewer.editingContext.representation.description.completionProposals;
        setState((prevState) => ({
          ...prevState,
          proposals: proposals.length > 0 ? proposals : null,
        }));
      }
    }
  }, [proposalsLoading, proposalsData, proposalsError]);

  const onKeyDown: React.KeyboardEventHandler<HTMLInputElement> = (event) => {
    if (event.code === 'Escape') {
      setState((prevState) => ({
        ...prevState,
        completionRequest: null,
        proposals: null,
      }));
    } else if (widget.supportsCompletion && event.ctrlKey && event.code === 'Space') {
      const cursorPosition = (event.target as HTMLInputElement).selectionStart ?? 0;

      const variables: GQLCompletionProposalsQueryVariables = {
        editingContextId,
        formId,
        widgetId: widget.id,
        currentText: state.value,
        cursorPosition,
      };
      getCompletionProposals({ variables });
      setState((prevState) => ({
        ...prevState,
        completionRequest: { currentText: state.value, cursorPosition },
      }));
    }
  };

  useEffect(() => {
    if (state.caretPos && inputElt.current) {
      inputElt.current.setSelectionRange(state.caretPos, state.caretPos);
      inputElt.current.focus();
      setState((prevState) => ({
        ...prevState,
        caretPos: null,
      }));
    }
  }, [state.caretPos, inputElt.current]);

  const dismissProposals = () => {
    setState((prevState) => ({
      ...prevState,
      completionRequest: null,
      proposals: null,
    }));
  };

  const onProposalSelected = (proposal: GQLCompletionProposal) => {
    if (state.completionRequest) {
      const result = applyCompletionProposal(
        { textValue: state.value, cursorPosition: state.completionRequest.cursorPosition },
        proposal
      );

      setState((prevState) => ({
        ...prevState,
        completionRequest: null,
        proposals: null,
        value: result.textValue,
        caretPos: result.cursorPosition,
      }));
    }
  };

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
          value={state.value}
          spellCheck={false}
          margin="dense"
          multiline={isTextarea(widget)}
          maxRows={isTextarea(widget) ? 4 : 1}
          fullWidth
          onKeyDown={onKeyDown}
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
        {state.proposals && inputElt.current ? (
          <ProposalsList
            anchorEl={inputElt.current}
            proposals={state.proposals}
            onProposalSelected={onProposalSelected}
            onClose={dismissProposals}
          />
        ) : null}
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
