/*******************************************************************************
 * Copyright (c) 2022, 2024 Obeo.
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
import { useMutation } from '@apollo/client';
import { ServerContext, ServerContextValue, getCSSColor, useMultiToast } from '@eclipse-sirius/sirius-components-core';
import Button from '@material-ui/core/Button';
import { Theme, makeStyles } from '@material-ui/core/styles';
import gql from 'graphql-tag';
import { useContext, useEffect } from 'react';
import { PropertySectionComponent, PropertySectionComponentProps } from '../form/Form.types';
import { GQLButton } from '../form/FormEventFragments.types';
import {
  ButtonStyleProps,
  GQLErrorPayload,
  GQLPushButtonInput,
  GQLPushButtonMutationData,
  GQLPushButtonMutationVariables,
  GQLPushButtonPayload,
  GQLSuccessPayload,
  GQLUpdateWidgetFocusInput,
  GQLUpdateWidgetFocusMutationData,
  GQLUpdateWidgetFocusMutationVariables,
  GQLUpdateWidgetFocusPayload,
} from './ButtonPropertySection.types';
import { PropertySectionLabel } from './PropertySectionLabel';
import { getTextDecorationLineValue } from './getTextDecorationLineValue';

const useStyle = makeStyles<Theme, ButtonStyleProps>((theme) => ({
  style: {
    backgroundColor: ({ backgroundColor }) =>
      backgroundColor ? getCSSColor(backgroundColor, theme) : theme.palette.primary.light,
    color: ({ foregroundColor }) => (foregroundColor ? getCSSColor(foregroundColor, theme) : 'white'),
    fontSize: ({ fontSize }) => (fontSize ? fontSize : null),
    fontStyle: ({ italic }) => (italic ? 'italic' : null),
    fontWeight: ({ bold }) => (bold ? 'bold' : null),
    textDecorationLine: ({ underline, strikeThrough }) => getTextDecorationLineValue(underline, strikeThrough),
    '&:hover': {
      backgroundColor: ({ backgroundColor }) =>
        backgroundColor ? getCSSColor(backgroundColor, theme) : theme.palette.primary.main,
      color: ({ foregroundColor }) => (foregroundColor ? getCSSColor(foregroundColor, theme) : 'white'),
      fontSize: ({ fontSize }) => (fontSize ? fontSize : null),
      fontStyle: ({ italic }) => (italic ? 'italic' : null),
      fontWeight: ({ bold }) => (bold ? 'bold' : null),
      textDecorationLine: ({ underline, strikeThrough }) => getTextDecorationLineValue(underline, strikeThrough),
    },
  },
  icon: {
    marginRight: ({ iconOnly }) => (iconOnly ? theme.spacing(0) : theme.spacing(2)),
  },
}));

export const pushButtonMutation = gql`
  mutation pushButton($input: PushButtonInput!) {
    pushButton(input: $input) {
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

export const updateWidgetFocusMutation = gql`
  mutation updateWidgetFocus($input: UpdateWidgetFocusInput!) {
    updateWidgetFocus(input: $input) {
      __typename
      ... on ErrorPayload {
        messages {
          body
          level
        }
      }
    }
  }
`;

const isErrorPayload = (payload: GQLPushButtonPayload | GQLUpdateWidgetFocusPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';

const isSuccessPayload = (payload: GQLPushButtonPayload | GQLUpdateWidgetFocusPayload): payload is GQLSuccessPayload =>
  payload.__typename === 'SuccessPayload';

/**
 * Defines the content of a Button property section.
 * The content is submitted when pushing the button.
 */
export const ButtonPropertySection: PropertySectionComponent<GQLButton> = ({
  editingContextId,
  formId,
  widget,
  subscribers,
  readOnly,
}: PropertySectionComponentProps<GQLButton>) => {
  const props: ButtonStyleProps = {
    backgroundColor: widget.style?.backgroundColor ?? null,
    foregroundColor: widget.style?.foregroundColor ?? null,
    fontSize: widget.style?.fontSize ?? null,
    italic: widget.style?.italic ?? null,
    bold: widget.style?.bold ?? null,
    underline: widget.style?.underline ?? null,
    strikeThrough: widget.style?.strikeThrough ?? null,
    iconOnly: widget.buttonLabel ? false : true,
  };
  const classes = useStyle(props);

  const { httpOrigin } = useContext<ServerContextValue>(ServerContext);

  const [pushButton, { loading, data, error }] = useMutation<GQLPushButtonMutationData, GQLPushButtonMutationVariables>(
    pushButtonMutation
  );

  const { addErrorMessage, addMessages } = useMultiToast();

  useEffect(() => {
    if (!loading) {
      if (error) {
        addErrorMessage('An unexpected error has occurred, please refresh the page');
      }
      if (data) {
        const { pushButton } = data;
        if (isErrorPayload(pushButton) || isSuccessPayload(pushButton)) {
          addMessages(pushButton.messages);
        }
      }
    }
  }, [loading, error, data]);

  const [
    updateWidgetFocus,
    { loading: updateWidgetFocusLoading, data: updateWidgetFocusData, error: updateWidgetFocusError },
  ] = useMutation<GQLUpdateWidgetFocusMutationData, GQLUpdateWidgetFocusMutationVariables>(updateWidgetFocusMutation);

  const sendUpdateWidgetFocus = (selected: boolean) => {
    const input: GQLUpdateWidgetFocusInput = {
      id: crypto.randomUUID(),
      editingContextId,
      representationId: formId,
      widgetId: widget.id,
      selected,
    };
    const variables: GQLUpdateWidgetFocusMutationVariables = {
      input,
    };
    updateWidgetFocus({ variables });
  };

  useEffect(() => {
    if (!updateWidgetFocusLoading) {
      if (updateWidgetFocusError) {
        addErrorMessage('An unexpected error has occurred, please refresh the page');
      }
      if (updateWidgetFocusData) {
        const { updateWidgetFocus } = updateWidgetFocusData;
        if (isErrorPayload(updateWidgetFocus)) {
          addMessages(updateWidgetFocus.messages);
        }
      }
    }
  }, [updateWidgetFocusLoading, updateWidgetFocusData, updateWidgetFocusError]);

  const onFocus = () => sendUpdateWidgetFocus(true);
  const onBlur = () => {
    sendUpdateWidgetFocus(false);
  };
  const onClick = () => {
    const input: GQLPushButtonInput = {
      id: crypto.randomUUID(),
      editingContextId,
      representationId: formId,
      buttonId: widget.id,
    };
    const variables: GQLPushButtonMutationVariables = { input };
    pushButton({ variables });
  };

  return (
    <div>
      <PropertySectionLabel
        editingContextId={editingContextId}
        formId={formId}
        widget={widget}
        subscribers={subscribers}
        data-testid={widget.label}
      />
      <Button
        data-testid={widget.buttonLabel}
        variant="contained"
        color="primary"
        onClick={onClick}
        onBlur={onBlur}
        onFocus={onFocus}
        disabled={readOnly || widget.readOnly}
        classes={{ root: classes.style }}>
        {widget.imageURL?.length > 0 ? (
          <img className={classes.icon} width="16" height="16" alt={widget.label} src={httpOrigin + widget.imageURL} />
        ) : null}
        {widget.buttonLabel}
      </Button>
    </div>
  );
};
