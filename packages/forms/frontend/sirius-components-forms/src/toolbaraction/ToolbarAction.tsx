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
import { GQLButton } from '../form/FormEventFragments.types';
import { HelpTooltip } from '../propertysections/HelpTooltip';
import { getTextDecorationLineValue } from './../propertysections/getTextDecorationLineValue';
import {
  GQLErrorPayload,
  GQLPushButtonInput,
  GQLPushButtonMutationData,
  GQLPushButtonMutationVariables,
  GQLPushButtonPayload,
  GQLSuccessPayload,
  ToolbarActionProps,
  ToolbarActionStyleProps,
} from './ToolbarAction.types';

const useStyle = makeStyles<Theme, ToolbarActionStyleProps>((theme) => ({
  style: {
    minWidth: '32px',
    lineHeight: 1.25,
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

const isErrorPayload = (payload: GQLPushButtonPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';

const isSuccessPayload = (payload: GQLPushButtonPayload): payload is GQLSuccessPayload =>
  payload.__typename === 'SuccessPayload';

/**
 * Defines the content of a ToolbarAction.
 * The content is submitted when pushing the associated button.
 */
export const ToolbarAction = ({ editingContextId, formId, widget, readOnly }: ToolbarActionProps) => {
  const props: ToolbarActionStyleProps = {
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

  const getImageURL = (widget: GQLButton) => {
    if (widget.imageURL.startsWith('http://') || widget.imageURL.startsWith('https://')) {
      return widget.imageURL;
    }
    return httpOrigin + widget.imageURL;
  };

  return (
    <div>
      <HelpTooltip editingContextId={editingContextId} formId={formId} widgetId={widget.id}>
        <Button
          data-testid={widget.buttonLabel}
          size="small"
          variant="contained"
          color="primary"
          onClick={onClick}
          disabled={readOnly || widget.readOnly}
          classes={{ root: classes.style }}>
          {widget.imageURL?.length > 0 ? (
            <img className={classes.icon} width="16" height="16" alt={widget.label} src={getImageURL(widget)} />
          ) : null}
          {widget.buttonLabel}
        </Button>
      </HelpTooltip>
    </div>
  );
};
